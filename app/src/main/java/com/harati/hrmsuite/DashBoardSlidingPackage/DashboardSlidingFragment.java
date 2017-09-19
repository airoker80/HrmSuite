package com.harati.hrmsuite.DashBoardSlidingPackage;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harati.hrmsuite.Helper.DatabaseHandler;
import com.harati.hrmsuite.Helper.NetworkManager;
import com.harati.hrmsuite.R;
import com.harati.hrmsuite.Retrofit.Interface.ApiInterface;
import com.harati.hrmsuite.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.hrmsuite.UserSessionManager.UserSessionManager;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.Model;

import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 8/11/2017.
 */

public class DashboardSlidingFragment extends Fragment {

    ViewPager mViewPager;
    AppCompatActivity mActivity;
    DashboardViewPageAdapter dashboardViewPageAdapter;
    private ApiInterface apiInterface;
    UserSessionManager userSessionManager;
    DatabaseHandler databaseHandler;
    List<HolidayModel.HolidayData> holidayDataList = new ArrayList<HolidayModel.HolidayData>();
    ImageView image;
    TextView holidayName, holidayDate, comingInDay;
    private DateConverter dateConverter;


    public DashboardSlidingFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dashboard_sliding, container, false);

        // to reset menu item
        setHasOptionsMenu(true);
        dateConverter = new DateConverter();
        apiInterface = RetrofitClient.getApiService();
        userSessionManager = new UserSessionManager(getContext());
        databaseHandler = DatabaseHandler.getInstance(getContext());
        mViewPager = (ViewPager) view.findViewById(R.id.viewPager);
        image = (ImageView) view.findViewById(R.id.image);
        holidayName = (TextView) view.findViewById(R.id.holidayName);
        holidayDate = (TextView) view.findViewById(R.id.holidayDay);
        comingInDay = (TextView) view.findViewById(R.id.comingInDay);
        dashboardViewPageAdapter = new DashboardViewPageAdapter(getChildFragmentManager(), getActivity());
        mViewPager.setAdapter(dashboardViewPageAdapter);
        mViewPager.setOffscreenPageLimit(0);
        PagerTabStrip pagerTabStrip = (PagerTabStrip) view.findViewById(R.id.pagerTabStrip);
        pagerTabStrip.setTabIndicatorColor(Color.BLACK);
        Typeface fontTypeFace = Typeface.createFromAsset(getContext().getAssets(),
                "Lato-Light.ttf");

        for (int i = 0; i < pagerTabStrip.getChildCount(); ++i) {
            View nextChild = pagerTabStrip.getChildAt(i);
            if (nextChild instanceof TextView) {
                TextView textViewToConvert = (TextView) nextChild;
                textViewToConvert.setTypeface(fontTypeFace);
                textViewToConvert.setTextColor(Color.BLACK);
            }
        }
        getCheckInOutDetails();
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            mActivity = (AppCompatActivity) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        try {

            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {

            throw new RuntimeException(e);

        } catch (IllegalAccessException e) {

            throw new RuntimeException(e);
        }
    }

    public void getCheckInOutDetails() {

        if (NetworkManager.isConnected(getContext())) {
            Call<HolidayModel> call = apiInterface.getUpcomingHoliday(userSessionManager.getKeyUsercode(), userSessionManager.getKeyAccessToken());
            call.enqueue(new Callback<HolidayModel>() {
                @Override
                public void onResponse(Call<HolidayModel> call, Response<HolidayModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getMsgTitle().equals("Success")) {
                            Log.d("res11111111----", response.body().getData().toString());
                            databaseHandler.deleteHoliday();
                            for (HolidayModel.HolidayData holidayData : response.body().getData()) {

                                databaseHandler.addHoliday(holidayData);
                            }

                            prepareData();


                        } else {
                            prepareData();
                        }
                    } else {
                        prepareData();
                    }
                }

                @Override
                public void onFailure(Call<HolidayModel> call, Throwable t) {
                    prepareData();
                }


            });
        } else {
            prepareData();
        }
    }

    public void prepareData() {
        holidayDataList.clear();
        holidayDataList = databaseHandler.getHoliday();
        if (holidayDataList.size() > 0) {
            for (HolidayModel.HolidayData holidayData : holidayDataList) {
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(holidayData.getHolidayDate());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Calendar thatDay = Calendar.getInstance();
                thatDay.setTime(date);

                Calendar today = Calendar.getInstance();

                long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
                long days = diff / (24 * 60 * 60 * 1000);
                holidayName.setText(holidayData.getHolidayName());

                try {
                    holidayDate.setText(getDate(holidayData.getHolidayDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                comingInDay.setText("In " + String.valueOf(days) + " days");
            }


        } else {
            holidayName.setText("No Upcoming holiday");

        }

    }


    public String getDate(String dateHoliday) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateHoliday);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        String month = new SimpleDateFormat("MMM").format(calendar.getTime());
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String fulldate = null;
        String dateDetails = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String[] dateTime = dateDetails.split("-");
        if (userSessionManager.getKeyLanguage().equals("English")) {
            fulldate = day + " " + month + ", " + year;
        } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
            Model nepDate = dateConverter.getNepaliDate(Integer.parseInt(dateTime[0]), Integer.parseInt(dateTime[1]), Integer.parseInt(dateTime[2]));
            String yearNepali = String.valueOf(nepDate.getYear());
            String monthNepali = getResources().getString(DateConverter.getNepaliMonth(nepDate.getMonth()));

            String dayNepali = String.valueOf(nepDate.getDay());

            fulldate = dayNepali + " " + monthNepali + ", " + yearNepali;
        }

        return fulldate;
    }
}
