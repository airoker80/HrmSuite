package com.harati.hrmsuite.AppliedLeavePackage;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.harati.hrmsuite.UserSessionManager.UserSessionManager;
import com.harati.hrmsuite.R;
import com.hornet.dateconverter.DateConverter;
import com.hornet.dateconverter.Model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by User on 8/20/2017.
 */

public class AppliedLeaveAdapter extends RecyclerView.Adapter<AppliedLeaveAdapter.ViewHolder> {
    Context context;
    List<LeaveModel.AppliedLeaveData> leaveModelList = new ArrayList<LeaveModel.AppliedLeaveData>();
    DateConverter dateConverter;
    UserSessionManager userSessionManager;

    public AppliedLeaveAdapter(Context context, List<LeaveModel.AppliedLeaveData> leaveModelList) {
        this.context = context;
        this.leaveModelList = leaveModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_appled_leaves,parent,false);
        dateConverter= new DateConverter();
        userSessionManager= new UserSessionManager(context);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LeaveModel.AppliedLeaveData leaveModel = leaveModelList.get(position);
        Date leaveFrom=null;
        Date leaveTo=null;

        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        try {
            leaveFrom = new SimpleDateFormat("yyyy-MM-dd").parse(leaveModel.getLeaveFrom());
            leaveTo= new SimpleDateFormat("yyyy-MM-dd").parse(leaveModel.getLeaveTo());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formatFrom= dateFormat.format(leaveFrom);
        String formatTo= dateFormat.format(leaveTo);
        String [] leaveDateDetailsFrom= formatFrom.split("-");
        String [] leaveDateDetailsTo= formatTo.split("-");
        if (userSessionManager.getKeyLanguage().equals("English")){
            holder.leaveDate.setText(dateFormat.format(leaveFrom)+" to "+ dateFormat.format(leaveTo));
        }
        else if (userSessionManager.getKeyLanguage().equals("Nepali")){
            Model nepDateFrom = dateConverter.getNepaliDate(Integer.parseInt(leaveDateDetailsFrom[0]), Integer.parseInt(leaveDateDetailsFrom[1]), Integer.parseInt(leaveDateDetailsFrom[2]));
            Model nepDateTo = dateConverter.getNepaliDate(Integer.parseInt(leaveDateDetailsTo[0]), Integer.parseInt(leaveDateDetailsTo[1]), Integer.parseInt(leaveDateDetailsTo[2]));
            String yearFrom = String.valueOf(nepDateFrom.getYear());
            String monthFrom;
            if(nepDateFrom.getMonth()<10)
                monthFrom= "0"+(nepDateFrom.getMonth()+1);
            else
                monthFrom= String.valueOf(nepDateFrom.getMonth()+1);
            String dayFrom;
            if(nepDateFrom.getDay()<10)
                dayFrom= "0"+nepDateFrom.getDay();
            else
                dayFrom= String.valueOf(nepDateFrom.getDay());

            String yearTo = String.valueOf(nepDateFrom.getYear());
            String monthTo;
            if(nepDateTo.getMonth()<10)
                monthTo= "0"+(nepDateTo.getMonth()+1);
            else
                monthTo= String.valueOf(nepDateTo.getMonth()+1);
            String dayTo;
            if(nepDateTo.getDay()<10)
                dayTo= "0"+nepDateTo.getDay();
            else
                dayTo= String.valueOf(nepDateTo.getDay());
            holder.leaveDate.setText(yearFrom+"-"+monthFrom+"-"+dayFrom+" to "+ yearTo+"-"+monthTo+"-"+dayTo);
        }


        holder.leaveType.setText(leaveModel.getSubject()+" - "+leaveModel.getLeaveType());
        holder.leaveDetails.setText(leaveModel.getBody());

        if (leaveModel.getStatus().equals("Denied")){
            holder.approvedImg.setImageResource(R.drawable.ic_red_cross);
        }
        else if (leaveModel.getStatus().equals("Approved")){
            holder.approvedImg.setImageResource(R.drawable.ic_tick_circular);
        }
        else if (leaveModel.getStatus().equals("Recommended")||leaveModel.getStatus().equals("Not Checked")){
            holder.approvedImg.setImageResource(R.drawable.ic_pending_circular);
        }

    }

    @Override
    public int getItemCount() {
        return leaveModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView leaveDate,leaveType,leaveDetails;
        public ImageView approvedImg;
        public ViewHolder(View itemView) {
            super(itemView);
            leaveDate=(TextView)itemView.findViewById(R.id.leaveDate);
            leaveType=(TextView)itemView.findViewById(R.id.leaveType);
            leaveDetails=(TextView)itemView.findViewById(R.id.leaveDetails);
            approvedImg=(ImageView)itemView.findViewById(R.id.approvedImg);
        }
    }
}
