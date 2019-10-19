package com.example.absensi.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {

    private final List<Fragment> lstFragment = new ArrayList<>();
    private final List<String> lstTitle = new ArrayList<>(  );


    public FragmentAdapter(FragmentManager fm) {
        super( fm );
    }

    @Override
    public Fragment getItem(int i) {
        return lstFragment.get( i );
    }

    @Override
    public int getCount() {
        return lstTitle.size();
    }

    public CharSequence getPageTitle(int position){
        return lstTitle.get( position );
    }
    public void AddFragment (Fragment fragment, String title){
        lstFragment.add( fragment );
        lstTitle.add( title );
    }

}