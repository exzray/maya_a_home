package com.afiq.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.afiq.myapplication.databinding.ActivityMainAdminBinding;
import com.afiq.myapplication.fragment_adapters.MainAdminPagerAdapter;
import com.afollestad.materialdialogs.MaterialDialog;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainAdminActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_ADD_AGENT = 1;
    public static final int REQUEST_CODE_NEW_PROJECT = 2;

    private ActivityMainAdminBinding binding;

    private int request_code = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setTitle("Maya A Home Admin");
        setupNavigation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);

        if (result.getContents() != null) {
            String id = result.getContents();

            switch (request_code) {
                case REQUEST_CODE_NEW_PROJECT:
                    newProject(id);
                    break;
                case REQUEST_CODE_ADD_AGENT:
                    addAgent(id);
                    break;
            }
        }
    }

    public void setRequestCode(int code) {
        request_code = code;
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

    private void newProject(String user_id) {

    }

    private void addAgent(String agent_id) {

    }
}
