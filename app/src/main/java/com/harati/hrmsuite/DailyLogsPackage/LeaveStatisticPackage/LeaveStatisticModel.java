package com.harati.hrmsuite.DailyLogsPackage.LeaveStatisticPackage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 8/23/2017.
 */

public class LeaveStatisticModel {

    /**
     * msg : Leave details Status
     * msgTitle : Success
     * data : [{"id":72,"leave_name":"Casual Leave","days":null,"max_day":20,"remainingDays":15},{"id":41,"leave_name":"Maternity Leave","days":null,"max_day":52,"remainingDays":52},{"id":86,"leave_name":"Mourn Leave","days":null,"max_day":13,"remainingDays":13},{"id":55,"leave_name":"Official Trip","days":null,"max_day":180,"remainingDays":180},{"id":42,"leave_name":"Paid Leave","days":null,"max_day":24,"remainingDays":24},{"id":119,"leave_name":"Sick Leave","days":null,"max_day":15,"remainingDays":15},{"id":114,"leave_name":"Substitute Holiday","days":null,"max_day":20,"remainingDays":20}]
     */
    @SerializedName("msg")
    private String msg;
    @SerializedName("msgTitle")
    private String msgTitle;
    @SerializedName("data")
    private List<LeaveData> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsgTitle() {
        return msgTitle;
    }

    public void setMsgTitle(String msgTitle) {
        this.msgTitle = msgTitle;
    }

    public List<LeaveData> getData() {
        return data;
    }

    public void setData(List<LeaveData> data) {
        this.data = data;
    }

    public static class LeaveData {
        /**
         * id : 72
         * leave_name : Casual Leave
         * days : null
         * max_day : 20
         * remainingDays : 15
         */
        @SerializedName("id")
        private String id;
        @SerializedName("leave_name")
        private String leave_name;
        @SerializedName("days")
        private String days;
        @SerializedName("max_day")
        private String max_day;
        @SerializedName("remainingDays")
        private String remainingDays;

        public LeaveData() {
        }

        public LeaveData(String id, String leave_name, String days, String max_day, String remainingDays) {
            this.id = id;
            this.leave_name = leave_name;
            this.days = days;
            this.max_day = max_day;
            this.remainingDays = remainingDays;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLeave_name() {
            return leave_name;
        }

        public void setLeave_name(String leave_name) {
            this.leave_name = leave_name;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

        public String getMax_day() {
            return max_day;
        }

        public void setMax_day(String max_day) {
            this.max_day = max_day;
        }

        public String getRemainingDays() {
            return remainingDays;
        }

        public void setRemainingDays(String remainingDays) {
            this.remainingDays = remainingDays;
        }
    }
}
