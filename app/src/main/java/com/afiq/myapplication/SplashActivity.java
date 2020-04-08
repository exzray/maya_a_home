package com.afiq.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.afiq.myapplication.databinding.ActivitySplashBinding;
import com.afiq.myapplication.models.ProfileModel;
import com.afiq.myapplication.services.UserService;
import com.afiq.myapplication.utilities.FirebaseHelper;
import com.afiq.myapplication.utilities.Interaction;
import com.google.firebase.auth.FirebaseUser;
import com.roger.catloadinglibrary.CatLoadingView;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private CatLoadingView dialog;

    private Intent intent;
    private UserService service;
    private SplashServiceConnection serviceConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        intent = new Intent(this, UserService.class);
        serviceConnection = new SplashServiceConnection();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (service != null) unbindService(serviceConnection);
    }

    @Override
    protected void onStart() {
        super.onStart();

        checkUser();
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
        if (FirebaseHelper.getAuth().getCurrentUser() != null) {
            dialog = new CatLoadingView();
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(), "");

            savedUser(FirebaseHelper.getUser());

            startService(intent);
            bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);

        } else animate();
    }

    private void savedUser(FirebaseUser user) {
        SharedPreferences.Editor editor = getSharedPreferences(Interaction.APPLICATION_NAME, Context.MODE_PRIVATE).edit();
        editor.putString(Interaction.SHARED_SAVED_USER_EMAIL, user.getEmail());
        editor.apply();
    }

    private void executeAuthType(ProfileModel data) {
        Intent intent;

        if (data != null) {
            if (data.getStaff()) intent = new Intent(this, MainAdminActivity.class);
            else intent = new Intent(this, MainActivity.class);
        } else {
            intent = new Intent(this, ProfileActivity.class);
            intent.putExtra(Interaction.EXTRA_BOOLEAN_PROFILE_EXIST, false);
        }

        Interaction.nextEnd(this, intent);
    }


    private class SplashServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder ibinder) {
            UserService.UserBinder binder = (UserService.UserBinder) ibinder;
            service = binder.getService();
            service.getProfile().observe(SplashActivity.this, data -> {

                executeAuthType(data);

                dialog.dismiss();
                finish();
            });
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service = null;
        }
    }
}
