package com.example.bazaruno.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Helpers.MyCommand;
import com.example.bazaruno.Item_Details;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Model.ShopModel;
import com.example.bazaruno.Model.Spacecraft;

import java.util.ArrayList;

/**
 * DATABASE ADAPTER CLASS
 */
public class DBAdapter {

    Context c;
    SQLiteDatabase db;
    DBHelper helper;
    private ArrayList<ShopModel> shopModel=new ArrayList<>();

    /*
    1. INITIALIZE DB HELPER AND PASS IT A CONTEXT

     */
    public DBAdapter(Context c) {
        this.c = c;
        helper = new DBHelper(c);
    }


    /*
    SAVE DATA TO Table Favorite
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
  * SAVE DATA TO SHOP
  * */
    public boolean saveShopToDb(ShopModel spacecraft) {
      try {
          db = helper.getWritableDatabase();

          ContentValues cv = new ContentValues();
          cv.put(Constants.shop_id, spacecraft.getId());
          cv.put(Constants.shop_name, spacecraft.getShop_name());
          cv.put(Constants.shop_img, spacecraft.getShop_img());
          cv.put(Constants.shop_lat_lang, spacecraft.getShop_lat_lang());
          cv.put(Constants.city_area, spacecraft.getCity_area());
          cv.put(Constants.city, spacecraft.getCity());



          long result = db.insertWithOnConflict(Constants.TB_Shop, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
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
  * SAVE DATA TO COMPARE
  * */
    public boolean saveToComapre(ItemModel spacecraft) {
      try {
          db = helper.getWritableDatabase();

          ContentValues cv = new ContentValues();
          cv.put(Constants.Com_item_image_urls, spacecraft.getItem_images_url());
          cv.put(Constants.Com_item_type, spacecraft.getSub_sub_cat());
          cv.put(Constants.Com_item_price, spacecraft.getItem_price());
          cv.put(Constants.Com_item_color, spacecraft.getColor());
          cv.put(Constants.Com_item_image_id, spacecraft.getId());
          cv.put(Constants.Com_item_size, spacecraft.getSize());
          cv.put(Constants.Com_item_name, spacecraft.getItem_name());


          long result = db.insertWithOnConflict(Constants.TB_COMPARE, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
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
     * SAVE DATA TO COMPARE
     * */
    public boolean saveToNotification(ItemModel spacecraft) {
        try {
            db = helper.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(Constants.shop_name, spacecraft.getShop_name());
            cv.put(Constants.main_cat, spacecraft.getMain_cat());
            cv.put(Constants.shop_id, spacecraft.getShop_Id());
            cv.put(Constants.sub_cat, spacecraft.getSub_cat());
            cv.put(Constants.sub_sub_cat, spacecraft.getSub_sub_cat());
            cv.put(Constants.size, spacecraft.getSize());
            cv.put(Constants.color, spacecraft.getColor());
            cv.put(Constants.item_images_url, spacecraft.getItem_images_url());
            cv.put(Constants.item_price, spacecraft.getItem_price());
            cv.put(Constants.item_descount, spacecraft.getItem_descount());
            cv.put(Constants.item_name, spacecraft.getItem_name());
            cv.put(Constants.item_city, spacecraft.getItem_city());
            cv.put(Constants.item_bazzar, spacecraft.getItem_bazzar());
            cv.put(Constants.shop_name, spacecraft.getShop_name());
            cv.put(Constants.brand_name, spacecraft.getBrand_name());


            long result = db.insertWithOnConflict(Constants.TB_NT, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
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
  /*------------------------------------------------------------------------------------------------*/

    /*
    * GET DATA FROM ITEM TABLE
    * */
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

    /*
    * GET DATA FROM SHOP TABLE
    * */
    public ArrayList<ShopModel> retrieveShop() {
        ArrayList<ItemModel> spacecrafts = new ArrayList<>();

        String[] columns = {
                Constants.ROW_ID, Constants.shop_id, Constants.shop_name, Constants.shop_img,
                Constants.shop_lat_lang, Constants.city_area, Constants.city_area
        };

        try {
            db = helper.getWritableDatabase();
            Cursor c = db.query(Constants.TB_Shop, columns, null, null, null, null, null);

            ShopModel s;

            if (c != null) {
                while (c.moveToNext()) {
                    String shop_id = c.getString(1);
                    String shop_name = c.getString(2);
                    String shop_img = c.getString(3);
                    String shop_lat_lang = c.getString(4);
                    String city_area = c.getString(5);
                    String city = c.getString(5);



                    s = new ShopModel();
                    s.setId(shop_id);
                    s.setShop_name(shop_name);
                    s.setShop_img(shop_img);
                    s.setShop_lat_lang(shop_lat_lang);
                    s.setCity_area(city_area);
                    s.setCity(city);



                    shopModel.add(s);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return shopModel;
    }

   /*
   * GET DATA FROM COMPARE TABLE
   * */
    public ArrayList<ItemModel> getCompareFromDb() {
    ArrayList<ItemModel> spacecrafts = new ArrayList<>();

    String[] columns = {
            Constants.Com_item_id, Constants.Com_item_image_urls, Constants.Com_item_type,
            Constants.Com_item_price, Constants.Com_item_color,Constants.Com_item_image_id,
            Constants.Com_item_size,Constants.Com_item_name
    };

    try {
        db = helper.getWritableDatabase();
        Cursor c = db.query(Constants.TB_COMPARE, columns, null, null, null, null, null);

        ItemModel s;

        if (c != null) {
            while (c.moveToNext()) {
                String id = c.getString(0);
                String Com_item_image_urls = c.getString(1);
                String Com_item_type = c.getString(2);
                String Com_item_price = c.getString(3);
                String Com_item_color = c.getString(4);
                String Com_item_image_id = c.getString(5);
                String Com_item_size = c.getString(6);
                String Com_item_name = c.getString(7);



                s = new ItemModel();
                s.setId(Com_item_image_id);
                s.setItem_images_url(Com_item_image_urls);
                s.setSub_sub_cat(Com_item_type);
                s.setItem_price(Com_item_price);
                s.setColor(Com_item_color);
                s.setSize(Com_item_size);
                s.setItem_name(Com_item_name);


                spacecrafts.add(s);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }


    return spacecrafts;
}


    /*
     * GET DATA FROM ITEM TABLE
     * */
    public ArrayList<ItemModel> retrieveNotification() {
        ArrayList<ItemModel> spacecrafts = new ArrayList<>();

        String[] columns = {
                Constants.ROW_ID, Constants.Shop_Id, Constants.shop_name, Constants.main_cat,
                Constants.sub_cat, Constants.sub_sub_cat, Constants.size, Constants.color,
                Constants.brand_name, Constants.item_images_url, Constants.item_price, Constants.item_descount,
                Constants.item_name, Constants.item_city, Constants.item_bazzar,
        };

        try {
            db = helper.getWritableDatabase();
            Cursor c = db.query(Constants.TB_NT, columns, null, null, null, null, null);

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


 /*
 * CHECK DUPLICATE IN ITEM TABLE
 * */
    public boolean rowIdExists(String StrId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("select shop_Id from " + Constants.TB_NAME
                    + " where shop_Id=?", new String[]{StrId});
            Log.e(AppConstant.TAG+" : cursor count",cursor.getCount()+"");
            if (cursor.getCount() <= 0){
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            Log.e(AppConstant.TAG+": exist item ",e.getMessage());
            return false;
        }



    }

    /*
    * CHECK DUBLICATE IN SHOP TABLE
    * */

    public boolean rowIdExistsShop(String StrId) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select shop_id from " + Constants.TB_Shop
                + " where shop_id=?", new String[]{StrId});
        Log.d(AppConstant.TAG+" : cursor count",cursor.getCount()+"");
        if (cursor.getCount() <= 0){
            return true;
        }
        else {
            return false;
        }


    }

    /*
    * GET Dublicate IN COMPARE
    * */
    public boolean CompareExists(String Com_item_image_id) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("select Com_item_image_id from " + Constants.TB_COMPARE
                    + " where Com_item_image_id=?", new String[]{Com_item_image_id});
            Log.e(AppConstant.TAG+" : cursor count",cursor.getCount()+"");
            if (cursor.getCount() <= 0){
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e){
            Log.e(AppConstant.TAG+": exist item ",e.getMessage());
            return false;
        }



    }


    /*
     * GET Tye IN COMPARE
     * */
    public boolean TypeExists(String Com_item_type) {
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("select Com_item_type from " + Constants.TB_COMPARE
                    + " where Com_item_type=?", new String[]{Com_item_type});
            Log.e(AppConstant.TAG+" : cursor count",cursor.getCount()+"");
            if (cursor.getCount() > 0){
                //item found
                return true;
            }
            else if (cursor.getCount()==0){
                return false;
            }
            else {
                //item not found
                return false;
            }
        }
        catch (Exception e){
            Log.e(AppConstant.TAG+": exist item ",e.getMessage());
            return false;
        }



    }

    /*remove item from item fav*/

    public void removeSingleContact(String title) {
        //Open the database
        SQLiteDatabase database = helper.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + Constants.TB_NAME + " WHERE " + Constants.Shop_Id + "= '" + title + "'");

        //Close the database
        database.close();
    }

    /*REMOVE SHOP FROM SHOP TABLE
    * */
    public void removeShopContact(String title) {
        //Open the database
        SQLiteDatabase database = helper.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + Constants.TB_Shop + " WHERE " + Constants.shop_id + "= '" + title + "'");

        //Close the database
        database.close();
    }

    /*remove ALL from item Compare*/

    public void removeAllCompare() {
        //Open the database
        SQLiteDatabase database = helper.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + Constants.TB_COMPARE);

        //Close the database
        database.close();
    }

   /*
   * romve notification*/

    public void removeNotification(String title) {
        //Open the database
        SQLiteDatabase database = helper.getWritableDatabase();

        //Execute sql query to remove from database
        //NOTE: When removing by String in SQL, value must be enclosed with ''
        database.execSQL("DELETE FROM " + Constants.TB_NT + " WHERE " + Constants.ROW_ID + "= '" + title + "'");

        //Close the database
        database.close();
    }




}
