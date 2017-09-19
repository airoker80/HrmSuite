package com.harati.hrmsuite.DashBoardSlidingPackage;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.harati.hrmsuite.DailyLogsPackage.LeaveStatisticPackage.LeaveStatisticFragment;
import com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage.ViewLogsFragment;

/**
 * Created by User on 8/21/2017.
 */

public class DashboardViewPageAdapter extends FragmentPagerAdapter {

    final int PAGE_COUNT = 2;

    /*private String titles[] = new String[] { "Recharge Code","Use Recharge Code", "Generate Recharge Code" };*/

    private String titles[]= new String[]{"Daily Logs", "Leave Status", "Pay Roll"};;
//    private String titles[] = new String[] {"Summary Report"};


    // The key to this is to return a SpannableString,
    // containing your icon in an ImageSpan, from your PagerAdapter's getPageTitle(position) method:


    Context context;

    public DashboardViewPageAdapter(FragmentManager fm, Context context) {
        super(fm);

        this.context = context;

    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            case 0:
                Fragment fragment1 = new ViewLogsFragment();
                return fragment1;
            case 1:
                Fragment fragment2 = new LeaveStatisticFragment();
                return fragment2;
            case 2:
                Fragment fragment3 = new LeaveStatisticFragment();
                return fragment3;
        }
        return null;
    }

    public CharSequence getPageTitle(int position) {

        // Generate title based on item position
        return titles[position];

    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }



}