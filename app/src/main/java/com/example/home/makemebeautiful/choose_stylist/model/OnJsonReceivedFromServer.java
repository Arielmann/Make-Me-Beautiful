package com.example.home.makemebeautiful.choose_stylist.model;

import android.content.Context;

import java.util.List;

/**
 * Created by home on 9/2/2016.
 */
public interface OnJsonReceivedFromServer {
    void onJsonReceived(Context context, List<Object> arrayFromJson);
}
