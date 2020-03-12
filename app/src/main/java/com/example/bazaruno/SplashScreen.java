package com.example.bazaruno;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.DB.DBAdapter;
import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.ItemModel;
import com.example.bazaruno.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

public class SplashScreen extends AppCompatActivity {
    MySharePreferences mySharePreferences=new MySharePreferences();
    int checkBringData;
    DBAdapter dbAdapter;
    ArrayList<ItemModel> itemModelArrayList=new ArrayList<>();

    @SuppressLint("LongLogTag")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        dbAdapter=new DBAdapter(this);
        mySharePreferences.setBringData(this,0);
        FirebaseMessaging.getInstance().subscribeToTopic("database")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SplashScreen.this, "Successfull", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

        if (mySharePreferences.firsttime(this)){
            Users users=new Users();
            users.setType("guest");
            mySharePreferences.SaveUserAds(this,users);
            mySharePreferences.entery(this,false);
        }
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1500);

        itemModelArrayList=dbAdapter.retrieveNotification();
        for (int i=0;i<itemModelArrayList.size();i++){

            ItemModel itemModel=itemModelArrayList.get(i);
            Log.d(AppConstant.TAG+" Notification saved"," shop name "+itemModel.getShop_name());
        }
    }



}
