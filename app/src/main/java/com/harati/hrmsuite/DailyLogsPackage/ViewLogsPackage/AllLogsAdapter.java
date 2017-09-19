package com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.harati.hrmsuite.R;
import com.harati.hrmsuite.UserSessionManager.UserSessionManager;
import com.hornet.dateconverter.DateConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/26/2017.
 */

public class AllLogsAdapter extends BaseAdapter implements Filterable {
    Context context;
    FilterData filter;
    List<AllDailyLogsDetailModel> dataLogsList = new ArrayList<AllDailyLogsDetailModel>();
    UserSessionManager userSessionManager;
    DateConverter dateConverter;
    TextView logDate, logTime, logDuration, logLeaveType;
    ImageView logImg;

    public AllLogsAdapter(Context context, List<AllDailyLogsDetailModel> dataLogsList) {
        this.context = context;
        this.dataLogsList = dataLogsList;
    }

    @Override
    public int getCount() {
        return dataLogsList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataLogsList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup viewGroup) {
        LayoutInflater inflater = null;
        if (inflater == null)
            inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.model_daily_logs, null);
        dateConverter = new DateConverter();
        userSessionManager = new UserSessionManager(context);
        logImg = (ImageView) convertView.findViewById(R.id.logImg);
        logDate = (TextView) convertView.findViewById(R.id.logDate);
        logTime = (TextView) convertView.findViewById(R.id.logTime);
        logDuration = (TextView) convertView.findViewById(R.id.logDuration);
        logLeaveType = (TextView) convertView.findViewById(R.id.logLeaveType);
        AllDailyLogsDetailModel dataLogs = dataLogsList.get(position);
        if (dataLogs.getRemarks().equals("[Absent]") || dataLogs.getRemarks() == null) {
            logImg.setImageResource(R.drawable.ic_pending_circular);
        } else if (dataLogs.getRemarks().equals("[Off]")) {
            logImg.setImageResource(R.drawable.ic_red_cross);
        } else if (dataLogs.getRemarks().equals("")) {
            logImg.setImageResource(R.drawable.ic_tick_circular);

        }
        if (userSessionManager.getKeyLanguage().equals("English")) {
            logDate.setText(dataLogs.getLogDateEnglish());
        } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
            logDate.setText(dataLogs.getLogDateNepali());
        }


        if (!dataLogs.getWorkTime().equals("")&& !dataLogs.getWorkTime().equals("null")) {
            logDuration.setText(dataLogs.getWorkTime());
        } else {
           logDuration.setText("");
        }
        if (!dataLogs.getLogInTime().equals("")&& !dataLogs.getLogInTime().equals("null")&&
                !dataLogs.getLogOutTime().equals("")&& !dataLogs.getLogOutTime().equals("null")) {
           logTime.setText(dataLogs.getLogInTime() + "-" + dataLogs.getLogOutTime());
        }else{
            logTime.setText("");
        }
//        logDuration.setText(dataLogs.getWorkTime());
//        logTime.setText(dataLogs.getLogInTime() + "-" + dataLogs.getLogOutTime());
        logLeaveType.setText(dataLogs.getRemarks());
        return convertView;
    }
    @Override
    public Filter getFilter() {
        if (filter == null) {

            filter = new FilterData(dataLogsList, this);
        }

        return filter;
    }
}

