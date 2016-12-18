package com.example.home.makemebeautiful.utils.imageutils;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

/**
 * Created by home on 8/24/2016.
 */
public class PicassoChatImageTarget extends PicassoLoadedBitmapHandler implements Target {

    private int counter;
    private final String TAG = "Picasso chat img target";

    public PicassoChatImageTarget(Context context, String senderName, ImageLoader interfaceHolder, String url) {
        super(context, interfaceHolder, senderName, url);
    }

    @Override
    public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
        Log.d(TAG, "Bitmap loaded in Picasso chat image target");
        super.handleBitmap(bitmap);
        counter = 0;
    }

    @Override
    public void onBitmapFailed(Drawable errorDrawable) {
        if (counter > 5) {
        Log.d(TAG, "Loading failed for the" + counter + 1 + " time.");
            ImageUtils.downloadChatImage(super.getContext(),
                    super.getLoader(),
                    super.getSenderName(),
                    super.getUrl());
            counter++;
        }
    }

    @Override
    public void onPrepareLoad(Drawable placeHolderDrawable) {
    }
};

