package com.harati.hrmsuite.RemarkPackage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 8/29/2017.
 */

public class RemarkModel {


    /**
     * msg : Remark Detail
     * msgTitle : Success
     * data : [{"isDoNotCountLateMin":false,"status":"Not Checked","remark":"aa","applicationDate":"2017-08-30 15:39:45.0","remarkDate":"2017-08-30 00:00:00.0","isDoNotCountEarlyMin":false},{"isDoNotCountLateMin":true,"status":"Not Checked","remark":"aa","applicationDate":"2017-08-30 15:40:51.0","remarkDate":"2017-08-30 00:00:00.0","isDoNotCountEarlyMin":true}]
     */
    @SerializedName("msg")
    private String msg;
    @SerializedName("msgTitle")
    private String msgTitle;
    @SerializedName("data")
    private List<RemarkData> data;

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

    public List<RemarkData> getData() {
        return data;
    }

    public void setData(List<RemarkData> data) {
        this.data = data;
    }

    public static class RemarkData {
        /**
         * isDoNotCountLateMin : false
         * status : Not Checked
         * remark : aa
         * applicationDate : 2017-08-30 15:39:45.0
         * remarkDate : 2017-08-30 00:00:00.0
         * isDoNotCountEarlyMin : false
         */
        @SerializedName("isDoNotCountLateMin")
        private String isDoNotCountLateMin;
        @SerializedName("status")
        private String status;
        @SerializedName("remark")
        private String remark;
        @SerializedName("applicationDate")
        private String applicationDate;
        @SerializedName("remarkDate")
        private String remarkDate;
        @SerializedName("isDoNotCountEarlyMin")
        private String isDoNotCountEarlyMin;

        public RemarkData() {
        }

        public String getIsDoNotCountLateMin() {
            return isDoNotCountLateMin;
        }

        public void setIsDoNotCountLateMin(String isDoNotCountLateMin) {
            this.isDoNotCountLateMin = isDoNotCountLateMin;
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

        public String getApplicationDate() {
            return applicationDate;
        }

        public void setApplicationDate(String applicationDate) {
            this.applicationDate = applicationDate;
        }

        public String getRemarkDate() {
            return remarkDate;
        }

        public void setRemarkDate(String remarkDate) {
            this.remarkDate = remarkDate;
        }

        public String getIsDoNotCountEarlyMin() {
            return isDoNotCountEarlyMin;
        }

        public void setIsDoNotCountEarlyMin(String isDoNotCountEarlyMin) {
            this.isDoNotCountEarlyMin = isDoNotCountEarlyMin;
        }
    }
}
