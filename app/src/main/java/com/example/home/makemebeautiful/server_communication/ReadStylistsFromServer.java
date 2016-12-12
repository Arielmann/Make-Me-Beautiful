package com.example.home.makemebeautiful.server_communication;

import android.app.Activity;

import com.example.home.makemebeautiful.choose_stylist.model.ChooseStylistModel;
import com.example.home.makemebeautiful.choose_stylist.model.OnJsonReceivedFromServer;
import com.example.home.makemebeautiful.user_profile.profile_objects.Stylist;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by home on 8/13/2016.
 */
public class ReadStylistsFromServer extends Thread {

    /*
    * This class downloads stylists from server.
    * Thread is used to make the action asynchronous;
    * */

    OnJsonReceivedFromServer interfaceHolder;
    private Activity activity;
    List<Object> stylists = null;
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

    Runnable notifyDataSetChangedInMainThread = new Runnable() {
        @Override
        public void run() {
            ChooseStylistModel.getInstance(null).getAdapter().notifyDataSetChanged();
        }
    };

    Runnable startInitializingStylistsDetails = new Runnable() {
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
            int counter = 0;
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
        } catch (MalformedURLException e) {
            e.printStackTrace();
            run();
        } catch (IOException e) {
            e.printStackTrace();
            run();
        }
    }
}