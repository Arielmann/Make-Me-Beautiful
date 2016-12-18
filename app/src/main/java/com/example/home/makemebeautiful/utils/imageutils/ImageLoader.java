package com.example.home.makemebeautiful.utils.imageutils;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.home.makemebeautiful.chat.model.ChatItem;

import java.util.concurrent.ExecutionException;

/**
 * Created by home on 6/25/2016.
 */
public interface ImageLoader {
    void onImageLoaded(String senderName, Bitmap scaledBitmap, ChatItem.ItemType itemType, Uri imageUri) throws ExecutionException, InterruptedException;
}
