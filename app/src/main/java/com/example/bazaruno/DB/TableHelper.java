package com.example.bazaruno.DB;

import android.content.Context;

import com.example.bazaruno.Model.ItemModel;

import java.util.ArrayList;

/**
 * TABLE HELPER CLASS. GETS ARRAYLIST FROM SQLITE DATABASE AND RETURNS A MULTIDIMENSIONAL ARRAY FOR BINDING TO OUR ADAPTER
 */
public class TableHelper {

    //DECLARATIONS
    Context c;
    private String[] spaceProbeHeaders={
            "shop_Id","shop_name","main_cat",
            "sub_cat","sub_sub_cat","size",
            "color","brand_name","item_images_url","item_price","item_descount","item_name",
            "item_city","item_bazzar"
    };
    private String[][] spaceProbes;

    //CONSTRUCTOR
    public TableHelper(Context c) {
        this.c = c;
    }

    //RETURN TABLE HEADERS
    public String[] getSpaceProbeHeaders()
    {
        return spaceProbeHeaders;
    }

    //RETURN TABLE ROWS
    public  String[][] getSpaceProbes()
    {
        ArrayList<ItemModel> spacecrafts=new DBAdapter(c).retrieveSpacecrafts();
        ItemModel s;

        spaceProbes= new String[spacecrafts.size()][3];

        for (int i=0;i<spacecrafts.size();i++) {

             s=spacecrafts.get(i);

            spaceProbes[i][0]=s.getShop_Id();
            spaceProbes[i][1]=s.getShop_name();
            spaceProbes[i][2]=s.getMain_cat();
            spaceProbes[i][4]=s.getSub_cat();
            spaceProbes[i][5]=s.getSub_sub_cat();
            spaceProbes[i][6]=s.getSize();
            spaceProbes[i][7]=s.getColor();
            spaceProbes[i][8]=s.getBrand_name();
            spaceProbes[i][9]=s.getItem_images_url();
            spaceProbes[i][10]=s.getItem_price();
            spaceProbes[i][11]=s.getItem_descount();
            spaceProbes[i][12]=s.getItem_name();
            spaceProbes[i][13]=s.getItem_city();
            spaceProbes[i][14]=s.getItem_bazzar();

        ;
        }

        return spaceProbes;





    }
}





