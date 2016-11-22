package com.example.home.makemebeautiful.app_data_init;

import android.content.Context;

import com.example.home.makemebeautiful.db_manager.DataBaseManager;

/**
 * Created by home on 7/30/2016.
 */
public class PastChatItemsLoader extends Thread {

    /*
    * All conversations are being loaded into
    * a singleton of 2d array. Thread is used
    * to make the action asynchronous;
    * */

    private Context context;

    public PastChatItemsLoader(String threadName, Context context) {
        super(threadName);
        this.context = context;
    }

    public void run() {
       DataBaseManager.getInstance(context).getChatItemsTable().getAllConversations();
    }
}
