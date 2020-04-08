package com.afiq.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.afiq.myapplication.adapters.ProgressAdapter;
import com.afiq.myapplication.databinding.ActivityProjectBinding;
import com.afiq.myapplication.models.ProgressModel;
import com.afiq.myapplication.utilities.Interaction;

public class ProjectActivity extends AppCompatActivity {

    private String _projectUID;

    private ActivityProjectBinding binding;
    private ProgressAdapter progressAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        _projectUID = getIntent().getStringExtra(Interaction.EXTRA_STRING_PROJECT_UID);

        progressAdapter = new ProgressAdapter(this::onClickItemProgress);

        setupRecycler();
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

    private void setupRecycler() {
        binding.recycler.setAdapter(progressAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
        binding.recycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
    }

    private void onClickItemProgress(ProgressModel data) {
        switch (data.getStatus()) {
            case NOTHING:
            case REJECT:
                unPay();
                break;
            case PENDING:
            case SUCCESS:
                alreadyPaid();
                break;
        }
    }

    private void alreadyPaid() {
        Toast.makeText(this, "Thanks for paying", Toast.LENGTH_SHORT).show();
    }

    private void unPay() {
        Intent intent = new Intent(this, UploadReceiptActivity.class);
        startActivity(intent);
    }
}
