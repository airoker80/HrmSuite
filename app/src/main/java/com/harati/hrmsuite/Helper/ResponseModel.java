package com.harati.hrmsuite.Helper;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 8/22/2017.
 */

public class ResponseModel {

    /**
     * msg : Successfully sent !
     * msgTitle : Success
     */
    @SerializedName("msg")
    private String msg;
    @SerializedName("msgTitle")
    private String msgTitle;

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
}
