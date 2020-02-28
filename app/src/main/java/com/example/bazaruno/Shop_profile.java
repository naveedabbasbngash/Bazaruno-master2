package com.example.bazaruno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Model.Users;
import com.example.bazaruno.Services.VolleyService;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.bazaruno.AppConstants.AppConstant.cat;

public class Shop_profile extends AppCompatActivity {

    TextView shopName;
    private VolleyService volleyService;
    private ExpandableHeightGridView gridView;
    private RotateLoading newton_cradle_loading;
    private ImageView not_found;
    private ArrayList<ItemModel> itemModelslist=new ArrayList<>();
    private Main_Gird_View_Adapter adapter;
    MySharePreferences mySharePreferences;
    Users users=new Users();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_profile);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        shopName=findViewById(R.id.shopName);
        mySharePreferences=new MySharePreferences();
        users=mySharePreferences.getUserData(this);
        volleyService=new VolleyService(this);
        shopName.setText(users.getShop_name());

        gridView=(ExpandableHeightGridView) findViewById(R.id.girdview);
        newton_cradle_loading=findViewById(R.id.newton_cradle_loading);
        not_found=findViewById(R.id.not_found);

        BringItems(users.getId());
    }
    void BringItems(String city)
    {


        newton_cradle_loading.start();
        volleyService.OwnerPorfile(AppConstant.DomainName + AppConstant.Dir + AppConstant.ownerDetails,
                city, new VolleyService.VolleyResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(AppConstant.TAG + " Search Items :", response.length()+"");

                        if (response.length() < 70) {
                            newton_cradle_loading.stop();
                            not_found.setVisibility(View.VISIBLE);
                            Toast.makeText(Shop_profile.this, "Nothing Found", Toast.LENGTH_SHORT).show();

                        } else {
                            try {
                                JSONArray jsonElements = new JSONArray(response);
                                Log.d(AppConstant.TAG + " BringItems :", jsonElements + "And" + response);
                                for (int i = 0; i < jsonElements.length(); i++) {
                                    JSONObject jsonObject = (JSONObject) jsonElements.get(i);
                                    ItemModel itemModel = new ItemModel();
                                    itemModel.setId(jsonObject.getString("id"));
                                    itemModel.setItem_name(jsonObject.getString("item_name"));
                                    itemModel.setShop_Id(jsonObject.getString("shop_Id"));
                                    itemModel.setShop_name(jsonObject.getString("shop_name"));
                                    itemModel.setMain_cat(jsonObject.getString("main_cat"));
                                    itemModel.setSub_cat(jsonObject.getString("sub_cat"));
                                    itemModel.setSub_sub_cat(jsonObject.getString("sub_sub_cat"));
                                    itemModel.setSize(jsonObject.getString("size"));
                                    itemModel.setColor(jsonObject.getString("color"));
                                    itemModel.setBrand_name(jsonObject.getString("brand_name"));
                                    itemModel.setItem_images_url(jsonObject.getString("item_images_url"));
                                    itemModel.setItem_price(jsonObject.getString("item_price"));
                                    itemModel.setItem_descount(jsonObject.getString("item_descount"));

                                    itemModelslist.add(itemModel);


                                }
                                adapter = new Main_Gird_View_Adapter(Shop_profile.this, itemModelslist);
                                gridView.setExpanded(true);
                                adapter.notifyDataSetChanged();
                                gridView.setAdapter(adapter);
                                newton_cradle_loading.stop();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                newton_cradle_loading.stop();
                            }

                        }
                    }

                    @Override
                    public void onError(VolleyError error) {

                        newton_cradle_loading.stop();
                    }
                });

/*       ArrayList<Gird_Data_Container> list=new ArrayList<>();

       Gird_Data_Container container=new Gird_Data_Container();
       container.setImage("https://hull4heroes.org.uk/wp-content/uploads/2018/07/hull_4_heroes_logo_tshirt_dark_gray.png");
       container.setName("T-Shirts Free");
       container.setPrice(34);
       container.setRating("5.0");

        Gird_Data_Container container1=new Gird_Data_Container();
        container1.setName("Sumsung j-10");
        container1.setImage("https://static.businessinsider.com/image/5c54826474c58717f026a77d-1200/galaxy-s10e-leak-4x3.png");
        container1.setPrice(200);
        container1.setRating("4.6");


        Gird_Data_Container container2=new Gird_Data_Container();
        container2.setName("Bluetooth headphones");
        container2.setImage("https://images-na.ssl-images-amazon.com/images/I/61BYbqQaPpL._SX466_.jpg");
        container2.setPrice(400);
        container2.setRating("4.2");

        Gird_Data_Container container3=new Gird_Data_Container();
        container3.setName("Golden Free");
        container3.setImage("https://cdn.shopify.com/s/files/1/0658/1297/products/LXG_LR_1024x1024.jpg?v=1504121273");
        container3.setPrice(100);
        container3.setRating("4.1");


        list.add(container);
        list.add(container1);
        list.add(container2);
        list.add(container3);*/





    }
}
