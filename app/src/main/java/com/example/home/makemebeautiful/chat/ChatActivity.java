package com.example.home.makemebeautiful.chat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;

//import com.example.home.makemebeautiful.chat.fragments.AddressedUserChatDetailsFrag;
import com.example.home.makemebeautiful.chat.model.MessageItemsFromServer;
import com.example.home.makemebeautiful.handlers.FragmentBuilder;
import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.chat.controllers.ChatImagesController;
import com.example.home.makemebeautiful.chat.controllers.ChatTextMessagesController;
import com.example.home.makemebeautiful.chat.fragments.ChatFrag;
import com.example.home.makemebeautiful.chat.model.ChatDataModel;
import com.example.home.makemebeautiful.chat.model.ChatItem;
import com.example.home.makemebeautiful.imageproviding.ChooseImageProvider;
import com.example.home.makemebeautiful.imageproviding.ImageLoader;
import com.example.home.makemebeautiful.imageproviding.ImageUtils;
import com.example.home.makemebeautiful.imageproviding.fragments.FullScreenImageViewFrag;
import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;

import org.greenrobot.eventbus.EventBus;

import java.util.concurrent.ExecutionException;

/*
    *  the addressedUserName is ALWAYS the name
    *  of the conversation, thus necessary for
    *  the ChatModel for writing to the database.

   The Activity is being called in 3 different scenarios:

    * 1. From StylistDetailsScreen: The user
    * chose a new Stylist to connect with.
    *
    * 2. From OnContactedUserClicked: The user reloaded
    * an old conversation from the contactedUsers history.
    *
    * 3. from GcmIntentService when a message was received from GcmReceiver
    *
    * The addressedUser will be provided
    * by the event bus
    */


public class ChatActivity extends AppCompatActivity implements OnTextTransferred, ImageLoader, ChooseImageProvider {
    private static final String TAG = "Chat Screen";
    private ChatFrag chatPresenter;
    private ChatTextMessagesController chatTextMessagesController;
    private ChatImagesController chatImagesController;
    private Stylist addressedUser;
    private ChatDataModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initActivity();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        initActivity();
    }

    private void initActivity() {
        FrameLayout chatFrameLayout = (FrameLayout) findViewById(R.id.chatFrameLayout);
        chatFrameLayout.setId(R.id.chatFrameLayout);
        addressedUser = EventBus.getDefault().removeStickyEvent(Stylist.class);
        model = new ChatDataModel(this, addressedUser);
        chatTextMessagesController = new ChatTextMessagesController(model);
        chatImagesController = new ChatImagesController(model);
        //AddressedUserChatDetailsFrag addressedUserDetailsInChat = (AddressedUserChatDetailsFrag) getSupportFragmentManager().findFragmentById(R.id.addressedUserChatDetailsFrag);
        //addressedUserDetailsInChat.setFragData(addressedUser);
        chatPresenter = (ChatFrag) getSupportFragmentManager().findFragmentById(R.id.messagesPresenterFrag);
        chatPresenter.setFragData(model, chatTextMessagesController);
        MessageItemsFromServer.getInstance().getMessageItems().clear();
        chatPresenter.scrollChatToBottom(model);
    }

    @Override
    public void onTextSentFromUser(String senderName, String message) {
        chatTextMessagesController.presentChatItemsOnScreen(senderName, null, null, message, ChatItem.ItemType.TEXT_RIGHT);
        chatPresenter.scrollChatToBottom(model);
        chatTextMessagesController.sendPushNotificationToServer(message);
    }

    @Override
    public void onImageLoaded(String senderName, Bitmap scaledBitmap, ChatItem.ItemType itemType, Uri originalImageUri) throws ExecutionException, InterruptedException {
        Uri finalImageUri = ImageUtils.createImageUri(this, scaledBitmap); //send uri of the final ROTATED image
        chatImagesController.presentChatItemsOnScreen(senderName, scaledBitmap, originalImageUri, null, itemType);
        String thisUserName = SharedPrefManager.getInstance(this).getUserName();
        if (thisUserName.equals(senderName)) { //if image is sent from this user
            chatImagesController.uploadChatImageToServer(this, finalImageUri);
        }
        chatPresenter.scrollChatToBottom(model);
    }

    @Override
    public void onImageProviderChosen(Fragment imageResolverFrag, String fragTag) {
        FragmentBuilder fragmentBuilder = new FragmentBuilder(this);
        fragmentBuilder.buildFrag(imageResolverFrag, fragTag);
    }

    @Override
    public void onBackPressed() {
        //before going to previous activity, check
        //if chat is simply in full image view mode

        FullScreenImageViewFrag fullScreenImageFrag = (FullScreenImageViewFrag) getSupportFragmentManager().findFragmentByTag("fullScreenImageViewFrag");
        if (fullScreenImageFrag != null) {
            Log.d(TAG, "Closing full screen image fragment");
            getSupportFragmentManager().beginTransaction().remove(fullScreenImageFrag).commit();
        } else {
            Log.d(TAG, "Closing chat activity");
            EventBus.getDefault().postSticky(addressedUser);
            super.onBackPressed();
        }
    }
}

