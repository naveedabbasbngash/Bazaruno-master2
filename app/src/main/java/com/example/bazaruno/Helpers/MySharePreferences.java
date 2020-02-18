package com.example.bazaruno.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bazaruno.Model.Users;
import com.google.gson.Gson;

import java.util.Calendar;

public class MySharePreferences {
    public static final String MyPREFERENCES = "appsfree";
    public static SharedPreferences.Editor editor;
    private SharedPreferences sharedpreferences;


    public MySharePreferences() {
    }

    public void SaveUserAds(Context context , Users user){


        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String mydata=gson.toJson(user);
        editor.putString("userData",mydata);
        editor.commit();

    }

    public Users getUserData(Context ctx){

        Users userdatahere=new Users();
        SharedPreferences prfs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);

        Gson gson = new Gson();
        String json = prfs.getString("userData", "");
        userdatahere = gson.fromJson(json, Users.class);
        return userdatahere;
    }


    public Boolean loginStatus(Context context){

        SharedPreferences prfs = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        boolean  checkfirs=prfs.getBoolean("loginStatus", false);
        return checkfirs;

    }
    public void setLoginStatus(Context context, Boolean loginStatus){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        editor.putBoolean("loginStatus", loginStatus);
        editor.commit();

    }
}
