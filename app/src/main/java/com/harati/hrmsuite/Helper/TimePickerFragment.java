package com.harati.hrmsuite.Helper;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.harati.hrmsuite.R;

/**
 * Created by User on 8/11/2017.
 */

public class TimePickerFragment extends DialogFragment {
    TimePickerDialog.OnTimeSetListener onTimeSet;

    public TimePickerFragment() {
    }

    public void setCallBack(TimePickerDialog.OnTimeSetListener ontime) {
        onTimeSet = ontime;
    }

    private int hour, min;
    boolean is24;

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        hour = args.getInt("hour");
        min = args.getInt("min");
        is24 = args.getBoolean("is24");

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
    return new TimePickerDialog(getActivity(), R.style.DialogTheme, onTimeSet, hour, min, is24);
    }
}
