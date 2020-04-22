package com.afiq.myapplication.fragment_adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.afiq.myapplication.fragments.AdminAgentFragment;
import com.afiq.myapplication.fragments.AdminEnquireFragment;
import com.afiq.myapplication.fragments.AdminPaymentFragment;
import com.afiq.myapplication.fragments.AdminProjectFragment;

import java.util.ArrayList;
import java.util.List;

public class MainAdminPagerAdapter extends FragmentStatePagerAdapter {

    private static final List<Fragment> FRAGMENTS = new ArrayList<>();


    public MainAdminPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);

        AdminProjectFragment fragment1 = new AdminProjectFragment();
        AdminPaymentFragment fragment2 = new AdminPaymentFragment();
        AdminAgentFragment fragment3 = new AdminAgentFragment();
        AdminEnquireFragment fragment4 = new AdminEnquireFragment();

        FRAGMENTS.add(fragment1);
        FRAGMENTS.add(fragment2);
        FRAGMENTS.add(fragment3);
        FRAGMENTS.add(fragment4);
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
