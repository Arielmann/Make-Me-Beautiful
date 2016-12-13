package com.example.home.makemebeautiful.choosestylist.model;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.home.makemebeautiful.choosestylist.choosing_screen.adapter.StylistsListFromServerAdapter;
import com.example.home.makemebeautiful.image_providing.ImageUtils;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by home on 8/13/2016.
 */
public class ChooseStylistModel implements OnJsonReceivedFromServer {

    private static final String TAG = "Choose stylist model";
    private static ChooseStylistModel instance;
    private static LinearLayoutManager layoutManager;
    private static List<Stylist> stylistsFromServer = new ArrayList<>();
    private static StylistsListFromServerAdapter adapter = new StylistsListFromServerAdapter(stylistsFromServer);
    private Runnable downloadsTask;

    public static ChooseStylistModel getInstance(Context context) {
        if (instance == null) {
            instance = new ChooseStylistModel(context);
        }
        layoutManager = (new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        adapter.notifyDataSetChanged();
        return instance;
    }

    private ChooseStylistModel(Context context) {
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public StylistsListFromServerAdapter getAdapter() {
        return adapter;
    }

    @Override
    public void onJsonReceived(Context context, List<Object> stylistsAsObjects) {
        List lastReceivedStylists = new ArrayList(stylistsAsObjects);
        stylistsFromServer.addAll(lastReceivedStylists);
        initProfileImages(context, stylistsFromServer);
    }

    public List<Stylist> getStylistsFromServer() {
        return stylistsFromServer;
    }

    private void initProfileImages(final Context context, final List<Stylist> stylists) {
        final Handler downloadsHandler = new Handler();
        final int[] position = new int[1];
        downloadsTask = new Runnable() {
            @Override
            public void run() {
                if (position[0] < stylists.size()) {
                    Stylist stylist = stylists.get(position[0]);
                    stylist.setUserImageBitmap(ImageUtils.defaultProfileImage);
                    Log.d(TAG, "default image set");
                    Log.d(TAG, "Stylist downloaded. " + stylist.toString());
                    //singleton does not implement ImageLoader, therefore passes NULL as interfaceHolder to method
                    ImageUtils.downloadProfileImage(context, null, stylist, stylist.getProfileImageUrl());
                    adapter.notifyDataSetChanged();
                    position[0]++;
                    downloadsHandler.postDelayed(downloadsTask, 500);
                }
            }
        };
        downloadsTask.run();
    }
}

