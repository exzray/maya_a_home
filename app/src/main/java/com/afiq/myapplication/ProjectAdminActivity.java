package com.afiq.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.afiq.myapplication.databinding.ActivityProjectAdminBinding;

public class ProjectAdminActivity extends AppCompatActivity {

    private ActivityProjectAdminBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize binding
        binding = ActivityProjectAdminBinding.inflate(getLayoutInflater());

        // activity setup
        setContentView(binding.getRoot());
        setTitle("Project Form");
    }
}
