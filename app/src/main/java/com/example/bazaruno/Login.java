package com.example.bazaruno;

import android.content.Context;
import android.content.Intent;

import com.android.volley.VolleyError;
import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.Users;
import com.example.bazaruno.Services.VolleyService;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonObject;
import com.victor.loading.rotate.RotateLoading;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {

    RadioGroup type;
    int user_type=0;
    Users users;
    VolleyService volleyService;
    RotateLoading rotateLoading;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        users=new Users();
        volleyService=new VolleyService(this);

        Toolbar toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        final EditText email=(EditText) findViewById(R.id.email);
        final EditText pass=(EditText) findViewById(R.id.pass);
        rotateLoading=findViewById(R.id.newton_cradle_loading);
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
//                    Snackbar.make(v, email.getText()+"\n"+pass.getText(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                    email.clearFocus();
//                    pass.clearFocus();
//                    email.getText().clear();
//                    pass.getText().clear();
//
//                    inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                            InputMethodManager.HIDE_NOT_ALWAYS);

                    users.setEmail(email.getText().toString().trim());
                    users.setPassword(pass.getText().toString().trim());
                    users.setStatus(String.valueOf(user_type));
                    signInNow(users);
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



        type=findViewById(R.id.type);
        type.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i==R.id.seller){
                    user_type=0;

                } else if (i==R.id.rd_buyer) {
                    user_type=1;
                }
            }
        });

    }

    private void signInNow(Users users) {
        rotateLoading.start();
        volleyService.SignInNow(AppConstant.DomainName + AppConstant.Dir + AppConstant.login_now
                , users, new VolleyService.VolleyResponseListener() {
                    @Override
                    public void onSuccess(String response) {
                        Log.d(AppConstant.TAG+" login success",response);

                        try {
                            JSONObject jsonObject=new JSONObject(response);
                            Boolean success=jsonObject.getBoolean("success");
                            if (success){
                                if (jsonObject.getString("type").matches("buyer")){
                                    Users users1=new Users();
                                    MySharePreferences mySharePreferences=new MySharePreferences();
                                    users1.setId(jsonObject.getString("id"));
                                    users1.setType("buyer");
                                    users1.setEmail(jsonObject.getString("email"));
                                    users1.setUsername(jsonObject.getString("username"));
                                    mySharePreferences.SaveUserAds(Login.this,users1);
                                    mySharePreferences.setLoginStatus(Login.this,true);

                                    Toast.makeText(Login.this, "Login Successfully. . ", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this,MainActivity.class));


                                }
                                else {
                                    MySharePreferences mySharePreferences=new MySharePreferences();
                                    Users sellerUsers1=new Users();
                                    sellerUsers1.setId(jsonObject.getString("id"));
                                    sellerUsers1.setType("seller");
                                    sellerUsers1.setEmail(jsonObject.getString("email"));
                                    sellerUsers1.setUsername(jsonObject.getString("username"));
                                    sellerUsers1.setMobile_no(jsonObject.getString("mobile_no"));
                                    sellerUsers1.setCnic(jsonObject.getString("cnic"));
                                    sellerUsers1.setShop_name(jsonObject.getString("shop_name"));
                                    sellerUsers1.setShop_address(jsonObject.getString("shop_address"));
                                    sellerUsers1.setShop_lat_lang(jsonObject.getString("shop_lat_lang"));
                                    sellerUsers1.setCity(jsonObject.getString("city"));
                                    sellerUsers1.setCity_area(jsonObject.getString("city_area"));
                                    sellerUsers1.setBazzar(jsonObject.getString("bazzar"));
                                    mySharePreferences.SaveUserAds(Login.this,sellerUsers1);
                                    mySharePreferences.setLoginStatus(Login.this,true);
                                    Toast.makeText(Login.this, "Login Successfully. . ", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Login.this,MainActivity.class));

                                }

                            }
                            else {
                                Toast.makeText(Login.this, "Incorrect Email Or Password", Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        rotateLoading.stop();
                    }

                    @Override
                    public void onError(VolleyError error) {
                        Log.d(AppConstant.TAG+" VolleyError ",error.getMessage());

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
