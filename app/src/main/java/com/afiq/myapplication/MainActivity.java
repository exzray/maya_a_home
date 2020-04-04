package com.afiq.myapplication;

import android.content.Intent;
import android.os.Bundle;
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
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.utilities.QrCode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements ProjectAdapter.ProjectActionItem, FirebaseAuth.AuthStateListener {

    private ActivityMainBinding binding;

    private ProjectAdapter projectAdapter;

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        projectAdapter = new ProjectAdapter(this, this);

        auth.addAuthStateListener(this);

        setupRecycler();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        auth.removeAuthStateListener(this);
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

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        updateUI(firebaseAuth.getCurrentUser());
    }

    @Override
    public void onClick(ProjectModel data) {
        Intent intent = new Intent(this, ProjectActivity.class);
        intent.putExtra(Interaction.EXTRA_STRING_PROJECT_UID, data.getSnapshot().getId());

        startActivity(intent);
    }

    private void updateUI(FirebaseUser user) {
        if (user == null) Interaction.nextEnd(this, SplashActivity.class);
    }

    private void setupRecycler() {
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.setAdapter(projectAdapter);
    }

    private void actionCode() {
        FirebaseUser user = auth.getCurrentUser();
        assert user != null;

        DialogQrCodeBinding binding = DialogQrCodeBinding.inflate(getLayoutInflater());

        binding.image.setImageBitmap(QrCode.generateBitmapQrCode(user.getUid()));

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(binding.getRoot());
        builder.setPositiveButton("OK", null);
        builder.create().show();
    }

    private void actionLogout() {
        auth.signOut();
    }
}
