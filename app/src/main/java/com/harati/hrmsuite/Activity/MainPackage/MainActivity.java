package com.harati.hrmsuite.Activity.MainPackage;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.harati.hrmsuite.Helper.BottomNavigationViewHelper;
import com.harati.hrmsuite.AppliedLeavePackage.AppliedLeaveFragment;
import com.harati.hrmsuite.CheckInOutPackage.CheckInOutFragment;
import com.harati.hrmsuite.DashBoardSlidingPackage.DashboardSlidingFragment;
import com.harati.hrmsuite.R;
import com.harati.hrmsuite.fragments.UserProfileFragment;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Fragment fragment = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragment= new DashboardSlidingFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content_frame1,fragment);
        fragmentTransaction.commit();
        bottomNavigationView =(BottomNavigationView)findViewById(R.id.bottomNV);
        BottomNavigationViewHelper bottomNavigationViewHelper =new BottomNavigationViewHelper();
//        bottomNavigationViewHelper.removeShiftMode(bottomNavigationView);
//        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                Menu menu = bottomNavigationView.getMenu();

                switch (item.getItemId()) {
                    case R.id.quick_board:
                        // Switch to page one
//                        item.setIcon(R.drawable.ic_quickboard_orange);
                        if (!(fragment instanceof DashboardSlidingFragment)){
                            fragment= new DashboardSlidingFragment();
                            FragmentManager fragmentManager3 = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction3 = fragmentManager3.beginTransaction();
                            fragmentTransaction3.replace(R.id.content_frame1,fragment);
                            fragmentTransaction3.commit();
                        }
                        break;
                    case R.id.plan_and_package:
                        fragment = new AppliedLeaveFragment();
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.content_frame1,fragment);
                        fragmentTransaction.commit();
                        // Switch to page two
                        break;
                    case R.id.product_and_services:
                        fragment = new CheckInOutFragment();
                        FragmentManager fragmentManager1 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction1 = fragmentManager1.beginTransaction();
                        fragmentTransaction1.replace(R.id.content_frame1,fragment);
                        fragmentTransaction1.commit();
                        break;
                    case R.id.user_profile:
                        fragment = new UserProfileFragment();
                        FragmentManager fragmentManager2 = getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction2 = fragmentManager2.beginTransaction();
                        fragmentTransaction2.replace(R.id.content_frame1,fragment);
                        fragmentTransaction2.commit();
                        break;
                }
                return true;
            }
        });
    }
}
