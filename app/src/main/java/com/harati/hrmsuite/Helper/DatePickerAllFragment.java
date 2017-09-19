package com.harati.hrmsuite.Helper;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.harati.hrmsuite.R;

/**
 * Created by User on 8/11/2017.
 */

public class DatePickerAllFragment extends DialogFragment {
    DatePickerDialog.OnDateSetListener ondateSet;

    public DatePickerAllFragment() {
    }

    public void setCallBack(DatePickerDialog.OnDateSetListener ondate) {
        ondateSet = ondate;
    }

    private int year, month, day;
    String type;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        year = args.getInt("year");
        month = args.getInt("month");
        day = args.getInt("day");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DatePickerDialog datePickerDialog = null;

//        if (type.equals("check")||type.equals("remark")) {
//            datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, ondateSet, year, month, day);
//            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
//
//
//        } else if (type.equals("leave")) {
        datePickerDialog = new DatePickerDialog(getActivity(), R.style.DialogTheme, ondateSet, year, month, day);


        return datePickerDialog;
    }
}
