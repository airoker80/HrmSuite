package com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage;

/**
 * Created by User on 8/26/2017.
 */

public class AllDailyLogsDetailModel {
    private String earlyMinutes;
    private String lateMinutes;
    private String shiftInTime;
    private String overTime;
    private String underTime;
    private String workTime;
    private String logInTime;
    private String remarks;
    private String logDateEnglish;
    private String logDateNepali;
    private String shiftOutTime;
    private String logOutTime;

    public AllDailyLogsDetailModel(String earlyMinutes, String lateMinutes, String shiftInTime, String overTime, String underTime, String workTime, String logInTime, String remarks, String logDateEnglish, String logDateNepali, String shiftOutTime, String logOutTime) {
        this.earlyMinutes = earlyMinutes;
        this.lateMinutes = lateMinutes;
        this.shiftInTime = shiftInTime;
        this.overTime = overTime;
        this.underTime = underTime;
        this.workTime = workTime;
        this.logInTime = logInTime;
        this.remarks = remarks;
        this.logDateEnglish = logDateEnglish;
        this.logDateNepali = logDateNepali;
        this.shiftOutTime = shiftOutTime;
        this.logOutTime = logOutTime;
    }

    public AllDailyLogsDetailModel() {
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

    public String getLogDateEnglish() {
        return logDateEnglish;
    }

    public void setLogDateEnglish(String logDateEnglish) {
        this.logDateEnglish = logDateEnglish;
    }
    public String getLogDateNepali() {
        return logDateNepali;
    }

    public void setLogDateNepali(String logDateNepali) {
        this.logDateNepali = logDateNepali;
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
