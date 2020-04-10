package com.afiq.myapplication;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class App extends Application {

    public static final String NOTIFICATION_CHANNEL_ID = "renovation";


    @Override
    public void onCreate() {
        super.onCreate();
        NotificationManager manager = getSystemService(NotificationManager.class);
        assert manager != null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Project", NotificationManager.IMPORTANCE_HIGH);
            manager.createNotificationChannel(channel);
        }
    }
}
