package com.example.bazaruno;

import android.content.Context;
import android.content.Intent;

import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final EditText email=(EditText) findViewById(R.id.email);
        final EditText pass=(EditText) findViewById(R.id.pass);
        final InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);


        // create new account text
        TextView create_account=(TextView) findViewById(R.id.create);
        create_account.setText(Html.fromHtml(String.format(getString(R.string.create))));

        create_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             Intent intent=new Intent(getApplicationContext(),SignUp.class);
             startActivity(intent);
            }
        });


        // login button
        Button login=(Button) findViewById(R.id.logins);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!String.valueOf(email.getText()).equals("") || !String.valueOf(pass.getText()).equals(""))
                {
                    Snackbar.make(v, email.getText()+"\n"+pass.getText(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    email.clearFocus();
                    pass.clearFocus();
                    email.getText().clear();
                    pass.getText().clear();

                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                else
                {
                    Toast.makeText(Login.this, "Please fill the form correctly", Toast.LENGTH_SHORT).show();
                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        });


        // forgot button
        Button forgot=(Button) findViewById(R.id.forgot);
        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(getApplicationContext(),ForgotPassword.class);
                startActivity(intent);
            }
        });




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }
        return super.onOptionsItemSelected(item);
    }
}
