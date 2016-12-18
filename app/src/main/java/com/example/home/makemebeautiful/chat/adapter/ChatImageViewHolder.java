package com.example.home.makemebeautiful.chat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.home.makemebeautiful.chat.model.ChatItem;
import com.example.home.makemebeautiful.imageproviding.ImageLoader;
import com.example.home.makemebeautiful.imageproviding.ImageUtils;
import com.example.home.makemebeautiful.imageproviding.OnImageLoadingError;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by home on 6/28/2016.
 */

class ChatImageViewHolder extends GenericViewHolder implements ImageLoader, OnImageLoadingError {

    private static final String CHAT_IMAGE_VH_TAG = "Chat image VH: ";
    private final View view;
    private ImageButton chatImageButton;
    private Context context = null;
    private List<ChatItem> dataSet;

    public ChatImageViewHolder(Context context, View itemView, List<ChatItem> dataSet) {
        super(itemView);
        this.context = context;
        this.view = itemView;
        this.dataSet = dataSet;
    }

    @Override
    public void setUIDataOnView(int position) {
        try {
            int finalViewTypeValue = dataSet.get(position).getFinalViewValue();
            this.chatImageButton = (ImageButton) view.findViewById(finalViewTypeValue);
            Bitmap image = dataSet.get(position).getImage();
            String imagePath = dataSet.get(position).getImagePath();
            if (image != null) {
                setFullImageScreenOnClickListener(image);
                this.chatImageButton.setImageBitmap(image);
                Log.d(CHAT_IMAGE_VH_TAG, "image loaded. Path: " + imagePath);
            } else {
                getBitmapFromFilePath(imagePath, "" + position);
            }
        } catch (Exception e) {
            new Error(CHAT_IMAGE_VH_TAG + e.getMessage());
        }
    }

    private void getBitmapFromFilePath(String imagePath, String position) {
        if (imagePath != null) {
            File chatImageFile = new File(imagePath);
            chatImageFile.mkdir();
            int[] imageSizes = ImageUtils.chooseImageSizes(context, 4, 4);
            int targetImageHeight = imageSizes[0];
            int targetImageWidth = imageSizes[1];
            ImageUtils.createBitmapFromImageSource(position, context, this, chatImageFile, targetImageHeight, targetImageWidth);
        }
    }

    @Override
    public void onImageLoaded(String senderName, Bitmap scaledBitmap, ChatItem.ItemType itemType, Uri imageResource) throws ExecutionException, InterruptedException {
        this.chatImageButton.setImageBitmap(scaledBitmap);
        dataSet.get(Integer.parseInt(senderName)).setImage(scaledBitmap);
        setFullImageScreenOnClickListener(scaledBitmap);
    }

    @Override
    public void onImageLoadingError() {
        this.chatImageButton.setImageBitmap(ImageUtils.defaultProfileImage);
        setFullImageScreenOnClickListener(ImageUtils.defaultProfileImage);
    }

    private void setFullImageScreenOnClickListener(Bitmap image) {
        int[] imageSizes = ImageUtils.chooseImageSizes(context, 2, 1);
        Bitmap fullScreenImage = Bitmap.createScaledBitmap(image, imageSizes[0], imageSizes[1], false);
        OnImageClickedListener onImageClicked = new OnImageClickedListener(context, fullScreenImage);
        chatImageButton.setOnClickListener(onImageClicked);
    }
}
