package com.afiq.myapplication.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.afiq.myapplication.databinding.FragmentAdminProjectBinding;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.recycler_adapters.AdminProjectAdapter;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.viewmodels.ProjectListViewModel;

import java.util.List;


public class AdminProjectFragment extends Fragment {

    private static final String TAG = "AdminProjectFragment";

    private FragmentAdminProjectBinding binding;

    private AdminProjectAdapter adapter;


    public AdminProjectFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAdminProjectBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getContext() == null) {
            Log.i(TAG, "context: null");
            return;
        }
        adapter = new AdminProjectAdapter(this::onClickProject);

        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        ProjectListViewModel vm = new ViewModelProvider(this).get(ProjectListViewModel.class);
        vm.getData(Database.queryAgentProjectList()).observe(getViewLifecycleOwner(), this::listener);
    }

    private void onClickProject(ProjectModel data) {
        Intent intent = new Intent(getContext(), ProjectAdminActivity.class);
        intent.putExtra(Interaction.EXTRA_STRING_PROJECT_ID, data.getSnapshot().getId());

        startActivity(intent);
    }

    private void listener(List<ProjectModel> list) {
        adapter.update(list);
    }
}
