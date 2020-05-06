package com.afiq.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.afiq.myapplication.databinding.ActivityProjectAdminNavigationBinding;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class ProjectAdminNavigationActivity extends AppCompatActivity {

    private ActivityProjectAdminNavigationBinding binding;
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
            case R.id.action_view_feedback:
                viewFeedback();
                break;
        }

        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult result = IntentIntegrator.parseActivityResult(resultCode, data);

        if (result.getContents() != null) {
            String user_id = result.getContents();

            FirebaseFirestore.getInstance().runTransaction(transaction -> {
                DocumentReference reference  = Database.DOC_PROJECT(project_id);
                ProjectModel project = ProjectModel.createInstance(transaction.get(reference));

                if (project == null) return null;

                project.setUserID(user_id);

                transaction.set(reference, project);

                return null;
            });
        }
    }

    public String getProject_id() {

        return project_id;
    }

    private void viewProgress() {

    }

    private void viewFeedback() {

    }

    private void getExtras() {
        project_id = getIntent().getStringExtra(Interaction.EXTRA_STRING_PROJECT_ID);
    }
}
