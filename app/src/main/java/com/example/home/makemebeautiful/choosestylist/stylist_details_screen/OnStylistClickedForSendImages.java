package com.example.home.makemebeautiful.choosestylist.stylist_details_screen;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.home.makemebeautiful.chat.ChatActivity;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;

import org.greenrobot.eventbus.EventBus;

class OnStylistClickedForSendImages implements View.OnClickListener {

    public Intent getIntent() {
        return goToChatScreen;
    }


    private Activity chatScreenActivity;
    private Intent goToChatScreen;

    OnStylistClickedForSendImages(Activity activity, Stylist addressedStylist) {
        this.chatScreenActivity = activity;
        this.goToChatScreen = new Intent(activity, ChatActivity.class);
        goToChatScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        EventBus.getDefault().postSticky(addressedStylist);
    }

    @Override
    public void onClick(View v) {//TODO: Make it single purpose somehow?
        chatScreenActivity.startActivity(goToChatScreen);
    }
}

