package com.truongtvd.callsmsfilter.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.internal.telephony.ITelephony;

import java.lang.reflect.Method;

/**
 * Created by truongtvd on 6/11/14.
 */
public class CallFilterBroastcast extends BroadcastReceiver {
    private TelephonyManager telephonyManager;
    private ITelephony telephonyService;

    @Override
    public void onReceive(Context context, Intent intent) {
        telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        MyPhoneStateListener PhoneListener = new MyPhoneStateListener();
        telephonyManager.listen(PhoneListener, PhoneStateListener.LISTEN_CALL_STATE);
        try {
            Class c = Class.forName(telephonyManager.getClass().getName());
            Method m = c.getDeclaredMethod("getITelephony");
            m.setAccessible(true);
            telephonyService = (ITelephony) m.invoke(telephonyManager);
            //telephonyService.silenceRinger();
            //telephonyService.endCall();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private class MyPhoneStateListener extends PhoneStateListener {

        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            //super.onCallStateChanged(state, incomingNumber);
            switch (state) {
                case LISTEN_SERVICE_STATE:
                    if (incomingNumber.equalsIgnoreCase("0963290010")) {
                        try {
                            telephonyService.silenceRinger();
                            telephonyService.endCall();

                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }
                    break;

            }

        }
    }


}
