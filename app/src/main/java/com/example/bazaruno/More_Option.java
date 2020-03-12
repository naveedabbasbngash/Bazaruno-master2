package com.example.bazaruno;

import android.content.Intent;
import androidx.annotation.NonNull;

import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.Users;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class More_Option extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more__option);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // work for recycel view to set and send data
        recyclerView=(RecyclerView) findViewById(R.id.recycleview);
        Set_Data();

        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        bottomNavigationView.getMenu().getItem(1).setCheckable(false);
        bottomNavigationView.getMenu().getItem(2).setCheckable(false);
        bottomNavigationView.getMenu().getItem(3).setChecked(true);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(More_Option.this, "I was click", Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
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
        else if (id == R.id.more)
        {
            Intent intent=new Intent(getApplicationContext(),More_Option.class);
            startActivity(intent);
        }

        return false;
    }


    // this set data for recycleview
    void Set_Data() {
        MySharePreferences mySharePreferences = new MySharePreferences();
        Users users = new Users();
        users = mySharePreferences.getUserData(More_Option.this);
        if (users.getType().matches("seller")) {
            ArrayList<String> name = new ArrayList<>();
            ArrayList<Integer> image = new ArrayList<>();

            name.add("My Shop");
            name.add("Account");
            name.add("Logout");

            image.add(R.drawable.m_shop_icon);
            image.add(R.drawable.m_notification_icon);
            image.add(R.drawable.logout);
            recyclerView.setHasFixedSize(true);
            More_Recycleview adapter=new More_Recycleview(this,name,image);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        }
        else if (users.getType().matches("buyer")){
            ArrayList<String> name = new ArrayList<>();
            ArrayList<Integer> image = new ArrayList<>();
            /*name.add("Notifications");*/
            name.add("Logout");


            image.add(R.drawable.logout);
            /*image.add(R.drawable.m_notification_icon);*/
            recyclerView.setHasFixedSize(true);
            More_Recycleview adapter=new More_Recycleview(this,name,image);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        }


    }
}
