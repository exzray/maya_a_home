package com.afiq.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Toast;

import com.afiq.myapplication.adapters.TimelineAdapter;
import com.afiq.myapplication.databinding.ActivityProjectBinding;
import com.afiq.myapplication.models.ProgressModel;
import com.afiq.myapplication.utilities.Interaction;

public class ProjectActivity extends AppCompatActivity {

    private String _projectUID;

    private ActivityProjectBinding binding;
    private TimelineAdapter timelineAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProjectBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        _projectUID = getIntent().getStringExtra(Interaction.EXTRA_STRING_PROJECT_UID);

        timelineAdapter = new TimelineAdapter(this::onClickItemProgress);

        setupRecycler();
    }

    private void setupRecycler() {
        binding.recycler.setAdapter(timelineAdapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(this));
    }

    private void onClickItemProgress(ProgressModel data){
        Toast.makeText(this, data.getDescription(), Toast.LENGTH_SHORT).show();
    }
}
