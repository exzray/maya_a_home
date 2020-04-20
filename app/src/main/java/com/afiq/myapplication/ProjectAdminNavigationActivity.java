package com.afiq.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afiq.myapplication.databinding.ActivityProjectAdminNavigationBinding;

public class ProjectAdminNavigationActivity extends AppCompatActivity {

    private ActivityProjectAdminNavigationBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectAdminNavigationBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_project_admin_navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_view_progress:
                viewProgress();
                break;
            case R.id.action_change_user:
                changeUser();
                break;
            case R.id.action_edit_project:
                editProject();
                break;
            case R.id.action_view_feedback:
                viewFeedback();
                break;
        }

        return false;
    }

    private void viewProgress() {

    }

    private void changeUser() {

    }

    private void editProject() {

    }

    private void viewFeedback() {

    }
}
