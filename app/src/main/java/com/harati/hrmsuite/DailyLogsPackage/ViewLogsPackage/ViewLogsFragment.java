package com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.harati.hrmsuite.Helper.DatabaseHandler;
import com.harati.hrmsuite.Helper.NetworkManager;
import com.harati.hrmsuite.MainPackage.Acitivity.MainActivity;
import com.harati.hrmsuite.R;
import com.harati.hrmsuite.Retrofit.Interface.ApiInterface;
import com.harati.hrmsuite.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.hrmsuite.UserSessionManager.UserSessionManager;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.Model;

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
 * Created by User on 8/23/2017.
 */

public class ViewLogsFragment extends Fragment {
    RecyclerView logs_recycler_view;
    private ApiInterface apiInterface;
    UserSessionManager userSessionManager;
    DatabaseHandler databaseHandler;
    private List<DailyLogsDetailModel.DataLogs> dataLogsList = new ArrayList<DailyLogsDetailModel.DataLogs>();
    SwipeRefreshLayout swipeRefreshLayout;
    TextView dateFromTo;
    TextView emptyMsg;
    TextView viewALL;
    DateConverter dateConverter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.logs_recycler_view, container, false);
        logs_recycler_view = (RecyclerView) view.findViewById(R.id.logs_recycler_view);
        apiInterface = RetrofitClient.getApiService();
        dateConverter = new DateConverter();
        userSessionManager = new UserSessionManager(getContext());
        databaseHandler = DatabaseHandler.getInstance(getContext());
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        logs_recycler_view = (RecyclerView) view.findViewById(R.id.logs_recycler_view);
        dateFromTo = (TextView) view.findViewById(R.id.dateFomTo);
        emptyMsg = (TextView) view.findViewById(R.id.emptyMsg);
        viewALL = (TextView) view.findViewById(R.id.viewALL);
        getDailyLogsFromServer();
        final Vibrator vibe = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);


        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        logs_recycler_view.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vibe.vibrate(100);
                swipeRefreshLayout.setSoundEffectsEnabled(true);
                getDailyLogsFromServer();

            }
        });
        viewALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), ViewDailyLogsActivity.class));
            }
        });
        return view;
    }

    public void getDailyLogsFromServer() {
        swipeRefreshLayout.setRefreshing(true);
        emptyMsg.setVisibility(View.VISIBLE);
        emptyMsg.setText("Searching Data..");

        if (NetworkManager.isConnected(getContext())) {
            Call<DailyLogsDetailModel> call = apiInterface.getDailyLogs(userSessionManager.getKeyUsercode(), userSessionManager.getKeyAccessToken(), "Limit", "");
            call.enqueue(new Callback<DailyLogsDetailModel>() {
                @Override
                public void onResponse(Call<DailyLogsDetailModel> call, Response<DailyLogsDetailModel> response) {
                    if (response.isSuccessful()) {
                        Log.d("res----", response.body().getMsgTitle() + "");
                        if (response.body().getMsgTitle().equals("Success")) {
                            Log.d("res11111111----", response.body().getData().toString());
                            databaseHandler.deleteAllLimitLogs();
                            for (DailyLogsDetailModel.DataLogs datalogs : response.body().getData()) {
                                Log.d("result----", datalogs.getLogDate());
                                databaseHandler.addLimitLogs(datalogs);
                            }


                            swipeRefreshLayout.setRefreshing(false);
                            prepareData();

                        } else {
                            swipeRefreshLayout.setRefreshing(false);
//                                Snackbar snackbar = Snackbar.make(((MainActivity)getActivity()).mainView, response.body().getMsg(), Snackbar.LENGTH_SHORT);
//                                snackbar.show();
                            prepareData();
                        }
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        prepareData();
                    }
                }

                @Override
                public void onFailure(Call<DailyLogsDetailModel> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection..", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    prepareData();

                }


            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
//            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection..", Snackbar.LENGTH_SHORT);
//            snackbar.show();
            prepareData();
        }


    }

    public void prepareData() {
        dataLogsList.clear();
        dataLogsList = databaseHandler.getAllLimitLogs();
        if (dataLogsList.size() > 0) {
            emptyMsg.setVisibility(View.GONE);
            logs_recycler_view.setVisibility(View.VISIBLE);
            try {
               dateFromTo.setText(getDateFromTo(dataLogsList.get(dataLogsList.size() - 1).getLogDate().toString()) + " to " + getDateFromTo(dataLogsList.get(0).getLogDate().toString()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            DailyLogsAdapter logAdapter = new DailyLogsAdapter(getContext(), dataLogsList);
            logs_recycler_view.setAdapter(logAdapter);
        } else {
            dateFromTo.setText("");
            emptyMsg.setText("No Data Available");
            logs_recycler_view.setVisibility(View.GONE);
        }

    }

    public String getDateFromTo(String dateFromTo) throws ParseException {
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateFromTo);
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
