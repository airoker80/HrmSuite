package com.harati.hrmsuite.MainPackage.Acitivity;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.harati.hrmsuite.AppliedLeavePackage.AppliedLeaveFragment;
import com.harati.hrmsuite.CheckInOutPackage.CheckInOutFragment;
import com.harati.hrmsuite.DashBoardSlidingPackage.DashboardSlidingFragment;
import com.harati.hrmsuite.RemarkPackage.RemarkFragment;
import com.harati.hrmsuite.UserPackage.UserProfileFragment;
import com.harati.hrmsuite.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    BottomNavigationView bottomNavigationView;
    ImageView quick_board, leave, checkInOut, profile, remark;
    ImageView currentSelectedIcon;
    Fragment fragment = null;
    public static CoordinatorLayout mainView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNV);
        mainView = (CoordinatorLayout) findViewById(R.id.mainView);
        quick_board = (ImageView) findViewById(R.id.quick_board);
        leave = (ImageView) findViewById(R.id.leave);
        checkInOut = (ImageView) findViewById(R.id.checkInOut);
        profile = (ImageView) findViewById(R.id.profile);
        remark = (ImageView) findViewById(R.id.remark);


        quick_board.setOnClickListener(this);
        leave.setOnClickListener(this);
        checkInOut.setOnClickListener(this);
        profile.setOnClickListener(this);
        remark.setOnClickListener(this);
        //Manually displaying the first fragment - one time only
//        setPage(R.id.quick_board);
//        resetSelectedIconDrawable(quick_board);
        String title= getIntent().getStringExtra("title");
        if (!title.equals("")) {
            switch (getIntent().getStringExtra("title")) {
                case "Remark":
                    setPage(R.id.remark);
                    resetSelectedIconDrawable(remark);
                    break;
                case "CheckIn/Out":
                    setPage(R.id.checkInOut);
                    resetSelectedIconDrawable(checkInOut);
                    break;
                case "Leave":
                    setPage(R.id.leave);
                    resetSelectedIconDrawable(leave);
                    break;
            }
        } else {
            setPage(R.id.quick_board);
            resetSelectedIconDrawable(quick_board);
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.quick_board:
                setPage(R.id.quick_board);
                resetSelectedIconDrawable(quick_board);
                break;

            case R.id.leave:
                setPage(R.id.leave);
                resetSelectedIconDrawable(leave);
                break;

            case R.id.checkInOut:
                setPage(R.id.checkInOut);
                resetSelectedIconDrawable(checkInOut);

                break;
            case R.id.remark:
                setPage(R.id.remark);
                resetSelectedIconDrawable(remark);

                break;

            case R.id.profile:
                setPage(R.id.profile);
                resetSelectedIconDrawable(profile);
                break;
        }
    }


    //switch fragment in main activity
    public void setPage(int id) {


        switch (id) {
            case R.id.quick_board:
                fragment = new DashboardSlidingFragment();
                break;
            case R.id.leave:
                fragment = new AppliedLeaveFragment();
                break;
            case R.id.checkInOut:
                fragment = new CheckInOutFragment();
                break;
            case R.id.remark:
                fragment = new RemarkFragment();
                break;
            case R.id.profile:
                fragment = new UserProfileFragment();
                break;


        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content_frame1, fragment);
        transaction.commit();
    }

    //change icon drawable
    public void resetSelectedIconDrawable(ImageView selectedView) {
        if (currentSelectedIcon != null) {
            if (currentSelectedIcon == quick_board) {
                quick_board.setImageResource(R.drawable.ic_quickboard_grey);
                quick_board.setEnabled(true);
            } else if (currentSelectedIcon == leave) {
                leave.setImageResource(R.drawable.ic_leave_grey);
                leave.setEnabled(true);
            } else if (currentSelectedIcon == checkInOut) {
                checkInOut.setImageResource(R.drawable.ic_check_grey);
                checkInOut.setEnabled(true);
            } else if (currentSelectedIcon == remark) {
                remark.setImageResource(R.drawable.ic_remark_grey);
                remark.setEnabled(true);
            } else if (currentSelectedIcon == profile) {
                profile.setImageResource(R.drawable.ic_profile_grey);
                profile.setEnabled(true);
            }
        }
        currentSelectedIcon = selectedView;
        if (currentSelectedIcon == quick_board) {
            quick_board.setImageResource(R.drawable.ic_quickboard);
            quick_board.setEnabled(false);
        } else if (currentSelectedIcon == leave) {
            leave.setImageResource(R.drawable.ic_leave);
            leave.setEnabled(false);
        } else if (currentSelectedIcon == checkInOut) {
            checkInOut.setImageResource(R.drawable.ic_check);
            checkInOut.setEnabled(false);
        } else if (currentSelectedIcon == remark) {
            remark.setImageResource(R.drawable.ic_remark);
            remark.setEnabled(false);
        } else if (currentSelectedIcon == profile) {
            profile.setImageResource(R.drawable.ic_profile);
            profile.setEnabled(false);
        }
    }

    @Override
    public void onBackPressed() {

        if (fragment instanceof AppliedLeaveFragment || fragment instanceof CheckInOutFragment || fragment instanceof RemarkFragment || fragment instanceof UserProfileFragment) {
            setPage(R.id.quick_board);
            resetSelectedIconDrawable(quick_board);
        } else {
            super.onBackPressed();
        }
    }
}
