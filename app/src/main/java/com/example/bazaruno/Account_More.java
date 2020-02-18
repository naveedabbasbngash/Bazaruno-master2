package com.example.bazaruno;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.Users;

public class Account_More extends AppCompatActivity {

    MySharePreferences mySharePreferences;
    Users users;
    TextView owner_name,area;
    EditText email,name,phone,cnic,shop_name,shop_full_address,pass;
    Spinner sp_city,sp_area,sp_bazzar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account__more);
        owner_name=findViewById(R.id.owner_name);
        area=findViewById(R.id.area);

        email=findViewById(R.id.email);
        name=findViewById(R.id.name);
        phone=findViewById(R.id.phone);
        cnic=findViewById(R.id.cnic);
        shop_name=findViewById(R.id.shop_name);
        shop_full_address=findViewById(R.id.shop_full_address);
        pass=findViewById(R.id.pass);


        sp_city=findViewById(R.id.sp_city);
        sp_area=findViewById(R.id.sp_area);
        sp_bazzar=findViewById(R.id.sp_bazzar);


        mySharePreferences=new MySharePreferences();
        users=new Users();
        users=mySharePreferences.getUserData(this);

        owner_name.setText(users.getUsername());
        area.setText(users.getCity_area());
        email.setText(users.getEmail());
        name.setText(users.getUsername());
        phone.setText(users.getMobile_no());
        cnic.setText(users.getCnic());
        shop_name.setText(users.getShop_name());
        shop_full_address.setText(users.getShop_address());
        pass.setText(users.getPassword());


        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, AppConstant.cities);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_city.setAdapter(adapter);

        sp_city.setSelection(getIndex(sp_city,users.getCity()));



        sp_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (i==1){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            Account_More.this, android.R.layout.simple_spinner_item, AppConstant.peshawarAreas);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_area.setAdapter(adapter);
                    sp_area.setSelection(getIndex(sp_area,users.getCity_area()));


                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                            Account_More.this, android.R.layout.simple_spinner_item, AppConstant.peshawarBazzar);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_bazzar.setAdapter(adapter2);
                    sp_bazzar.setSelection(getIndex(sp_bazzar,users.getBazzar()));
                }
                if (i==2){
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                            Account_More.this, android.R.layout.simple_spinner_item, AppConstant.kohatArea);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_area.setAdapter(adapter);
                    sp_area.setSelection(getIndex(sp_area,users.getCity_area()));

                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
                            Account_More.this, android.R.layout.simple_spinner_item, AppConstant.kohatBazzar);

                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    sp_bazzar.setAdapter(adapter2);
                    sp_bazzar.setSelection(getIndex(sp_bazzar,users.getBazzar()));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(true);
        }


        ImageView image=(ImageView) findViewById(R.id.image);
        ImageView icon=(ImageView) findViewById(R.id.icon);

        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 1);

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

    private int getIndex(Spinner spinner, String myString){

        int index = 0;

        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).equals(myString)){
                index = i;
            }
        }
        return index;
    }
}
