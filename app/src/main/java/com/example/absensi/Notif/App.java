package com.example.absensi.Notif;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {
    public static final String chanel_ID ="chanel";

    @Override
    public void onCreate() {
        super.onCreate();
        creatNotifcannel();
    }

    private void creatNotifcannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            NotificationChannel channel = new NotificationChannel(
                    chanel_ID,"chanel",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel.setDescription("this Notif");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }
    }
}
