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

import com.afiq.myapplication.MainAdminActivity;
import com.afiq.myapplication.ProjectAdminNavigationActivity;
import com.afiq.myapplication.databinding.FragmentDashboardAdminBinding;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.recycler_adapters.ProjectAdminAdapter;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.utilities.Interaction;
import com.afiq.myapplication.viewmodels.ProjectListViewModel;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.List;


public class DashboardAdminFragment extends Fragment {

    private static final String TAG = "AdminProjectFragment";

    private FragmentDashboardAdminBinding binding;

    private ProjectAdminAdapter adapter;


    public DashboardAdminFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDashboardAdminBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getContext() == null) {
            Log.i(TAG, "context: null");
            return;
        }
        adapter = new ProjectAdminAdapter(this::onClickProject);

        binding.recycler.setAdapter(adapter);
        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        ProjectListViewModel vm = new ViewModelProvider(this).get(ProjectListViewModel.class);
        vm.getData().observe(getViewLifecycleOwner(), this::listener);
        vm.start(Database.queryAgentProjectList());

        setupButtonOnClick();
    }

    private void setupButtonOnClick() {
        binding.buttonNewProject.setOnClickListener(this::onClickNewProject);
        binding.buttonAddAgent.setOnClickListener(this::onClickAddAgent);
    }

    private void onClickNewProject(View view) {
        if (getActivity() != null)
            ((MainAdminActivity) getActivity()).setRequestCode(MainAdminActivity.REQUEST_CODE_NEW_PROJECT);

        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setBeepEnabled(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.initiateScan();
    }

    private void onClickAddAgent(View view) {
        if (getActivity() != null)
            ((MainAdminActivity) getActivity()).setRequestCode(MainAdminActivity.REQUEST_CODE_ADD_AGENT);

        IntentIntegrator integrator = IntentIntegrator.forSupportFragment(this);
        integrator.setBeepEnabled(true);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.initiateScan();
    }

    private void onClickProject(ProjectModel data) {
        Intent intent = new Intent(getContext(), ProjectAdminNavigationActivity.class);
        intent.putExtra(Interaction.EXTRA_STRING_USER_ID, data.getUserID());
        intent.putExtra(Interaction.EXTRA_STRING_PROJECT_ID, data.getSnapshot().getId());

        startActivity(intent);
    }

    private void listener(List<ProjectModel> list) {
        adapter.update(list);
    }
}
