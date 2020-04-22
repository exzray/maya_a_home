package com.afiq.myapplication.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.afiq.myapplication.databinding.FragmentProjectAdminDetailBinding;
import com.afiq.myapplication.models.ProfileModel;
import com.afiq.myapplication.utilities.Database;
import com.afiq.myapplication.viewmodels.ProfileViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProjectAdminDetailFragment extends Fragment {

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
        ProfileViewModel pvm = new ViewModelProvider(this).get(ProfileViewModel.class);
        pvm.getData().observe(getViewLifecycleOwner(), this::listener);
        pvm.listen(Database.refProfile());
    }

    private void listener(ProfileModel data) {

    }
}
