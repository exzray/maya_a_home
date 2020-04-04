package com.afiq.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;

import androidx.appcompat.app.AppCompatActivity;

import com.afiq.myapplication.databinding.ActivitySplashBinding;
import com.afiq.myapplication.utilities.Interaction;
import com.google.firebase.auth.FirebaseAuth;

public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;

    private FirebaseAuth auth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (getSupportActionBar() != null) getSupportActionBar().hide();

        animate();
    }

    @Override
    protected void onStart() {
        super.onStart();

        animate();
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
        if (auth.getCurrentUser() != null) Interaction.nextEnd(this, MainActivity.class);
    }
}
