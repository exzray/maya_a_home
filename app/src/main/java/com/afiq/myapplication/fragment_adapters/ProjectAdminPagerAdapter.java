package com.afiq.myapplication.fragment_adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.afiq.myapplication.fragments.ProjectEditFragment;
import com.afiq.myapplication.fragments.ProjectPaymentFragment;
import com.afiq.myapplication.fragments.ProjectProgressFragment;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdminPagerAdapter extends FragmentStatePagerAdapter {

    private static final List<String> TITLES = new ArrayList<>();
    private static final List<Fragment> FRAGMENTS = new ArrayList<>();


    public ProjectAdminPagerAdapter(@NonNull FragmentManager fm, String project_id) {
        super(fm);

        TITLES.add("Project");
        TITLES.add("Progress");
        TITLES.add("Payment");

        ProjectEditFragment edit = new ProjectEditFragment();
        ProjectProgressFragment progress = new ProjectProgressFragment();
        ProjectPaymentFragment payment = new ProjectPaymentFragment();

        FRAGMENTS.add(edit);
        FRAGMENTS.add(progress);
        FRAGMENTS.add(payment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return FRAGMENTS.get(position);
    }

    @Override
    public int getCount() {
        return FRAGMENTS.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES.get(position);
    }
}
