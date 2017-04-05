package com.example.home.makemebeautiful.chat.controllers;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.example.home.makemebeautiful.chat.model.ChatDataModel;
import com.example.home.makemebeautiful.chat.model.ChatItem;
import com.example.home.makemebeautiful.contactedusers.model.ContactedUsersModel;
import com.example.home.makemebeautiful.contactedusers.model.ContactedUsersRowsHashMap;
import com.example.home.makemebeautiful.utils.imageutils.ImageUtils;
import com.example.home.makemebeautiful.servercommunication.OnImageUploadedToServer;
import com.example.home.makemebeautiful.servercommunication.SendMessagePushNotification;
import com.example.home.makemebeautiful.servercommunication.UploadImage;
import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;

public class ChatImagesController extends IChatController implements OnImageUploadedToServer {

    /*
    * NOTE: this class checks every time if the
    * sent ChatItem is the first message of this user
    * (by checking if it exists in the contactedUserHashMap or in the database).
    * if it's true (trying to retrieve the contact return null),
    * it will save it in the database
    * */
    //TODO: improve the mentioned above method, should be ONE PURPOSE

    private ChatDataModel model;
    private static final String TAG = "Chat images controller";

    public ChatImagesController(ChatDataModel model) {

        this.model = model;
    }

    private ChatItem addChatImageToArray(String senderName, Bitmap scaledImage, String imagePath, ChatItem.ItemType itemType) {
        ChatItem chatItem = new ChatItem(senderName, scaledImage, imagePath, itemType, "Photo from " + senderName);
        model.getChatItems().add(chatItem);
        return chatItem;
    }

    @Override
    public void saveChatItemInTable(ChatItem chatItem, Uri imageUri) {
        final String imagePath = ImageUtils.getRealPathFromURI(model.getContext(), imageUri);
        chatItem.setImagePath(imagePath);
        model.getChatItemsTable().saveChatItemInTable(chatItem, model.getAddressedUser().getName());
    }

    @Override
    public void saveAddressedUserToTable(ChatItem item) {
        //if the contact user doesn't exists on the db (his name == null), save it.
        String addressedUserName = model.getAddressedUser().getName();
        boolean userIsInSingleTon= ContactedUsersRowsHashMap.getInstance().IsUserInDataBase(model.getContext(), addressedUserName);
        if (userIsInSingleTon) {
            //IMPORTANT: user MUST be in contacts Singleton at this point
            ContactedUsersRowsHashMap.getInstance().getHashMap().get(addressedUserName).setLastMessageAsText(item);
            ContactedUsersRowsHashMap.getInstance().getHashMap().get(addressedUserName).setLastMessageAsDate(item);
            ContactedUsersModel.getInstance(model.getContext()).initDataSet();
            ContactedUsersModel.getInstance(model.getContext()).getAdapter().notifyDataSetChanged();

        } else {
            Stylist[] stylistInArray = new Stylist[]{model.getAddressedUser()};
            model.getStylistsTableWriter().addContactToTable(model.getContext(), stylistInArray, item.getMessageDate(), item.getTextMessage());
        }
    }

    @Override
    public void presentChatItemsOnScreen(String senderName, Bitmap bitmap, Uri imageUri, String message, ChatItem.ItemType itemType) {
        String imagePath = ImageUtils.getRealPathFromURI(model.getContext(), imageUri);
        ChatItem chatItem = addChatImageToArray(senderName, bitmap, imagePath, itemType); //The original image uri before glide manipulations will be saved to DB
        model.getAdapter().notifyDataSetChanged();
        saveChatItemInTable(chatItem, imageUri);
        saveAddressedUserToTable(chatItem); //AsyncTask executed here
    }

    public void uploadChatImageToServer(Activity activity, Uri imageUri) {
        Log.d(TAG, "start uploading image method");
        String imagePath = ImageUtils.getRealPathFromURI(activity, imageUri);
        UploadImage imageUploader = new UploadImage(activity, this, imagePath, imageUri);
        imageUploader.execute();
        Log.d(TAG, "image uploader executed");
    }

    @Override
    public void handleServerImageUrl(String imageUrl, String imagePath, Uri rotatedImageUriForDelete) {
        ImageUtils.deleteImage(model.getContext(), rotatedImageUriForDelete);
        String userName = SharedPrefManager.getInstance(model.getContext()).getUserName();
        SendMessagePushNotification pushNotification = new SendMessagePushNotification(model.getContext(), model.getAddressedUser().getGcmToken(), imageUrl,  "Photo from " + userName);
        pushNotification.execute();
    }
}

