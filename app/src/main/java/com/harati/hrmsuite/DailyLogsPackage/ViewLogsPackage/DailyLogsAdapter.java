package com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harati.hrmsuite.R;
import com.harati.hrmsuite.UserSessionManager.UserSessionManager;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 8/26/2017.
 */

public class DailyLogsAdapter extends RecyclerView.Adapter<DailyLogsAdapter.ViewHolder> {
    Context context;
    List<DailyLogsDetailModel.DataLogs> dataLogsList = new ArrayList<DailyLogsDetailModel.DataLogs>();
    UserSessionManager userSessionManager;
    DateConverter dateConverter;

    public DailyLogsAdapter(Context context, List<DailyLogsDetailModel.DataLogs> dataLogsList) {
        this.context = context;
        this.dataLogsList = dataLogsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_daily_logs, parent, false);
        dateConverter = new DateConverter();
        userSessionManager = new UserSessionManager(context);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DailyLogsDetailModel.DataLogs dataLogs = dataLogsList.get(position);
        if (dataLogs.getRemarks().equals("[Absent]") || dataLogs.getRemarks() == null) {
            holder.logImg.setImageResource(R.drawable.ic_pending_circular);
        } else if (dataLogs.getRemarks().equals("[Off]")) {
            holder.logImg.setImageResource(R.drawable.ic_red_cross);
        } else if (dataLogs.getRemarks().equals("")) {
            holder.logImg.setImageResource(R.drawable.ic_tick_circular);

        }
        Date date = null;

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dataLogs.getLogDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String lodDetails = dateFormat.format(date);
        String[] logTime = lodDetails.split("-");
        if (userSessionManager.getKeyLanguage().equals("English")) {
            holder.logDate.setText(dateFormat.format(date));
        } else if (userSessionManager.getKeyLanguage().equals("Nepali")) {
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

            holder.logDate.setText(year + "-" + month + "-" + day);
        }
        if (!dataLogs.getWorkTime().equals("")&& !dataLogs.getWorkTime().equals("null")) {
            String[] time = dataLogs.getWorkTime().split(":");
            int hours = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);
            if (hours > 1 && minutes > 1)
                holder.logDuration.setText(hours + "hrs" + minutes + "mins");
            else
                holder.logDuration.setText(hours + "hr" + minutes + "min");
        } else {
            holder.logDuration.setText("");
        }
        if (!dataLogs.getLogInTime().equals("")&& !dataLogs.getLogInTime().equals("null")&&
                !dataLogs.getLogOutTime().equals("")&& !dataLogs.getLogOutTime().equals("null")) {
            holder.logTime.setText(dataLogs.getLogInTime() + "-" + dataLogs.getLogOutTime());
        }else{
            holder.logTime.setText("");
        }
        holder.logLeaveType.setText(dataLogs.getRemarks());


    }

    @Override
    public int getItemCount() {
        return dataLogsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView logDate, logTime, logDuration, logLeaveType;
        ImageView logImg;

        public ViewHolder(View itemView) {
            super(itemView);
            logImg = (ImageView) itemView.findViewById(R.id.logImg);
            logDate = (TextView) itemView.findViewById(R.id.logDate);
            logTime = (TextView) itemView.findViewById(R.id.logTime);
            logDuration = (TextView) itemView.findViewById(R.id.logDuration);
            logLeaveType = (TextView) itemView.findViewById(R.id.logLeaveType);
        }
    }

}
