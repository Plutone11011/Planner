package com.example.scheduler.Adapters;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.scheduler.UI.TabHistogram;
import com.example.scheduler.UI.TabPie;

public class ChartPagerAdapter extends FragmentPagerAdapter {

    public ChartPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch (position) {
            case 0:
                return new TabPie();
            case 1:
                return new TabHistogram();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {

        return 2;
    }

    /*@Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Pie chart";
            case 1:
                return "Histogram";
        }
        return null;
    }*/
}
