package com.example.home.makemebeautiful.server_communication;

import android.net.Uri;

/**
 * Created by home on 9/4/2016.
 */
public interface OnImageUploadedToServer {

    void handleServerImageUrl(String imageUrl, String imagePath, Uri imageUri);
}
