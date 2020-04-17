package com.afiq.myapplication;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.afiq.myapplication.databinding.ActivityProjectAdminBinding;
import com.afiq.myapplication.fragment_adapters.ProjectAdminPagerAdapter;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.viewmodels.ProjectViewModel;

public class ProjectAdminActivity extends AppCompatActivity {

    private static final String TAG = "ProjectAdminActivity";

    private ActivityProjectAdminBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize binding
        binding = ActivityProjectAdminBinding.inflate(getLayoutInflater());

        // activity setup
        setContentView(binding.getRoot());
        setTitle("Project");

        getProjectID();
    }

    private void getProjectID() {
        String project_id = getIntent().getStringExtra(Interaction.EXTRA_STRING_PROJECT_ID);

        setupViewPager(project_id);

        if (project_id == null) {
            Log.i(TAG, "project_id: null");
            return;
        }

        bindViewModel(project_id);
    }

    private void bindViewModel(String project_id) {
        ProjectViewModel vm = new ViewModelProvider(this).get(ProjectViewModel.class);
        vm.getData(Database.refProject(project_id)).observe(this, this::listener);
    }

    private void setupViewPager(String project_id) {
        ProjectAdminPagerAdapter adapter = new ProjectAdminPagerAdapter(getSupportFragmentManager(), project_id);

        binding.pager.setAdapter(adapter);
        binding.tab.setupWithViewPager(binding.pager);
    }

    private void listener(ProjectModel data) {
        setTitle(data.getLabel());
    }
}
