package com.harati.hrmsuite.DailyLogsPackage.LeaveStatisticPackage;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
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

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 8/23/2017.
 */

public class LeaveStatisticFragment extends Fragment {
    RecyclerView leave_recycler_view;
    SwipeRefreshLayout swipeRefreshLayout;
    private ApiInterface apiInterface;
    UserSessionManager userSessionManager;
    DatabaseHandler databaseHandler;
    TextView emptyMsg;
    List<LeaveStatisticModel.LeaveData> leaveStatisticModelArrayList = new ArrayList<LeaveStatisticModel.LeaveData>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.leave_recycler_view, container, false);
        apiInterface = RetrofitClient.getApiService();
        userSessionManager = new UserSessionManager(getContext());
        databaseHandler = DatabaseHandler.getInstance(getContext());
        leave_recycler_view = (RecyclerView) view.findViewById(R.id.recycler_view_leave);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe);
        emptyMsg = (TextView) view.findViewById(R.id.emptyMsg);
        getLeaveStatus();
        final Vibrator vibe = (Vibrator) getContext().getSystemService(Context.VIBRATOR_SERVICE);
        RecyclerView.LayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 3);
        leave_recycler_view.setLayoutManager(linearLayoutManager);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                vibe.vibrate(100);
                swipeRefreshLayout.setSoundEffectsEnabled(true);
                getLeaveStatus();

            }
        });
        return view;
    }

    public void getLeaveStatus() {
        Log.d("enter", "entering");
        swipeRefreshLayout.setRefreshing(true);
        emptyMsg.setVisibility(View.VISIBLE);
        emptyMsg.setText("Searching Data..");
        if (NetworkManager.isConnected(getContext())) {
            Log.d("enter", "entering1111");
            Call<LeaveStatisticModel> call = apiInterface.getLeaveDetailsStatus(userSessionManager.getKeyUsercode(), userSessionManager.getKeyAccessToken(), "leaveStatusList");
            call.enqueue(new Callback<LeaveStatisticModel>() {
                @Override
                public void onResponse(Call<LeaveStatisticModel> call, Response<LeaveStatisticModel> response) {

                    if (response.isSuccessful()) {
                        if (response.body().getMsgTitle().equals("Success")) {
                            Log.d("res11111111----", response.body().getData().toString());
                            Log.d("enter", "entering111133333");
                            databaseHandler.deleteAllLeaveStatus();
                            for (LeaveStatisticModel.LeaveData leaveData : response.body().getData()) {

                                databaseHandler.addLeaveStatus(leaveData);
                            }
                            prepareData();
                            swipeRefreshLayout.setRefreshing(false);

                        } else {
                            swipeRefreshLayout.setRefreshing(false);
                            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            prepareData();

                        }
                    } else {
                        swipeRefreshLayout.setRefreshing(false);
                        Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in server end", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        prepareData();

                    }
                }

                @Override
                public void onFailure(Call<LeaveStatisticModel> call, Throwable t) {
                    Log.d("enter", "entering111144444" + t.getMessage());
                    swipeRefreshLayout.setRefreshing(false);
                    prepareData();

                }

            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Log.d("error", "rrrr");
            prepareData();
        }


    }

    public void prepareData() {
        leaveStatisticModelArrayList.clear();
        leaveStatisticModelArrayList = databaseHandler.getAllLeaveStatus();
        if (leaveStatisticModelArrayList.size() > 0) {
            emptyMsg.setVisibility(View.GONE);
            leave_recycler_view.setVisibility(View.VISIBLE);
            LeavesStatisticAdapter leavesStatisticAdapter = new LeavesStatisticAdapter(getContext(), leaveStatisticModelArrayList);
            leave_recycler_view.setAdapter(leavesStatisticAdapter);

        } else {
            emptyMsg.setText("No Data Available");
            leave_recycler_view.setVisibility(View.GONE);
        }

    }
}
