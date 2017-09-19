package com.harati.hrmsuite.AppliedLeavePackage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 8/28/2017.
 */

public class LeaveModel {

    /**
     * msg : Applied Leave details
     * msgTitle : Success
     * data : [{"body":"inflammation  on arms need to rest with Arm Support Belt for a week.","totalDays":2,"status":"Approved","leaveFrom":"2017-07-16T18:15:00Z","subject":"Casual Leave","leaveTo":"2017-07-17T18:15:00Z","adminRemarks":null,"requestDate":"2017-07-13T20:02:38Z","checkedDate":"2017-07-19T20:08:30Z","leaveType":"Full"},{"body":"BE Exam, there is no 'causal leave' option so I had problem with apply","totalDays":1,"status":"Approved","leaveFrom":"2017-06-08T18:15:00Z","subject":"Casual Leave","leaveTo":"2017-06-08T18:15:00Z","adminRemarks":null,"requestDate":"2017-06-07T01:35:59Z","checkedDate":"2017-06-07T18:15:00Z","leaveType":"Full"},{"body":"BE exam","totalDays":1,"status":"Approved","leaveFrom":"2017-06-01T18:15:00Z","subject":"Casual Leave","leaveTo":"2017-06-01T18:15:00Z","adminRemarks":null,"requestDate":"2017-05-27T17:07:19Z","checkedDate":"2017-05-27T18:15:00Z","leaveType":"Full"},{"body":"BE exam","totalDays":1,"status":"Approved","leaveFrom":"2017-05-28T18:15:00Z","subject":"Casual Leave","leaveTo":"2017-05-28T18:15:00Z","adminRemarks":null,"requestDate":"2017-05-21T00:44:57Z","checkedDate":"2017-05-20T18:15:00Z","leaveType":"Full"},{"body":"BE exam + eye checkup","totalDays":2,"status":"Approved","leaveFrom":"2017-05-23T18:15:00Z","subject":"Casual Leave","leaveTo":"2017-05-24T18:15:00Z","adminRemarks":null,"requestDate":"2017-05-21T00:43:38Z","checkedDate":"2017-05-20T18:15:00Z","leaveType":"Full"}]
     */
    @SerializedName("msg")
    private String msg;
    @SerializedName("msgTitle")
    private String msgTitle;
    @SerializedName("data")
    private List<AppliedLeaveData> data;

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

    public List<AppliedLeaveData> getData() {
        return data;
    }

    public void setData(List<AppliedLeaveData> data) {
        this.data = data;
    }

    public static class AppliedLeaveData {
        /**
         * body : inflammation  on arms need to rest with Arm Support Belt for a week.
         * totalDays : 2
         * status : Approved
         * leaveFrom : 2017-07-16T18:15:00Z
         * subject : Casual Leave
         * leaveTo : 2017-07-17T18:15:00Z
         * adminRemarks : null
         * requestDate : 2017-07-13T20:02:38Z
         * checkedDate : 2017-07-19T20:08:30Z
         * leaveType : Full
         */
        @SerializedName("body")
        private String body;
        @SerializedName("totalDays")
        private Double totalDays;
        @SerializedName("status")
        private String status;
        @SerializedName("leaveFrom")
        private String leaveFrom;
        @SerializedName("subject")
        private String subject;
        @SerializedName("leaveTo")
        private String leaveTo;
        @SerializedName("adminRemarks")
        private String adminRemarks;
        @SerializedName("requestDate")
        private String requestDate;
        @SerializedName("checkedDate")
        private String checkedDate;
        @SerializedName("leaveType")
        private String leaveType;

        public AppliedLeaveData() {
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public Double getTotalDays() {
            return totalDays;
        }

        public void setTotalDays(Double totalDays) {
            this.totalDays = totalDays;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getLeaveFrom() {
            return leaveFrom;
        }

        public void setLeaveFrom(String leaveFrom) {
            this.leaveFrom = leaveFrom;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getLeaveTo() {
            return leaveTo;
        }

        public void setLeaveTo(String leaveTo) {
            this.leaveTo = leaveTo;
        }

        public String getAdminRemarks() {
            return adminRemarks;
        }

        public void setAdminRemarks(String adminRemarks) {
            this.adminRemarks = adminRemarks;
        }

        public String getRequestDate() {
            return requestDate;
        }

        public void setRequestDate(String requestDate) {
            this.requestDate = requestDate;
        }

        public String getCheckedDate() {
            return checkedDate;
        }

        public void setCheckedDate(String checkedDate) {
            this.checkedDate = checkedDate;
        }

        public String getLeaveType() {
            return leaveType;
        }

        public void setLeaveType(String leaveType) {
            this.leaveType = leaveType;
        }
    }
}
