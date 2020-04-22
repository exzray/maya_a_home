package com.afiq.myapplication;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.afiq.myapplication.databinding.ActivityProjectAdminNavigationBinding;
import com.afiq.myapplication.utilities.Interaction;

public class ProjectAdminNavigationActivity extends AppCompatActivity {

    private ActivityProjectAdminNavigationBinding binding;
    private String user_id;
    private String project_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectAdminNavigationBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        getExtras();
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

    public String getUser_id() {
        return user_id;
    }

    public String getProject_id() {
        return project_id;
    }

    private void viewProgress() {

    }

    private void changeUser() {

    }

    private void editProject() {

    }

    private void viewFeedback() {

    }

    private void getExtras() {
        user_id = getIntent().getStringExtra(Interaction.EXTRA_STRING_USER_ID);
        project_id = getIntent().getStringExtra(Interaction.EXTRA_STRING_PROJECT_ID);
    }
}
