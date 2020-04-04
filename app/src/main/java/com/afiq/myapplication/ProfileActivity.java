package com.afiq.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.afiq.myapplication.utilities.Interaction;

public class ProfileActivity extends AppCompatActivity {

    private boolean project_exist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        project_exist = getIntent().getBooleanExtra(Interaction.EXTRA_BOOLEAN_PROJECT_EXIST, true);

        setupActionBar();
    }

    private void setupActionBar() {
        setTitle("Update Profile");

        if (!project_exist && getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }
}
