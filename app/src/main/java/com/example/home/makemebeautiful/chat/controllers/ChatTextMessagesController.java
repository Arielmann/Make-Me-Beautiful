package com.example.home.makemebeautiful.chat.controllers;

import android.graphics.Bitmap;
import android.net.Uri;

import com.example.home.makemebeautiful.chat.model.ChatDataModel;
import com.example.home.makemebeautiful.chat.model.ChatItem;
import com.example.home.makemebeautiful.contactedusers.model.ContactedUsersModel;
import com.example.home.makemebeautiful.contactedusers.model.ContactedUsersRowsHashMap;
import com.example.home.makemebeautiful.servercommunication.SendMessagePushNotification;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;

public class ChatTextMessagesController extends IChatController {
    private static final String CHAT_TEXT_CONTROLLER_TAG = "Chat text controller";

    /*
    * NOTE: this class checks every time if the
    * sent ChatItem is the first message of this user
    * (by checking if it exists in the database).
    * if it's true (trying to retrieve the contact return null),
    * it will save it in the database
    * */ //TODO: improve this method, should be ONE PURPOSE


    private ChatDataModel model;

    public ChatTextMessagesController(ChatDataModel model) {
        this.model = model;
    }

    private ChatItem addChatTextMessageToArray(String senderName, String message, ChatItem.ItemType itemType) {
        ChatItem chatItem = new ChatItem(senderName, message, itemType);
        model.getChatItems().add(chatItem);
        return chatItem;
    }

    @Override
    public void saveChatItemInTable(ChatItem chatItem, Uri imageUri) {
        model.getChatItemsTable().saveChatItemInTable(chatItem, model.getAddressedUser().getName());
    }
    @Override
    public void saveAddressedUserToTable(ChatItem item) {
        //if the contact user doesn't exists on the db (his name == null), save it.
        String addressedUserName = model.getAddressedUser().getName();
        boolean userIsInSingleton = ContactedUsersRowsHashMap.getInstance().IsUserInDataBase(model.getContext(), addressedUserName);
        if (userIsInSingleton){
            //IMPORTANT: user MUST be in contacts Singleton at this point
            ContactedUsersRowsHashMap.getInstance().getHashMap().get(addressedUserName).setLastMessageAsText(item);
            ContactedUsersRowsHashMap.getInstance().getHashMap().get(addressedUserName).setLastMessageAsDate(item);
            ContactedUsersModel.getInstance(model.getContext()).initDataSet();
            ContactedUsersModel.getInstance(model.getContext()).getAdapter().notifyDataSetChanged();

        }else {
            Stylist[] stylistInArray = new Stylist[]{model.getAddressedUser()};
            model.getStylistsTableWriter().addContactToTable(model.getContext(), stylistInArray, item.getMessageDate(), item.getTextMessage());
        }
    }

    @Override
    public void presentChatItemsOnScreen(String senderName, Bitmap bitmap, Uri imageUri, String message, ChatItem.ItemType itemType) {
        ChatItem chatItem = addChatTextMessageToArray(senderName, message, itemType);
        model.getAdapter().notifyDataSetChanged();
        saveAddressedUserToTable(chatItem);
        saveChatItemInTable(chatItem, null);
    }

    public void sendPushNotificationToServer(String textMessage) {
        SendMessagePushNotification pushNotification = new SendMessagePushNotification(model.getContext(), model.getAddressedUser().getGcmToken(), "-1", textMessage);
        pushNotification.execute();
    }

}
