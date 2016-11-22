package com.example.home.makemebeautiful.chat.broadcastReceivers;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.example.home.makemebeautiful.chat.services.MultiMessagesGcmService;


/**
 * Created by home on 5/30/2016.
 */
public class GcmReceiver extends WakefulBroadcastReceiver {

    private static final String TAG = "Gcm receiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "message received from server");
        ComponentName comp = new ComponentName(context.getPackageName(),
                MultiMessagesGcmService.class.getName());
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
