package com.example.bazaruno;

import android.content.Intent;
import androidx.annotation.NonNull;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Shop_Details_Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop__details_);

        Bundle bundle=getIntent().getExtras();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (bundle != null)
        {
            toolbar.setTitle(bundle.getString("name"));
        }
        else {
            toolbar.setTitle("Shop Details");
        }
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

          TextView Rating=(TextView) findViewById(R.id.ratingvalues);
        Rating.setText(Html.fromHtml("&#9733;") + " "+"4.1");

        ImageView image=(ImageView) findViewById(R.id.image);
        final ProgressBar progressBar=(ProgressBar) findViewById(R.id.progress);
        TextView title=(TextView) findViewById(R.id.title);
        TextView city=(TextView) findViewById(R.id.city);


        if (bundle != null)
        {
            title.setText(bundle.getString("name"));
            city.setText(bundle.getString("city"));
            Picasso.get().load(bundle.getString("image")).into(image, new com.squareup.picasso.Callback() {
                @Override
                public void onSuccess() {
                  progressBar.setVisibility(View.GONE);
                }

                @Override
                public void onError(Exception e) {

                }
            });
        }

        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(0).setCheckable(false);
        bottomNavigationView.getMenu().getItem(1).setCheckable(false);
        bottomNavigationView.getMenu().getItem(2).setCheckable(false);
        bottomNavigationView.getMenu().getItem(3).setCheckable(false);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Shop_Details_Activity.this, "Click me", Toast.LENGTH_SHORT).show();
            }
        });

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
}
