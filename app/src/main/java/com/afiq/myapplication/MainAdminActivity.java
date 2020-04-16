package com.afiq.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.afiq.myapplication.fragment_adapters.AdminMainAdapter;
import com.afiq.myapplication.databinding.ActivityMainAdminBinding;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;

public class MainAdminActivity extends AppCompatActivity {

    private ActivityMainAdminBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Maya A Home Admin");
        setupNavigation();
    }

    private void setupNavigation() {
        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.option_admin_navigation);
        navigationAdapter.setupWithBottomNavigation(binding.navigation);

        binding.pager.setAdapter(new AdminMainAdapter(getSupportFragmentManager()));
        binding.navigation.setAccentColor(ContextCompat.getColor(this, R.color.colorPrimary));
        binding.navigation.setOnTabSelectedListener(this::tabSelectedListener);
    }

    private boolean tabSelectedListener(int position, boolean wasSelected) {
        binding.pager.setCurrentItem(position, true);
        return true;
    }
}
