package com.example.bazaruno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.DB.DBAdapter;
import com.example.bazaruno.DB.DBHelper;
import com.example.bazaruno.DB.TableHelper;
import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Model.ShopModel;
import com.example.bazaruno.Model.Users;
import com.example.bazaruno.Services.VolleyService;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class Item_Details extends AppCompatActivity {
    ImageSlider imageSlider;
    TextView item_name, item_price1,shop_name,shop_location,item_cat,
            item_color,item_price,item_size,item_brand_name;
    ArrayList<SlideModel> slideModelArrayList=new ArrayList<>();

    MySharePreferences mySharePreferences=new MySharePreferences();
    ItemModel itemModel=new ItemModel();
    VolleyService volleyService;
    private String area;
    private String shop_lat_lang;
    LinearLayout buyer_stuff,seller_stuff;
    Button add_item_fav,add_shop_fav,delete_item;
    private RotateLoading rotating;
    private TableHelper tableHelper;
    private String shop_nameS;
    private String city_areaS;
    private String cityS;

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item__details);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        imageSlider=findViewById(R.id.image_slider);
        item_name=findViewById(R.id.item_name);
        item_price1=findViewById(R.id.item_price1);
        shop_name=findViewById(R.id.shop_name);
        shop_location=findViewById(R.id.shop_location);
        item_cat=findViewById(R.id.item_cat);
        item_color=findViewById(R.id.item_color);
        item_price=findViewById(R.id.item_price);
        item_size=findViewById(R.id.item_size);
        item_brand_name=findViewById(R.id.item_brand_name);
        buyer_stuff=findViewById(R.id.buyer_stuff);
        seller_stuff=findViewById(R.id.seller_stuff);
        add_item_fav=findViewById(R.id.add_item_fav);
        add_shop_fav=findViewById(R.id.add_shop_fav);
        delete_item=findViewById(R.id.delete_item);
        rotating = findViewById(R.id.newton_cradle_loading);
        tableHelper=new TableHelper(this);

        Users users=mySharePreferences.getUserData(this);
        if (users.getType()==null){
            Toast.makeText(this, "Guest is here", Toast.LENGTH_SHORT).show();
            buyer_stuff.setVisibility(View.VISIBLE);
        }
        else if (users.getType().matches("seller")){
            Toast.makeText(this, "Seller is here", Toast.LENGTH_SHORT).show();
            seller_stuff.setVisibility(View.VISIBLE);
        }
        else if (users.getType().matches("buyer")){
            Toast.makeText(this, "buyer is here", Toast.LENGTH_SHORT).show();
            buyer_stuff.setVisibility(View.VISIBLE);


        }


        itemModel=mySharePreferences.GetItemData(this);

        volleyService=new VolleyService(this);
        List<String> items = Arrays.asList(itemModel.getItem_images_url().split("\\s*,\\s*"));
        Log.d(AppConstant.TAG+" image list size", String.valueOf(items.size()));
        for (int i=0;i<items.size();i++){
            SlideModel slideModel=new SlideModel("https://kheloaurjeeto.net/bazarona/php/"+items.get(i));
            slideModelArrayList.add(slideModel);
        }
        imageSlider.setImageList(slideModelArrayList);
        item_name.setText(itemModel.getItem_name());
        item_price1.setText("Price "+itemModel.getItem_price()+" PKR");
        shop_name.setText("Shop " +itemModel.getShop_name());
        item_cat.setText(itemModel.getMain_cat());
        item_color.setText(itemModel.getColor());
        item_price.setText(itemModel.getItem_price());
        item_size.setText(itemModel.getSize());
        item_brand_name.setText(itemModel.getBrand_name());
        BringLocationData();
        delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItem(itemModel.getId());
            }
        });

        add_item_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemModel checItemModel = new ItemModel(
                        "1", itemModel.getId(), itemModel.getShop_name(),
                        itemModel.getMain_cat(), "u", itemModel.getSub_sub_cat(),
                        itemModel.getSize(), itemModel.getColor(), itemModel.getBrand_name(),
                        itemModel.getItem_images_url(), itemModel.getItem_price(), itemModel.getItem_descount(),
                        itemModel.getItem_name(), itemModel.getItem_city(), itemModel.getItem_bazzar()
                );



                Log.d(AppConstant.TAG, itemModel.getShop_Id() + " "
                        + itemModel.getId() + " " +
                        itemModel.getMain_cat() + " "
                        +"u" + " " +
                        itemModel.getSub_sub_cat() + " " +
                        itemModel.getSize() + " " +
                        itemModel.getColor() + " " +
                        itemModel.getBrand_name() + " " +
                        itemModel.getItem_images_url() + " " +
                        itemModel.getItem_price() + " " +
                        itemModel.getItem_descount() + " " +
                        itemModel.getItem_name() + " " +
                        itemModel.getItem_city() + " " +
                        itemModel.getItem_bazzar());


                DBAdapter dbAdapter = new DBAdapter(Item_Details.this);
                Log.d(AppConstant.TAG + " : exit", dbAdapter.rowIdExists(itemModel.getShop_Id()) + "");

                if (dbAdapter.rowIdExists(itemModel.getId())) {
                    if (new DBAdapter(Item_Details.this).saveSpacecraft(checItemModel)) {
                        ArrayList<ItemModel> itemModels = new DBAdapter(Item_Details.this).retrieveSpacecrafts();

                        for (int i = 0; i < itemModels.size(); i++) {
                            ItemModel itemModel = itemModels.get(i);
                            Log.d(AppConstant.TAG+": Added Data i"+i+" ",
                                    "item name = "+itemModel.getItem_name()+
                                            " Shop Name = "+itemModel.getShop_name()+
                                            " Price = "+itemModel.getItem_price()+
                                            " image urls = "+itemModel.getItem_images_url());
                            Toast.makeText(Item_Details.this, "Saved to Favourite", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(Item_Details.this, "not saved", Toast.LENGTH_SHORT).show();
                    }

                }
                else {
                    Toast.makeText(Item_Details.this, "Already Saved To Favourite", Toast.LENGTH_SHORT).show();
                }
            }


        });



        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Item_Details.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        add_shop_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DBAdapter dbAdapter=new DBAdapter(Item_Details.this);
                boolean checkEntery=dbAdapter.rowIdExistsShop(itemModel.getShop_Id());
                if (checkEntery){
                    ShopModel shopModel = new ShopModel();
                    shopModel.setId(itemModel.getShop_Id());
                    shopModel.setShop_name(shop_nameS);
                    shopModel.setShop_img(area);
                    shopModel.setCity(cityS);
                    shopModel.setCity_area(city_areaS);
                    shopModel.setShop_lat_lang(shop_lat_lang);
                    Log.d(AppConstant.TAG+": Added Data i"+" ",
                            "shop id = "+shopModel.getId()+
                                    " Shop Name = "+shopModel.getShop_name()+
                                    " lat lang = "+shopModel.getShop_lat_lang()+
                                    " image urls = "+shopModel.getShop_img());
                    if (dbAdapter.saveShopToDb(shopModel)){

                        ArrayList<ShopModel> itemModels = new DBAdapter(Item_Details.this).retrieveShop();

                        for (int i = 0; i < itemModels.size(); i++) {
                            ShopModel itemModel = itemModels.get(i);
                            Log.d(AppConstant.TAG+": Added Data i"+i+" ",
                                    "shop id = "+itemModel.getId()+
                                            " Shop Name = "+itemModel.getShop_name()+
                                            " lat lang = "+itemModel.getShop_lat_lang()+
                                            " image urls = "+itemModel.getShop_img());
                            Toast.makeText(Item_Details.this, "Saved Shop to Favourite", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {

                    }
                }
                else {


                    Toast.makeText(Item_Details.this, "Shop Already Exist", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void deleteItem(String id) {
        rotating.start();
        volleyService.DeleteItem(AppConstant.DomainName + AppConstant.Dir + AppConstant.delete_item,
                id, new VolleyService.VolleyResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(AppConstant.TAG+": Item Delete",response);
                       if (response.matches("Record deleted successfully")){
                           Toast.makeText(Item_Details.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(Item_Details.this,Shop_profile.class));
                           finish();
                           rotating.stop();
                       }
                       else {
                           Toast.makeText(Item_Details.this, "something went wrong Try Again Later", Toast.LENGTH_SHORT).show();
                           rotating.stop();
                       }

                    }

                    @SuppressLint("LongLogTag")
                    @Override
                    public void onError(VolleyError error) {
                        Log.d(AppConstant.TAG+": VolleyError Item Delete",error.getMessage());
                        rotating.stop();

                    }
                });


    }

    public void BringLocationData(){
        volleyService.GetItemLocation(AppConstant.DomainName + AppConstant.Dir + AppConstant.get_item_location,
                itemModel.getShop_Id(), new VolleyService.VolleyResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(AppConstant.TAG+": Item location",response);
                        try {
                            JSONArray jsonArray=new JSONArray(response);
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject jsonObject= (JSONObject) jsonArray.get(i);
                                shop_nameS=jsonObject.getString("shop_name");
                                area=jsonObject.getString("shop_img");
                                shop_lat_lang=jsonObject.getString("shop_lat_lang");
                                city_areaS=jsonObject.getString("city_area");
                                cityS=jsonObject.getString("city");
                            }


                            shop_location.setText(city_areaS);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(VolleyError error) {

                    }
                });


    }

    public void viewOnMap(View view) {


        List<String> items = Arrays.asList(shop_lat_lang.split("\\s*,\\s*"));

        String strUri = "http://maps.google.com/maps?q=loc:" + items.get(0) + "," + items.get(1) + " (" + "Label which you want" + ")";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));

        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");

        startActivity(intent);
    }

    public void ViewShop(View view) {
        startActivity(new Intent(Item_Details.this,Shop_profile_by_customer.class));

    }
}
