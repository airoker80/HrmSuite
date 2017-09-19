package com.harati.hrmsuite.CheckInOutPackage;

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
 * Created by User on 8/28/2017.
 */

public class CheckInAdapter extends RecyclerView.Adapter<CheckInAdapter.ViewHolder> {
    Context context;
    List<CheckInModel.CheckInOutData> checkInModelList = new ArrayList<CheckInModel.CheckInOutData>();
    private UserSessionManager userSessionManager;
    DateConverter dateConverter;


    public CheckInAdapter(Context context, List<CheckInModel.CheckInOutData> checkInModelList) {
        this.context = context;
        this.checkInModelList = checkInModelList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_check_in_out,parent,false);
        dateConverter = new DateConverter();
        userSessionManager= new UserSessionManager(context);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CheckInModel.CheckInOutData checkInModel = checkInModelList.get(position);
        Date date=null;

        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(checkInModel.getCheckInOutDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String checkTimeDetail= dateFormat.format(date);
        String [] checkDetails= checkTimeDetail.split("-");

        if (userSessionManager.getKeyLanguage().equals("English")){
            holder.checkTime.setText(dateFormat.format(date));
        }
        else if (userSessionManager.getKeyLanguage().equals("Nepali")){
            Model nepDate = dateConverter.getNepaliDate(Integer.parseInt(checkDetails[0]), Integer.parseInt(checkDetails[1]), Integer.parseInt(checkDetails[2]));
            String year = String.valueOf(nepDate.getYear());
            String month;
            if(nepDate.getMonth()<10)
                month= "0"+(nepDate.getMonth()+1);
            else
                month= String.valueOf(nepDate.getMonth()+1);
            String day;
            if(nepDate.getDay()<10)
                day= "0"+nepDate.getDay();
            else
                day= String.valueOf(nepDate.getDay());

            holder.checkTime.setText(year+"-"+month+"-"+day);
        }

        holder.checkReason.setText(checkInModel.getReason());
        holder.checkinTime.setText(checkInModel.getTime());
        holder.in_out.setText(checkInModel.getType());
        if (checkInModel.getStatus().equals("Approve")||checkInModel.getStatus().equals("Approved")){
            holder.CheckinConfirmation.setImageResource(R.drawable.ic_tick_circular);
        }
        else if (checkInModel.getStatus().equals("Not Checked")|| checkInModel.getStatus().equals("Recommended") || checkInModel.getStatus().equals("null")){
            holder.CheckinConfirmation.setImageResource(R.drawable.ic_pending_circular);
        }
        else if (checkInModel.getStatus().equals("Denied")){
            holder.CheckinConfirmation.setImageResource(R.drawable.ic_red_cross);
        }
    }

    @Override
    public int getItemCount() {
        return checkInModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView checkTime,in_out,checkinTime,checkReason;
        ImageView CheckinConfirmation;
        public ViewHolder(View itemView) {
            super(itemView);
            checkTime=(TextView)itemView.findViewById(R.id.checkTime);
            in_out=(TextView)itemView.findViewById(R.id.in_out);
            checkinTime=(TextView)itemView.findViewById(R.id.checkinTime);
            checkReason=(TextView)itemView.findViewById(R.id.checkReason);
            CheckinConfirmation=(ImageView)itemView.findViewById(R.id.CheckinConfirmation);
        }
    }
}
