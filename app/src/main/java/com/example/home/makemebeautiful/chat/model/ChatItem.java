package com.example.home.makemebeautiful.chat.model;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.user_profile.SharedPrefrences.SharedPrefManager;

import java.io.Serializable;

/**
 * Created by home on 6/18/2016.
 */
public class ChatItem implements Serializable {

    /*
    * Object for saving a chat message (image OR text) data
    */

    public enum ItemType {
        IMAGE_LEFT(R.layout.component_presented_image_left, R.id.presentedImageLeft),
        IMAGE_RIGHT(R.layout.component_presented_image_right, R.id.presentedImageRight),
        TEXT_LEFT(R.layout.component_presented_text_left, R.id.presentedTextLeft),
        TEXT_RIGHT(R.layout.component_presented_text_right, R.id.presentedTextRight);

        private int parentLayoutValue;
        private int finalViewValue;

        ItemType(int layoutValue, int viewValue) {
            this.parentLayoutValue = layoutValue;
            this.finalViewValue = viewValue;
        }
    }

    private String senderName;
    private String textMessage;
    private Bitmap image;
    private String imagePath;
    private ItemType itemType;
    private String messageDate = "";

    //for images send from User OR server
    public ChatItem(String senderName, Bitmap image, String imagePath, ItemType itemType, String textPresentingImage) {
        this.senderName = senderName;
        this.image = image;
        this.imagePath = imagePath;
        this.itemType = itemType;
        this.messageDate = "" + System.currentTimeMillis();
        this.textMessage = textPresentingImage;
    }

    //For text messages sent in chat from User OR Server
    public ChatItem(String senderName, java.lang.String textMessage, ItemType itemType) {
        this.senderName = senderName;
        this.textMessage = textMessage;
        this.itemType = itemType;
        this.messageDate = "" + System.currentTimeMillis();
    }

    //for images chosen from database
    public ChatItem(Context context, String senderName, String textPresentingImage, String imagePath, String messageDate) {
        this.senderName = senderName;
        this.imagePath = imagePath;
        this.textMessage = textPresentingImage;
        this.itemType = determineItemImageType(context, senderName);
        this.messageDate = messageDate;
    }

    //for text message chosen from database
    public ChatItem(Context context, String senderName, String textMessage, String messageDate) {
        this.senderName = senderName;
        this.textMessage = textMessage;
        this.itemType = determineItemTextType(context, senderName);
        this.messageDate = messageDate;
    }

    //for items with image and text from camera. NOT USED YET
    public ChatItem(String senderName, java.lang.String textMessage, Bitmap image, String filePath, ItemType itemType) {
        this.senderName = senderName;
        this.image = image;
        this.imagePath = filePath;
        this.textMessage = textMessage;
        this.itemType = itemType;
        this.messageDate = "" + System.currentTimeMillis();
    }

    private ItemType determineItemImageType(Context context, String senderName) {
        String userName = SharedPrefManager.getInstance(context).getUserName();
        if (senderName.equals(userName)) {
            return ItemType.IMAGE_RIGHT;
        } else
            return ItemType.IMAGE_LEFT;
    }

    private ItemType determineItemTextType(Context context, String senderName) {
        String userName = SharedPrefManager.getInstance(context).getUserName();
        if (senderName.equals(userName)) {
            return ItemType.TEXT_RIGHT;
        } else
            return ItemType.TEXT_LEFT;
    }

    public java.lang.String getSenderName() {
        return senderName;
    }

    public java.lang.String getTextMessage() {
        return textMessage;
    }

    public int getParentTypeLayoutValue() {
        return itemType.parentLayoutValue;
    }

    public int getFinalViewValue() {
        return itemType.finalViewValue;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public java.lang.String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String path) {
        this.imagePath = path;
    }


    public String getMessageDate() {
        return messageDate;
    }

    public String getTextOrImageBasedOnItemType() {
        if (itemType == itemType.TEXT_LEFT || itemType == itemType.TEXT_RIGHT)
            return textMessage;
        else {
            return "Photo from " + senderName;
        }
    }

    @Override
    public String toString() {
        return "Sender name: " + senderName +
                " Text message: " + textMessage +
                " Image exist? " + (image != null) +
                " Image Path: " + imagePath +
                " Item type: " + itemType +
                " Date: " + messageDate;
    }
}
