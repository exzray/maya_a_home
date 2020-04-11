package com.afiq.myapplication.broadcasts;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.afiq.myapplication.App;
import com.afiq.myapplication.R;
import com.afiq.myapplication.models.ProjectModel;
import com.afiq.myapplication.utilities.Interaction;

public class NotificationReceiver extends BroadcastReceiver {

    private static final String TAG = "NotificationReceiver";


    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationManager manager = context.getSystemService(NotificationManager.class);

        if (manager == null) {
            Log.i(TAG, "manager: null");
            return;
        }

        String title = "" + intent.getStringExtra(Interaction.EXTRA_STRING_TITLE);
        String message = "" + intent.getStringExtra(Interaction.EXTRA_STRING_MESSAGE);

        int id = intent.getIntExtra(Interaction.EXTRA_INT_NOTIFICATION_ID, 1);

        Uri uri = Uri.parse("android.resource://com.afiq.myapplication/" + R.raw.notification);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, App.NOTIFICATION_CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_home);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSound(uri);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

        manager.notify(id, builder.build());
    }
}
