package com.example.scheduler.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.scheduler.UI.TabDate;
import com.example.scheduler.UI.TabPriority;
import com.example.scheduler.UI.TabState;
import com.example.scheduler.UI.TabType;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new TabDate();
            case 1:
                return new TabPriority();
            case 2:
                return new TabState();
            case 3:
                return new TabType();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Date";
            case 1:
                return "Priority";
            case 2:
                return "State";
            case 3:
                return "Type";
        }
        return null;
    }
}
