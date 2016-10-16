package com.nizar.abdelhedi.adhd;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Ghassen on 15/10/2016.
 */

public class PrefManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "guesmi";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String ATTENTION_LIMIT="attention_limit";
    private static final String USER_ID="userId";
    private static final String USER_Name="userName";
    private static final String IS_DOCTOR="isDoctor";



    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public String getUserId() {
        return pref.getString(USER_ID,"");
    }

    public void setUserId(String user) {
        editor.putString(USER_ID, user);
        editor.commit();
    }


    public int getAttentionLimit() {
        return pref.getInt(ATTENTION_LIMIT,50);
    }

    public void setAttentionLimit(int limit) {
        editor.putInt(ATTENTION_LIMIT, limit);
        editor.commit();
    }

    public String getUSER_Name() {
        return pref.getString(USER_Name,"");
    }

    public void setUSER_Name(String user) {
        editor.putString(USER_Name, user);
        editor.commit();
    }

    public void setIsDoctor(boolean isDoctor){
        editor.putBoolean(IS_DOCTOR, isDoctor);
        editor.commit();
    }

    public boolean getIsDoctor(){return pref.getBoolean(IS_DOCTOR,false);}


}