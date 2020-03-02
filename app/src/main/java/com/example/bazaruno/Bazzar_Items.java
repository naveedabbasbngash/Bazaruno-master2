package com.example.bazaruno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;

import com.android.volley.VolleyError;
import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Services.VolleyService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Bazzar_Items extends AppCompatActivity {

    private ExpandableHeightGridView gridView;
    private VolleyService volleyService;
    ArrayList<ItemModel> itemModelslist=new ArrayList<>();
    private Main_Gird_View_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazzar__items);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        int upper=getIntent().getIntExtra("upper",0);
        volleyService=new VolleyService(this);

        gridView=(ExpandableHeightGridView) findViewById(R.id.girdview);
        BringItems(upper);
    }

    void BringItems(int upper)
    {


        volleyService.BringByBazzar(AppConstant.DomainName + AppConstant.Dir + AppConstant.bringData_bazzar,
                upper,upper+3, new VolleyService.VolleyResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        try {
                            JSONArray jsonElements=new JSONArray(response);
                            Log.d(AppConstant.TAG+" BringItems :",jsonElements+"And"+response);
                            for (int i=0;i<jsonElements.length();i++){
                                JSONObject jsonObject= (JSONObject) jsonElements.get(i);
                                ItemModel itemModel=new ItemModel();
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
                            adapter=new Main_Gird_View_Adapter(Bazzar_Items.this,itemModelslist);
                            gridView.setExpanded(true);
                            adapter.notifyDataSetChanged();
                            gridView.setAdapter(adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(VolleyError error) {

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
