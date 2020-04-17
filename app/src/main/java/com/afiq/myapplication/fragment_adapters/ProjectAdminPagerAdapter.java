package com.afiq.myapplication.fragment_adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ProjectAdminPagerAdapter extends FragmentStatePagerAdapter {

    private static final List<Fragment> FRAGMENTS = new ArrayList<>();


    public ProjectAdminPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
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
}
