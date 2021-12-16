package com.example.covid19tracker.LoginForm.SharedPreferences;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.covid19tracker.LoginForm.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;

public class SharedPrefManager {

    Context context;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    private FirebaseAuth mAuth;

    private static final String PREF_NAME = "Shared pref";

    private static final String KEY_EMAIL = "email";
    private static final String KEY_PASS = "pass";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_NAME = "name";
    private static final String KEY_AGE = "age";

    private static final String IS_USER_LOGIN = "IsUserLoggedIn";

    public SharedPrefManager(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(PREF_NAME,Context.MODE_PRIVATE);
        editor = preferences.edit();
        mAuth = FirebaseAuth.getInstance();
    }

    // for user activities

    public void createUserLoginSession(String email,String pass){
        editor.putBoolean(IS_USER_LOGIN,true);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_PASS,pass);
        editor.commit();
    }


    public void getEmail(String email){
        editor.putString(KEY_EMAIL,email);
        editor.commit();
    }

    public boolean checkLogin(){
        if(!this.isUserLoggedIn()){
            Intent intent = new Intent(context, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            return true;
        }
        return false;
    }

    public boolean isUserLoggedIn(){
        return preferences.getBoolean(IS_USER_LOGIN,false);
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(context,LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public void getUserDetails(String name,String email,String mobile,String age,String gender){
        editor.putString(KEY_NAME,name);
        editor.putString(KEY_EMAIL,email);
        editor.putString(KEY_MOBILE,mobile);
        editor.putString(KEY_AGE,age);
        editor.putString(KEY_GENDER,gender);
        editor.commit();
    }
}

