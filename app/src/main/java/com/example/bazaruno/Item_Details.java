package com.example.bazaruno;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.DB.Constants;
import com.example.bazaruno.DB.DBAdapter;
import com.example.bazaruno.DB.DBHelper;
import com.example.bazaruno.DB.TableHelper;
import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Model.ShopModel;
import com.example.bazaruno.Model.Users;
import com.example.bazaruno.Services.VolleyService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
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
    Button compareItem;
    ItemModel NotificationModel=new ItemModel();


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
        compareItem=findViewById(R.id.compareItem);
        rotating = findViewById(R.id.newton_cradle_loading);
        tableHelper=new TableHelper(this);

        Users users=mySharePreferences.getUserData(this);
        itemModel=mySharePreferences.GetItemData(this);

        if (users.getType()==null){
            Toast.makeText(this, "Guest is here", Toast.LENGTH_SHORT).show();
            buyer_stuff.setVisibility(View.VISIBLE);
        }
        else if (users.getType().matches("seller")){
            if (users.getId().matches(itemModel.getShop_Id())){
                delete_item.setVisibility(View.VISIBLE);
            }
            else {
                delete_item.setVisibility(View.GONE);
            }
            Toast.makeText(this, "Seller is here", Toast.LENGTH_SHORT).show();
            seller_stuff.setVisibility(View.VISIBLE);
        }
        else if (users.getType().matches("buyer")){
            Toast.makeText(this, "buyer is here", Toast.LENGTH_SHORT).show();
            buyer_stuff.setVisibility(View.VISIBLE);


        }

        Bundle extras = getIntent().getExtras();

        if (extras!=null){
            Toast.makeText(this, "From Notification", Toast.LENGTH_SHORT).show();
            NotificationModel.setShop_name(extras.getString("shop_name"));
            NotificationModel.setMain_cat(extras.getString("main_cat"));
            NotificationModel.setShop_Id(extras.getString("shop_Id"));
            NotificationModel.setSub_cat(extras.getString("sub_cat"));
            NotificationModel.setSub_sub_cat(extras.getString("sub_sub_cat"));
            NotificationModel.setSize(extras.getString("size"));
            NotificationModel.setColor(extras.getString("color"));
            NotificationModel.setBrand_name(extras.getString("brand_name"));
            NotificationModel.setItem_images_url(extras.getString("item_images_url"));
            NotificationModel.setItem_price(extras.getString("item_price"));
            NotificationModel.setItem_descount(extras.getString("item_descount"));
            NotificationModel.setItem_name(extras.getString("item_name"));
            NotificationModel.setItem_city(extras.getString("item_city"));
            NotificationModel.setItem_bazzar(extras.getString("item_bazzar"));

            volleyService = new VolleyService(this);
            final List<String> items = Arrays.asList(NotificationModel.getItem_images_url().split("\\s*,\\s*"));
            Log.d(AppConstant.TAG + " image list size", String.valueOf(items.size()));
            for (int i = 0; i < items.size(); i++) {
                SlideModel slideModel = new SlideModel("https://kheloaurjeeto.net/bazarona/php/" + items.get(i));
                slideModelArrayList.add(slideModel);
            }
            imageSlider.setImageList(slideModelArrayList);
            item_name.setText(NotificationModel.getItem_name());
            item_price1.setText("Price " + NotificationModel.getItem_price() + " PKR");
            shop_name.setText("Shop " + NotificationModel.getShop_name());
            item_cat.setText(NotificationModel.getMain_cat());
            item_color.setText(NotificationModel.getColor());
            item_price.setText(NotificationModel.getItem_price());
            item_size.setText(NotificationModel.getSize());
            item_brand_name.setText(NotificationModel.getBrand_name());
            BringLocationData(1);

        }
        else {
            Toast.makeText(this, "Not Notification", Toast.LENGTH_SHORT).show();


            volleyService = new VolleyService(this);
            final List<String> items = Arrays.asList(itemModel.getItem_images_url().split("\\s*,\\s*"));
            Log.d(AppConstant.TAG + " image list size", String.valueOf(items.size()));
            for (int i = 0; i < items.size(); i++) {
                SlideModel slideModel = new SlideModel("https://kheloaurjeeto.net/bazarona/php/" + items.get(i));
                slideModelArrayList.add(slideModel);
            }
            imageSlider.setImageList(slideModelArrayList);
            item_name.setText(itemModel.getItem_name());
            item_price1.setText("Price " + itemModel.getItem_price() + " PKR");
            shop_name.setText("Shop " + itemModel.getShop_name());
            item_cat.setText(itemModel.getMain_cat());
            item_color.setText(itemModel.getColor());
            item_price.setText(itemModel.getItem_price());
            item_size.setText(itemModel.getSize());
            item_brand_name.setText(itemModel.getBrand_name());
            BringLocationData(0);

        }
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
                        FirebaseMessaging.getInstance().subscribeToTopic(shopModel.getId())
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(Item_Details.this, "Successfull", Toast.LENGTH_SHORT).show();

                                        }
                                    }
                                });

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

        compareItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(AppConstant.TAG, itemModel.getId() + " "+
                        itemModel.getSub_sub_cat() + " " +
                        itemModel.getSize() + " " +
                        itemModel.getColor() + " " +
                        itemModel.getItem_images_url() + " " +
                        itemModel.getItem_price() + " " +
                        itemModel.getItem_name() + " ");
                final MySharePreferences mySharePreferences=new MySharePreferences();
                String checkCompare=mySharePreferences.checkComparetype(Item_Details.this);
                if (checkCompare.matches("no")){
                    Log.d(AppConstant.TAG+": first time adding" ," "+itemModel.getSub_sub_cat());
                    mySharePreferences.setCompare(Item_Details.this,itemModel.getSub_sub_cat());
                }
                else if (checkCompare.matches(itemModel.getSub_sub_cat())){
                    DBAdapter dbAdapter=new DBAdapter(Item_Details.this);

                    Log.d(AppConstant.TAG+": category exits",itemModel.getSub_sub_cat());
                    if (dbAdapter.CompareExists(itemModel.getId())) {
                        Log.d(AppConstant.TAG+": inserted done","done");
                        dbAdapter.saveToComapre(itemModel);
                        Toast.makeText(Item_Details.this, "Added To Compare", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Log.d(AppConstant.TAG+": exist",itemModel.getId());
                    }
                }
                else if (!checkCompare.matches(itemModel.getSub_sub_cat())){
                    Log.d(AppConstant.TAG+": new cat found",itemModel.getSub_sub_cat());
                    AlertDialog alertDialog = new AlertDialog.Builder(Item_Details.this).create();
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("New Compare Category Found");
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Add New Compare",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    DBAdapter dbAdapter=new DBAdapter(Item_Details.this);
                                    dbAdapter.removeAllCompare();
                                    Toast.makeText(Item_Details.this, "New Compare Category Added", Toast.LENGTH_SHORT).show();
                                    mySharePreferences.setCompare(Item_Details.this,itemModel.getSub_sub_cat());
                                    if (dbAdapter.CompareExists(itemModel.getId())) {
                                        dbAdapter.saveToComapre(itemModel);
                                        Log.d(AppConstant.TAG+": inserted done","done");

                                    }
                                    else {
                                        Log.d(AppConstant.TAG+": exist",itemModel.getId());
                                    }
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Ignore",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();

                }

               /* DBAdapter dbAdapter=new DBAdapter(Item_Details.this);
                boolean TypeExist=dbAdapter.TypeExists(itemModel.getSub_sub_cat());
                if (!TypeExist){
                    Log.d(AppConstant.TAG+" :  Exist ",TypeExist+"");
                    if (dbAdapter.CompareExists(itemModel.getId())){
                        if (dbAdapter.saveToComapre(itemModel)){

                            Log.d(AppConstant.TAG+" :  Inserted  ","true");

                        }
                        else{
                            Log.d(AppConstant.TAG+" :  Inserted  ","false");

                        }
                    }
                    else{
                        Log.d(AppConstant.TAG+" :  Already exist  ","true");

                    }

                }
                else{
                    Log.d(AppConstant.TAG+" :  Same Type ",TypeExist+"");
                }
*/
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

    public void BringLocationData(int i){
        String shopId = null;
        if (i==0){
            shopId=itemModel.getShop_Id();
        }
        else {
            shopId=NotificationModel.getShop_Id();
        }
        volleyService.GetItemLocation(AppConstant.DomainName + AppConstant.Dir + AppConstant.get_item_location,
                shopId, new VolleyService.VolleyResponseListener() {
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


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        if (id == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Item_Details.this,MainActivity.class));
        finishAffinity();
    }
}
