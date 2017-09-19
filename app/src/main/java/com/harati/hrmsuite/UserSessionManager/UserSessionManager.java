package com.harati.hrmsuite.UserSessionManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.harati.hrmsuite.LoginPackage.Activity.LoginActivity;

/**
 * Created by User on 8/29/2017.
 */

public class UserSessionManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;
    int PRIVATE_MODE = 0;
    public static final String PREFS_NAME = "LoginPrefs";
    private static final String IS_USER_LOGIN = "IsUserLoggedIn";
    public static final String KEY_USERCODE = "userCode";

    public static final String KEY_ACCESS_TOKEN = "access_token";
    public static final String KEY_LANGUAGE = "language";


    public UserSessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void logoutUser() {
        editor.clear();
        editor.commit();
        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction("com.package.ACTION_LOGOUT");
        _context.sendBroadcast(broadcastIntent);
        Intent i = new Intent(_context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        _context.startActivity(i);
    }

    public void saveUserInformation(String userCode, String accessToken) {
        editor.putBoolean(IS_USER_LOGIN, true);
        editor.putString(KEY_USERCODE, userCode);
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.commit();
    }
    public void setKeyLanguage(String language){
        editor.putString(KEY_LANGUAGE, language);
        editor.commit();
    }

    public String getKeyUsercode() {
        return pref.getString(KEY_USERCODE, "");
    }

    public String getKeyAccessToken() {
        return pref.getString(KEY_ACCESS_TOKEN, "");
    }

    public String getKeyLanguage() {
        return pref.getString(KEY_LANGUAGE, "");
    }

    public boolean isUserLoggedIn() {
        return pref.getBoolean(IS_USER_LOGIN, false);
    }

}
