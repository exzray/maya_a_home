package com.afiq.myapplication.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.afiq.myapplication.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProgressAdminEditFragment extends Fragment {

    public ProgressAdminEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress_admin_edit, container, false);
    }
}
