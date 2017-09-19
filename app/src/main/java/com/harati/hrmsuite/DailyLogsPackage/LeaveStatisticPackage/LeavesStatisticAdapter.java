package com.harati.hrmsuite.DailyLogsPackage.LeaveStatisticPackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.harati.hrmsuite.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by User on 8/23/2017.
 */

public class LeavesStatisticAdapter extends RecyclerView.Adapter<LeavesStatisticAdapter.ViewHolder>  {
    Context context;
    List<LeaveStatisticModel.LeaveData> leaveStatisticModelList = new ArrayList<LeaveStatisticModel.LeaveData>();
    static int previousViewColor = 0;
    public LeavesStatisticAdapter(Context context, List<LeaveStatisticModel.LeaveData> leaveStatisticModelList) {
        this.context = context;
        this.leaveStatisticModelList = leaveStatisticModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_leave_grid_stats,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        float leaveTakenIn = 0;
        float totalleaveInt = 0;
        LeaveStatisticModel.LeaveData leaveStatisticModel = leaveStatisticModelList.get(position);
        holder.leaveTypeName.setText(leaveStatisticModel.getLeave_name());
        holder.leaveTypeName.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        holder.leaveTypeName.setSelected(true);
        holder.leaveTypeName.setSingleLine(true);
        if(!leaveStatisticModel.getDays().equals("null")&& !leaveStatisticModel.getMax_day().equals("null")) {
            holder.leavesTaken.setText(((Double.parseDouble(leaveStatisticModel.getMax_day()) + Double.parseDouble(leaveStatisticModel.getDays())) - Double.parseDouble(leaveStatisticModel.getRemainingDays())) + "/" + (Double.parseDouble(leaveStatisticModel.getMax_day()) + Double.parseDouble(leaveStatisticModel.getDays())));
            leaveTakenIn = (float) ((Double.parseDouble(leaveStatisticModel.getMax_day()) + Double.parseDouble(leaveStatisticModel.getDays())) - Double.parseDouble(leaveStatisticModel.getRemainingDays()));
            totalleaveInt = (float) (Double.parseDouble(leaveStatisticModel.getMax_day()) + Double.parseDouble(leaveStatisticModel.getDays()));
        }else if(!leaveStatisticModel.getDays().equals("null")&& leaveStatisticModel.getMax_day().equals("null")) {
            holder.leavesTaken.setText((Double.parseDouble(leaveStatisticModel.getDays()) - Double.parseDouble(leaveStatisticModel.getRemainingDays())) + "/" + Double.parseDouble(leaveStatisticModel.getDays()));
            leaveTakenIn = (float) (Double.parseDouble(leaveStatisticModel.getDays()) - Double.parseDouble(leaveStatisticModel.getRemainingDays()));
            totalleaveInt = (float) Double.parseDouble(leaveStatisticModel.getDays());
        }else if(!leaveStatisticModel.getMax_day().equals("null")&&leaveStatisticModel.getDays().equals("null")){
            holder.leavesTaken.setText((Double.parseDouble(leaveStatisticModel.getMax_day())-Double.parseDouble(leaveStatisticModel.getRemainingDays()))+"/"+Double.parseDouble(leaveStatisticModel.getMax_day()));
            leaveTakenIn= (float) (Double.parseDouble(leaveStatisticModel.getMax_day())-Double.parseDouble(leaveStatisticModel.getRemainingDays()));
            totalleaveInt = (float) Double.parseDouble(leaveStatisticModel.getMax_day());
        }

        float floatPercent =(float) (leaveTakenIn/totalleaveInt)*100;


        int intPercent = (int) floatPercent;
        int[] androidColors = context.getResources().getIntArray(R.array.androidcolors);
        int randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];

        // to stop color from repeating in adjacent layout
        while (previousViewColor==randomAndroidColor){
            randomAndroidColor = androidColors[new Random().nextInt(androidColors.length)];
        }

        previousViewColor = randomAndroidColor;

        holder.leaveProgress.setProgress(Integer.parseInt(String.valueOf(intPercent)));
        holder.leaveProgress.getProgressDrawable().setColorFilter(
                randomAndroidColor, android.graphics.PorterDuff.Mode.SRC_IN);


    }

    @Override
    public int getItemCount() {
        return leaveStatisticModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ProgressBar leaveProgress;
        TextView leaveTypeName,leavesTaken;
        public ViewHolder(View itemView) {

            super(itemView);
            leaveProgress=(ProgressBar)itemView.findViewById(R.id.leaveProgress);
            leaveTypeName=(TextView)itemView.findViewById(R.id.leaveTypeName);
            leavesTaken=(TextView)itemView.findViewById(R.id.leavesTaken);
        }
    }
}
