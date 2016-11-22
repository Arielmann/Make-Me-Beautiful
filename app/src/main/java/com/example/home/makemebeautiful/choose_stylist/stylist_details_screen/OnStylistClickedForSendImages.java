package com.example.home.makemebeautiful.choose_stylist.stylist_details_screen;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.home.makemebeautiful.chat.ChatScreen;
import com.example.home.makemebeautiful.user_profile.profile_objects.Stylist;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by home on 7/23/2016.
 */
public class OnStylistClickedForSendImages implements View.OnClickListener {
    /*
    * This model is designed to go to chat
    * screen AND create a new conversation
    * in sqlite once the user opened the chat
    * window with a new stylist
    *
    * The model creates a ChatScreen DIRECTLY.
    * It MUST transfer a valid stylist
    * to the ChatScreen so it can use
    * it to save it's name in the
    * "conversation_name" column
    * in the ChatItemsTable.
    */

    public Intent getIntent() {
        return goToChatScreen;
    }


    private Activity chatScreenActivity;
    private Intent goToChatScreen;

    public OnStylistClickedForSendImages(Activity activity,  Stylist addressedStylist) {
        this.chatScreenActivity = activity;
        this.goToChatScreen = new Intent(activity, ChatScreen.class);
        goToChatScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        EventBus.getDefault().postSticky(addressedStylist);
    }

    @Override
    public void onClick(View v) {//TODO: Make it single purpose somehow?
        chatScreenActivity.startActivity(goToChatScreen);
    }
}

