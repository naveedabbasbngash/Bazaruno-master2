package com.example.bazaruno.Helpers;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.bazaruno.Model.FilterModel;
import com.example.bazaruno.Model.ItemModel;
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


    public void SaveItemData(Context context , ItemModel user){


        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String mydata=gson.toJson(user);
        editor.putString("itemData",mydata);
        editor.commit();

    }

    public ItemModel GetItemData(Context ctx){

        ItemModel userdatahere=new ItemModel();
        SharedPreferences prfs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);

        Gson gson = new Gson();
        String json = prfs.getString("itemData", "");
        userdatahere = gson.fromJson(json, ItemModel.class);
        return userdatahere;
    }



    public int checkBringData(Context context){

        SharedPreferences prfs = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        int  checkfirs=prfs.getInt("checkBringData", 0);
        return checkfirs;

    }
    public void setBringData(Context context, int loginStatus){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        editor.putInt("checkBringData", loginStatus);
        editor.commit();

    }



    public void SaveFilterData(Context context , FilterModel user){


        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        Gson gson = new Gson();
        String mydata=gson.toJson(user);
        editor.putString("SaveFilterData",mydata);
        editor.commit();

    }

    public FilterModel GetFilterData(Context ctx){

        FilterModel userdatahere=new FilterModel();
        SharedPreferences prfs = ctx.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);

        Gson gson = new Gson();
        String json = prfs.getString("SaveFilterData", "");
        userdatahere = gson.fromJson(json, FilterModel.class);
        return userdatahere;
    }


    public Boolean firsttime(Context context){

        SharedPreferences prfs = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        boolean  checkfirs=prfs.getBoolean("firsttime", true);
        return checkfirs;

    }
    public void entery(Context context, Boolean loginStatus){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        editor.putBoolean("firsttime", loginStatus);
        editor.commit();

    }

    public String checkComparetype(Context context){

        SharedPreferences prfs = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        String  checkfirs=prfs.getString("checkComparetype", "no");
        return checkfirs;

    }
    public void setCompare(Context context, String Compare){
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_MULTI_PROCESS);
        editor = sharedpreferences.edit();
        editor.putString("checkComparetype", Compare);
        editor.commit();

    }
}
