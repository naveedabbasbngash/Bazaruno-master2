package com.example.bazaruno.Helpers;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;


import com.example.bazaruno.AppConstants.AppConstant;
import com.example.bazaruno.Item_Details;
import com.example.bazaruno.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static String NOTIFICATION_CHANNEL_ID = "com.itw.firebasepushnotificationdemo";
    public static final int NOTIFICATION_ID = 0;
    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;
    private NotificationManager mNotificationManager;

    @SuppressLint("LongLogTag")


    @Override
    public void onMessageReceived(@NonNull final RemoteMessage remoteMessage) {


        Log.d(AppConstant.TAG, "onMessageReceived: " + remoteMessage.toString());

        if (remoteMessage.getData().size() > 0) {

            Log.d("TEMP", "Video url: " + remoteMessage.getData().get("shop_name"));
            Log.d("TEMP", "nameofSub: " + remoteMessage.getData().get("main_cat"));
            Log.d("TEMP", "pdfuri: " + remoteMessage.getData().get("shop_Id"));
            Log.d("TEMP", "likesuri: " + remoteMessage.getData().get("sub_cat"));
            Log.d("TEMP", "dislikesuri: " + remoteMessage.getData().get("sub_sub_cat"));


            Intent intent = new Intent(this, Item_Details.class);

            intent.setAction(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.putExtra("shop_name", remoteMessage.getData().get("shop_name"));
            intent.putExtra("main_cat", remoteMessage.getData().get("main_cat"));
            intent.putExtra("shop_Id", remoteMessage.getData().get("shop_Id"));
            intent.putExtra("sub_cat", remoteMessage.getData().get("sub_cat"));
            intent.putExtra("sub_sub_cat", remoteMessage.getData().get("sub_sub_cat"));
            intent.putExtra("size", remoteMessage.getData().get("size"));

            intent.putExtra("color", remoteMessage.getData().get("color"));
            intent.putExtra("brand_name", remoteMessage.getData().get("brand_name"));
            intent.putExtra("item_images_url", remoteMessage.getData().get("item_images_url"));
            intent.putExtra("item_price", remoteMessage.getData().get("item_price"));
            intent.putExtra("item_descount", remoteMessage.getData().get("item_descount"));
            intent.putExtra("item_name", remoteMessage.getData().get("item_name"));
            intent.putExtra("item_city", remoteMessage.getData().get("item_city"));
            intent.putExtra("item_bazzar", remoteMessage.getData().get("item_bazzar"));

            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);
            String channelId = "Default";
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, channelId)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Check Out New Collection Of "+remoteMessage.getData().get("item_name"))
                    .setContentText("By "+remoteMessage.getData().get("shop_name")).setAutoCancel(true).setContentIntent(pendingIntent);
            ;
            NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(channelId, "Default channel", NotificationManager.IMPORTANCE_DEFAULT);
                manager.createNotificationChannel(channel);
            }
            manager.notify(0, builder.build());

        }
    }
}