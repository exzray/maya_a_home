package com.afiq.myapplication.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.afiq.myapplication.databinding.FragmentAdminProjectBinding;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.recycler_adapters.AdminProjectAdapter;


public class AdminProjectFragment extends Fragment {

    private static final String TAG = "AdminProjectFragment";

    private FragmentAdminProjectBinding binding;


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

        binding.recycler.setAdapter(new AdminProjectAdapter(this::onClickProject));
        binding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycler.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
    }

    private void onClickProject(ProjectModel data) {

    }
}
