package com.example.home.makemebeautiful.choosestylist.model;

import android.content.Context;

import java.util.List;

public interface OnJsonReceivedFromServer {
    void onJsonReceived(Context context, List<Object> arrayFromJson);
}
