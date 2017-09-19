package com.harati.hrmsuite.DashBoardSlidingPackage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 8/22/2017.
 */

public class HolidayModel {

    /**
     * msg : Holiday Details
     * msgTitle : Success
     * data : [{"holidayName":"Ghatasthapana","holidayDate":"2017-09-21 00:00:00.0","applicableFor":"All"}]
     */
    @SerializedName("msg")
    private String msg;
    @SerializedName("msgTitle")
    private String msgTitle;
    @SerializedName("data")
    private List<HolidayData> data;

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

    public List<HolidayData> getData() {
        return data;
    }

    public void setData(List<HolidayData> data) {
        this.data = data;
    }

    public static class HolidayData {
        /**
         * holidayName : Ghatasthapana
         * holidayDate : 2017-09-21 00:00:00.0
         * applicableFor : All
         */
        @SerializedName("holidayName")
        private String holidayName;
        @SerializedName("holidayDate")
        private String holidayDate;
        @SerializedName("applicableFor")
        private String applicableFor;

        public HolidayData() {
        }

        public String getHolidayName() {
            return holidayName;
        }

        public void setHolidayName(String holidayName) {
            this.holidayName = holidayName;
        }

        public String getHolidayDate() {
            return holidayDate;
        }

        public void setHolidayDate(String holidayDate) {
            this.holidayDate = holidayDate;
        }

        public String getApplicableFor() {
            return applicableFor;
        }

        public void setApplicableFor(String applicableFor) {
            this.applicableFor = applicableFor;
        }
    }
}
