package com.example.home.makemebeautiful.contactedusers.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.chat.adapter.GenericViewHolder;
import com.example.home.makemebeautiful.chat.model.ChatItem;
import com.example.home.makemebeautiful.contactedusers.model.ContactedUserRow;
import com.example.home.makemebeautiful.utils.imageutils.ImageUtils;
import com.example.home.makemebeautiful.utils.imageutils.ImageLoader;
import com.example.home.makemebeautiful.utils.imageutils.OnImageLoadingError;
import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;

import java.io.File;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by home on 7/2/2016.
 */
public class ContactedUserViewHolder extends GenericViewHolder implements ImageLoader, OnImageLoadingError {

    /*
    * This ViewHolder creates ContactedUserRows shown on the
    * ContactedUsersScreen. unlike ChatItems, all
    * ContactedUserRows MUST have images if image is not provided
    * by the contact, there will be a default Image.
    */

    private final View view;
    private final CircleImageView conversationImageView;
    private final TextView addressedUserTextView;
    private final TextView lastMessageTextView;
    private final TextView lastMessageDateTextView;
    private List<ContactedUserRow> dataSet;
    private int targetImageHeight;
    private int targetImageWidth;
    private Context context;

    protected ContactedUserViewHolder(Context context, View itemView, List<ContactedUserRow> dataSet) {
        super(itemView);
        this.context = context;
        this.view = itemView;
        this.conversationImageView = (CircleImageView) view.findViewById(R.id.conversationImage);
        this.addressedUserTextView = (TextView) view.findViewById(R.id.addressedUser);
        this.lastMessageTextView = (TextView) view.findViewById(R.id.lastTextMessage);
        this.lastMessageDateTextView = (TextView) view.findViewById(R.id.lastMessageDate);
        this.dataSet = dataSet;
        int[] imageSizes = ImageUtils.chooseImageSizes(context, 13, 13);
        this.targetImageHeight = imageSizes[0];
        this.targetImageWidth = imageSizes[1];

    }

    public void setUIDataOnView(int position) {
        try {
            final String imagePath = dataSet.get(position).getProfileImagePath();
            final String message = dataSet.get(position).getLastMessageAsText();
            final String addressedUserName = dataSet.get(position).getAddressedUserName();
            String lastMessageDate = dataSet.get(position).getLastMessageDate();

            if (imagePath != null && message != null && addressedUserName != null && lastMessageDate != null) {

                this.addressedUserTextView.setText(addressedUserName);
                this.lastMessageTextView.setText(message);
                lastMessageDate = lastMessageDate.replace("_", ""); //remove the "_" char to prevent parse error
                long lastMessageDateAsLong = Long.parseLong(lastMessageDate);
                Timestamp stampOfLastMessage = new Timestamp(lastMessageDateAsLong);
                Date date = new Date(stampOfLastMessage.getTime());
                this.lastMessageDateTextView.setText(date.toString());

                if (dataSet.get(position).getImageBitmap() != null) {
                    this.conversationImageView.setImageBitmap(dataSet.get(position).getImageBitmap());
                    Log.d("Contacted users VH", dataSet.get(position).getAddressedUserName() +
                            " profile image set from inside user data. Path: " + dataSet.get(position).getProfileImagePath());
                    return;
                }

                Storage storage = SimpleStorage.getExternalStorage();
                File profileImageFile = storage.getFile("Make Me Beautiful", "Contact Image: " + addressedUserName);
                if (profileImageFile != null) {
                    ImageUtils.createBitmapFromImageSource("" + position, context, this, Uri.fromFile(profileImageFile), targetImageHeight, targetImageWidth); //create the image from the filepath.
                    Log.d("Contacted users VH", dataSet.get(position).getAddressedUserName() +
                            " profile image created from file. Path: " + dataSet.get(position).getProfileImagePath());
                }


            }
        } catch (Exception e) {
            new Error("Custom Error: " + e.getMessage());
        }
    }

    @Override
    public void onImageLoaded(String senderName, Bitmap scaledBitmap, ChatItem.ItemType itemType, Uri imageUri) {
        this.conversationImageView.setImageBitmap(scaledBitmap);
        dataSet.get(Integer.parseInt(senderName)).setBitmap(scaledBitmap);
        Log.d("Contacted users VH", "image was loaded from interface and attached to " +  dataSet.get(Integer.parseInt(senderName)).getAddressedUserName());
    }


    @Override
    public void onImageLoadingError() {
        Glide.with(context).load(R.drawable.female_icon).override(targetImageHeight, targetImageHeight).into(conversationImageView);
        conversationImageView.setBorderColor(Color.WHITE);
        conversationImageView.setBorderWidth(2);
        Log.d("Loading Error", "image should be loaded from setDataOnUIView");
    }
}

