package com.harati.hrmsuite.Helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.harati.hrmsuite.AppliedLeavePackage.LeaveModel;
import com.harati.hrmsuite.CheckInOutPackage.CheckInModel;
import com.harati.hrmsuite.DailyLogsPackage.LeaveStatisticPackage.LeaveStatisticModel;
import com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage.AllDailyLogsDetailModel;
import com.harati.hrmsuite.DailyLogsPackage.ViewLogsPackage.DailyLogsDetailModel;
import com.harati.hrmsuite.DashBoardSlidingPackage.HolidayModel;
import com.harati.hrmsuite.RemarkPackage.RemarkModel;
import com.harati.hrmsuite.UserPackage.UserModel;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 8/14/2017.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "hrm";


    // table name
    private static final String TABLE_DAILYLOGS_LIMIT = "limitLogs";
    private static final String TABLE_DAILYLOGS_ALL = "allLogs";
    private static final String TABLE_LEAVE_STATUS = "leaveStatus";
    private static final String TABLE_APPLIEDLEAVE_STATUS = "appliedleaveStatus";
    private static final String TABLE_USERDETAILS = "userDetails";
    private static final String TABLE_CHECKINOUT = "checkInOut";
    private static final String TABLE_HOLIDAY = "holiday";
    private static final String TABLE_REMARK = "remark";

    //DailyLogs table column names
    private static final String KEY_EARLYMINUTES = "earlyMinutes ";
    private static final String KEY_LATEMINUTES = "lateMinutes";
    private static final String KEY_SHIFTINTIME = "shiftInTime";
    private static final String KEY_OVERTIME = "overTime";
    private static final String KEY_UNDERTIME = "underTime";
    private static final String KEY_WORKTIME = "workTime";
    private static final String KEY_LOGINTIME = "logInTime";
    private static final String KEY_REMARKS = "remarks";
    private static final String KEY_LOGDATE = "logDate";
    private static final String KEY_SHIFTOUTIME = "shiftOutTime";
    private static final String KEY_LOGOUTTIME = "logOutTime";

    private static final String KEY_LOGDATEENGLISH = "logDateEnglish";
    private static final String KEY_LOGDATENEPALI = "logDateNepali";
    //LeaveStatus table column names
    private static final String KEY_ID = "id";
    private static final String KEY_LEAVENAME = "leavename";
    private static final String KEY_DAYS = "days";
    private static final String KEY_MAXDAYS = "maxdays";
    private static final String KEY_REMAININGDAYS = "remianingdays";
    //AppliedLeave Status table column names
    private static final String KEY_BODY = "body ";
    private static final String KEY_TOTALDAYS = "totalDays";
    private static final String KEY_STATUS = "status";
    private static final String KEY_LEAVEFROM = "leaveFrom";
    private static final String KEY_SUBJECT = "subject";
    private static final String KEY_LEAVETO = "leaveTo";
    private static final String KEY_ADMIN_REMARKS = "adminRemarks";
    private static final String KEY_REQUEST_DATE = "requestDate";
    private static final String KEY_CHECK_DATE = "checkedDate";
    private static final String KEY_LEAVETYPE = "leaveType";
    //User Details table column names
    private static final String KEY_TEMPORARY = "temperoryAdd";
    private static final String KEY_DOB = "birthday";
    private static final String KEY_JOBTYPE = "jobType";
    private static final String KEY_PASSPORTNO = "passportNo";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_PERMANENTADD = "permanentAdd";
    private static final String KEY_BLOOGGROUP = "bloodGroup";
    private static final String KEY_USER_STATUS = "status";
    private static final String KEY_REGISTERDATE = "registerDate";
    private static final String KEY_LASTNAME = "lastname";
    private static final String KEY_FIRSTNAME = "firstname";
    private static final String KEY_COMPANYNAME = "companyName";
    private static final String KEY_TEMPORARYADDTYPE = "temporaryAddType";
    private static final String KEY_HEIGHT = "height";
    private static final String KEY_CITIZENNO = "citizenNumber";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_DRIVINGNO = "drivingLicenceNo";
    private static final String KEY_PHOTOURL = "photoUrl";

    //CheckInOut table column names
    private static final String KEY_SENDDATE = "sendDate";
    private static final String KEY_CHECKINOUTDATE = "checkInOutDate";
    private static final String KEY_TIME = "time";
    private static final String KEY_STATUSCHECK = "status";
    private static final String KEY_REMARKSCHECK = "remark";
    private static final String KEY_REASONCHECK = "reason";
    private static final String KEY_CHECKTYPE = "type";
    //holiday table column names
    private static final String KEY_HOLIDAYNAME = "holidayName";
    private static final String KEY_HOLIDAYDATE = "holidayDate";
    private static final String KEY_APPLICABLEFOR = "applicableFor";
    //remark table column names
    private static final String KEY_REMARKCOUNTLATEMIN = "isDoNotCountLateMin";
    private static final String KEY_REMARKSTATUS = "status";
    private static final String KEY_REMARK = "remark";
    private static final String KEY_APPLICATIONDATE = "applicationDate";
    private static final String KEY_REMARKDATE = "remarkDate";
    private static final String KEY_REMARKCOUNTEARLYMIN = "isDoNotCountEarlyMin";


    private static DatabaseHandler mInstance = null;


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static DatabaseHandler getInstance(Context ctx) {

        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (mInstance == null) {
            mInstance = new DatabaseHandler(ctx.getApplicationContext());
        }
        return mInstance;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        createDailyLimitLogs(db);
        createDailyAllLogs(db);
        createLeaveStatus(db);
        createAppliedLeave(db);
        createUserDetails(db);
        createCheckInOut(db);
        createHoliday(db);
        createRemark(db);
    }


    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILYLOGS_LIMIT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DAILYLOGS_ALL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LEAVE_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPLIEDLEAVE_STATUS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERDETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CHECKINOUT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HOLIDAY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_REMARK);

        // Create tables again
        onCreate(db);
    }


    // table limit daily logs create
    public void createDailyLimitLogs(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_DAILYLOGS_LIMIT + "("
                + KEY_EARLYMINUTES + " TEXT," + KEY_LATEMINUTES + " TEXT,"
                + KEY_SHIFTINTIME + " TEXT," + KEY_OVERTIME + " TEXT," + KEY_UNDERTIME + " TEXT," + KEY_WORKTIME + " TEXT," +
                KEY_LOGINTIME + " TEXT," + KEY_REMARKS + " TEXT," + KEY_LOGDATE + " TEXT," + KEY_SHIFTOUTIME + " TEXT," +
                KEY_LOGOUTTIME + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);


    }

    // adding all data in limit daily logs
    public long addLimitLogs(DailyLogsDetailModel.DataLogs dataLogs) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EARLYMINUTES, dataLogs.getEarlyMinutes());
        values.put(KEY_LATEMINUTES, dataLogs.getLateMinutes());
        values.put(KEY_SHIFTINTIME, dataLogs.getShiftInTime());
        values.put(KEY_OVERTIME, dataLogs.getOverTime());
        values.put(KEY_UNDERTIME, dataLogs.getUnderTime());
        values.put(KEY_WORKTIME, dataLogs.getWorkTime());
        values.put(KEY_LOGINTIME, dataLogs.getLogInTime());
        values.put(KEY_REMARKS, dataLogs.getRemarks());
        values.put(KEY_LOGDATE, dataLogs.getLogDate());
        values.put(KEY_SHIFTOUTIME, dataLogs.getShiftOutTime());
        values.put(KEY_LOGOUTTIME, dataLogs.getLogOutTime());

        // Inserting Row
        long result = db.insert(TABLE_DAILYLOGS_LIMIT, null, values);
        db.close(); // Closing database connection
        return result;
    }

    // getting all data of limit daily logs
    public List<DailyLogsDetailModel.DataLogs> getAllLimitLogs() {
        List<DailyLogsDetailModel.DataLogs> dataLogsList = new ArrayList<DailyLogsDetailModel.DataLogs>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DAILYLOGS_LIMIT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                DailyLogsDetailModel.DataLogs dataLogs = new DailyLogsDetailModel.DataLogs();
                dataLogs.setEarlyMinutes(cursor.getString(0));
                dataLogs.setLateMinutes(cursor.getString(1));
                dataLogs.setShiftInTime(cursor.getString(2));
                dataLogs.setOverTime(cursor.getString(3));
                dataLogs.setUnderTime(cursor.getString(4));
                dataLogs.setWorkTime(cursor.getString(5));
                dataLogs.setLogInTime(cursor.getString(6));
                dataLogs.setRemarks(cursor.getString(7));
                dataLogs.setLogDate(cursor.getString(8));
                dataLogs.setShiftOutTime(cursor.getString(9));
                dataLogs.setLogOutTime(cursor.getString(10));


                // Adding tour to list
                dataLogsList.add(dataLogs);

            } while (cursor.moveToNext());
        }

        // return list
        db.close();
        return dataLogsList;

    }

    //delete data daily logs limit
    public void deleteAllLimitLogs() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_DAILYLOGS_LIMIT);
    }

    // table limit daily logs create
    public void createDailyAllLogs(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_DAILYLOGS_ALL + "("
                + KEY_EARLYMINUTES + " TEXT," + KEY_LATEMINUTES + " TEXT,"
                + KEY_SHIFTINTIME + " TEXT," + KEY_OVERTIME + " TEXT," + KEY_UNDERTIME + " TEXT," + KEY_WORKTIME + " TEXT," +
                KEY_LOGINTIME + " TEXT," + KEY_REMARKS + " TEXT," + KEY_LOGDATEENGLISH + " TEXT," +  KEY_LOGDATENEPALI + " TEXT," +
                KEY_SHIFTOUTIME + " TEXT," +
                KEY_LOGOUTTIME + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);


    }

    // adding all data in limit daily logs
    public long addAllLogs(AllDailyLogsDetailModel dataLogs) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EARLYMINUTES, dataLogs.getEarlyMinutes());
        values.put(KEY_LATEMINUTES, dataLogs.getLateMinutes());
        values.put(KEY_SHIFTINTIME, dataLogs.getShiftInTime());
        values.put(KEY_OVERTIME, dataLogs.getOverTime());
        values.put(KEY_UNDERTIME, dataLogs.getUnderTime());
        values.put(KEY_WORKTIME, dataLogs.getWorkTime());
        values.put(KEY_LOGINTIME, dataLogs.getLogInTime());
        values.put(KEY_REMARKS, dataLogs.getRemarks());
        values.put(KEY_LOGDATEENGLISH, dataLogs.getLogDateEnglish());
        values.put(KEY_LOGDATENEPALI, dataLogs.getLogDateNepali());
        values.put(KEY_SHIFTOUTIME, dataLogs.getShiftOutTime());
        values.put(KEY_LOGOUTTIME, dataLogs.getLogOutTime());

        // Inserting Row
        long result = db.insert(TABLE_DAILYLOGS_ALL, null, values);
        db.close(); // Closing database connection
        return result;
    }

    // getting all data of limit daily logs
    public List<AllDailyLogsDetailModel> getAllLogs(int start, int end) {
        List<AllDailyLogsDetailModel> dataLogsList = new ArrayList<AllDailyLogsDetailModel>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DAILYLOGS_ALL+ " ORDER BY " +KEY_LOGDATEENGLISH + " DESC LIMIT " + start +", "+ end;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                AllDailyLogsDetailModel dataLogs = new AllDailyLogsDetailModel();
                dataLogs.setEarlyMinutes(cursor.getString(0));
                dataLogs.setLateMinutes(cursor.getString(1));
                dataLogs.setShiftInTime(cursor.getString(2));
                dataLogs.setOverTime(cursor.getString(3));
                dataLogs.setUnderTime(cursor.getString(4));
                dataLogs.setWorkTime(cursor.getString(5));
                dataLogs.setLogInTime(cursor.getString(6));
                dataLogs.setRemarks(cursor.getString(7));
                dataLogs.setLogDateEnglish(cursor.getString(8));
                dataLogs.setLogDateNepali(cursor.getString(9));
                dataLogs.setShiftOutTime(cursor.getString(10));
                dataLogs.setLogOutTime(cursor.getString(11));


                // Adding tour to list
                dataLogsList.add(dataLogs);

            } while (cursor.moveToNext());
        }

        // return list
        db.close();
        return dataLogsList;

    }
    //count total logs
    public int getAllLogsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_DAILYLOGS_ALL;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }
    //delete data daily logs limit
    public void deleteAllLogs() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_DAILYLOGS_ALL);
    }

    // table leave Status create
    public void createLeaveStatus(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_LEAVE_STATUS + "("
                + KEY_ID + " TEXT," + KEY_LEAVENAME + " TEXT,"
                + KEY_DAYS + " TEXT," + KEY_MAXDAYS + " TEXT," + KEY_REMAININGDAYS + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);


    }

    // adding all data in leave Status
    public long addLeaveStatus(LeaveStatisticModel.LeaveData leaveData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, leaveData.getId());
        values.put(KEY_LEAVENAME, leaveData.getLeave_name());
        values.put(KEY_DAYS, leaveData.getDays());
        values.put(KEY_MAXDAYS, leaveData.getMax_day());
        values.put(KEY_REMAININGDAYS, leaveData.getRemainingDays());


        // Inserting Row
        long result = db.insert(TABLE_LEAVE_STATUS, null, values);
        db.close(); // Closing database connection
        return result;
    }

    // getting all data of leave Status
    public List<LeaveStatisticModel.LeaveData> getAllLeaveStatus() {
        List<LeaveStatisticModel.LeaveData> leaveDatas = new ArrayList<LeaveStatisticModel.LeaveData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_LEAVE_STATUS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                LeaveStatisticModel.LeaveData leaveData = new LeaveStatisticModel.LeaveData();
                leaveData.setId(cursor.getString(0));
                leaveData.setLeave_name(cursor.getString(1));
                leaveData.setDays(cursor.getString(2));
                leaveData.setMax_day(cursor.getString(3));
                leaveData.setRemainingDays(cursor.getString(4));


                // Adding tour to list
                leaveDatas.add(leaveData);

            } while (cursor.moveToNext());
        }

        // return list
        db.close();
        return leaveDatas;

    }

    // getting leave id
    public String getLeaveId(String leavename) {
        String leaveId = "";
        // Select All Query
        String selectQuery = "SELECT " + KEY_ID + " FROM " + TABLE_LEAVE_STATUS + " WHERE " + KEY_LEAVENAME + "= " + '"' + leavename + '"';

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor != null && cursor.moveToFirst()) {
            leaveId = cursor.getString(0);
            cursor.close();
        }
        // return list
        db.close();
        return leaveId;

    }

    //delete data Leave Status
    public void deleteAllLeaveStatus() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_LEAVE_STATUS);
    }

    // table Appliedleave create
    public void createAppliedLeave(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_APPLIEDLEAVE_STATUS + "("
                + KEY_BODY + " TEXT," + KEY_TOTALDAYS + " TEXT,"
                + KEY_STATUS + " TEXT," + KEY_LEAVEFROM + " TEXT," + KEY_SUBJECT + " TEXT," + KEY_LEAVETO + " TEXT," +
                KEY_ADMIN_REMARKS + " TEXT," + KEY_REQUEST_DATE + " TEXT," + KEY_CHECK_DATE + " TEXT," + KEY_LEAVETYPE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);


    }

    // adding all AppliedLeave
    public long addAllAppliedLeave(LeaveModel.AppliedLeaveData appliedLeaveData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_BODY, appliedLeaveData.getBody());
        values.put(KEY_TOTALDAYS, appliedLeaveData.getTotalDays());
        values.put(KEY_STATUS, appliedLeaveData.getStatus());
        values.put(KEY_LEAVEFROM, appliedLeaveData.getLeaveFrom());
        values.put(KEY_SUBJECT, appliedLeaveData.getSubject());
        values.put(KEY_LEAVETO, appliedLeaveData.getLeaveTo());
        values.put(KEY_ADMIN_REMARKS, appliedLeaveData.getAdminRemarks());
        values.put(KEY_REQUEST_DATE, appliedLeaveData.getRequestDate());
        values.put(KEY_CHECK_DATE, appliedLeaveData.getCheckedDate());
        values.put(KEY_LEAVETYPE, appliedLeaveData.getLeaveType());


        // Inserting Row
        long result = db.insert(TABLE_APPLIEDLEAVE_STATUS, null, values);
        db.close(); // Closing database connection
        return result;
    }


    // getting all AppliedLeave
    public List<LeaveModel.AppliedLeaveData> getAllAppliedLeave() {
        List<LeaveModel.AppliedLeaveData> appliedLeaveDataList = new ArrayList<LeaveModel.AppliedLeaveData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_APPLIEDLEAVE_STATUS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {

                LeaveModel.AppliedLeaveData appliedLeaveData = new LeaveModel.AppliedLeaveData();
                appliedLeaveData.setBody(cursor.getString(0));
                appliedLeaveData.setTotalDays(cursor.getDouble(1));
                appliedLeaveData.setStatus(cursor.getString(2));
                appliedLeaveData.setLeaveFrom(cursor.getString(3));
                appliedLeaveData.setSubject(cursor.getString(4));
                appliedLeaveData.setLeaveTo(cursor.getString(5));
                appliedLeaveData.setAdminRemarks(cursor.getString(6));
                appliedLeaveData.setRequestDate(cursor.getString(7));
                appliedLeaveData.setCheckedDate(cursor.getString(8));
                appliedLeaveData.setLeaveType(cursor.getString(9));


                // Adding tour to list
                appliedLeaveDataList.add(appliedLeaveData);

            } while (cursor.moveToNext());
        }

        // return list
        db.close();
        return appliedLeaveDataList;

    }

    //delete AppliedLeave
    public void deleteAllAppliedLeave() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_APPLIEDLEAVE_STATUS);
    }

    // table User Details create
    public void createUserDetails(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_USERDETAILS + "("
                + KEY_TEMPORARY + " TEXT," + KEY_DOB + " TEXT,"
                + KEY_JOBTYPE + " TEXT," + KEY_PASSPORTNO + " TEXT," + KEY_PHONE + " TEXT," + KEY_WEIGHT + " TEXT," +
                KEY_PERMANENTADD + " TEXT," + KEY_BLOOGGROUP + " TEXT," + KEY_USER_STATUS + " TEXT," + KEY_REGISTERDATE + " TEXT," +
                KEY_LASTNAME + " TEXT," + KEY_FIRSTNAME + " TEXT," + KEY_COMPANYNAME + " TEXT," + KEY_TEMPORARYADDTYPE + " TEXT," +
                KEY_HEIGHT + " TEXT," + KEY_CITIZENNO + " TEXT," + KEY_GENDER + " TEXT," + KEY_DRIVINGNO + " TEXT," +
                KEY_PHOTOURL + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

    }

    // adding all User Details
    public long addUserDetails(UserModel.UserData userData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_TEMPORARY, userData.getTemperoryAdd());
        values.put(KEY_DOB, userData.getBirthday());
        values.put(KEY_JOBTYPE, userData.getJobType());
        values.put(KEY_PASSPORTNO, userData.getPassportNo());
        values.put(KEY_PHONE, userData.getPhone());
        values.put(KEY_WEIGHT, userData.getWeight());
        values.put(KEY_PERMANENTADD, userData.getPermanentAdd());
        values.put(KEY_BLOOGGROUP, userData.getBloodGroup());
        values.put(KEY_USER_STATUS, userData.getStatus());
        values.put(KEY_REGISTERDATE, userData.getRegisterDate());
        values.put(KEY_LASTNAME, userData.getLastname());
        values.put(KEY_FIRSTNAME, userData.getFirstname());
        values.put(KEY_COMPANYNAME, userData.getCompanyName());
        values.put(KEY_TEMPORARYADDTYPE, userData.getTemporaryAddType());
        values.put(KEY_HEIGHT, userData.getHeight());
        values.put(KEY_CITIZENNO, userData.getCitizenNumber());
        values.put(KEY_GENDER, userData.getGender());
        values.put(KEY_DRIVINGNO, userData.getDrivingLicenceNo());
        values.put(KEY_PHOTOURL, userData.getPhotoUrl());


        // Inserting Row
        long result = db.insert(TABLE_USERDETAILS, null, values);
        db.close(); // Closing database connection
        return result;
    }

    // getting all User Details
    public List<UserModel.UserData> getUserDetails() {
        List<UserModel.UserData> userDataList = new ArrayList<UserModel.UserData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_USERDETAILS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                UserModel.UserData userData = new UserModel.UserData();
                userData.setTemperoryAdd(cursor.getString(0));
                userData.setBirthday(cursor.getString(1));
                userData.setJobType(cursor.getString(2));
                userData.setPassportNo(cursor.getString(3));
                userData.setPhone(cursor.getString(4));
                userData.setWeight(cursor.getString(5));
                userData.setPermanentAdd(cursor.getString(6));
                userData.setBloodGroup(cursor.getString(7));
                userData.setStatus(cursor.getString(8));
                userData.setRegisterDate(cursor.getString(9));
                userData.setLastname(cursor.getString(10));
                userData.setFirstname(cursor.getString(11));
                userData.setCompanyName(cursor.getString(12));
                userData.setTemporaryAddType(cursor.getString(13));
                userData.setHeight(cursor.getString(14));
                userData.setCitizenNumber(cursor.getString(15));
                userData.setGender(cursor.getString(16));
                userData.setDrivingLicenceNo(cursor.getString(17));
                userData.setPhotoUrl(cursor.getString(18));
                userDataList.add(userData);

            } while (cursor.moveToNext());
        }

        // return list
        db.close();
        return userDataList;

    }

    //delete User Details

    public void deleteUserDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_USERDETAILS);
    }

    public void deleteALL() {
        deleteAllLimitLogs();
        deleteUserDetails();
        deleteAllLeaveStatus();
        deleteAllAppliedLeave();
        deleteAllLogs();
        deleteCheckInOut();
        deleteHoliday();
        deleteRemark();
    }


    // table check in out create
    public void createCheckInOut(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_CHECKINOUT + "("
                + KEY_SENDDATE + " TEXT," + KEY_CHECKINOUTDATE + " TEXT,"
                + KEY_TIME + " TEXT," + KEY_STATUSCHECK + " TEXT," + KEY_REMARKSCHECK + " TEXT," + KEY_REASONCHECK + " TEXT," +
                KEY_CHECKTYPE + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

    }

    // adding all check in out
    public long addCheckInOut(CheckInModel.CheckInOutData checkInOutData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_SENDDATE, checkInOutData.getSendDate());
        values.put(KEY_CHECKINOUTDATE, checkInOutData.getCheckInOutDate());
        values.put(KEY_TIME, checkInOutData.getTime());
        values.put(KEY_STATUSCHECK, checkInOutData.getStatus());
        values.put(KEY_REMARKSCHECK, checkInOutData.getRemark());
        values.put(KEY_REASONCHECK, checkInOutData.getReason());
        values.put(KEY_CHECKTYPE, checkInOutData.getType());
        // Inserting Row
        long result = db.insert(TABLE_CHECKINOUT, null, values);
        db.close(); // Closing database connection
        return result;
    }

    // getting all check in outs
    public List<CheckInModel.CheckInOutData> getCheckInOut() {
        List<CheckInModel.CheckInOutData> checkInOutDataList = new ArrayList<CheckInModel.CheckInOutData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CHECKINOUT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                CheckInModel.CheckInOutData checkInOutData = new CheckInModel.CheckInOutData();
                checkInOutData.setSendDate(cursor.getString(0));
                checkInOutData.setCheckInOutDate(cursor.getString(1));
                checkInOutData.setTime(cursor.getString(2));
                checkInOutData.setStatus(cursor.getString(3));
                checkInOutData.setRemark(cursor.getString(4));
                checkInOutData.setReason(cursor.getString(5));
                checkInOutData.setType(cursor.getString(6));

                checkInOutDataList.add(checkInOutData);

            } while (cursor.moveToNext());
        }

        // return list
        db.close();
        return checkInOutDataList;

    }

    //delete check in out

    public void deleteCheckInOut() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_CHECKINOUT);
    }


    // table holiday create
    public void createHoliday(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_HOLIDAY + "("
                + KEY_HOLIDAYNAME + " TEXT," + KEY_HOLIDAYDATE + " TEXT,"
                + KEY_APPLICABLEFOR + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

    }

    // adding all holiday
    public long addHoliday(HolidayModel.HolidayData holidayData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_HOLIDAYNAME, holidayData.getHolidayName());
        values.put(KEY_HOLIDAYDATE, holidayData.getHolidayDate());
        values.put(KEY_APPLICABLEFOR, holidayData.getApplicableFor());

        // Inserting Row
        long result = db.insert(TABLE_HOLIDAY, null, values);
        db.close(); // Closing database connection
        return result;
    }

    // getting all check in outs
    public List<HolidayModel.HolidayData> getHoliday() {
        List<HolidayModel.HolidayData> holidayDataList = new ArrayList<HolidayModel.HolidayData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_HOLIDAY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                HolidayModel.HolidayData holidayData = new HolidayModel.HolidayData();
                holidayData.setHolidayName(cursor.getString(0));
                holidayData.setHolidayDate(cursor.getString(1));
                holidayData.setApplicableFor(cursor.getString(2));


                holidayDataList.add(holidayData);

            } while (cursor.moveToNext());
        }

        // return list
        db.close();
        return holidayDataList;

    }

    //delete check in out

    public void deleteHoliday() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_HOLIDAY);
    }


    // table remark create
    public void createRemark(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_REMARK + "("
                + KEY_REMARKCOUNTLATEMIN + " TEXT," + KEY_REMARKSTATUS + " TEXT,"
                + KEY_REMARK + " TEXT," + KEY_APPLICATIONDATE + " TEXT," + KEY_REMARKDATE + " TEXT," +
                KEY_REMARKCOUNTEARLYMIN + " TEXT" + ")";
        db.execSQL(CREATE_TABLE);

    }

    // adding all remark
    public long addRemark(RemarkModel.RemarkData remarkData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_REMARKCOUNTLATEMIN, remarkData.getIsDoNotCountLateMin());
        values.put(KEY_REMARKSTATUS, remarkData.getStatus());
        values.put(KEY_REMARK, remarkData.getRemark());
        values.put(KEY_APPLICATIONDATE, remarkData.getApplicationDate());
        values.put(KEY_REMARKDATE, remarkData.getRemarkDate());
        values.put(KEY_REMARKCOUNTEARLYMIN, remarkData.getIsDoNotCountEarlyMin());

        // Inserting Row
        long result = db.insert(TABLE_REMARK, null, values);
        db.close(); // Closing database connection
        return result;
    }

    // getting all remark
    public List<RemarkModel.RemarkData> getRemark() {
        List<RemarkModel.RemarkData> remarkDataList = new ArrayList<RemarkModel.RemarkData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_REMARK;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                RemarkModel.RemarkData remarkData = new RemarkModel.RemarkData();
                remarkData.setIsDoNotCountLateMin(cursor.getString(0));
                remarkData.setStatus(cursor.getString(1));
                remarkData.setRemark(cursor.getString(2));
                remarkData.setApplicationDate(cursor.getString(3));
                remarkData.setRemarkDate(cursor.getString(4));
                remarkData.setIsDoNotCountEarlyMin(cursor.getString(5));


                remarkDataList.add(remarkData);

            } while (cursor.moveToNext());
        }

        // return list
        db.close();
        return remarkDataList;

    }

    //delete remark

    public void deleteRemark() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + TABLE_REMARK);
    }
}
