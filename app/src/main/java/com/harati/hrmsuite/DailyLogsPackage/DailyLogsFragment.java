package com.harati.hrmsuite.DailyLogsPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage.DailyLogsDetailModel;
import com.harati.hrmsuite.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sameer on 6/26/2017.
 */

public class DailyLogsFragment extends Fragment {
    RecyclerView leaveRecyclerView;
    List<DailyLogsDetailModel> dailydetailModelList = new ArrayList<DailyLogsDetailModel>();
    DailyLogsDetailModel logmodel;
    ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_daily_logs, container, false);

/*
        for (int i =0;i<1;i++){
            Bitmap bitmap = BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_red_cross);
            dailydetailModelList.add(new DailyLogsDetailModel("2017-01-01","09:56:45-19:20:20","10hrs05mins","All good ","P"));
            dailydetailModelList.add(new DailyLogsDetailModel("2017-01-01","09:56:45-19:20:20","10hrs05mins","All good ","A"));
            dailydetailModelList.add(new DailyLogsDetailModel("2017-01-01","09:56:45-19:20:20","10hrs05mins","All good ","P"));
            dailydetailModelList.add(new DailyLogsDetailModel("2017-01-01","09:56:45-19:20:20","10hrs05mins","All good ","H"));
            dailydetailModelList.add(new DailyLogsDetailModel("2017-01-01","09:56:45-19:20:20","10hrs05mins","All good ","P"));
            dailydetailModelList.add(new DailyLogsDetailModel("2017-01-01","09:56:45-19:20:20","10hrs05mins","All good ","H"));
            dailydetailModelList.add(new DailyLogsDetailModel("2017-01-01","09:56:45-19:20:20","10hrs05mins","All good ","A"));
*//*            dailydetailModelList.add(new DailyLogsDetailModel("111","111","111","1011","P"));
            dailydetailModelList.add(new DailyLogsDetailModel("111","111","111","1011","P"));
            dailydetailModelList.add(new DailyLogsDetailModel("111","111","111","1011","H"));
            dailydetailModelList.add(new DailyLogsDetailModel("111","111","111","All good","A"));
            dailydetailModelList.add(new DailyLogsDetailModel("111","111","111","All good","A"));*//*
        }
        leaveRecyclerView=(RecyclerView)view.findViewById(R.id.leaveRecyclerView);
        RecyclerView.LayoutManager linearLayoutManager =new LinearLayoutManager(getContext());
        leaveRecyclerView.setLayoutManager(linearLayoutManager);
        DailyLogsAdapter logAdapter = new DailyLogsAdapter(getContext(),dailydetailModelList);
        leaveRecyclerView.setAdapter(logAdapter);*/



        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Daily Logs"));
        tabLayout.addTab(tabLayout.newTab().setText("Leave Status"));
        tabLayout.addTab(tabLayout.newTab().setText("Pay Roll"));
        tabLayout.setTabGravity( TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
        viewPager = (ViewPager)view.findViewById(R.id.dailyLogsViewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem( tab.getPosition() );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        LogsViewPagerAdapter logsViewPagerAdapter =new LogsViewPagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(logsViewPagerAdapter);
        return view;
    }
}
