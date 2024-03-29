package com.afiq.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.afiq.myapplication.databinding.ActivityProjectBinding;
import com.afiq.myapplication.models.ProgressModel;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.recycler_adapters.ProgressAdapter;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.viewmodels.ProgressListViewModel;
import com.afiq.myapplication.viewmodels.ProjectViewModel;
import com.bumptech.glide.Glide;
import com.google.firebase.firestore.Query;

public class ProjectActivity extends AppCompatActivity {

    private String _projectID;

    private ActivityProjectBinding binding;
    private ProgressAdapter progressAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        _projectID = getIntent().getStringExtra(Interaction.EXTRA_STRING_PROJECT_ID);

        progressAdapter = new ProgressAdapter(this::onClickItemProgress);

        setupBanner();
        setupRecycler();
        setupProjectViewModel();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_project, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_file:
                Toast.makeText(this, "file", Toast.LENGTH_SHORT).show();
                break;
            case R.id.action_chat:
                Toast.makeText(this, "Coming soon", Toast.LENGTH_SHORT).show();
                break;
        }

        return false;
    }

    private void setupBanner() {
        Glide.with(this).load(R.drawable.progress).into(binding.banner);
    }

    private void setupRecycler() {
        binding.recycler.setAdapter(progressAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void setupProjectViewModel() {
        if (_projectID == null) return;

        ProjectViewModel project_vm = new ViewModelProvider(this).get(ProjectViewModel.class);
        project_vm.start(Database.DOC_PROJECT(_projectID));
        project_vm.getData().observe(this, this::listener);
    }

    private void setupProgressListViewModel(String project_id) {
        Query query = Database.queryProgressList(project_id);

        ProgressListViewModel vm = new ViewModelProvider(this).get(ProgressListViewModel.class);
        vm.getData(query).observe(this, list -> progressAdapter.update(list));
    }

    private void listener(ProjectModel project) {
        setTitle(project.getLabel());

        String payment_str = "RM " + project.getTotalCost();
        binding.payment.setText(payment_str);

        // retrieve progress for this project only
        setupProgressListViewModel(project.getSnapshot().getId());
    }

    private void onClickItemProgress(ProgressModel data) {
        String user_id = data.getUserID();
        String agent_id = data.getAgentID();
        String project_id = data.getProjectID();
        String progress_id = data.getSnapshot().getId();

        Intent intent = new Intent(this, UploadTransferActivity.class);
        intent.putExtra(Interaction.EXTRA_STRING_USER_ID, user_id);
        intent.putExtra(Interaction.EXTRA_STRING_AGENT_ID, agent_id);
        intent.putExtra(Interaction.EXTRA_STRING_PROJECT_ID, project_id);
        intent.putExtra(Interaction.EXTRA_STRING_PROGRESS_ID, progress_id);

        startActivity(intent);
    }
}
