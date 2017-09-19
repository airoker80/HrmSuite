package com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.harati.hrmsuite.Helper.DatabaseHandler;
import com.harati.hrmsuite.Helper.DividerItemDecoration;
import com.harati.hrmsuite.Helper.NetworkManager;
import com.harati.hrmsuite.R;
import com.harati.hrmsuite.Retrofit.Interface.ApiInterface;
import com.harati.hrmsuite.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.hrmsuite.UserSessionManager.UserSessionManager;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewDailyLogsActivity extends AppCompatActivity {
    ListView logs_recycler_view;
    private ApiInterface apiInterface;
    UserSessionManager userSessionManager;
    CoordinatorLayout coordinatorLayout;
    DatabaseHandler databaseHandler;
    private List<AllDailyLogsDetailModel> dataLogsList = new ArrayList<AllDailyLogsDetailModel>();
    //SwipeRefreshLayout swipeRefreshLayout;
    DividerItemDecoration itemDecoration;
    TextView emptyMsg;
    private AllLogsAdapter logAdapter;
    private DateConverter dateConverter;
    Boolean firstFetch = false;
    int displayStart;
    int displayLength;
    int totalLogsCount;
    private boolean nextFinish = false;
    private boolean loading = false;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_daily_logs);
        getSupportActionBar().setTitle("ALL Logs");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        apiInterface = RetrofitClient.getApiService();
        dateConverter = new DateConverter();
        userSessionManager = new UserSessionManager(getApplicationContext());
        databaseHandler = DatabaseHandler.getInstance(getApplicationContext());
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        //swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe);
        logs_recycler_view = (ListView) findViewById(R.id.logs_recycler_view);
        emptyMsg = (TextView) findViewById(R.id.emptyMsg);
        progressBar = (ProgressBar) findViewById(R.id.moreProgress);
        displayStart = 0;
        displayLength = 10;
        firstFetch = true;
        dataLogsList.clear();
        getDailyLogsFromServer();

        //   final Vibrator vibe = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);


//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                vibe.vibrate(100);
//                swipeRefreshLayout.setSoundEffectsEnabled(true);
//                getDailyLogsFromServer();
//
//            }
//        });
        logAdapter = new AllLogsAdapter(getApplicationContext(), dataLogsList);
        logs_recycler_view.setAdapter(logAdapter);
        logs_recycler_view.setOnScrollListener(
                new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView absListView, int i) {

                    }

                    @Override
                    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        final int lastItem = firstVisibleItem + visibleItemCount;
                        int preLast = 0;
                        if (lastItem == totalItemCount) {
                            if (preLast != lastItem) {
                                if (!nextFinish) {
                                    if (!loading) {
                                        loading = true;
                                        progressBar.setVisibility(View.VISIBLE);
                                        getDailyLogsFromServer();
                                        Log.e("here", "here");
                                    }
                                }
//                                if (displayLength < totalLogsCount) {
//                                    getDailyLogsFromServer();
//                                }
                            }
                        }
                    }
                }
        );

    }

    public void getDailyLogsFromServer() {
        //swipeRefreshLayout.setRefreshing(true);
        emptyMsg.setVisibility(View.VISIBLE);
        emptyMsg.setText("Searching Data..");

        if (NetworkManager.isConnected(getApplicationContext())) {
            Call<DailyLogsDetailModel> call = apiInterface.getDailyLogs(userSessionManager.getKeyUsercode(), userSessionManager.getKeyAccessToken(), "All",
                    String.valueOf(displayStart));
            call.enqueue(new Callback<DailyLogsDetailModel>() {
                @Override
                public void onResponse(Call<DailyLogsDetailModel> call, Response<DailyLogsDetailModel> response) {
                    if(response.isSuccessful()){
                    loading = false;
                    Log.d("res----", response.body().getMsgTitle() + "");
                    if (response.body().getMsgTitle().equals("Success")) {
                        Log.d("res11111111----", response.body().getData().toString());
                        if (firstFetch) {
                            databaseHandler.deleteAllLogs();
                            firstFetch = false;
                        }
                        totalLogsCount = Integer.parseInt(response.body().getTotalLogsCount());

                        for (DailyLogsDetailModel.DataLogs datalogs : response.body().getData()) {
                            Date date = null;

                            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                            try {
                                date = new SimpleDateFormat("yyyy-MM-dd").parse(datalogs.getLogDate());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            String lodDetails = dateFormat.format(date);
                            String[] logTime = lodDetails.split("-");
                            String logDateEnglish = dateFormat.format(date);
                            Model nepDate = dateConverter.getNepaliDate(Integer.parseInt(logTime[0]), Integer.parseInt(logTime[1]), Integer.parseInt(logTime[2]));
                            String year = String.valueOf(nepDate.getYear());
                            String month;
                            if (nepDate.getMonth() < 10)
                                month = "0" + (nepDate.getMonth() + 1);
                            else
                                month = String.valueOf(nepDate.getMonth() + 1);
                            String day;
                            if (nepDate.getDay() < 10)
                                day = "0" + nepDate.getDay();
                            else
                                day = String.valueOf(nepDate.getDay());

                            String logDateNepali = year + "-" + month + "-" + day;
                            String workTime = null;
                            if (!datalogs.getWorkTime().equals("")&& !datalogs.getWorkTime().equals("null")) {
                                String[] time = datalogs.getWorkTime().split(":");
                                int hours = Integer.parseInt(time[0]);
                                int minutes = Integer.parseInt(time[1]);
                                if (hours > 1 && minutes > 1)
                                    workTime = hours + "hrs" + minutes + "mins";
                                else
                                    workTime = hours + "hr" + minutes + "min";

                            } else {
                                workTime = datalogs.getWorkTime();
                            }
                            databaseHandler.addAllLogs(new AllDailyLogsDetailModel(datalogs.getEarlyMinutes(), datalogs.getLateMinutes(), datalogs.getShiftInTime(),
                                    datalogs.getOverTime(), datalogs.getUnderTime(), workTime, datalogs.getLogInTime(), datalogs.getRemarks(),
                                    logDateEnglish, logDateNepali, datalogs.getShiftOutTime(), datalogs.getLogOutTime()));
                        }


                        // swipeRefreshLayout.setRefreshing(false);
                        prepareData();

                    } else {
                        //swipeRefreshLayout.setRefreshing(false);
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        totalLogsCount = databaseHandler.getAllLogsCount();
                        prepareData();
                    }
                    }
                    else {
                        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Error in server end", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        totalLogsCount = databaseHandler.getAllLogsCount();
                        prepareData();
                    }
                }

                @Override
                public void onFailure(Call<DailyLogsDetailModel> call, Throwable t) {
                    loading = false;
                    //swipeRefreshLayout.setRefreshing(false);
                    Snackbar snackbar = Snackbar.make(coordinatorLayout, "Error in connection..", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    totalLogsCount = databaseHandler.getAllLogsCount();
                    prepareData();

                }


            });
        } else {
            //swipeRefreshLayout.setRefreshing(false);
            Snackbar snackbar = Snackbar.make(coordinatorLayout, "Error in connection..", Snackbar.LENGTH_SHORT);
            snackbar.show();
            totalLogsCount = databaseHandler.getAllLogsCount();
            prepareData();
        }


    }

    public void prepareData() {
        List<AllDailyLogsDetailModel> data = new ArrayList<AllDailyLogsDetailModel>();
        data.clear();
        data = databaseHandler.getAllLogs(0, displayLength);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }
        if (data.size() > 0) {
            emptyMsg.setVisibility(View.GONE);
            logs_recycler_view.setVisibility(View.VISIBLE);
            dataLogsList.clear();
            dataLogsList.addAll(data);
            if (displayStart > totalLogsCount) {
                nextFinish = true;
            }
            if (logAdapter != null) {
                displayStart = displayStart + 10;
                displayLength = displayLength + 10;
            }
            if (NetworkManager.isConnected(getApplicationContext())) {
                logAdapter.notifyDataSetChanged();
            }

        } else {
            emptyMsg.setText("No Data Available");
            logs_recycler_view.setVisibility(View.GONE);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchViewAndroidActionBar = (SearchView) MenuItemCompat.getActionView(searchViewItem);
        searchViewAndroidActionBar.setIconifiedByDefault(false);
        searchViewAndroidActionBar.clearFocus();

        searchViewAndroidActionBar.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                //FILTER AS YOU TYPE
                logAdapter.getFilter().filter(query);
                return false;
            }
        });
        searchViewAndroidActionBar.setOnQueryTextFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    searchViewAndroidActionBar.setIconified(true);
                }
            }
        });
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
