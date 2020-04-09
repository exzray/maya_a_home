package com.afiq.myapplication;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.afiq.myapplication.adapters.ProjectAdapter;
import com.afiq.myapplication.databinding.ActivityMainBinding;
import com.afiq.myapplication.databinding.DialogQrCodeBinding;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.services.UserService;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.utilities.QrCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    private ProjectAdapter projectAdapter;

    private UserService service;
    private MainServiceConnection serviceConnection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        projectAdapter = new ProjectAdapter(this::onClickProject);

        bindService();
        setupRecycler();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
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

    private void bindService() {
        Intent intent = new Intent(this, UserService.class);
        serviceConnection = new MainServiceConnection();

        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private void onClickProject(ProjectModel data) {
        Intent intent = new Intent(this, ProjectActivity.class);
        intent.putExtra(Interaction.EXTRA_STRING_PROJECT_UID, data.getSnapshot().getId());

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


    private class MainServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            service = ((UserService.UserBinder) binder).getService();
            service.getProjects().observe(MainActivity.this, list -> projectAdapter.update(list));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            service.getProjects().removeObservers(MainActivity.this);
        }
    }
}
