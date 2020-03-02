package com.example.bazaruno.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Item_Details;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Model.Spacecraft;

import java.util.ArrayList;

/**
 * DATABASE ADAPTER CLASS
 */
public class DBAdapter {

    Context c;
    SQLiteDatabase db;
    DBHelper helper;

    /*
    1. INITIALIZE DB HELPER AND PASS IT A CONTEXT

     */
    public DBAdapter(Context c) {
        this.c = c;
        helper = new DBHelper(c);
    }


    /*
    SAVE DATA TO DB
     */
    public boolean saveSpacecraft(ItemModel spacecraft) {
        try {
            db = helper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(Constants.Shop_Id, spacecraft.getShop_Id());
            cv.put(Constants.shop_name, spacecraft.getShop_name());
            cv.put(Constants.main_cat, spacecraft.getMain_cat());
            cv.put(Constants.sub_cat, spacecraft.getSub_cat());
            cv.put(Constants.sub_sub_cat, spacecraft.getSub_sub_cat());
            cv.put(Constants.size, spacecraft.getSize());
            cv.put(Constants.color, spacecraft.getColor());
            cv.put(Constants.brand_name, spacecraft.getBrand_name());
            cv.put(Constants.item_images_url, spacecraft.getItem_images_url());
            cv.put(Constants.item_price, spacecraft.getItem_price());
            cv.put(Constants.item_descount, spacecraft.getItem_descount());
            cv.put(Constants.item_name, spacecraft.getItem_name());
            cv.put(Constants.item_city, spacecraft.getItem_city());
            cv.put(Constants.item_bazzar, spacecraft.getItem_bazzar());


            long result = db.insertWithOnConflict(Constants.TB_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            if (result > 0) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            helper.close();
        }

        return false;
    }

    /*
     1. RETRIEVE SPACECRAFTS FROM DB AND POPULATE ARRAYLIST
     2. RETURN THE LIST
     */

    public ArrayList<ItemModel> retrieveSpacecrafts() {
        ArrayList<ItemModel> spacecrafts = new ArrayList<>();

        String[] columns = {
                Constants.ROW_ID, Constants.Shop_Id, Constants.shop_name, Constants.main_cat,
                Constants.sub_cat, Constants.sub_sub_cat, Constants.size, Constants.color,
                Constants.brand_name, Constants.item_images_url, Constants.item_price, Constants.item_descount,
                Constants.item_name, Constants.item_city, Constants.item_bazzar,
        };

        try {
            db = helper.getWritableDatabase();
            Cursor c = db.query(Constants.TB_NAME, columns, null, null, null, null, null);

            ItemModel s;

            if (c != null) {
                while (c.moveToNext()) {
                    String Shop_Id = c.getString(1);
                    String shop_name = c.getString(2);
                    String main_cat = c.getString(3);
                    String sub_cat = c.getString(4);
                    String sub_sub_cat = c.getString(5);
                    String size = c.getString(6);
                    String color = c.getString(7);
                    String brand_name = c.getString(8);
                    String item_images_url = c.getString(9);
                    String item_price = c.getString(10);
                    String item_descount = c.getString(11);
                    String item_name = c.getString(12);
                    String item_city = c.getString(13);
                    String item_bazzar = c.getString(14);


                    s = new ItemModel();
                    s.setShop_Id(Shop_Id);
                    s.setShop_name(shop_name);
                    s.setMain_cat(main_cat);
                    s.setSub_cat(sub_cat);
                    s.setSub_sub_cat(sub_sub_cat);
                    s.setSize(size);
                    s.setColor(color);
                    s.setBrand_name(brand_name);
                    s.setItem_images_url(item_images_url);
                    s.setItem_price(item_price);
                    s.setItem_descount(item_descount);
                    s.setItem_name(item_name);
                    s.setItem_city(item_city);
                    s.setItem_bazzar(item_bazzar);


                    spacecrafts.add(s);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return spacecrafts;
    }

    public boolean isRecordExistInDatabase(String value) {
        String query = "SELECT * FROM " + Constants.TB_NAME + " WHERE " + Constants.Shop_Id + " = '" + value + "'";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            //Record exist
            c.close();
            return true;
        }
        //Record available
        c.close();
        return false;
    }

    public boolean rowIdExists(String StrId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select shop_Id from " + Constants.TB_NAME
                + " where shop_Id=?", new String[]{StrId});
        Log.d(AppConstant.TAG+" : cursor count",cursor.getCount()+"");
       if (cursor.getCount() <= 0){
           return true;
       }
       else {
           return false;
       }


    }
}
