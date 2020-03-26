package com.example.test.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
/*
Added by Lalit Kumar on 20.march.2020 at 14:24 PM
*/
import com.example.test.Model.UserDetails;
import com.example.test.R;


public class SessionManager {
    private static SharedPreferences userCredentialsPrefs;


    private SharedPreferences.Editor credentialEditor;


    public final static String PREF_USER_CREDENTIALS = "USER_CREDENTIALS";
    public final static String KEY_IS_LOGIN = "IS_LOGIN";
    public final static String KEY_USER_ID = "USER_ID";
    public final static String KEY_USER_NAME = "USER_NAME";
    public final static String KEY_MOBILE_NO = "MOBILE_NO";
    public final static String KEY_EMAIL = "EMAIL";
    public final static String KEY_GENDER = "GENDER";
    public final static String KEY_DOB = "DOB";
    public final static String KEY_PIN_CODE = "PINCODE";
    public final static String KEY_REMEMBER_TOKEN = "REMEMBER_TOKEN";


    Context _context;


    private static SharedPreferences userInsurancePref;
    private SharedPreferences.Editor userInsuranceEditor;

    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context cntx) {
        _context = cntx;
        userCredentialsPrefs = cntx.getSharedPreferences(PREF_USER_CREDENTIALS, Context.MODE_PRIVATE);
        credentialEditor = userCredentialsPrefs.edit();


    }


    public void createUserSession(UserDetails details) {
        credentialEditor = userCredentialsPrefs.edit();
        credentialEditor.putBoolean(KEY_IS_LOGIN, details.isLogin());
        credentialEditor.putString(KEY_MOBILE_NO, details.getMobileNumber());
        credentialEditor.putString(KEY_EMAIL, details.getEmail());
        credentialEditor.putString(KEY_USER_NAME, details.getUsername());
        credentialEditor.putString(KEY_USER_ID, details.getId());
        credentialEditor.putString(KEY_GENDER, details.getGender());
        credentialEditor.putString(KEY_DOB, details.getDob());
        credentialEditor.putString(KEY_PIN_CODE, details.getPincode());
        credentialEditor.putString(KEY_REMEMBER_TOKEN, details.getRemeber_token());
        credentialEditor.commit();
    }

    public UserDetails getUserSession() {
        UserDetails details = new UserDetails();
        details.setId(userCredentialsPrefs.getString(KEY_USER_ID, null));
        details.setUsername(userCredentialsPrefs.getString(KEY_USER_NAME, null));
        details.setMobileNumber(userCredentialsPrefs.getString(KEY_MOBILE_NO, null));
        details.setEmail(userCredentialsPrefs.getString(KEY_EMAIL, null));
        details.setGender(userCredentialsPrefs.getString(KEY_GENDER, null));
        details.setDob(userCredentialsPrefs.getString(KEY_DOB, null));
        details.setPincode(userCredentialsPrefs.getString(KEY_PIN_CODE, null));
        details.setRemeber_token(userCredentialsPrefs.getString(KEY_REMEMBER_TOKEN, null));
        return details;
    }


    public SharedPreferences getUserPrefs() {
        return userCredentialsPrefs;
    }


    public boolean isLoggedIn() {
        return userCredentialsPrefs.getBoolean(KEY_IS_LOGIN, false);
    }


    public void logoutUser_accout_delect() {
        clearUserCredentialsEditor();

    }

    public void clearUserCredentialsEditor() {
        credentialEditor.clear();
        credentialEditor.commit();
    }

    public void logoutUser() {
        clearUserCredentialsEditor();
        Intent intent = new Intent(_context, Home_Screen.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        _context.startActivity(intent);

        ((Activity) _context).finish();
    }

    public void clear_userInsuranceEditor() {

        userInsuranceEditor.clear();
        userInsuranceEditor.commit();
    }


}
