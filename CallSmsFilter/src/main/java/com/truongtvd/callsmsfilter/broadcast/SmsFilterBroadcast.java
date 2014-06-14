package com.truongtvd.callsmsfilter.broadcast;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.util.Log;

import com.truongtvd.callsmsfilter.MainActivity;
import com.truongtvd.callsmsfilter.R;


/**
 * Created by truongtvd on 6/11/14.
 */
public class SmsFilterBroadcast extends BroadcastReceiver {

    final SmsManager sms = SmsManager.getDefault();
    private Context context;
    @Override
    public void onReceive(Context context, Intent intent) {

        this.context=context;
        final Bundle bundle = intent.getExtras();
            if (bundle != null) {
                final Object[] pdusObj = (Object[]) bundle.get("pdus");
                for (int i = 0; i < pdusObj.length; i++) {
                    SmsMessage currentMessage = SmsMessage.createFromPdu((byte[]) pdusObj[i]);
                    String phoneNumber = currentMessage.getDisplayOriginatingAddress();
                    String senderNum = phoneNumber;
                    String message = currentMessage.getDisplayMessageBody();
                    if(phoneNumber.contains("+84963290010")) {
                        abortBroadcast();
                        createNotificaion();
                        Log.i("SmsReceiver", "senderNum: " + senderNum + "; message: " + message);
                    }
                }
            }
    }


    private void createNotificaion(){
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(context);
        mBuilder.setSmallIcon(R.drawable.ic_launcher);
        long time = 2000;
        mBuilder.setAutoCancel(true);
        mBuilder.setLights(Color.GREEN,500,500);
        mBuilder.setDefaults(Notification.DEFAULT_VIBRATE|Notification.DEFAULT_SOUND);


        mBuilder.setContentTitle("Hello");
        mBuilder.setContentText("Tin nhắn quan trọng");
        Intent resultIntent = new Intent(context, MainActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
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
