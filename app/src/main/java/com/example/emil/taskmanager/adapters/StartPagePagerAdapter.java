package com.example.emil.taskmanager.adapters;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.emil.taskmanager.fragments.RegisterFragment;
import com.example.emil.taskmanager.fragments.LoginFragment;

/**
 * Created by Administrator on 5/15/2016.
 */
public class StartPagePagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public StartPagePagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext = context;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return LoginFragment.newInstance("a", "b");
            case 1:
                return RegisterFragment.newInstance("a", "b");
        }
        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
