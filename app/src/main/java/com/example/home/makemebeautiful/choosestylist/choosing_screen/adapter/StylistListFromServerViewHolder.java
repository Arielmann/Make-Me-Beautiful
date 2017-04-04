package com.example.home.makemebeautiful.choosestylist.choosing_screen.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.chat.adapter.GenericViewHolder;
import com.example.home.makemebeautiful.chat.model.ChatItem;
import com.example.home.makemebeautiful.utils.handlers.FontsManager;
import com.example.home.makemebeautiful.utils.handlers.FontsManager.FontLoader;
import com.example.home.makemebeautiful.utils.imageutils.ImageUtils;
import com.example.home.makemebeautiful.utils.imageutils.ImageLoader;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;

import java.util.List;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

class StylistListFromServerViewHolder extends GenericViewHolder implements ImageLoader {

    private static final String TAG = "Stylists from serve VH";
    private final Context context;
    private final CircleImageView stylistImageView;
    private final TextView stylistNameTextView;
    private final TextView stylistLocationTextView;
    private List<Stylist> dataSet;

    StylistListFromServerViewHolder(Context context, View itemView, List<Stylist> dataSet) {
        super(itemView);
        this.context = context;
        this.stylistImageView = (CircleImageView) itemView.findViewById(R.id.quickStylistImage);
        this.stylistNameTextView = (TextView) itemView.findViewById(R.id.quickStylistName);
        this.stylistLocationTextView = (TextView) itemView.findViewById(R.id.quickStylistLocation);
        FontsManager.setUpFontOnTV(context.getAssets(), FontLoader.MONTSERRAT_BOLD, stylistNameTextView);
        FontsManager.setUpFontOnTV(context.getAssets(), FontLoader.MONTSERRAT_BOLD, stylistLocationTextView);
        this.dataSet = dataSet;
    }

    public void setUIDataOnView(int position) {
        try {

            final String name = dataSet.get(position).getName();
            final String location = dataSet.get(position).getLocation();

            if (name != null && location != null) {
                setImageInView(position);
                stylistImageView.setImageBitmap(dataSet.get(position).getUserImageBitmap());
                this.stylistNameTextView.setText(name);
                this.stylistLocationTextView.setText(location);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setImageInView(int position) {
        Bitmap fullSizeImage = dataSet.get(position).getUserImageBitmap();
        Bitmap scaledProfileImage = ImageUtils.createSquaredScaledBitmap(context, fullSizeImage, 2);
        if (scaledProfileImage.sameAs(ImageUtils.defaultProfileImage)) {
            stylistImageView.setImageBitmap(scaledProfileImage);
            startDownloadingProfileImageProcess(position);
            Log.d(TAG, dataSet.get(position).getName() + "'s profile image is default, start downloading image from server");
            return;
        }
        stylistImageView.setImageBitmap(scaledProfileImage);
        Log.d(TAG, dataSet.get(position).getName() + "'s chosen profile image was set dircetly from the dataset, without downloading");
    }


    //sender name is NULL, Uri is generated in PicassoTargetBitmap which calls this method, alignment is -1
    @Override
    public void onImageLoaded(String senderName, Bitmap finalBitmap, ChatItem.ItemType
            itemType, Uri imageUri) throws ExecutionException, InterruptedException {
        stylistImageView.setImageBitmap(finalBitmap);
        Log.d(TAG, "user image has been downloaded and set");
    }

    private void startDownloadingProfileImageProcess(int position) {
        String imageUrl = dataSet.get(position).getProfileImageUrl();
        if (!imageUrl.isEmpty()) {
            //passing stylist to downloading method setting his bitmap in her data
            ImageUtils.downloadProfileImage(context, this, dataSet.get(position), imageUrl);
            Log.d(TAG, "entered imageUtils downloading method for " + dataSet.get(position).getName() + "'s profile image");
            return;
        }
        Log.d(TAG, "url is empty, Stylist details: " + dataSet.get(position).toString());
    }
}
