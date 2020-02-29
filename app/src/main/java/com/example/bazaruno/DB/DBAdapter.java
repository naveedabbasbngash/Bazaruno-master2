package com.example.bazaruno.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

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
    public boolean saveSpacecraft(Spacecraft spacecraft) {
        try {
            db = helper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(Constants.Shop_Id, spacecraft.getPropellant());
            cv.put(Constants.shop_name, spacecraft.getDestination());
            cv.put(Constants.main_cat, spacecraft.getDestination());
            cv.put(Constants.sub_cat, spacecraft.getDestination());
            cv.put(Constants.sub_sub_cat, spacecraft.getDestination());
            cv.put(Constants.size, spacecraft.getDestination());
            cv.put(Constants.color, spacecraft.getDestination());
            cv.put(Constants.brand_name, spacecraft.getDestination());
            cv.put(Constants.item_images_url, spacecraft.getDestination());
            cv.put(Constants.item_price, spacecraft.getDestination());
            cv.put(Constants.item_descount, spacecraft.getDestination());
            cv.put(Constants.item_name, spacecraft.getDestination());
            cv.put(Constants.item_city, spacecraft.getDestination());
            cv.put(Constants.item_bazzar, spacecraft.getName());



            long result = db.insert(Constants.TB_NAME, Constants.ROW_ID, cv);
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

    public ArrayList<ItemModel> retrieveSpacecrafts()
    {
        ArrayList<ItemModel> spacecrafts=new ArrayList<>();

        String[] columns={
                Constants.ROW_ID,Constants.Shop_Id,Constants.shop_name,Constants.main_cat,
                Constants.sub_cat,Constants.sub_sub_cat,Constants.size,Constants.color,
                Constants.brand_name,Constants.item_images_url,Constants.item_price,Constants.item_descount,
                Constants.item_name,Constants.item_city,Constants.item_bazzar,
        };

        try
        {
            db = helper.getWritableDatabase();
            Cursor c=db.query(Constants.TB_NAME,columns,null,null,null,null,null);

            ItemModel s;

            if(c != null)
            {
                while (c.moveToNext())
                {
                    String Shop_Id=c.getString(1);
                    String shop_name=c.getString(2);
                    String main_cat=c.getString(3);
                    String sub_cat=c.getString(3);
                    String sub_sub_cat=c.getString(1);
                    String size=c.getString(2);
                    String color=c.getString(3);
                    String brand_name=c.getString(3);
                    String item_images_url=c.getString(1);
                    String item_price=c.getString(3);
                    String item_descount=c.getString(3);
                    String item_name=c.getString(1);
                    String item_city=c.getString(2);
                    String item_bazzar=c.getString(3);



                    s=new ItemModel();
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

        }catch (SQLException e)
        {
            e.printStackTrace();
        }


        return spacecrafts;
    }

}
