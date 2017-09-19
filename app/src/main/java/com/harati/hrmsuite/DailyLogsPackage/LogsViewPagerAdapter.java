package com.harati.hrmsuite.DailyLogsPackage;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.harati.hrmsuite.DailyLogsPackage.LeaveStatisticPackage.LeaveStatisticFragment;
import com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage.ViewLogsFragment;

/**
 * Created by Sameer on 6/27/2017.
 */

public class LogsViewPagerAdapter extends FragmentPagerAdapter {


    public LogsViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position){
            case 0:
                fragment = new ViewLogsFragment();
                break;
            case 1:
                fragment = new LeaveStatisticFragment();
                break;
            case 2:
                break;
            default:
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
