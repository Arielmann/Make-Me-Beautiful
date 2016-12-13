package com.example.home.makemebeautiful.servercommunication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

/**
 * Created by home on 5/25/2016.
 */
public class RegisterToGcm extends AsyncTask<Void, Void, String> {
    private static final String REGISTER_GCM_TAG = "Register to gcm";
    String TAG = "GcmUserId";
    Context context;

    public RegisterToGcm(Context context) {
        this.context = context;
        // id for this application in this device
    }

    @Override
    protected String doInBackground(Void... params) {
        String gcmToken = null;
        try {
            InstanceID instanceID = InstanceID.getInstance(context);
            gcmToken = instanceID.getToken(Config.SENDER_ID,
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return gcmToken;
    }

    @Override
    protected void onPostExecute(String gcmToken) { //PUT ALL INB A DIFFERENT CLASS!
        SharedPrefManager.getInstance(context).saveStringInfoToSharedPreferences(context, "gcmToken", gcmToken);
        Log.d(REGISTER_GCM_TAG, "Token from shared prefrences: " + SharedPrefManager.getInstance(context).getUserGcmToken());
    }
}


