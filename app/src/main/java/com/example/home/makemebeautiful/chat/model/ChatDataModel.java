package com.example.home.makemebeautiful.chat.model;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.example.home.makemebeautiful.chat.adapter.ChatAdapter;
import com.example.home.makemebeautiful.chat.sqlite.ChatItemsTable;
import com.example.home.makemebeautiful.contactedusers.sqlite.ContactedStylistTableWriter;
import com.example.home.makemebeautiful.dbmanager.DataBaseManager;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;

import java.util.List;

/**
 * Created by home on 8/23/2016.
 */
public class ChatDataModel {

    private static final String TAG = "Chat Data Model";
    private Context context;
    private ChatAdapter adapter;
    private LinearLayoutManager layoutManager;
    private Stylist addressedUser;
    private List<ChatItem> chatItems;

    public ChatDataModel(Context context, Stylist addressedUser) {
        this.context = context;
        this.addressedUser = addressedUser;
        this.layoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        setChatItems();
        adapter = new ChatAdapter(context, chatItems);
    }

    //********************Getters*********************//
    public Context getContext() {
        return context;
    }

    public ChatItemsTable getChatItemsTable() {
        return DataBaseManager.getInstance(context).getChatItemsTable();
    }

    public ContactedStylistTableWriter getStylistsTableWriter() {
        return DataBaseManager.getInstance(context).getContactedStylistsWriter();
    }

    public ChatAdapter getAdapter() {

        return adapter; //TODO: Find out why does it being called twice??
    }

    public LinearLayoutManager getLayoutManager() {
        return layoutManager;
    }

    public Stylist getAddressedUser() {
        return addressedUser;
    }

    public List<ChatItem> getChatItems() {
        return chatItems;
    }

    //*****************Setters**************//

    public void setContext(Context context) {
        this.context = context;
    }

    public void setAdapter(ChatAdapter adapter) {
        this.adapter = adapter;
    }

    public void setChatItems() {
        try {
            List<ChatItem> addressedUserChatItems = AllConversationsHashMap.getInstance()
                    .getHashMap().get(addressedUser.getName());
            if (addressedUserChatItems == null) {
                //if it's not in DB, set chatItems to be empty array
                chatItems = getChatItemsTable().getAllSingleConversationChatItems(new String[]{addressedUser.getName()});
                AllConversationsHashMap.getInstance()
                        .getHashMap().put(addressedUser.getName(), chatItems);
                return;
            }
            chatItems = addressedUserChatItems;
        }catch(NullPointerException e){
            Log.e(TAG, "null pointer exception, addressed stylist might be null");
        }
    }
}
