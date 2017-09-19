package com.harati.hrmsuite.LoginPackage.LoginModel;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 8/14/2017.
 */

public class LoginModel {

    /**
     * msg : User Login Success
     * msgTitle : Success
     * loginStatus : Success
     * access_token : WJ28KA4D2KGL26GH2NTUPN2VGU24F2Q4EJHTMPDLOMF1HRX0VS85OR6FHCB7U4P5HUXK0JLY9YNF93OZW1ACPV7RMKSM0RR71QM0
     * userCode : aXtHCNwenCHa4bC9fng2aP5pgzIZPA3KWY3hrYCAn60=
     */

    @SerializedName("msg")
    private String msg;
    @SerializedName("msgTitle")
    private String msgTitle;
    @SerializedName("loginStatus")
    private String loginStatus;
    @SerializedName("access_token")
    private String access_token;
    @SerializedName("userCode")
    private String userCode;

    public LoginModel(String msg, String msgTitle, String loginStatus, String access_token, String userCode) {
        this.msg = msg;
        this.msgTitle = msgTitle;
        this.loginStatus = loginStatus;
        this.access_token = access_token;
        this.userCode = userCode;
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

    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getUserCode() {
        return userCode;
    }

    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }
}
