package com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 8/26/2017.
 */

public class DailyLogsDetailModel {


    /**
     * msg : Daily Logs details
     * msgTitle : Success
     * data : [{"earlyMinutes":"","lateMinutes":"","shiftInTime":"1970-01-01T03:30:00Z","overTime":"","underTime":"","workTime":"","logInTime":"No In","remarks":"[Absent]","logDate":"2017-08-14T18:15:00Z","shiftOutTime":"1970-01-01T12:30:00Z","logOutTime":"No Out"},{"earlyMinutes":"","lateMinutes":"","shiftInTime":"1970-01-01T03:30:00Z","overTime":"","underTime":"","workTime":"","logInTime":"No In","remarks":"[Absent]","logDate":"2017-08-13T18:15:00Z","shiftOutTime":"1970-01-01T12:30:00Z","logOutTime":"No Out"},{"earlyMinutes":"","lateMinutes":"","shiftInTime":"1970-01-01T03:30:00Z","overTime":"","underTime":"","workTime":"","logInTime":"No In","remarks":"[Off]","logDate":"2017-08-12T18:15:00Z","shiftOutTime":"1970-01-01T12:30:00Z","logOutTime":"No Out"},{"earlyMinutes":"","lateMinutes":"","shiftInTime":"1970-01-01T03:30:00Z","overTime":"","underTime":"","workTime":"","logInTime":"No In","remarks":"[Off]","logDate":"2017-08-11T18:15:00Z","shiftOutTime":"1970-01-01T12:30:00Z","logOutTime":"No Out"},{"earlyMinutes":"","lateMinutes":"","shiftInTime":"1970-01-01T03:30:00Z","overTime":"","underTime":"","workTime":"","logInTime":"No In","remarks":"[Absent]","logDate":"2017-08-10T18:15:00Z","shiftOutTime":"1970-01-01T12:30:00Z","logOutTime":"No Out"},{"earlyMinutes":"","lateMinutes":"","shiftInTime":"1970-01-01T03:30:00Z","overTime":"","underTime":"","workTime":"","logInTime":"No In","remarks":"[Absent]","logDate":"2017-08-09T18:15:00Z","shiftOutTime":"1970-01-01T12:30:00Z","logOutTime":"No Out"},{"earlyMinutes":"","lateMinutes":"","shiftInTime":"1970-01-01T03:30:00Z","overTime":"","underTime":"","workTime":"","logInTime":"No In","remarks":"[Absent]","logDate":"2017-08-08T18:15:00Z","shiftOutTime":"1970-01-01T12:30:00Z","logOutTime":"No Out"},{"earlyMinutes":"","lateMinutes":"","shiftInTime":"1970-01-01T03:30:00Z","overTime":"","underTime":"","workTime":"","logInTime":"No In","remarks":"[Absent]","logDate":"2017-08-07T18:15:00Z","shiftOutTime":"1970-01-01T12:30:00Z","logOutTime":"No Out"},{"earlyMinutes":"","lateMinutes":"","shiftInTime":"1970-01-01T03:30:00Z","overTime":"","underTime":"","workTime":"","logInTime":"No In","remarks":"[Absent]","logDate":"2017-08-06T18:15:00Z","shiftOutTime":"1970-01-01T12:30:00Z","logOutTime":"No Out"}]
     */
    @SerializedName("msg")
    private String msg;
    @SerializedName("msgTitle")
    private String msgTitle;
    @SerializedName("data")
    private List<DataLogs> data;
    @SerializedName("totalLogsCount")
    private String totalLogsCount;

    public DailyLogsDetailModel() {
    }

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

    public List<DataLogs> getData() {
        return data;
    }

    public void setData(List<DataLogs> data) {
        this.data = data;
    }
    public String getTotalLogsCount() {
        return totalLogsCount;
    }

    public void setTotalLogsCount(String totalLogsCount) {
        this.totalLogsCount = totalLogsCount;
    }

    public static class DataLogs {
        /**
         * earlyMinutes :
         * lateMinutes :
         * shiftInTime : 1970-01-01T03:30:00Z
         * overTime :
         * underTime :
         * workTime :
         * logInTime : No In
         * remarks : [Absent]
         * logDate : 2017-08-14T18:15:00Z
         * shiftOutTime : 1970-01-01T12:30:00Z
         * logOutTime : No Out
         */
        @SerializedName("earlyMinutes")
        private String earlyMinutes;
        @SerializedName("lateMinutes")
        private String lateMinutes;
        @SerializedName("shiftInTime")
        private String shiftInTime;
        @SerializedName("overTime")
        private String overTime;
        @SerializedName("underTime")
        private String underTime;
        @SerializedName("workTime")
        private String workTime;
        @SerializedName("logInTime")
        private String logInTime;
        @SerializedName("remarks")
        private String remarks;
        @SerializedName("logDate")
        private String logDate;
        @SerializedName("shiftOutTime")
        private String shiftOutTime;
        @SerializedName("logOutTime")
        private String logOutTime;

        public DataLogs(String earlyMinutes, String lateMinutes, String shiftInTime, String overTime, String underTime, String workTime, String logInTime, String remarks, String logDate, String shiftOutTime, String logOutTime) {
            this.earlyMinutes = earlyMinutes;
            this.lateMinutes = lateMinutes;
            this.shiftInTime = shiftInTime;
            this.overTime = overTime;
            this.underTime = underTime;
            this.workTime = workTime;
            this.logInTime = logInTime;
            this.remarks = remarks;
            this.logDate = logDate;
            this.shiftOutTime = shiftOutTime;
            this.logOutTime = logOutTime;
        }

        public DataLogs() {
        }

        public String getEarlyMinutes() {
            return earlyMinutes;
        }

        public void setEarlyMinutes(String earlyMinutes) {
            this.earlyMinutes = earlyMinutes;
        }

        public String getLateMinutes() {
            return lateMinutes;
        }

        public void setLateMinutes(String lateMinutes) {
            this.lateMinutes = lateMinutes;
        }

        public String getShiftInTime() {
            return shiftInTime;
        }

        public void setShiftInTime(String shiftInTime) {
            this.shiftInTime = shiftInTime;
        }

        public String getOverTime() {
            return overTime;
        }

        public void setOverTime(String overTime) {
            this.overTime = overTime;
        }

        public String getUnderTime() {
            return underTime;
        }

        public void setUnderTime(String underTime) {
            this.underTime = underTime;
        }

        public String getWorkTime() {
            return workTime;
        }

        public void setWorkTime(String workTime) {
            this.workTime = workTime;
        }

        public String getLogInTime() {
            return logInTime;
        }

        public void setLogInTime(String logInTime) {
            this.logInTime = logInTime;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getLogDate() {
            return logDate;
        }

        public void setLogDate(String logDate) {
            this.logDate = logDate;
        }

        public String getShiftOutTime() {
            return shiftOutTime;
        }

        public void setShiftOutTime(String shiftOutTime) {
            this.shiftOutTime = shiftOutTime;
        }

        public String getLogOutTime() {
            return logOutTime;
        }

        public void setLogOutTime(String logOutTime) {
            this.logOutTime = logOutTime;
        }
    }
}
