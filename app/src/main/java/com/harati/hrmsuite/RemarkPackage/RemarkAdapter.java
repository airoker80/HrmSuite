package com.harati.hrmsuite.RemarkPackage;

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

public class RemarkAdapter extends RecyclerView.Adapter<RemarkAdapter.ViewHolder> {
    Context context;
    List<RemarkModel.RemarkData> remarkDataList = new ArrayList<RemarkModel.RemarkData>();
    UserSessionManager userSessionManager;
    DateConverter dateConverter;

    public RemarkAdapter(Context context, List<RemarkModel.RemarkData> remarkDataList) {
        this.context = context;
        this.remarkDataList = remarkDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.model_remark,parent,false);
        dateConverter= new DateConverter();
        userSessionManager= new UserSessionManager(context);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        RemarkModel.RemarkData remarkData = remarkDataList.get(position);
        Date date=null;

        DateFormat dateFormat= new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(remarkData.getRemarkDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String remarkDetails= dateFormat.format(date);
        String [] remarkTime= remarkDetails.split("-");
        if (userSessionManager.getKeyLanguage().equals("English")){
            holder.remarkDate.setText(dateFormat.format(date));
        }
        else if (userSessionManager.getKeyLanguage().equals("Nepali")){
            Model nepDate = dateConverter.getNepaliDate(Integer.parseInt(remarkTime[0]), Integer.parseInt(remarkTime[1]), Integer.parseInt(remarkTime[2]));
            String year = String.valueOf(nepDate.getYear());
            String month;
            if(nepDate.getMonth()<10)
                month= "0"+nepDate.getMonth();
            else
                month= String.valueOf(nepDate.getMonth());
            String day;
            if(nepDate.getDay()<10)
                day= "0"+nepDate.getDay();
            else
                day= String.valueOf(nepDate.getDay());

            holder.remarkDate.setText(year+"-"+month+"-"+day);
        }
        holder.remarkReason.setText(remarkData.getRemark());
        holder.statusRemark.setText("Status -"+remarkData.getStatus());

        if (remarkData.getStatus().equals("Approve")){
            holder.remarkStatus.setImageResource(R.drawable.ic_tick_circular);
        }
        else if (remarkData.getStatus().equals("Not Checked")||remarkData.getStatus().equals("Recommended")){
            holder.remarkStatus.setImageResource(R.drawable.ic_pending_circular);
        }
        else if (remarkData.getStatus().equals("Denied")){
            holder.remarkStatus.setImageResource(R.drawable.ic_red_cross);
        }
    }

    @Override
    public int getItemCount() {
        return remarkDataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView remarkDate,remarkReason,statusRemark;
        ImageView remarkStatus;
        public ViewHolder(View itemView) {
            super(itemView);
            remarkDate=(TextView)itemView.findViewById(R.id.remarkDate);
            remarkReason=(TextView)itemView.findViewById(R.id.remarkReason);
            remarkStatus=(ImageView)itemView.findViewById(R.id.remarkStatus);
            statusRemark=(TextView)itemView.findViewById(R.id.statusRemark);
        }
    }
}
