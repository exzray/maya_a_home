package com.afiq.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.afiq.myapplication.databinding.ActivityMainAdminBinding;
import com.afiq.myapplication.fragment_adapters.MainAdminPagerAdapter;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
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
        AHBottomNavigationAdapter navigationAdapter = new AHBottomNavigationAdapter(this, R.menu.option_admin_bottom_navigation);
        navigationAdapter.setupWithBottomNavigation(binding.navigation);

        binding.pager.setAdapter(new MainAdminPagerAdapter(getSupportFragmentManager()));
        binding.navigation.setAccentColor(ContextCompat.getColor(this, R.color.colorPrimary));
        binding.navigation.setOnTabSelectedListener(this::tabSelectedListener);

        binding.navigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
    }

    private boolean tabSelectedListener(int position, boolean wasSelected) {
        binding.pager.setCurrentItem(position, true);
        return true;
    }
}
