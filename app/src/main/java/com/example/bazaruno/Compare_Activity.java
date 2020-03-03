package com.example.bazaruno;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.example.bazaruno.DB.DBAdapter;
import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.ItemModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Compare_Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare_);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        viewPager=(ViewPager) findViewById(R.id.view_pager);
        // function work for recyclerview in activity
        Set_Data();
        ImageView back=(ImageView) findViewById(R.id.backarrow);
        ImageView forward=(ImageView) findViewById(R.id.forword);

       back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewPager.getCurrentItem() != 0)
                    viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
            }
        });

        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             if (viewPager.getCurrentItem() < viewPager.getAdapter().getCount())
             {
                 viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
             }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Compare_Activity.this, "I was click", Toast.LENGTH_SHORT).show();
            }
        });


        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        bottomNavigationView.getMenu().getItem(2).setCheckable(false);
        bottomNavigationView.getMenu().getItem(3).setCheckable(false);


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

       int id=menuItem.getItemId();

        if (id == R.id.search)
        {
            Intent intent=new Intent(getApplicationContext(),Search_Activity.class);
            startActivity(intent);
        }
        else if(id == R.id.favorites)
        {
            Intent intent=new Intent(getApplicationContext(),Favorites.class);
            startActivity(intent);
        }
        else if (id == R.id.more)
        {
            Intent intent=new Intent(getApplicationContext(),More_Option.class);
            startActivity(intent);
        }
        return false;
    }



    // setting data to the recycle view item

    void Set_Data()
    {
        ArrayList<Compare_Data_Container> list=new ArrayList<>();

        Compare_Data_Container compare=new Compare_Data_Container();
        compare.setName("Best quality shirts");
        compare.setImage("https://images-na.ssl-images-amazon.com/images/I/810cdIUimcL._UL1500_.jpg");
        compare.setRating("4.9");
        compare.setPrice("100");
        compare.setType("Clothes");
        compare.setColor("Pink");
        compare.setShop_rating("4.2");
        compare.setSize("Medium");

        Compare_Data_Container compare2=new Compare_Data_Container();
        compare2.setName("Sumsang A-10");
        compare2.setImage("https://images-na.ssl-images-amazon.com/images/I/71qJXXHQWXL._SY550_.jpg");
        compare2.setRating("4.3");
        compare2.setPrice("330");
        compare2.setType("Mobile");
        compare2.setColor("Blue");
        compare2.setShop_rating("3.3");
        compare2.setSize("Larg");

        Compare_Data_Container compare3=new Compare_Data_Container();
        compare3.setName("Top Sport Shirts");
        compare3.setImage("https://mbksports.net/wp-content/uploads/2017/04/pastedImage2.png");
        compare3.setRating("4.1");
        compare3.setPrice("123");
        compare3.setType("T-shirts");
        compare3.setColor("Red");
        compare3.setShop_rating("5.0");
        compare3.setSize("Small");

     /*   list.add(compare);
        list.add(compare2);
        list.add(compare3);*/

        DBAdapter dbAdapter=new DBAdapter(Compare_Activity.this);
        ArrayList<ItemModel> itemModels=dbAdapter.getCompareFromDb();
        for (int i=0;i<itemModels.size();i++ ){
            ItemModel itemModel=itemModels.get(i);
            Compare_Data_Container compare_data_container=new Compare_Data_Container();
            compare_data_container.setName(itemModel.getItem_name());
            final List<String> items = Arrays.asList(itemModel.getItem_images_url().split("\\s*,\\s*"));
            compare_data_container.setImage("https://kheloaurjeeto.net/bazarona/php/"+items.get(0));
            compare_data_container.setPrice(itemModel.getItem_price());
            compare_data_container.setColor(itemModel.getColor());
            compare_data_container.setSize(itemModel.getSize());
            compare_data_container.setType(itemModel.getSub_sub_cat());
            list.add(compare_data_container);

        }

        Compare_RecycleView adapter=new Compare_RecycleView(this,list);
         viewPager.setAdapter(adapter);


    }


}
