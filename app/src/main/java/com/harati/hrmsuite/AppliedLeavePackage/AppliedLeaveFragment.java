package com.harati.hrmsuite.AppliedLeavePackage;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.harati.hrmsuite.DailyLogsPackage.LeaveStatisticPackage.LeaveStatisticModel;
import com.harati.hrmsuite.Helper.DatabaseHandler;
import com.harati.hrmsuite.Helper.DatePickerAllFragment;
import com.harati.hrmsuite.Helper.NetworkManager;
import com.harati.hrmsuite.Helper.ResponseModel;
import com.harati.hrmsuite.MainPackage.Acitivity.MainActivity;
import com.harati.hrmsuite.Retrofit.Interface.ApiInterface;
import com.harati.hrmsuite.Retrofit.RetrofiltClient.RetrofitClient;
import com.harati.hrmsuite.UserSessionManager.UserSessionManager;
import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.Model;
import com.harati.hrmsuite.R;

import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
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

public class AppliedLeaveFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    TextView setSelectedDateBtn;
    TextView from, to, emptyMsg;
    EditText reason;
    String date, lvType;
    List<LeaveModel.AppliedLeaveData> leaveModelList = new ArrayList<LeaveModel.AppliedLeaveData>();
    RecyclerView appledLeaveRV;
    TextView fullDay, halfDay;
    Button addLeave;
    Spinner applyLeaveType;
    private ApiInterface apiInterface;
    UserSessionManager userSessionManager;
    DatabaseHandler databaseHandler;
    private List<LeaveStatisticModel.LeaveData> leaveStatisticModelArrayList = new ArrayList<LeaveStatisticModel.LeaveData>();
    private List<String> leaveType = new ArrayList<String>();
    private DateConverter dateConverter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_applied_leave, container, false);
        dateConverter = new DateConverter();
        apiInterface = RetrofitClient.getApiService();
        userSessionManager = new UserSessionManager(getContext());
        databaseHandler = DatabaseHandler.getInstance(getContext());
        appledLeaveRV = (RecyclerView) view.findViewById(R.id.appledLeaveRV);
        fullDay = (TextView) view.findViewById(R.id.fullDay);
        halfDay = (TextView) view.findViewById(R.id.halfDay);
        //fromToDate=(TextView) view.findViewById(R.id.fromToDate);
        addLeave = (Button) view.findViewById(R.id.addLeave);

        from = (TextView) view.findViewById(R.id.from);
        to = (TextView) view.findViewById(R.id.to);
        emptyMsg = (TextView) view.findViewById(R.id.emptyMsg);
        reason = (EditText) view.findViewById(R.id.reason);
        applyLeaveType = (Spinner) view.findViewById(R.id.applyLeaveType);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "Lato-Light.ttf");
        addLeave.setText("Submit");
        addLeave.setTypeface(typeface);

        reason.setTypeface(typeface);

        halfDay.setOnClickListener(this);
        fullDay.setOnClickListener(this);
        from.setOnClickListener(this);
        addLeave.setOnClickListener(this);
        to.setOnClickListener(this);
        setAdapter();
        getAppliedLeave();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        appledLeaveRV.setLayoutManager(linearLayoutManager);
        lvType = "Full";
        fullDay.setBackgroundColor(Color.parseColor("#1f93b0"));
        halfDay.setBackgroundColor(Color.TRANSPARENT);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.halfDay:
                lvType = "Half";
                halfDay.setBackgroundColor(Color.parseColor("#1f93b0"));
                fullDay.setBackgroundColor(Color.TRANSPARENT);
                break;
            case R.id.fullDay:
                lvType = "Full";
                fullDay.setBackgroundColor(Color.parseColor("#1f93b0"));
                halfDay.setBackgroundColor(Color.TRANSPARENT);
                break;

            case R.id.from:
                if (userSessionManager.getKeyLanguage().equals("English")) {
                    showDatePicker(from);
                } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
                    showNepaliDatePicker(from);
                }

                break;
            case R.id.to:
                if (userSessionManager.getKeyLanguage().equals("English")) {
                    showDatePicker(to);
                } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
                    showNepaliDatePicker(to);
                }

                break;
            case R.id.addLeave:
                Boolean result = false;
                try {
                    result = verifyDateSearch(from.getText()
                            .toString(), to.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if (result){
                    sendToServer();
                }else {
                    Toast.makeText(getContext(), "From date must be less than to date", Toast.LENGTH_SHORT).show();
                }


                break;
        }
    }

    public void showDatePicker(TextView fromToBtn) {

        setSelectedDateBtn = fromToBtn;


        DatePickerAllFragment date = new DatePickerAllFragment();

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

    public void getAppliedLeave() {
        emptyMsg.setVisibility(View.VISIBLE);
        emptyMsg.setText("Searching Data..");

        if (NetworkManager.isConnected(getContext())) {
            Call<LeaveModel> call = apiInterface.getAppliedLeaveDetails(userSessionManager.getKeyUsercode(), userSessionManager.getKeyAccessToken(), "appliedLeaveList");
            call.enqueue(new Callback<LeaveModel>() {
                @Override
                public void onResponse(Call<LeaveModel> call, Response<LeaveModel> response) {
                    if(response.isSuccessful()) {
                        if (response.body().getMsgTitle().equals("Success")) {
                            Log.d("res11111111----", response.body().getData().toString());
                            databaseHandler.deleteAllAppliedLeave();
                            for (LeaveModel.AppliedLeaveData appliedLeaveData : response.body().getData()) {
                                databaseHandler.addAllAppliedLeave(appliedLeaveData);
                            }
                            prepareData();
                        } else {

//                        Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, response.body().getMsg(), Snackbar.LENGTH_SHORT);
//                        snackbar.show();
                            prepareData();
                        }
                    }else {
                        prepareData();
                    }
                }

                @Override
                public void onFailure(Call<LeaveModel> call, Throwable t) {

                    Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, t.getMessage(), Snackbar.LENGTH_SHORT);
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
        leaveModelList.clear();
        leaveModelList = databaseHandler.getAllAppliedLeave();
        if (leaveModelList.size() > 0) {
            emptyMsg.setVisibility(View.GONE);
            appledLeaveRV.setVisibility(View.VISIBLE);

            AppliedLeaveAdapter appliedLeaveAdapter = new AppliedLeaveAdapter(getContext(), leaveModelList);
            appledLeaveRV.setAdapter(appliedLeaveAdapter);


        } else {
            emptyMsg.setText("No Data Available");
            appledLeaveRV.setVisibility(View.GONE);
        }

    }

    public void setAdapter() {
        leaveStatisticModelArrayList.clear();
        leaveStatisticModelArrayList = databaseHandler.getAllLeaveStatus();
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        for (LeaveStatisticModel.LeaveData leaveData : leaveStatisticModelArrayList) {
            leaveType.add(leaveData.getLeave_name() + "-(" + decimalFormat.format(Double.parseDouble(leaveData.getRemainingDays())) + ") ");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (getContext(), R.layout.text_layout, leaveType);
        applyLeaveType.setAdapter(adapter);
    }

    public void sendToServer() {

        Log.e("leavesd" , "Log.e(\"leavesd\" , leaveStartDateSend + \"\" + leaveEndDateSend);" );
        String[] split = applyLeaveType.getSelectedItem().toString().split("-");

        final String leaveName = split[0];
        String leaveStartDateSend = "";
        String leaveEndDateSend = "";
        if(from.getText().toString().matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && to.getText().toString().matches("([0-9]{4})-([0-9]{2})-([0-9]{2})")) {
            final String leaveStartDate = from.getText().toString().trim();

            if (userSessionManager.getKeyLanguage().equals("English")) {
                leaveStartDateSend = leaveStartDate;
            } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
                leaveStartDateSend = convertDate(leaveStartDate);
            }

            final String leaveEndDate = to.getText().toString().trim();

            if (userSessionManager.getKeyLanguage().equals("English")) {
                leaveEndDateSend = leaveEndDate;
            } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
                leaveEndDateSend = convertDate(leaveEndDate);
            }
        }
        final String reasonSend = reason.getText().toString().trim();

        Log.e("leavesd" , leaveStartDateSend + "===" + leaveEndDateSend);
//        Log.e("leavesd" , leaveStartDateSend + "===" + leaveEndDateSend);
        if (leaveStartDateSend.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && leaveEndDateSend.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && !reasonSend.isEmpty() && !lvType.isEmpty() && !applyLeaveType.getSelectedItem().toString().isEmpty()) {
            final String leaveId = databaseHandler.getLeaveId(leaveName);

            Log.e("leavename",leaveId);

            if (NetworkManager.isConnected(getContext())) {
                showProgressDialog("Sending Data to Server");
                Call<ResponseModel> call = apiInterface.saveLeaveApplication(userSessionManager.getKeyUsercode(), userSessionManager.getKeyAccessToken(), reasonSend,
                        leaveStartDateSend, leaveEndDateSend, leaveId, lvType);
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        hideProgressDialog();
                        if(response.isSuccessful()) {
                            if (response.body().getMsgTitle().equals("Success")) {
                                Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                reason.setText("");
                                from.setText("From Date");
                                to.setText("To Date");
                                getAppliedLeave();
                            } else {
                                Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                                snackbar.show();
                            }
                        }else {
                            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in server end", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        hideProgressDialog();
                        Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection..", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                    }


                });
            } else {
                Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection..", Snackbar.LENGTH_SHORT);
                snackbar.show();
                prepareData();
            }


        } else if (leaveStartDateSend.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && !leaveEndDateSend.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && reasonSend.isEmpty() && !lvType.isEmpty()) {
            reason.setError("This field can not be blank");
            reason.requestFocus();
        } else if (!leaveStartDateSend.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && !leaveEndDateSend.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && !reasonSend.isEmpty() && !lvType.isEmpty()) {
            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Leave Start Date not in date format.", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else if (leaveStartDateSend.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && !leaveEndDateSend.matches("([0-9]{4})-([0-9]{2})-([0-9]{2})") && !reasonSend.isEmpty() && !lvType.isEmpty()) {
            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Leave End Date not in date format.", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else {
            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Please fill out all the field.", Snackbar.LENGTH_SHORT);
            snackbar.show();
        }
    }

    public void showNepaliDatePicker(TextView fromToBtn) {

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

        if (month.length() ==3){
            if(String.valueOf(month.charAt(0)).equals("0")){
                Log.e("worng","wrong  " +month.substring(1));
                month = month.substring(1);
            }
        }


        fullDate = year + "-" + month + "-" + day;

        Log.e("checkdate","===+"+month);
        return fullDate;
    }

ProgressDialog progress ;
    public void showProgressDialog(String message) {

        Log.i("","showProgressDialog");
        progress = new ProgressDialog(getContext());
        if(!progress.isShowing()){
            SpannableString titleMsg= new SpannableString("Processing");
            titleMsg.setSpan(new ForegroundColorSpan(Color.BLACK),0,titleMsg.length(),0);
            progress.setTitle(titleMsg);
            progress.setMessage(message);
            progress.setCancelable(false);
            progress.setIndeterminate(true);
            progress.show();
        }
    }

    public void hideProgressDialog() {
        Log.i("","hideProgressDialog");
        if(progress !=null){
            if(progress.isShowing()){
                progress.dismiss();
            }
        }

    }

    private Boolean verifyDateSearch(String dateFromText, String dateToText)
            throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date fromDate = sdf.parse(dateFromText);
        Date toDate = sdf.parse(dateToText);
        Boolean returnValue = false;
        if (fromDate.compareTo(toDate) > 0) {
            // userDeviceDetails.showToast("Date1 is after Date2");
            returnValue = false;
        } else if (fromDate.compareTo(toDate) < 0) {
            returnValue = true;
            // userDeviceDetails.showToast("Date1 is before Date2");
        } else if (fromDate.compareTo(toDate) == 0) {
            returnValue = true;
            // userDeviceDetails.showToast("Date1 is equal to Date2");
        } else {
            returnValue = false;
            // userDeviceDetails.showToast("How to get here?");
        }

        return returnValue;
    }
}
