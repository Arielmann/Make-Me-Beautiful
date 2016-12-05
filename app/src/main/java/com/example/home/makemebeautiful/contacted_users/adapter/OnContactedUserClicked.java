package com.example.home.makemebeautiful.contacted_users.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.home.makemebeautiful.db_manager.DataBaseManager;
import com.example.home.makemebeautiful.chat.ChatScreen;
import com.example.home.makemebeautiful.user_profile.profile_objects.Stylist;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by home on 7/27/2016.
 */
public class OnContactedUserClicked implements View.OnClickListener{

    /*
    * This class creates a ChatScreen DIRECTLY.
    * It MUST create a valid User so the
    * new ChatScreen can save his name to the
    * "conversation_name" column in
    * the ChatItemsTable AND ALSO present it's
    * details within the ContactDetailsFrag
    */

    private Context context;
    private String[] addressedUserName;

    public OnContactedUserClicked(Context context, String addressedUserName[]) {
        this.context = context;
        this.addressedUserName = addressedUserName;
    }

    @Override
    public void onClick(View v) {
        Stylist addressedStylist = DataBaseManager.getInstance(context)
                .getContactedStylistsReader().getStylist(addressedUserName[0]);

        Intent chatScreenIntent = new Intent(context, ChatScreen.class);
        chatScreenIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        EventBus.getDefault().postSticky(addressedStylist);
        context.startActivity(chatScreenIntent);
    }
}
