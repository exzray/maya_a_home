package com.afiq.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.afiq.myapplication.ProjectAdminNavigationActivity;
import com.afiq.myapplication.databinding.FragmentProjectAdminDetailBinding;
import com.afiq.myapplication.models.ProfileModel;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.viewmodels.ProfileViewModel;
import com.afiq.myapplication.viewmodels.ProjectViewModel;

import java.text.DateFormat;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectAdminDetailFragment extends Fragment {

    private static final String TAG = "ProjectAdminDetailFragm";

    private FragmentProjectAdminDetailBinding binding;


    public ProjectAdminDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProjectAdminDetailBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindViewModel();
    }

    private void bindViewModel() {
        ProjectAdminNavigationActivity activity = (ProjectAdminNavigationActivity) getContext();
        if (activity == null) {
            Log.i(TAG, "ProjectAdminNavigationActivity: null");
            return;
        }

        ProfileViewModel profile_vm = new ViewModelProvider(this).get(ProfileViewModel.class);
        profile_vm.start(Database.refProfile(activity.getUser_id()));
        profile_vm.getData().observe(getViewLifecycleOwner(), this::listenerProfile);

        ProjectViewModel project_vm = new ViewModelProvider(this).get(ProjectViewModel.class);
        project_vm.start(Database.refProject(activity.getProject_id()));
        project_vm.getData().observe(getViewLifecycleOwner(), this::listenerProject);
    }

    private void listenerProfile(ProfileModel data) {
        binding.userName.setText(data.getName());
        binding.userEmail.setText(data.getEmail());
        binding.userContact.setText(data.getContact());
        binding.userAddress.setText(data.getAddress());
    }

    private void listenerProject(ProjectModel data) {
        String string_started = DateFormat.getDateInstance(DateFormat.MEDIUM).format(data.getCreated());
        String string_revenue = "RM" + data.getTotalCost();
        String string_settlement = "RM" + data.getTotalPay();
        String string_balance = "RM" + (data.getTotalCost() - data.getTotalPay());
        String string_status = (data.getTotalCost().equals(data.getTotalPay())) ? "Complete" : "Pending";

        binding.projectLabel.setText(data.getLabel());
        binding.projectStart.setText(string_started);
        binding.projectRevenue.setText(string_revenue);
        binding.projectSettlement.setText(string_settlement);
        binding.projectBalance.setText(string_balance);
        binding.projectStatus.setText(string_status);
    }
}
