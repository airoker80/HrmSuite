package com.harati.hrmsuite.RemarkPackage;

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
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.hornet.dateconverter.DatePicker.DatePickerDialog;
import com.harati.hrmsuite.Helper.DatabaseHandler;
import com.harati.hrmsuite.Helper.DatePickerAllFragment;
import com.harati.hrmsuite.Helper.NetworkManager;
import com.harati.hrmsuite.Helper.ResponseModel;
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
 * Created by User on 8/30/2017.
 */

public class RemarkFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    EditText setSelectedDateBtn;
    String date;
    EditText remarkDateEdt, reasonEdt;
    Button submitRemark;
    RecyclerView remarkRecyclerView;
    TextView emptyMsg;

    private ApiInterface apiInterface;
    UserSessionManager userSessionManager;
    DatabaseHandler databaseHandler;
    CheckBox remarkLate, remarkEarly;
    List<RemarkModel.RemarkData> remarkDataList = new ArrayList<RemarkModel.RemarkData>();
    private DateConverter dateConverter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_remark, container, false);
        apiInterface = RetrofitClient.getApiService();
        dateConverter= new DateConverter();
        userSessionManager = new UserSessionManager(getContext());
        databaseHandler = DatabaseHandler.getInstance(getContext());
        remarkRecyclerView = (RecyclerView) view.findViewById(R.id.remarkRecyclerView);
        remarkDateEdt = (EditText) view.findViewById(R.id.remarkDateEdt);
        reasonEdt = (EditText) view.findViewById(R.id.reasonEdt);
        submitRemark = (Button) view.findViewById(R.id.submitRemark);
        remarkLate = (CheckBox) view.findViewById(R.id.remarkLate);
        remarkEarly = (CheckBox) view.findViewById(R.id.remarkEarly);
        emptyMsg = (TextView) view.findViewById(R.id.emptyMsg);
        Typeface typeface = Typeface.createFromAsset(getActivity().getAssets(), "Lato-Light.ttf");
        remarkDateEdt.setTypeface(typeface);
        reasonEdt.setTypeface(typeface);
        getRemarkDetails();
        RecyclerView.LayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        remarkRecyclerView.setLayoutManager(linearLayoutManager);
        remarkDateEdt.setOnClickListener(this);
        submitRemark.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.remarkDateEdt:
                remarkDateEdt.setText("");
                if (userSessionManager.getKeyLanguage().equals("English")) {
                    showDatePicker(remarkDateEdt);
                } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
                    showNepaliDatePicker(remarkDateEdt);
                }
                break;
            case R.id.submitRemark:
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

    public void sendToServer() {
        final String remarkDate = remarkDateEdt.getText().toString().trim();

        String remarkDateSend= "";
        if(!remarkDate.equals("")) {
            if (userSessionManager.getKeyLanguage().equals("English")) {
                remarkDateSend = remarkDate;
            } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
                remarkDateSend = convertDate(remarkDate);
            }
        }
        final String reason = reasonEdt.getText().toString().trim();
        String notLate = null;
        String notEarly = null;
        if (remarkEarly.isChecked()) {
            notEarly = "notEarly";
        }
        if (remarkLate.isChecked()) {
            notLate = "notLate";
        }

        if (!remarkDateSend.isEmpty() && reason.isEmpty()) {
            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Please fill out all the field.", Snackbar.LENGTH_SHORT);
            snackbar.show();
        } else if (remarkDateSend.isEmpty() && !reason.isEmpty()) {
            remarkDateEdt.setError("This field can not be blank");
            remarkDateEdt.requestFocus();
        } else if (!remarkDateSend.isEmpty() && reason.isEmpty()) {
            reasonEdt.setError("This field can not be blank");
            reasonEdt.requestFocus();
        } else {
            if (NetworkManager.isConnected(getContext())) {
                Call<ResponseModel> call = apiInterface.saveRemark(userSessionManager.getKeyUsercode(), userSessionManager.getKeyAccessToken(),
                        remarkDateSend, reason, notLate, notEarly);
                call.enqueue(new Callback<ResponseModel>() {
                    @Override
                    public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                        if(response.isSuccessful()) {
                            if (response.body().getMsgTitle().equals("Success")) {
                                Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                reasonEdt.setText("");
                                remarkDateEdt.setText("");
                                getRemarkDetails();

                            } else {
                                Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, response.body().getMsg(), Snackbar.LENGTH_SHORT);
                                snackbar.show();
                                prepareData();
                            }
                        }else {
                            Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection", Snackbar.LENGTH_SHORT);
                            snackbar.show();
                            prepareData();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseModel> call, Throwable t) {
                        Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection..", Snackbar.LENGTH_SHORT);
                        snackbar.show();
                        prepareData();
                    }


                });
            } else {
                Snackbar snackbar = Snackbar.make(((MainActivity) getActivity()).mainView, "Error in connection..", Snackbar.LENGTH_SHORT);
                snackbar.show();
                prepareData();

            }
        }

    }

    public void getRemarkDetails() {
        emptyMsg.setVisibility(View.VISIBLE);
        emptyMsg.setText("Searching Data..");

        if (NetworkManager.isConnected(getContext())) {
            Call<RemarkModel> call = apiInterface.getRemark(userSessionManager.getKeyUsercode(), userSessionManager.getKeyAccessToken());
            call.enqueue(new Callback<RemarkModel>() {
                @Override
                public void onResponse(Call<RemarkModel> call, Response<RemarkModel> response) {
                    if (response.isSuccessful()) {
                        if (response.body().getMsgTitle().equals("Success")) {
                            Log.d("res11111111----", response.body().getData().toString());
                            databaseHandler.deleteRemark();
                            for (RemarkModel.RemarkData remarkData : response.body().getData()) {

                                databaseHandler.addRemark(remarkData);
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
                public void onFailure(Call<RemarkModel> call, Throwable t) {
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
        remarkDataList.clear();
        remarkDataList = databaseHandler.getRemark();
        if (remarkDataList.size() > 0) {
            emptyMsg.setVisibility(View.GONE);
            remarkRecyclerView.setVisibility(View.VISIBLE);
            RemarkAdapter remarkAdapter = new RemarkAdapter(getContext(), remarkDataList);
            remarkRecyclerView.setAdapter(remarkAdapter);


        } else {
            emptyMsg.setText("No Data Available");
            remarkRecyclerView.setVisibility(View.GONE);
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
}
