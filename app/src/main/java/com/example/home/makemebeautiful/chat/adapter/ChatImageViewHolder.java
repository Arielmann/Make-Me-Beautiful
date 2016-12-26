package com.example.home.makemebeautiful.chat.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.example.home.makemebeautiful.chat.model.ChatItem;
import com.example.home.makemebeautiful.utils.imageutils.ImageLoader;
import com.example.home.makemebeautiful.utils.imageutils.ImageUtils;
import com.example.home.makemebeautiful.utils.imageutils.OnImageLoadingError;

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
            String imagePath = dataSet.get(position).getImagePath();
        try {
            int finalViewTypeValue = dataSet.get(position).getFinalViewValue();
            this.chatImageButton = (ImageButton) view.findViewById(finalViewTypeValue);
            Bitmap fullSizeImage = dataSet.get(position).getImage();
            Bitmap scaledImage = ImageUtils.createSquaredScaledBitmap(context, fullSizeImage, 2);
            if (fullSizeImage != null && scaledImage != null) {
                setFullImageScreenOnClickListener(fullSizeImage); //full screen on click fragment defined on full sized image to prevent data loss.
                this.chatImageButton.setImageBitmap(scaledImage); // scaled image will be presented in chat screen.
                Log.d(CHAT_IMAGE_VH_TAG, "image loaded. Path: " + imagePath);
            } else {
                getBitmapFromFilePath(imagePath, "" + position);
            }
        } catch (Exception e) {
            getBitmapFromFilePath(imagePath, "" + position);
            new Error(CHAT_IMAGE_VH_TAG + e.getMessage());
        }
    }

    private void getBitmapFromFilePath(String imagePath, String position) {
        if (imagePath != null) {
            File chatImageFile = new File(imagePath);
            chatImageFile.mkdir();
            int[] imageSizes = ImageUtils.chooseImageSizes(context, 1, 1); //choose full scale sizes
            ImageUtils.createBitmapFromImageSource(position, context, this, chatImageFile, imageSizes[0], imageSizes[1]);
        }
    }

    @Override
    public void onImageLoaded(String senderName, Bitmap fullSizeImage, ChatItem.ItemType itemType, Uri imageResource) throws ExecutionException, InterruptedException {
        Bitmap scaledImage = ImageUtils.createSquaredScaledBitmap(context, fullSizeImage, 2);
        this.chatImageButton.setImageBitmap(scaledImage); // scaled image will be presented in chat screen.
        //TODO: fix next line, it is not effective. chat item's image should be set with scaled image and skip recscaling when reloaded
        dataSet.get(Integer.parseInt(senderName)).setImage(fullSizeImage); //data set saves full scale image for future usage
        setFullImageScreenOnClickListener(fullSizeImage);  //same for full screen image fragment
    }

    @Override
    public void onImageLoadingError() {
        this.chatImageButton.setImageBitmap(ImageUtils.defaultProfileImage);
        setFullImageScreenOnClickListener(ImageUtils.defaultProfileImage);
    }

    private void setFullImageScreenOnClickListener(Bitmap image) {
        Bitmap fullScreenImage = ImageUtils.createSquaredScaledBitmap(context, image, 1);
        OnImageClickedListener onImageClicked = new OnImageClickedListener(context, fullScreenImage);
        chatImageButton.setOnClickListener(onImageClicked);
    }
}
