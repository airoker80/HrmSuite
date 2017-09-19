package com.harati.hrmsuite.CheckInOutPackage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 8/28/2017.
 */

public class CheckInModel {

    /**
     * msg : Check In/Out details
     * msgTitle : Success
     * data : [{"sendDate":"2017-07-12 00:56:04.0","checkInOutDate":"2017-07-11 00:00:00.0","time":"18:10:00","status":"Approve","remark":"null","reason":"I forgot to check out.","type":"In"}]
     */
    @SerializedName("msg")
    private String msg;
    @SerializedName("msgTitle")
    private String msgTitle;
    @SerializedName("data")
    private List<CheckInOutData> data;

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

    public List<CheckInOutData> getData() {
        return data;
    }

    public void setData(List<CheckInOutData> data) {
        this.data = data;
    }

    public static class CheckInOutData {
        /**
         * sendDate : 2017-07-12 00:56:04.0
         * checkInOutDate : 2017-07-11 00:00:00.0
         * time : 18:10:00
         * status : Approve
         * remark : null
         * reason : I forgot to check out.
         * type : In
         */
        @SerializedName("sendDate")
        private String sendDate;
        @SerializedName("checkInOutDate")
        private String checkInOutDate;
        @SerializedName("time")
        private String time;
        @SerializedName("status")
        private String status;
        @SerializedName("remark")
        private String remark;
        @SerializedName("reason")
        private String reason;
        @SerializedName("type")
        private String type;

        public CheckInOutData() {
        }

        public String getSendDate() {
            return sendDate;
        }

        public void setSendDate(String sendDate) {
            this.sendDate = sendDate;
        }

        public String getCheckInOutDate() {
            return checkInOutDate;
        }

        public void setCheckInOutDate(String checkInOutDate) {
            this.checkInOutDate = checkInOutDate;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
