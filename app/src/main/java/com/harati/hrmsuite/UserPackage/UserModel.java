package com.harati.hrmsuite.UserPackage;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by User on 8/21/2017.
 */

public class UserModel {

    /**
     * msg : User Details
     * msgTitle : Success
     * data : {"temperoryAdd":"","birthday":"1994-04-05 00:00:00.0","jobType":"null","passportNo":"","phone":"9844400099","weight":"","permanentAdd":"","bloodGroup":"","status":"single","registerDate":"2017-04-05 01:00:33.0","lastname":"Tamang","firstname":"Arjun ","companyName":"HARATI Computer Services Pvt .Ltd.","temporaryAddType":"Own Home","height":"","citizenNumber":"","gender":"male","drivingLicenceNo":"","photoUrl":"http://localhost:8080/pms/static/images/default-user.png"}
     */
    @SerializedName("msg")
    private String msg;
    @SerializedName("msgTitle")
    private String msgTitle;
    @SerializedName("data")
    private List<UserData> data;

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

    public List<UserData> getData() {
        return data;
    }

    public void setData(List<UserData> data) {
        this.data = data;
    }

    public static class UserData {
        /**
         * temperoryAdd :
         * birthday : 1994-04-05 00:00:00.0
         * jobType : null
         * passportNo :
         * phone : 9844400099
         * weight :
         * permanentAdd :
         * bloodGroup :
         * status : single
         * registerDate : 2017-04-05 01:00:33.0
         * lastname : Tamang
         * firstname : Arjun
         * companyName : HARATI Computer Services Pvt .Ltd.
         * temporaryAddType : Own Home
         * height :
         * citizenNumber :
         * gender : male
         * drivingLicenceNo :
         * photoUrl : http://localhost:8080/pms/static/images/default-user.png
         */
        @SerializedName("temperoryAdd")
        private String temperoryAdd;
        @SerializedName("birthday")
        private String birthday;
        @SerializedName("jobType")
        private String jobType;
        @SerializedName("passportNo")
        private String passportNo;
        @SerializedName("phone")
        private String phone;
        @SerializedName("weight")
        private String weight;
        @SerializedName("permanentAdd")
        private String permanentAdd;
        @SerializedName("bloodGroup")
        private String bloodGroup;
        @SerializedName("status")
        private String status;
        @SerializedName("registerDate")
        private String registerDate;
        @SerializedName("lastname")
        private String lastname;
        @SerializedName("firstname")
        private String firstname;
        @SerializedName("companyName")
        private String companyName;
        @SerializedName("temporaryAddType")
        private String temporaryAddType;
        @SerializedName("height")
        private String height;
        @SerializedName("citizenNumber")
        private String citizenNumber;
        @SerializedName("gender")
        private String gender;
        @SerializedName("drivingLicenceNo")
        private String drivingLicenceNo;
        @SerializedName("photoUrl")
        private String photoUrl;

        public UserData() {
        }

        public String getTemperoryAdd() {
            return temperoryAdd;
        }

        public void setTemperoryAdd(String temperoryAdd) {
            this.temperoryAdd = temperoryAdd;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }

        public String getJobType() {
            return jobType;
        }

        public void setJobType(String jobType) {
            this.jobType = jobType;
        }

        public String getPassportNo() {
            return passportNo;
        }

        public void setPassportNo(String passportNo) {
            this.passportNo = passportNo;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getPermanentAdd() {
            return permanentAdd;
        }

        public void setPermanentAdd(String permanentAdd) {
            this.permanentAdd = permanentAdd;
        }

        public String getBloodGroup() {
            return bloodGroup;
        }

        public void setBloodGroup(String bloodGroup) {
            this.bloodGroup = bloodGroup;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getRegisterDate() {
            return registerDate;
        }

        public void setRegisterDate(String registerDate) {
            this.registerDate = registerDate;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getCompanyName() {
            return companyName;
        }

        public void setCompanyName(String companyName) {
            this.companyName = companyName;
        }

        public String getTemporaryAddType() {
            return temporaryAddType;
        }

        public void setTemporaryAddType(String temporaryAddType) {
            this.temporaryAddType = temporaryAddType;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getCitizenNumber() {
            return citizenNumber;
        }

        public void setCitizenNumber(String citizenNumber) {
            this.citizenNumber = citizenNumber;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getDrivingLicenceNo() {
            return drivingLicenceNo;
        }

        public void setDrivingLicenceNo(String drivingLicenceNo) {
            this.drivingLicenceNo = drivingLicenceNo;
        }

        public String getPhotoUrl() {
            return photoUrl;
        }

        public void setPhotoUrl(String photoUrl) {
            this.photoUrl = photoUrl;
        }
    }
}
