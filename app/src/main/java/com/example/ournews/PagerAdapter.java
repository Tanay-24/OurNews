package com.example.ournews;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

@SuppressWarnings("deprecation")
public class PagerAdapter extends FragmentPagerAdapter {

    int tabcount;

    public PagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        tabcount = behavior;
    }


    @Override
    public int getCount() {
        return tabcount;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new HomeFragement();
            case 1:
                return new SportsFragement();
            case 2:
                return new HealthFragement();
            case 3:
                return new ScienceFragement();
            case 4:
                return new EntertainmentFragement();
            case 5:
                return new TechnologyFragement();

            default:
                return null;
        }
    }
}
