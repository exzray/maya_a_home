package com.afiq.myapplication.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.afiq.myapplication.utilities.FirebaseHelper;

public class UserService extends Service {

    public UserService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        FirebaseHelper.getFirestore();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }
}

