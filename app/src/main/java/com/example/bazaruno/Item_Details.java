package com.example.bazaruno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Services.VolleyService;

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
        itemModel=mySharePreferences.GetItemData(this);

        volleyService=new VolleyService(this);
        List<String> items = Arrays.asList(itemModel.getItem_images_url().split("\\s*,\\s*"));
        Toast.makeText(this, "INSHALLAH ", Toast.LENGTH_SHORT).show();
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
                                area=jsonObject.getString("city_area");
                                shop_lat_lang=jsonObject.getString("shop_lat_lang");
                            }


                            shop_location.setText(area);
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
}
