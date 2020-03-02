package com.example.bazaruno;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.VolleyError;
import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.FilterModel;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Model.Users;
import com.example.bazaruno.Services.VolleyService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.View;
import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , BottomNavigationView.OnNavigationItemSelectedListener
{

    String cityS,bazzarS;

    VolleyService volleyService;
    BottomNavigationView bottomNavigationView; // use to show the bottom list of icon on main screen
    ViewPager viewPager;
    ExpandableHeightGridView gridView;
    int double_click=0;
    int doub_sub_click=0;
    MySharePreferences mySharePreferences;
    private Main_Gird_View_Adapter adapter;

    ArrayList<ItemModel> itemModelslist=new ArrayList<>();
    Users users=new Users();

    Spinner city,bazar;
    private int checkBringData;


    @SuppressLint({"RestrictedApi", "LongLogTag"})
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mySharePreferences=new MySharePreferences();
        users=mySharePreferences.getUserData(this);

        volleyService=new VolleyService(this);

        // define and identify toolbar and set some properties
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // These Line of codes work main page slide show
        viewPager=(ViewPager) findViewById(R.id.bazars);
         Slide_Show_Fun();

         // These line of code for main page gird view
        gridView=(ExpandableHeightGridView) findViewById(R.id.girdview);
         checkBringData=mySharePreferences.checkBringData(this);
        if (checkBringData==0) {
            BringItems();
        }
        else if (checkBringData==1){
            FilterModel filterModel1=mySharePreferences.GetFilterData(MainActivity.this);

            Log.d(AppConstant.TAG+" :"+" 2for man Fashion And Wear","For Man ,Clothing"+getIntent().getStringExtra("city")+
                    " "+ filterModel1.getItem_bazzar());
            if (getIntent().getStringExtra("city")==null){
                Toast.makeText(this, "here", Toast.LENGTH_SHORT).show();
            }
            String city=getIntent().getStringExtra("city");
            String bazzar=getIntent().getStringExtra("bazzar");
            BringItemsFilter(filterModel1,city,bazzar);
            Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
        }


        // This line of code is about listner on floating adding button on main screen

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

            if (users.getType().matches("buyer")) {
                fab.setVisibility(View.INVISIBLE);
            } else if (users.getType().matches("seller")) {

                fab.setVisibility(View.VISIBLE);
            } else {
                fab.setVisibility(View.VISIBLE);
            }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Upload_Product.class));
//                Snackbar.make(view, "Any thing you can do with this...", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(Color.parseColor("#ff6969"));
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


       // details or properties of bottom list bar items like search more etc
        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        bottomNavigationView.getMenu().getItem(1).setCheckable(false);
        bottomNavigationView.getMenu().getItem(2).setCheckable(false);
        bottomNavigationView.getMenu().getItem(3).setCheckable(false);

        city=navigationView.findViewById(R.id.city);
        bazar=navigationView.findViewById(R.id.bazar);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, AppConstant.cities);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        city.setAdapter(adapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (city.getSelectedItemPosition()==1){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            MainActivity.this, android.R.layout.simple_spinner_item, AppConstant.peshawarBazzar);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bazar.setAdapter(adapter);
                }
                else if (city.getSelectedItemPosition()==2){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            MainActivity.this, android.R.layout.simple_spinner_item, AppConstant.kohatBazzar);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bazar.setAdapter(adapter);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // ########################## For Man Section Code #############################//

       // This function work for show and hiding nav list and change image
        Parent_Controller(R.id.fashion_and_wear,R.id.fashion_drop,R.id.fashion_and_wear_layout);
        // This function work for show and hiding sub category of For Man (like Shoes,Accessories)
        Child_Controller(R.id.for_main_head_layout,R.id.for_main_sub_layout,R.id.for_main_image,R.id.line_for_main);
        // This function work for show and hiding sub category of For Woman (like Shoes,Accessories)
        Child_Controller(R.id.woman_for_main_head_layout,R.id.woman_for_main_sub_layout,R.id.woman_for_main_image,R.id.woman_line_for_main);
        // This function work for show and hiding sub category of For kids (like Shoes,Accessories)
        Child_Controller(R.id.kids_for_main_head_layout,R.id.kids_for_main_sub_layout,R.id.kids_for_main_image,R.id.kids_line_for_main);




            // Work for user click and send intent to another activity man
            Intent_For_Man_Section();
            // Work for user click and send intent to another activity woman
            Intent_For_Man_Section2();
            // Work for user click and send intent to another activity kids
            intent_for_kids_section();



        /*woman*/

        /*mobile and tablets*/
        Parent_Controller(R.id.mobile_fashion_and_wear,R.id.mobile_fashion_drop,R.id.mobile_fashion_and_wear_layout);
        Child_Controller(R.id.mobile_for_main_head_layout,R.id.android_for_main_sub_layout,R.id.android_for_main_image,R.id.andoird_line_for_main);
        intent_for_mobile_section();

        Child_Controller(R.id.iphone_for_main_head_layout,R.id.iphone_for_main_sub_layout,R.id.iphone_for_main_image,R.id.iphone_line_for_main);
        intent_for_hauwi_section();


    }

    private void intent_for_hauwi_section() {
        ArrayList<Integer> ids_for_man_section=new ArrayList<>();
        ids_for_man_section.add(R.id.iphone_clothes_for_man); // id of clothing
        ids_for_man_section.add(R.id.iphone_shoes_for_man); // id of shoes

        // Run when clother item is click or selected
        LinearLayout clother_for_man=(LinearLayout) findViewById(ids_for_man_section.get(0));
        clother_for_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {

                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Mobile & Tablets");
                filterModel.setSub_cat("Iphone");
                filterModel.setSub_sub_cat("Iphone 5s");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);
                FilterModel filterModel1=mySharePreferences.GetFilterData(MainActivity.this);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+filterModel1.getItem_city()+
                       " "+ filterModel1.getItem_bazzar());
//                Intent_Handler(MainActivity.class);
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));
            }
        });

        LinearLayout shoes=(LinearLayout) findViewById(ids_for_man_section.get(1));
        shoes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Mobile & Tablets");
                filterModel.setSub_cat("Iphone");
                filterModel.setSub_sub_cat("Iphone 6S");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });

    }

    private void intent_for_mobile_section() {
        ArrayList<Integer> ids_for_man_section=new ArrayList<>();
        ids_for_man_section.add(R.id.samsung_clothes_for_man); // id of clothing
        ids_for_man_section.add(R.id.huawei_shoes_for_man); // id of shoes
/*        ids_for_man_section.add(R.id.kids_accessories_for_man); // id of accessories
        ids_for_man_section.add(R.id.kids_beauity_and_care_for_man); // id of beauity and care*/

        // Run when clother item is click or selected
        LinearLayout clother_for_man=(LinearLayout) findViewById(ids_for_man_section.get(0));
        clother_for_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {

                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Mobile & Tablets");
                filterModel.setSub_cat("Android");
                filterModel.setSub_sub_cat("samsung");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });

        LinearLayout shoes=(LinearLayout) findViewById(ids_for_man_section.get(1));
        shoes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Mobile & Tablets");
                filterModel.setSub_cat("Android");
                filterModel.setSub_sub_cat("huawei");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });

    }

    private void intent_for_kids_section() {
        ArrayList<Integer> ids_for_man_section=new ArrayList<>();
        ids_for_man_section.add(R.id.kids_clothes_for_man); // id of clothing
        ids_for_man_section.add(R.id.kids_shoes_for_man); // id of shoes
        ids_for_man_section.add(R.id.kids_accessories_for_man); // id of accessories
        ids_for_man_section.add(R.id.kids_beauity_and_care_for_man); // id of beauity and care

        // Run when clother item is click or selected
        LinearLayout clother_for_man=(LinearLayout) findViewById(ids_for_man_section.get(0));
        clother_for_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {

                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Fashion & Wear");
                filterModel.setSub_cat("For Kids");
                filterModel.setSub_sub_cat("Clothings");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });

        LinearLayout shoes=(LinearLayout) findViewById(ids_for_man_section.get(1));
        shoes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Fashion & Wear");
                filterModel.setSub_cat("For Kids");
                filterModel.setSub_sub_cat("Shoes");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });

        LinearLayout accessories_for_man=(LinearLayout) findViewById(ids_for_man_section.get(2));
        accessories_for_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Fashion & Wear");
                filterModel.setSub_cat("For Kids");
                filterModel.setSub_sub_cat("Accessories");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });

        LinearLayout beauity_and_care_for_man=(LinearLayout) findViewById(ids_for_man_section.get(3));
        beauity_and_care_for_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Fashion & Wear");
                filterModel.setSub_cat("For Kids");
                filterModel.setSub_sub_cat("Beauty And Care");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });
    }

    private void Intent_For_Man_Section2()  {
        ArrayList<Integer> ids_for_man_section=new ArrayList<>();
        ids_for_man_section.add(R.id.woman_clothes_for_man); // id of clothing
        ids_for_man_section.add(R.id.woman_shoes_for_man); // id of shoes
        ids_for_man_section.add(R.id.woman_accessories_for_man); // id of accessories
        ids_for_man_section.add(R.id.woman_beauity_and_care_for_man); // id of beauity and care

        // Run when clother item is click or selected
        LinearLayout clother_for_man=(LinearLayout) findViewById(ids_for_man_section.get(0));
        clother_for_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {

                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Fashion & Wear");
                filterModel.setSub_cat("For Womens");
                filterModel.setSub_sub_cat("Clothings");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });

        LinearLayout shoes=(LinearLayout) findViewById(ids_for_man_section.get(1));
        shoes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Fashion & Wear");
                filterModel.setSub_cat("For Womens");
                filterModel.setSub_sub_cat("Shoes");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });

        LinearLayout accessories_for_man=(LinearLayout) findViewById(ids_for_man_section.get(2));
        accessories_for_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Fashion & Wear");
                filterModel.setSub_cat("For Womens");
                filterModel.setSub_sub_cat("Accessories");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });

        LinearLayout beauity_and_care_for_man=(LinearLayout) findViewById(ids_for_man_section.get(3));
        beauity_and_care_for_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Fashion & Wear");
                filterModel.setSub_cat("For Womens");
                filterModel.setSub_sub_cat("Beauty And Care");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // work for toolbar item such as account and search

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search) {
           Intent intent=new Intent(getApplicationContext(),Search_Activity.class);
           startActivity(intent);
        }
        else if (id == R.id.login){


           Intent intent=new Intent(getApplicationContext(),Account_Activity.class);
           startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    // this works for slide naviagation item and their selection

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // function to be run for listining call of bottom naviagation items

        Bottom_Naviation_Item_Listner(id);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    // this function works for bottom item icom menu such search, compare etc

    void  Bottom_Naviation_Item_Listner(int id)
    {
        if (id == R.id.search)
        {
          Intent intent=new Intent(getApplicationContext(),Search_Activity.class);
          startActivity(intent);
        }
        else if (id == R.id.compare)
        {
           Intent intent=new Intent(getApplicationContext(),Compare_Activity.class);
           startActivity(intent);
        }
        else if(id == R.id.favorites)
        {
           Intent intent=new Intent(getApplicationContext(),Favorites.class);
           startActivity(intent);
        }
        else if (id == R.id.more) {
            Boolean checkLogin = mySharePreferences.loginStatus(MainActivity.this);
            if (checkLogin) {
                Intent intent = new Intent(getApplicationContext(), More_Option.class);
                startActivity(intent);
            }
            else {
                startActivity(new Intent(MainActivity.this,Login.class));
            }
        }

    }

    // use for showing show hide content of fashion and wear

    void Parent_Controller(int Image_id, int head_LinearLayout_id, int child_linearlayout_id){

        final ImageView imageView=(ImageView) findViewById(Image_id);
       final LinearLayout fashion_drop=(LinearLayout) findViewById(head_LinearLayout_id);
       final LinearLayout fashion_and_wear_layout=(LinearLayout) findViewById(child_linearlayout_id);
        fashion_and_wear_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if (double_click==0){
                    imageView.setImageResource(R.drawable.down_arrow);
                    fashion_drop.setVisibility(View.VISIBLE);
                    fashion_and_wear_layout.setBackgroundColor(getResources().getColor(R.color.back));
                    double_click=1;
                }
                else if (double_click == 1)
                {
                    imageView.setImageResource(R.drawable.greater);
                    fashion_and_wear_layout.setBackgroundColor(Color.WHITE);
                    fashion_drop.setVisibility(View.GONE);

                    // set sub session to normal state
                    Set_To_Normal_State(R.id.for_main_sub_layout,R.id.line_for_main,R.id.for_main_head_layout,R.id.for_main_image);
                    double_click=0;
                }
            }
        });
    }


    // use for show sub categories such for man and its details
    void Child_Controller(int head,int sub,int sub_image,int sub_line){

        final LinearLayout for_man_head_layout=(LinearLayout) findViewById(head);
       final LinearLayout for_man_sub_layout=(LinearLayout) findViewById(sub);
       final TextView for_man_line=(TextView) findViewById(sub_line);
       final ImageView for_main_image=(ImageView) findViewById(sub_image);

        for_man_head_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (doub_sub_click==0){
                    for_main_image.setImageResource(R.drawable.sub_icon);
                    for_man_line.setVisibility(View.VISIBLE);
                    for_man_head_layout.setBackgroundColor(Color.parseColor("#E8ECE8"));
                    for_man_sub_layout.setBackgroundColor(Color.parseColor("#E8ECE8"));
                    for_man_sub_layout.setVisibility(View.VISIBLE);
                    doub_sub_click=1;
                }
                else if (doub_sub_click == 1)
                {
                    for_main_image.setImageResource(R.drawable.add_icon);
                    for_man_sub_layout.setVisibility(View.GONE);
                    for_man_head_layout.setBackgroundColor(getResources().getColor(R.color.back));
                    for_man_sub_layout.setBackgroundColor(getResources().getColor(R.color.back));
                    for_man_line.setVisibility(View.GONE);
                    doub_sub_click=0;
                }

            }
        });

    }

   // Set the properities of child list to normal when parent list is close
    void Set_To_Normal_State(int sub_Linear_layout_id,int Text_line_id,int head_linear_Layout,int sub_image){

        TextView textView=(TextView) findViewById(Text_line_id);
        textView.setVisibility(View.GONE);
        LinearLayout linearLayout=(LinearLayout) findViewById(sub_Linear_layout_id);
        linearLayout.setVisibility(View.GONE);
        LinearLayout head=(LinearLayout) findViewById(head_linear_Layout);
        head.setBackgroundColor(getResources().getColor(R.color.back));
        ImageView imageView=(ImageView) findViewById(sub_image);
        imageView.setImageResource(R.drawable.add_icon);
    }


    // This function will simply send the intent to another activity
    void Intent_Handler(Class activitys){

        Intent intent=new Intent(getApplicationContext(),activitys);
        startActivity(intent);
        finish();
    }

    void  Intent_For_Man_Section(){
        ArrayList<Integer> ids_for_man_section=new ArrayList<>();
        ids_for_man_section.add(R.id.clothes_for_man); // id of clothing
        ids_for_man_section.add(R.id.shoes_for_man); // id of shoes
        ids_for_man_section.add(R.id.accessories_for_man); // id of accessories
        ids_for_man_section.add(R.id.beauity_and_care_for_man); // id of beauity and care

        // Run when clother item is click or selected
        LinearLayout clother_for_man=(LinearLayout) findViewById(ids_for_man_section.get(0));
        clother_for_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {

                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Fashion & Wear");
                filterModel.setSub_cat("For Men");
                filterModel.setSub_sub_cat("Clothings");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });

        LinearLayout shoes=(LinearLayout) findViewById(ids_for_man_section.get(1));
        shoes.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Fashion & Wear");
                filterModel.setSub_cat("For Men");
                filterModel.setSub_sub_cat("Shoes");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });

        LinearLayout accessories_for_man=(LinearLayout) findViewById(ids_for_man_section.get(2));
        accessories_for_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Fashion & Wear");
                filterModel.setSub_cat("For Men");
                filterModel.setSub_sub_cat("Accessories");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));            }
        });

        LinearLayout beauity_and_care_for_man=(LinearLayout) findViewById(ids_for_man_section.get(3));
        beauity_and_care_for_man.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("LongLogTag")
            @Override
            public void onClick(View v) {
                mySharePreferences.setBringData(MainActivity.this,1);
                FilterModel filterModel=new FilterModel();
                filterModel.setMain_cat("Fashion & Wear");
                filterModel.setSub_cat("For Men");
                filterModel.setSub_sub_cat("Beauty And Care");
                if (city.getSelectedItemPosition()<0){
                    filterModel.setItem_city(city.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_city("");
                }
                if (bazar.getSelectedItemPosition()<0){
                    filterModel.setItem_bazzar(bazar.getSelectedItem().toString());
                }
                else {
                    filterModel.setItem_bazzar("");
                }
                mySharePreferences.SaveFilterData(MainActivity.this,filterModel);

                Log.d(AppConstant.TAG+" :"+"for man Fashion And Wear","For Man ,Clothing"+city.getSelectedItem()+
                        bazar.getSelectedItem());
                startActivity(new Intent(MainActivity.this,MainActivity.class)
                        .putExtra("city",city.getSelectedItem().toString())
                        .putExtra("bazzar",bazar.getSelectedItem().toString()));
                                   }
        });
    }

    // work for creating list slide show
    void Slide_Show_Fun()
    {
      // Demo data

        Clothes_Recycler_Data_Container container=new Clothes_Recycler_Data_Container();
        container.setImage("https://files.guidedanmark.org/files/484/19792_Bazar_Vest_-_Frugtmarked.jpg");
        container.setName("University Road Peshawar");

        Clothes_Recycler_Data_Container container1=new Clothes_Recycler_Data_Container();
        container1.setImage("https://media.dhakatribune.com/uploads/2016/12/20161226-RajibDhar-2459-1.jpg");
        container1.setName("Saddar Bazar Peshawar");

        Clothes_Recycler_Data_Container container2=new Clothes_Recycler_Data_Container();
        container2.setImage("https://media-cdn.tripadvisor.com/media/photo-s/06/f4/34/f0/zelyony-bazaar.jpg");
        container2.setName("Ring Road");

        Clothes_Recycler_Data_Container container3=new Clothes_Recycler_Data_Container();
        container3.setImage("https://islamicplaces.files.wordpress.com/2011/03/bazar-in-damascus-syria.jpg");
        container3.setName("Dalazak road");

        Clothes_Recycler_Data_Container container4=new Clothes_Recycler_Data_Container();
        container4.setImage("https://media.dhakatribune.com/uploads/2016/12/20161226-RajibDhar-2459-1.jpg");
        container4.setName("Qissa Khawani");

        // List of Data object for Recycle view list
        ArrayList<Clothes_Recycler_Data_Container> lists=new ArrayList<>();
        // add item object to the list
        lists.add(container);
        lists.add(container1);
        lists.add(container2);
        lists.add(container3);
        lists.add(container4);

        final Main_Side_Show_Adapter adapter=new Main_Side_Show_Adapter(this,lists);
        viewPager.setAdapter(adapter);

        final Handler handler=new Handler();
        final Runnable runnable=new Runnable() {
            @Override
            public void run() {
             int i=viewPager.getCurrentItem();
             if (i == adapter.list.size()-1)
             {
                 i=0;
                 viewPager.setCurrentItem(i,true);
             }
             else {
                 i++;
                 viewPager.setCurrentItem(i,true);
             }

            }
        };

        Timer timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            handler.post(runnable);
            }
        },5000,5000);

    }

    // work for creating main page girdview

    void BringItems()
    {


        volleyService.GetItems(AppConstant.DomainName + AppConstant.Dir + AppConstant.bringItems
                , new VolleyService.VolleyResponseListener() {
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
                                itemModel.setItem_city(jsonObject.getString("item_city"));
                                itemModel.setItem_bazzar(jsonObject.getString("item_bazzar"));

                                itemModelslist.add(itemModel);


                            }
                            adapter=new Main_Gird_View_Adapter(MainActivity.this,itemModelslist);
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

    void BringItemsFilter(FilterModel filterModel,String city,String bazzar)
    {


        volleyService.GetFilterItems(AppConstant.DomainName + AppConstant.Dir + AppConstant.bringItemsfilter
               ,filterModel,city,bazzar , new VolleyService.VolleyResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(AppConstant.TAG + " BringItems :", response + "");

                        if (response.length() < 90) {
                            Toast.makeText(MainActivity.this, "No Item Found", Toast.LENGTH_SHORT).show();

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
                                adapter = new Main_Gird_View_Adapter(MainActivity.this, itemModelslist);
                                gridView.setExpanded(true);
                                adapter.notifyDataSetChanged();
                                gridView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

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
