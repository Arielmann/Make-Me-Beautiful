package com.example.home.makemebeautiful.chat.controllers;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;

import com.example.home.makemebeautiful.chat.model.ChatItem;

import java.util.List;

/**
 * Created by home on 7/8/2016.
 */
public abstract class IChatController {


    public abstract void saveChatItemInTable(ChatItem chatItem, Uri imageUri);

    public abstract void saveAddressedUserToTable(ChatItem item);

    public abstract void presentChatItemsOnScreen(String senderName, Bitmap bitmap, Uri imageUri, String message, ChatItem.ItemType messageAlignment);

}



