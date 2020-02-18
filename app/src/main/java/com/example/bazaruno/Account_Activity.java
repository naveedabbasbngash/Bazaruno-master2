package com.example.bazaruno;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.bazaruno.Helpers.MySharePreferences;

public class Account_Activity extends AppCompatActivity {

    Button login,signup,no_thanks;
    MySharePreferences mySharePreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_account_);

        mySharePreferences=new MySharePreferences();
        login=(Button) findViewById(R.id.login);
        signup=(Button) findViewById(R.id.signup);
        no_thanks=(Button) findViewById(R.id.thanks);

        Boolean checkLoginStatus=mySharePreferences.loginStatus(this);
        if (checkLoginStatus){
            startActivity(new Intent(this,Account_More.class));
        }

        // click listener for Login button
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });

        // click listener for signup button
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
            }
        });

        // click listner for no_thanks button
        no_thanks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             finish();
            }
        });


    }
}
