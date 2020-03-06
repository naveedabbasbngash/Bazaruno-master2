package com.example.bazaruno;

import android.content.Intent;
import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.bazaruno.Helpers.MySharePreferences;
import com.example.bazaruno.Model.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

public class SplashScreen extends AppCompatActivity {
    MySharePreferences mySharePreferences=new MySharePreferences();
    int checkBringData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        mySharePreferences.setBringData(this,0);
     /*   FirebaseMessaging.getInstance().subscribeToTopic("weathers")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SplashScreen.this, "Successfull", Toast.LENGTH_SHORT).show();

                        }
                    }
                });*/

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
    }
}
