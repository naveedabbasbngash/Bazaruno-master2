package com.example.bazaruno.DB;

/**
 * Created by Oclemy on 9/27/2016 for ProgrammingWizards Channel and http://www.camposha.info.
 */
public class Constants {
    /*
  COLUMNS
   */
    static final String ROW_ID="id";
    static final String NAME="name";
    static final String PROPELLANT="propellant";
    static final String DESTINATION="destination";


    static final String Shop_Id="shop_Id";
    static final String shop_name="shop_name";
    static final String main_cat="main_cat";
    static final String sub_cat="sub_cat";
    static final String sub_sub_cat="sub_sub_cat";
    static final String size="size";
    static final String color="color";
    static final String brand_name="brand_name";
    static final String item_images_url="item_images_url";
    static final String item_price="item_price";
    static final String item_descount="item_descount";
    static final String item_name="item_name";
    static final String item_city="item_city";
    static final String item_bazzar="item_bazzar";






    /*
    DB PROPERTIES
     */
    static final String DB_NAME="tv_DB";
    static final String TB_NAME="tv_TB";
    static final int DB_VERSION=1;

    /*
    TABLE CREATION STATEMENT
     */
    static final String CREATE_TB="CREATE TABLE tv_TB(id INTEGER PRIMARY KEY AUTOINCREMENT,"
            + "shop_Id TEXT NOT NULL,shop_name TEXT NOT NULL,main_cat TEXT NOT NULL,sub_cat TEXT NOT NULL" +
            ",sub_sub_cat TEXT NOT NULL,size TEXT NOT NULL,color TEXT NOT NULL,brand_name TEXT NOT NULL,item_images_url " +
            "TEXT NOT NULL,item_price TEXT NOT NULL,item_descount TEXT NOT NULL,item_name TEXT NOT NULL,item_city TEXT NOT " +
            "NULL,item_bazzar TEXT NOT NULL);";


    /*
    TABLE DELETION STMT
     */
    static final String DROP_TB="DROP TABLE IF EXISTS "+TB_NAME;

}
