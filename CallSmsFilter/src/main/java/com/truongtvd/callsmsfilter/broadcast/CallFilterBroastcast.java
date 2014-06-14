package com.truongtvd.callsmsfilter.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.RemoteException;
import android.provider.CallLog;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.android.internal.telephony.ITelephony;
import com.truongtvd.callsmsfilter.database.DataCallFilterHelper;
import com.truongtvd.callsmsfilter.database.DataLogHelper;
import com.truongtvd.callsmsfilter.view.NotificationHelper;

import java.lang.reflect.Method;

/**
 * Created by truongtvd on 6/11/14.
 */
public class CallFilterBroastcast extends BroadcastReceiver {
    private TelephonyManager telephonyManager;
    private ITelephony telephonyService;
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener PhoneListener = new MyPhoneStateListener();
        telephonyManager.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        try {
            Class c = Class.forName(telephonyManager.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(telephonyManager);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class MyPhoneStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, final String incomingNumber) {
            //super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case LISTEN_SERVICE_STATE:
                    DataCallFilterHelper dataCallFilterHelper = new DataCallFilterHelper(context);
                    int filter = dataCallFilterHelper.getFilterCall(incomingNumber);
                    if (filter > 0) {
                        try {
                            telephonyService.endCall();
                            DataLogHelper dataLogHelper=new DataLogHelper(context);
                            dataLogHelper.addLog(incomingNumber);
                            new Thread() {
                                public void run() {
                                    deleteNumber(incomingNumber);
                                }
                            }.start();
                            int show=dataCallFilterHelper.getShowNotification(incomingNumber);

                            if(show==2){
                                String title=dataCallFilterHelper.getTitleNotification(incomingNumber);
                                String content=dataCallFilterHelper.getContentNotification(incomingNumber);
                                NotificationHelper notificationHelper=new NotificationHelper(context);
                                notificationHelper.createNotificaion(title,content);

                            }
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }

        }
        private void deleteNumber(String phoneNumber) {

            try {
                Thread.sleep(4000);
                String strNumberOne[] = { phoneNumber };
                Cursor cursor = context.getContentResolver().query(
                        CallLog.Calls.CONTENT_URI, null,
                        CallLog.Calls.NUMBER + " = ? ", strNumberOne, "");
                boolean bol = cursor.moveToFirst();
                if (bol) {
                    do {
                        int idOfRowToDelete = cursor.getInt(cursor
                                .getColumnIndex(CallLog.Calls._ID));
                        context.getContentResolver().delete(
                                CallLog.Calls.CONTENT_URI,
                                CallLog.Calls._ID + "= ? ",
                                new String[] { String.valueOf(idOfRowToDelete) });
                    } while (cursor.moveToNext());
                }
            } catch (Exception ex) {
                Log.v("deleteNumber",
                        "Exception, unable to remove # from call log: "
                                + ex.toString()
                );
            }
        }

    }


}
