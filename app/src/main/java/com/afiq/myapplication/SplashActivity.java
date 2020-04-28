package com.afiq.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.afiq.myapplication.databinding.ActivitySplashBinding;
import com.afiq.myapplication.models.ProfileModel;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.viewmodels.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.roger.catloadinglibrary.CatLoadingView;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private CatLoadingView dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (auth.getCurrentUser() != null) {
            String user_id = auth.getCurrentUser().getUid();
            DocumentReference ref = Database.DOC_PROFILE(user_id);

            ProfileViewModel vm = new ViewModelProvider(this).get(ProfileViewModel.class);
            vm.getData().observe(this, this::profileListener);
            vm.start(ref);

            showLoading();

        } else {
            animate();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

        ProfileViewModel vm = new ViewModelProvider(this).get(ProfileViewModel.class);
        vm.getData().removeObservers(this);
    }

    public void onClickSignIn(View view) {
        Interaction.next(this, LoginActivity.class);
    }

    public void onClickSignUp(View view) {
        Interaction.next(this, RegisterActivity.class);
    }

    private void profileListener(ProfileModel data) {
        if (data.getStaff()) {
            Intent intent = new Intent(this, MainAdminActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

            startActivity(intent);
        }

        dialog.dismiss();
    }

    private void animate() {
        binding.title.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_top));
        binding.subtitle.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_top));
        binding.container.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_bottom));
    }

    private void showLoading() {
        dialog = new CatLoadingView();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(), "");

        savedUser(Database.getUser());
    }

    private void savedUser(FirebaseUser user) {
        SharedPreferences.Editor editor = getSharedPreferences(Interaction.APPLICATION_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(Interaction.SHARED_SAVED_USER_EMAIL, user.getEmail());
        editor.apply();
    }
}
