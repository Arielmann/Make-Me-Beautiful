package com.example.home.makemebeautiful.servercommunication;

import android.app.Activity;

import com.example.home.makemebeautiful.choosestylist.model.ChooseStylistModel;
import com.example.home.makemebeautiful.choosestylist.model.OnJsonReceivedFromServer;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class ReadStylistsFromServer extends Thread {

    /*
    * This class downloads stylists from server.
    * Thread is used to make the action asynchronous;
    * */

    private OnJsonReceivedFromServer interfaceHolder;
    private Activity activity;
    private List<Object> stylists = null;
    private int readingTryouts;

   /* Context and activity are transferred in order to keep
      the activity as the place to execute uiThread
      commands, and preventing to mix
      it with other activities in the
      flow that serves as interfaceHolders
      in other classes nested with this class */

    public ReadStylistsFromServer(Activity activity, OnJsonReceivedFromServer interfaceHolder) {
        this.activity = activity;
        this.interfaceHolder = interfaceHolder;
    }

    private Runnable notifyDataSetChangedInMainThread = new Runnable() {
        @Override
        public void run() {
            ChooseStylistModel.getInstance(null).getAdapter().notifyDataSetChanged();
        }
    };

    private Runnable startInitializingStylistsDetails = new Runnable() {
        @Override
        public void run() {
            interfaceHolder.onJsonReceived(activity, stylists);
        }
    };


    public void run() {
        try {
            URL url = new URL(Config.READ_ALL_STYLISTS);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();

            StringBuilder builder = new StringBuilder();
            byte[] buffer = new byte[1024 * 8];
            int counter;
            while ((counter = inputStream.read(buffer)) != -1) {
                builder.append(new String(buffer, 0, counter));
            }
            String result = builder.toString();
            inputStream.close();
            Gson gson = new GsonBuilder().create();
            stylists = gson.fromJson(result, new TypeToken<List<Stylist>>() {
            }.getType());
            if (stylists != null) {
                activity.runOnUiThread(startInitializingStylistsDetails);
                activity.runOnUiThread(notifyDataSetChangedInMainThread);
            } else {
                readingTryouts++;
                if (readingTryouts < 3) {
                    run();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}