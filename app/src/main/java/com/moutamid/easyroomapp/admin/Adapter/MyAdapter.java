package com.moutamid.easyroomapp.admin.Adapter;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.moutamid.halalfoodadmin.Fragments.DislikeFragment;
import com.moutamid.halalfoodadmin.Fragments.LikeFragment;

public  class MyAdapter extends FragmentPagerAdapter {
    Context context;
    int totalTabs;

    public MyAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        context = c;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                LikeFragment LikeFragment = new LikeFragment();
                return LikeFragment;
            case 1:
                DislikeFragment dislikeFragment = new DislikeFragment();
                return dislikeFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return totalTabs;
    }
}