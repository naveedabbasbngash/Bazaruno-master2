package com.example.bazaruno;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.bazaruno.AppConstants.AppConstant;

public class Upload_Product extends AppCompatActivity {
    Spinner sp_cat;

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
        sp_cat=findViewById(R.id.sp_cat);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, AppConstant.cat);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_cat.setAdapter(adapter);
    }
}
