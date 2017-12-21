package com.harati.hrmsuite.Retrofit.Interface;

import com.harati.hrmsuite.AppliedLeavePackage.LeaveModel;
import com.harati.hrmsuite.CheckInOutPackage.CheckInModel;
import com.harati.hrmsuite.DailyLogsPackage.LeaveStatisticPackage.LeaveStatisticModel;
import com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage.DailyLogsDetailModel;
import com.harati.hrmsuite.DashBoardSlidingPackage.HolidayModel;
import com.harati.hrmsuite.Helper.ResponseModel;
import com.harati.hrmsuite.LoginPackage.LoginModel.LoginModel;
import com.harati.hrmsuite.RemarkPackage.RemarkModel;
import com.harati.hrmsuite.UserPackage.UserModel;


import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by User on 8/9/2017.
 */

public interface ApiInterface {
    @GET("login")
    Call<LoginModel> authenticate(@Query("username") String username, @Query("password") String password);

    @FormUrlEncoded
    @POST("forgotPassword")
    Call<ResponseModel> reset(@Field("email") String email);

    @FormUrlEncoded
    @POST("updatePassword")
    Call<ResponseModel> updatePassword(@Field("userCode") String userCode, @Field("access_token") String access_token, @Field("oldPassword") String oldPassword, @Field("newPassword") String newPassword);

    @GET("getDailyLogs")
    Call<DailyLogsDetailModel> getDailyLogs(@Query("userCode") String userCode, @Query("access_token") String access_token,
                                            @Query("dataLength") String dataLength, @Query("startLength") String startLength);

    @GET("getLeaveDetails")
    Call<LeaveStatisticModel> getLeaveDetailsStatus(@Query("userCode") String userCode, @Query("access_token") String access_token,
                                                    @Query("leaveDetails") String leaveDetails);

    @GET("getLeaveDetails")
    Call<LeaveModel> getAppliedLeaveDetails(@Query("userCode") String userCode, @Query("access_token") String access_token,
                                            @Query("leaveDetails") String leaveDetails);

    @GET("getUserDetails")
    Call<UserModel> getUserDetails(@Query("userCode") String userCode, @Query("access_token") String access_token);

    @GET("getAttendenceForgotList")
    Call<CheckInModel> getAttendenceForgotList(@Query("userCode") String userCode, @Query("access_token") String access_token);

    @GET("getUpcomingHoliday")
    Call<HolidayModel> getUpcomingHoliday(@Query("userCode") String userCode, @Query("access_token") String access_token);

    @FormUrlEncoded
    @POST("saveAttendanceForgot")
    Call<ResponseModel> saveAttendanceForgot(@Field("userCode") String userCode, @Field("access_token") String access_token
            , @Field("reason") String reason, @Field("forgotCheckInOutDate") String forgotCheckInOutDate
            , @Field("checkType") String checkType, @Field("time") String time);

    @FormUrlEncoded
    @POST("saveLeaveApplication")
    Call<ResponseModel>  saveLeaveApplication(@Field("userCode") String userCode, @Field("access_token") String access_token
            , @Field("reason") String reason, @Field("leaveStartDate") String leaveStartDate
            , @Field("leaveEndDate") String leaveEndDate, @Field("leaveId") String leaveId, @Field("leaveType") String leaveType);

    @FormUrlEncoded
    @POST("saveFirebaseToken")
    Call<ResponseModel> saveFirebaseToken(@Field("userCode") String userCode, @Field("access_token") String access_token
            , @Field("firebaseToken") String firebaseToken);

    @FormUrlEncoded
    @POST("saveRemark")
    Call<ResponseModel> saveRemark(@Query("userCode") String userCode, @Query("access_token") String access_token, @Field("remarkDate") String remarkDate,
                                   @Field("reason") String reason, @Field("notLate") String notLate, @Field("notEarly") String notEarly);

    @GET("getRemark")
    Call<RemarkModel> getRemark(@Query("userCode") String userCode, @Query("access_token") String access_token);
}
