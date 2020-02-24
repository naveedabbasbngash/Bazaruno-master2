package com.example.bazaruno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.bazaruno.Adapters.Item_Image_Adapter;
import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Helpers.MyCommand;
import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Model.Users;
import com.example.bazaruno.Services.VolleyService;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.PhotoLoader;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;
import com.victor.loading.rotate.RotateLoading;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Upload_Product extends AppCompatActivity {
    Spinner sp_cat;
    private Spinner sp_sub_cat;
    private Spinner sp_sub_sub_cat;
    private Spinner sp_size;
    final int GALLERY_REQUEST = 1200;
    private Item_Image_Adapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList<String> movieList=new ArrayList();
    private GalleryPhoto galleryPhoto;
    private String imagepaths="";
    private int counter=0;
    private MyCommand myCommand;
    RotateLoading rotateLoading;
    EditText item_product_name,item_color,item_price,item_discount,item_brand_name;
    private VolleyService volleyServices;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__product);
        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        Permissions.check(this, Manifest.permission.READ_EXTERNAL_STORAGE,
                null, new PermissionHandler() {
                    @Override
                    public void onGranted() {

                    }
                });

        sp_cat=findViewById(R.id.sp_cat);
        sp_sub_cat=findViewById(R.id.sp_sub_cat);
        sp_sub_sub_cat=findViewById(R.id.sp_sub_sub_cat);
        sp_size=findViewById(R.id.sp_size);
        recyclerView = (RecyclerView) findViewById(R.id.item_images);
        item_product_name=findViewById(R.id.item_product_name);
        item_color=findViewById(R.id.item_color);
        item_price=findViewById(R.id.item_price);
        item_discount=findViewById(R.id.item_discount);
        item_brand_name=findViewById(R.id.item_brand_name);
        rotateLoading=findViewById(R.id.newton_cradle_loading);

        volleyServices=new VolleyService(this);
        myCommand = new MyCommand(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        mAdapter = new Item_Image_Adapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, AppConstant.cat);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_cat.setAdapter(adapter);


        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, AppConstant.size);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_size.setAdapter(adapter2);


        sp_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==1){

                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            Upload_Product.this, android.R.layout.simple_spinner_item, AppConstant.fashion_sub_cat_1);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_sub_cat.setAdapter(adapter);


                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                            Upload_Product.this, android.R.layout.simple_spinner_item, AppConstant.fashion_sub_cat_2);

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_sub_sub_cat.setAdapter(adapter1);

                }
                if (i == 2) {


                    sp_size.setVisibility(View.GONE);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            Upload_Product.this, android.R.layout.simple_spinner_item, AppConstant.mobile_tablet_cat1);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_sub_cat.setAdapter(adapter);


                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                            Upload_Product.this, android.R.layout.simple_spinner_item, AppConstant.mobile_sub_cat_2);

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_sub_sub_cat.setAdapter(adapter1);
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sp_sub_cat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (sp_cat.getSelectedItemPosition()==2){
                    if (i==0){

                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                                Upload_Product.this, android.R.layout.simple_spinner_item, AppConstant.mobile_sub_cat_2);

                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_sub_sub_cat.setAdapter(adapter1);
                    }
                    if (i==1){

                        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                                Upload_Product.this, android.R.layout.simple_spinner_item, AppConstant.mobile_sub_cat_3);

                        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        sp_sub_sub_cat.setAdapter(adapter1);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void addImages(View view) {

        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, GALLERY_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_REQUEST) {
                galleryPhoto.setPhotoUri(data.getData());
                String photoPath = galleryPhoto.getPath();
                movieList.add(photoPath);
                mAdapter.notifyDataSetChanged();
                Log.d(AppConstant.TAG + " add images", photoPath);

            }

        }
    }

    public void addItems(View view) {


        if (item_product_name.getText().toString().length() < 3) {
            Toast.makeText(this, "Correct Item Name", Toast.LENGTH_SHORT).show();
        } else if (sp_cat.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select Category First", Toast.LENGTH_SHORT).show();
        } else if (sp_size.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "Select size First", Toast.LENGTH_SHORT).show();
        } else if (item_color.getText().toString().length() < 3) {
            Toast.makeText(this, "Correct Item Color", Toast.LENGTH_SHORT).show();
        } else if (item_brand_name.getText().toString().length() < 3) {
            Toast.makeText(this, "Enter Brand Name", Toast.LENGTH_SHORT).show();
        } else if (movieList.isEmpty()) {
            Toast.makeText(this, "Add Item Image First", Toast.LENGTH_SHORT).show();
        } else if (item_price.getText().toString().trim().length() < 1) {
            Toast.makeText(this, "Enter Item Price", Toast.LENGTH_SHORT).show();
        } else if (item_discount.getText().toString().trim().length() < 1) {
            item_discount.setText("0 %");
        } else {

            MySharePreferences idMySharePreferences=new MySharePreferences();

            Users itemModelId=new Users();
            itemModelId=idMySharePreferences.getUserData(Upload_Product.this);
            final ItemModel itemModelAdd=new ItemModel();

            itemModelAdd.setId(itemModelId.getId());
            itemModelAdd.setItem_name(item_product_name.getText().toString().trim());
            itemModelAdd.setShop_name(itemModelId.getShop_name());
            itemModelAdd.setMain_cat(sp_cat.getSelectedItem().toString());
            itemModelAdd.setSub_cat(sp_sub_cat.getSelectedItem().toString());
            itemModelAdd.setSub_sub_cat(sp_sub_sub_cat.getSelectedItem().toString());
            itemModelAdd.setSize(sp_size.getSelectedItem().toString());
            itemModelAdd.setBrand_name(item_brand_name.getText().toString().trim());
            itemModelAdd.setColor(item_color.getText().toString().trim());
            itemModelAdd.setItem_price(item_price.getText().toString().trim());
            itemModelAdd.setItem_descount(item_discount.getText().toString());

            rotateLoading.start();
            for (final String imagePath : movieList) {

                try {
                    Bitmap bitmap = PhotoLoader.init().from(imagePath).requestSize(512, 512).getBitmap();
                    final String encodedString = ImageBase64.encode(bitmap);

                    String url = AppConstant.DomainName + AppConstant.Dir + AppConstant.upload;
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(AppConstant.TAG + " onResponse", response);

//                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                Boolean success = jsonObject.getBoolean("success");
                                if (success) {
                                    Log.d("my images paths", response);
                                    if (imagepaths.length() <= 0) {
                                        imagepaths = jsonObject.getString("imageurl");
                                    } else {
                                        imagepaths = imagepaths + "," + jsonObject.getString("imageurl");
                                    }
                                    if (counter++ == movieList.size() - 1) {
                                        Log.d("my images paths here", imagepaths);
                                        itemModelAdd.setItem_images_url(imagepaths);

                                        sendToServer(itemModelAdd);
                                    }

                                } else {
                                    rotateLoading.stop();
                                    Toast.makeText(Upload_Product.this, "Please Try Again", Toast.LENGTH_SHORT).show();


                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d(AppConstant.TAG + " JSONException", e.getMessage());

                            }
                        }
                    }, new Response.ErrorListener() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), "Error while uploading image", Toast.LENGTH_SHORT).show();
                            Log.d(AppConstant.TAG+" onErrorResponse", error.getMessage());

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<>();
                            params.put("image", encodedString);
                            return params;
                        }
                    };

                    myCommand.add(stringRequest);

                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), "Error while loading image", Toast.LENGTH_SHORT).show();
                }

            }
            myCommand.execute();
        }
    }
    public void sendToServer(ItemModel itemModelAdd){


            volleyServices.AddItem(AppConstant.DomainName+AppConstant.Dir+AppConstant.additem,
                    itemModelAdd,new VolleyService.VolleyResponseListener() {
                        @SuppressLint("LongLogTag")
                        @Override
                        public void onSuccess(String response) {

                            Log.d(AppConstant.TAG+" upload Product",response);
                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                Boolean success=jsonObject.getBoolean("success");
                                if (success){
                                    Toast.makeText(Upload_Product.this, "Item Added Successfully", Toast.LENGTH_SHORT).show();

                                 startActivity(new Intent(Upload_Product.this,MainActivity.class));
                                 finish();

                                }
                                else {
                                    Toast.makeText(Upload_Product.this, "Item Added Failled", Toast.LENGTH_SHORT).show();


                                }
                                rotateLoading.stop();
                            } catch (JSONException e) {
                                e.printStackTrace();

                                rotateLoading.stop();
                                Log.d(AppConstant.TAG+" upload Product JSONException ",e.getMessage());

                            }

                        }

                        @SuppressLint("LongLogTag")
                        @Override
                        public void onError(VolleyError error) {
                            rotateLoading.stop();
                            Log.d(AppConstant.TAG+" upload Product VolleyError ",error.getMessage());


                        }
                    });


        }




    }

