package com.afiq.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.afiq.myapplication.databinding.ActivitySplashBinding;
import com.afiq.myapplication.models.ProfileModel;
import com.afiq.myapplication.utilities.FirebaseHelper;
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.viewmodels.ProfileViewModel;
import com.google.firebase.auth.FirebaseAuth;

import static com.afiq.myapplication.utilities.Interaction.EXTRA_BOOLEAN_PROFILE_EXIST;

public class SplashActivity extends AppCompatActivity implements Observer<ProfileModel> {

    private ActivitySplashBinding binding;
    private ProfileViewModel profileViewModel;

    private ProgressDialog dialog;

    private FirebaseAuth auth = FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        profileViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkUser();
    }

    @Override
    public void onChanged(ProfileModel data) {
        dialog.dismiss();

        Intent profileIntent = new Intent(this, ProfileActivity.class);
        profileIntent.putExtra(EXTRA_BOOLEAN_PROFILE_EXIST, (data != null));

        if (data == null) Interaction.nextEnd(this, profileIntent);
        else userPrivillege(data);
    }

    public void onClickSignIn(View view) {
        Interaction.next(this, LoginActivity.class);
    }

    public void onClickSignUp(View view) {
        Interaction.next(this, RegisterActivity.class);
    }

    private void animate() {
        binding.title.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_top));
        binding.subtitle.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_top));
        binding.container.setAnimation(AnimationUtils.loadAnimation(this, R.anim.in_from_bottom));
    }

    private void checkUser() {
        if (auth.getCurrentUser() != null) {
            dialog = new ProgressDialog(this);
            dialog.setMessage("Checking the profile...");
            dialog.setCancelable(false);
            dialog.show();

            profileViewModel.getData(FirebaseHelper.getUserProfile()).observe(this, this);
        } else animate();
    }

    private void userPrivillege(ProfileModel data) {
        Intent mainIntent = new Intent(this, MainActivity.class);
        Intent mainAdminIntent = new Intent(this, MainAdminActivity.class);

        if (data.getStaff()) Interaction.nextEnd(this, mainAdminIntent);
        else Interaction.nextEnd(this, mainIntent);
    }
}
