package com.harati.hrmsuite.CheckInOutPackage;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import com.harati.hrmsuite.Helper.ProgressHelper;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.TimePicker.RadialPickerLayout;
import com.hornet.dateconverter.TimePicker.TimePickerDialog;
import com.harati.hrmsuite.Helper.DatabaseHandler;
import com.harati.hrmsuite.Helper.DatePickerAllFragment;
import com.harati.hrmsuite.Helper.NetworkManager;
import com.harati.hrmsuite.Helper.ResponseModel;
import com.harati.hrmsuite.Helper.TimePickerFragment;
import com.harati.hrmsuite.MainPackage.Acitivity.MainActivity;
import com.harati.hrmsuite.R;
import com.harati.hrmsuite.Retrofit.Interface.ApiInterface;
import com.harati.hrmsuite.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.hrmsuite.UserSessionManager.UserSessionManager;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.Model;

import java.text.DateFormatSymbols;
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
 * Created by User on 8/27/2017.
 */

public class CheckInOutFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener,TimePickerDialog.OnTimeSetListener {
    EditText setSelectedDateBtn;
    EditText setSelectedTime;
    String date;
    String time, checkType;
    EditText dateEdt, timeEdit, reason;
    Button submitCheckInOut;
    RecyclerView checkInRecyclerView;
    List<CheckInModel.CheckInOutData> checkInModelList = new ArrayList<CheckInModel.CheckInOutData>();
    TextView emptyMsg, checkIn, checkOut;
    private ApiInterface apiInterface;
    UserSessionManager userSessionManager;
    DatabaseHandler databaseHandler;
    private DateConverter dateConverter;
    ProgressHelper progressHelper;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_in_out, container, false);
        progressHelper = new ProgressHelper(getContext());
        dateConverter = new DateConverter();
        apiInterface = RetrofitClient.getApiService();
        userSessionManager = new UserSessionManager(getContext());
        databaseHandler = DatabaseHandler.getInstance(getContext());
        checkInRecyclerView = (RecyclerView) view.findViewById(R.id.checkInRecyclerView);
        dateEdt = (EditText) view.findViewById(R.id.dateEdt);
        timeEdit = (EditText) view.findViewById(R.id.timeEdit);
        reason = (EditText) view.findViewById(R.id.reason);
        emptyMsg = (TextView) view.findViewById(R.id.emptyMsg);
        checkIn = (TextView) view.findViewById(R.id.checkIn);
        checkOut = (TextView) view.findViewById(R.id.checkOut);
        submitCheckInOut = (Button) view.findViewById(R.id.submitCheckInOut);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "Lato-Light.ttf");
        dateEdt.setTypeface(typeface);
        timeEdit.setTypeface(typeface);
        reason.setTypeface(typeface);
        getCheckInOutDetails();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        checkInRecyclerView.setLayoutManager(linearLayoutManager);
        dateEdt.setOnClickListener(this);
        timeEdit.setOnClickListener(this);
        checkIn.setOnClickListener(this);
        checkOut.setOnClickListener(this);
        submitCheckInOut.setOnClickListener(this);

        checkType = "In";
        checkIn.setBackgroundColor(Color.parseColor("#1f93b0"));
        checkOut.setBackgroundColor(Color.TRANSPARENT);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.dateEdt:
                dateEdt.setText("");
                if (userSessionManager.getKeyLanguage().equals("English")) {
                    showDatePicker(dateEdt);
                } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
                    showNepaliDatePicker(dateEdt);
                }

                break;
            case R.id.timeEdit:
                timeEdit.setText("");
                showTimePicker(timeEdit);
                break;
            case R.id.checkIn:
                checkType = "In";
                checkIn.setBackgroundColor(Color.parseColor("#1f93b0"));
                checkOut.setBackgroundColor(Color.TRANSPARENT);
                break;
            case R.id.checkOut:
                checkType = "Out";
                checkOut.setBackgroundColor(Color.parseColor("#1f93b0"));
                checkIn.setBackgroundColor(Color.TRANSPARENT);
                break;
            case R.id.submitCheckInOut:
                sendToServer();

                break;
        }
    }


    public void showDatePicker(EditText fromToBtn) {

        setSelectedDateBtn = fromToBtn;


        DatePickerAllFragment date = new DatePickerAllFragment();

        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        String date1 = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String[] selDate = date1.split("-");

        args.putInt("year", Integer.parseInt(selDate[0]));
        args.putInt("month", Integer.parseInt(selDate[1]) - 1);
        args.putInt("day", Integer.parseInt(selDate[2]));

        date.setArguments(args);

        date.setCallBack(ondate);

        date.show(getActivity().getSupportFragmentManager(), "Date Picker");


    }

    android.app.DatePickerDialog.OnDateSetListener ondate =
            new android.app.DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {


                    Calendar calendar = Calendar.getInstance();
                    calendar.set(year, monthOfYear, dayOfMonth);
                    String newFormatWeekdays = new DateFormatSymbols().getWeekdays()[calendar.get(Calendar.DAY_OF_WEEK)];
                    String monthName = new DateFormatSymbols().getShortMonths()[monthOfYear];
                    String month;
                    String day;

                    if (monthOfYear + 1 < 10) {
                        month = "0" + (monthOfYear + 1);
                    } else {
                        month = String.valueOf(monthOfYear + 1);
                    }
                    if (dayOfMonth < 10) {
                        day = "0" + (dayOfMonth);
                    } else {
                        day = String.valueOf(dayOfMonth);
                    }
                    date = String.valueOf(year) + "-" + month + "-" + day;

                    setSelectedDateBtn.setText(date);

                }
            };

    public void getCheckInOutDetails() {
        emptyMsg.setVisibility(View.VISIBLE);
        emptyMsg.setText("Searching Data..");

        if (NetworkManager.isConnected(getContext())) {
            Call<CheckInModel> call = apiInterface.getAttendenceForgotList(userSessionManager.getKeyUsercode(), userSessionManager.getKeyAccessToken());
            call.enqueue(new Callback<CheckInModel>() {
                @Override
                public void onResponse(Call<CheckInModel> call, Response<CheckInModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getMsgTitle().equals("Success")) {
                            Log.d("res11111111----", response.body().getData().toString());
                            databaseHandler.deleteCheckInOut();
                            for (CheckInModel.CheckInOutData checkInOutData : response.body().getData()) {

                                databaseHandler.addCheckInOut(checkInOutData);
                            }

                            prepareData();


                        } else {
//                        Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, response.body().getMsg(), Snackbar.LENGTH_SHORT);
//                        snackbar.show();
                            prepareData();
                        }
                    } else {
                        prepareData();
                    }
                }

                @Override
                public void onFailure(Call<CheckInModel> call, Throwable t) {
                    Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection..", Snackbar.LENGTH_SHORT);
                    snackbar.show();
                    prepareData();
                }


            });
        } else {
//            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection..", Snackbar.LENGTH_SHORT);
//            snackbar.show();
            prepareData();
        }


    }

    public void prepareData() {
        checkInModelList.clear();
        checkInModelList = databaseHandler.getCheckInOut();
        if (checkInModelList.size() > 0) {
            emptyMsg.setVisibility(View.GONE);
            checkInRecyclerView.setVisibility(View.VISIBLE);
            CheckInAdapter checkInAdapter = new CheckInAdapter(getContext(), checkInModelList);
            checkInRecyclerView.setAdapter(checkInAdapter);


        } else {
            emptyMsg.setText("No Data Available");
            checkInRecyclerView.setVisibility(View.GONE);
        }

    }

    public void showTimePicker(EditText time) {

        setSelectedTime = time;

        Calendar now = Calendar.getInstance();
        TimePickerDialog dpd = TimePickerDialog.newInstance(this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false);

        dpd.setAccentColor(Color.parseColor("#5bb3dd"));

        dpd.show(getActivity().getFragmentManager(), "TimePicker");



    }



    public void sendToServer() {
        final String timeSend = timeEdit.getText().toString().trim();
        String dateSend = dateEdt.getText().toString().trim();
        String dateSendToServer = "";
        if(!dateSend.equals("")) {
            if (userSessionManager.getKeyLanguage().equals("English")) {
                dateSendToServer = dateSend;
            } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
                dateSendToServer = convertDate(dateSend);
            }
        }

        final String reasonSend = reason.getText().toString().trim();
        if (!timeSend.isEmpty() && !dateSendToServer.isEmpty() && !reasonSend.isEmpty() && !checkType.isEmpty()) {
            Log.d("data", timeSend + "-" + dateSend + "-" + reasonSend + "-" + checkType);

            if (NetworkManager.isConnected(getContext())) {
                progressHelper.showProgressDialog("Sending Data to server");
                Call<ResponseModel> call = apiInterface.saveAttendanceForgot(userSessionManager.getKeyUsercode(), userSessionManager.getKeyAccessToken(), reasonSend,
                        dateSendToServer, checkType, timeSend);
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        progressHelper.hideProgressDialog();
                        if (response.isSuccessful()) {
                            if (response.body().getMsgTitle().equals("Success")) {
                                Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                reason.setText("");
                                dateEdt.setText("");
                                timeEdit.setText("");
                                getCheckInOutDetails();
                            } else {
                                Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        } else {
                            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in server end", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        progressHelper.hideProgressDialog();
                        Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection..", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }


                });
            } else {
                Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection..", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }


        } else if (reasonSend.isEmpty()) {
            reason.setError("This field can not be blank");
            timeEdit.requestFocus();
        } else {
            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Please fill out all the field.", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    public void showNepaliDatePicker(EditText fromToBtn) {

        setSelectedDateBtn = fromToBtn;


        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));
        dpd.setAccentColor(Color.parseColor("#5bb3dd"));
        dpd.setTitle("DatePicker Title");


        dpd.show(getActivity().getSupportFragmentManager(), "DatePicker");


    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String month, day;
        if (monthOfYear + 1 < 10) {
            month = "0" + (monthOfYear + 1);
        } else {
            month = String.valueOf(monthOfYear + 1);
        }
        if (dayOfMonth < 10) {
            day = "0" + (dayOfMonth);
        } else {
            day = String.valueOf(dayOfMonth);
        }
        date = String.valueOf(year) + "-" + month + "-" + day;

        setSelectedDateBtn.setText(date);
    }

    public String convertDate(String dateFull) {
        String fullDate = null;
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateFull);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String dateDetails = new SimpleDateFormat("yyyy-MM-dd").format(date);
        String[] dateTime = dateDetails.split("-");
        Model engDate = dateConverter.getEnglishDate(Integer.parseInt(dateTime[0]), Integer.parseInt(dateTime[1]), Integer.parseInt(dateTime[2]));
        String year = String.valueOf(engDate.getYear());
        String month;
        String day;
        if (engDate.getMonth() < 10)
            month = "0" + (engDate.getMonth() + 1);
        else
            month = String.valueOf(engDate.getMonth() + 1);
        if (engDate.getDay() < 10)
            day = "0" + (engDate.getDay());
        else
            day = String.valueOf(engDate.getDay());


        fullDate = year + "-" + month + "-" + day;
        return fullDate;
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);

        String hourInTime;
        String minInTime;
        if (hourOfDay < 10)
            hourInTime = "0" + hourOfDay;
        else
            hourInTime = String.valueOf(hourOfDay);
        if (minute < 10)
            minInTime = "0" + minute;
        else
            minInTime = String.valueOf(minute);
        time = hourInTime + ":" + minInTime;

        setSelectedTime.setText(time);
    }
}
