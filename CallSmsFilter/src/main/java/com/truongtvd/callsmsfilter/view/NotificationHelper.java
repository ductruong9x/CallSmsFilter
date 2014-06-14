package com.truongtvd.callsmsfilter.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.truongtvd.callsmsfilter.PasswordActivity;
import com.truongtvd.callsmsfilter.R;

/**
 * Created by truongtvd on 6/12/14.
 */
public class NotificationHelper {
    private Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void createNotificaion(String title,String content){
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        long time = 2000;
        mBuilder.setAutoCancel(true);
        mBuilder.setLights(Color.GREEN,500,500);
        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_SOUND);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(content);
        Intent resultIntent = new Intent(context, PasswordActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(PasswordActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager notificationManager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());
    }
}
