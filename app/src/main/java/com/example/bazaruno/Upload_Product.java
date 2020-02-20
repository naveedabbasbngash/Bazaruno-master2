package com.example.bazaruno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.bazaruno.Adapters.MoviesAdapter;
import com.example.bazaruno.AppConstants.AppConstant;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.nabinbhandari.android.permissions.PermissionHandler;
import com.nabinbhandari.android.permissions.Permissions;

import java.util.ArrayList;

public class Upload_Product extends AppCompatActivity {
    Spinner sp_cat;
    private Spinner sp_sub_cat;
    private Spinner sp_sub_sub_cat;
    private Spinner sp_size;
    final int GALLERY_REQUEST = 1200;
    private MoviesAdapter mAdapter;
    private RecyclerView recyclerView;
    private ArrayList movieList=new ArrayList();
    private GalleryPhoto galleryPhoto;


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

        galleryPhoto = new GalleryPhoto(getApplicationContext());
        mAdapter = new MoviesAdapter(movieList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

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
}
