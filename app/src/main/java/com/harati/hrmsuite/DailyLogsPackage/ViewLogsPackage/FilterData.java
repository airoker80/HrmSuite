package com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage;

import android.widget.Filter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/29/2017.
 */
public class FilterData extends Filter {
    AllLogsAdapter adapter;
    List<AllDailyLogsDetailModel> filterList;


    public FilterData(List<AllDailyLogsDetailModel> filterList, AllLogsAdapter adapter) {
        this.adapter = adapter;
        this.filterList = filterList;

    }

    //FILTERING OCURS
    @Override
    protected FilterResults performFiltering(CharSequence constraint) {
        FilterResults results = new FilterResults();

        //CHECK CONSTRAINT VALIDITY
        if (constraint != null && constraint.length() > 0) {
            //CHANGE TO UPPER
            constraint = constraint.toString().toUpperCase();
            //STORE OUR FILTERED restaurants
            List<AllDailyLogsDetailModel> filtered = new ArrayList<>();

            for (int i = 0; i < filterList.size(); i++) {
                //CHECK
                if (filterList.get(i).getLogInTime().toUpperCase().contains(constraint) || filterList.get(i).getLogOutTime().toUpperCase().contains(constraint)
                        || filterList.get(i).getRemarks().toUpperCase().contains(constraint)|| filterList.get(i).getEarlyMinutes().toUpperCase().contains(constraint)
                        || filterList.get(i).getLateMinutes().toUpperCase().contains(constraint)|| filterList.get(i).getLogDateEnglish().toUpperCase().contains(constraint)
                        || filterList.get(i).getOverTime().toUpperCase().contains(constraint)|| filterList.get(i).getShiftInTime().toUpperCase().contains(constraint)
                        || filterList.get(i).getShiftOutTime().toUpperCase().contains(constraint)|| filterList.get(i).getUnderTime().toUpperCase().contains(constraint)
                        || filterList.get(i).getWorkTime().toUpperCase().contains(constraint)|| filterList.get(i).getLogDateNepali().contains(constraint)) {
                    //ADD restaurant TO FILTERED restaurants
                    filtered.add(filterList.get(i));
                }
            }

            results.count = filtered.size();
            results.values = filtered;
        } else {
            results.count = filterList.size();
            results.values = filterList;

        }


        return results;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {

        adapter.dataLogsList = (List<AllDailyLogsDetailModel>) results.values;

        //REFRESH
        adapter.notifyDataSetChanged();
    }
}

