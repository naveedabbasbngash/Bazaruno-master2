package com.example.bazaruno;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import androidx.annotation.NonNull;

import com.example.bazaruno.AppConstants.AppConstant;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search_Activity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView bottomNavigationView;
    Search_Database database;
    Spinner citySpinner;
    Spinner bazar;
    Spinner category;
    private String city_string;
    private String bazzar_string;
    private String cat_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_);


        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        citySpinner=findViewById(R.id.city);
        bazar=findViewById(R.id.bazar);
        category=findViewById(R.id.category);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, AppConstant.cities);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, AppConstant.cat);

        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter3);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (citySpinner.getSelectedItemPosition()==1){
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                            Search_Activity.this, android.R.layout.simple_spinner_item, AppConstant.peshawarBazzar);

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bazar.setAdapter(adapter1);
                }
                else if (citySpinner.getSelectedItemPosition()==2){
                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
                            Search_Activity.this, android.R.layout.simple_spinner_item, AppConstant.kohatBazzar);

                    adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    bazar.setAdapter(adapter1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        bottomNavigationView=(BottomNavigationView) findViewById(R.id.bottom_nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.getMenu().getItem(0).setChecked(true);
        bottomNavigationView.getMenu().getItem(1).setCheckable(false);
        bottomNavigationView.getMenu().getItem(2).setCheckable(false);
        bottomNavigationView.getMenu().getItem(3).setCheckable(false);

        final ArrayList<String> list=new ArrayList<>();
        final TextView history=(TextView) findViewById(R.id.history);
        final ListView listView=(ListView) findViewById(R.id.list);
        final EditText searchedit=(EditText) findViewById(R.id.search);

        // create sqlite database object
        database=new Search_Database(this);

        searchedit.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_UP) {
                    if(event.getRawX() >= (searchedit.getRight() - searchedit.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {

                        String url=searchedit.getText().toString();
                        if (!url.equals(""))
                        {
                            if (citySpinner.getSelectedItemPosition()==0){
                                city_string="";
                            }
                            if (bazar.getSelectedItemPosition()==0){
                                bazzar_string="";
                            }
                            if (category.getSelectedItemPosition()==0){
                                cat_string="";
                            }
                            startActivity(new Intent(Search_Activity.this,Search_Items.class)
                                    .putExtra("city",city_string)
                                    .putExtra("bazzar",bazzar_string)
                            .        putExtra("cat",cat_string)
                            .        putExtra("search",searchedit.getText().toString()));

                        }
                        else {
                            Toast.makeText(Search_Activity.this, "Please enter value for search", Toast.LENGTH_SHORT).show();
                        }
                        return true;
                    }
                }
                return false;
            }
        });

        Cursor cursor=database.result();
        if (cursor.moveToFirst()){
            while(!cursor.isAfterLast()){
                String data = cursor.getString(cursor.getColumnIndex("Names"));
               list.add(data);
                cursor.moveToNext();
            }
        }
        cursor.close();

        if (list.isEmpty())
        {
            listView.setVisibility(View.GONE);
            history.setVisibility(View.VISIBLE);
        }
        else {
         /*   Search_ListView adapter=new Search_ListView(this,list);
            listView.setAdapter(adapter);*/
        }

        TextView clear_history=(TextView) findViewById(R.id.clear_history);
        clear_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                database.clear();
                list.clear();
                listView.setAdapter(null);
                history.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
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

        if (id == R.id.login){
            Intent intent=new Intent(getApplicationContext(),Account_Activity.class);
            startActivity(intent);
        }

        else if (id == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        int id = menuItem.getItemId();

         if (id == R.id.compare)
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
