package com.afiq.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.afiq.myapplication.databinding.ActivityMainBinding;
import com.afiq.myapplication.databinding.DialogQrCodeBinding;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.recycler_adapters.ProjectAdapter;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.utilities.QrCode;
import com.afiq.myapplication.viewmodels.ProjectListViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ProjectAdapter projectAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        projectAdapter = new ProjectAdapter(this::onClickProject);

        setupRecycler();
    }

    @Override
    protected void onStart() {
        super.onStart();

        ProjectListViewModel vm = new ViewModelProvider(this).get(ProjectListViewModel.class);
        vm.getData().observe(this, this::projectListListener);
        vm.start(Database.queryUserProjectList());
    }

    @Override
    protected void onPause() {
        super.onPause();

        ProjectListViewModel vm = new ViewModelProvider(this).get(ProjectListViewModel.class);
        vm.getData().removeObservers(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_code:
                actionCode();
                break;
            case R.id.action_profile:
                actionProfile();
                break;
            case R.id.action_logout:
                actionLogout();
                break;
            case R.id.action_about:
                Toast.makeText(this, "show about", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return false;
    }

    private void onClickProject(ProjectModel data) {
        Intent intent = new Intent(this, ProjectActivity.class);
        intent.putExtra(Interaction.EXTRA_STRING_PROJECT_ID, data.getSnapshot().getId());

        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {
        Intent intent = new Intent(this, SplashActivity.class);

        if (user == null) Interaction.nextEnd(this, intent);
    }

    private void setupRecycler() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(projectAdapter);
    }

    private void projectListListener(List<ProjectModel> list) {
        projectAdapter.update(list);
    }

    private void actionCode() {
        DialogQrCodeBinding binding = DialogQrCodeBinding.inflate(getLayoutInflater());

        binding.image.setImageBitmap(QrCode.generateBitmapQrCode(Database.getUser().getUid()));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(binding.getRoot());
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    private void actionProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void actionLogout() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signOut();
        updateUI(auth.getCurrentUser());
    }
}
