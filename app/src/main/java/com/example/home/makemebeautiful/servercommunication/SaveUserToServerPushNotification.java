package com.example.home.makemebeautiful.servercommunication;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SaveUserToServerPushNotification extends AsyncTask<String, Void, Boolean> {

    private static final String SAVE_USER_TAG = "Save user to server";
    private Context context;
    private OnPushNotificationSent onPushSent;

    public SaveUserToServerPushNotification(Context context, OnPushNotificationSent interfaceHolder) {
        this.context = context;
        this.onPushSent = interfaceHolder;
    }

    @Override
    protected Boolean doInBackground(final String... strings) {
        HashMap<String, String> userData = SharedPrefManager.getInstance(context).initSharedPrefData();
        OkHttpClient client = new OkHttpClient();
        final RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                //TODO: find out the meaning of the extra string when creating a RequestBody.
                .addFormDataPart("FindMeaning", "FindMeaningValue",
                        RequestBody.create(MediaType.parse("text/csv"), "FindMeaningValue"))
                .addFormDataPart("name", userData.get("name"))
                .addFormDataPart("location", userData.get("location"))
                .addFormDataPart("profile_image_url", userData.get("profileImageUrl"))
                .addFormDataPart("description", userData.get("description"))
                .addFormDataPart("gcmToken", userData.get("gcmToken"))
                .build();

        Request request = new Request.Builder()
                .url(Config.SAVE_USER)
                .post(requestBody)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                onPushSent.onPushFailure();
                Log.d(SAVE_USER_TAG, "UPLOAD FAILED. Details: " + e.toString());
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (!response.isSuccessful()) {
                    onPushSent.onPushFailure();
                    Log.d(SAVE_USER_TAG, "UPLOAD FAILED. Details: " + response.body().string());
                }
                onPushSent.onPushSuccess();
                Log.d(SAVE_USER_TAG, "UPLOAD SUCCESSFUL! Details: " + response.body().string());
            }
        });
        return true;
    }
}
