package com.example.home.makemebeautiful.image_providing;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

import com.example.home.makemebeautiful.chat.model.ChatItem;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;

import java.util.concurrent.ExecutionException;

/**
 * Created by home on 9/30/2016.
 */
public abstract class PicassoLoadedBitmapHandler {

    /*
    * This abstract class is inherited by chat and profile
    * image picasso targets. it saves code copying and
    * allows it's sons to retry loading the image upon error
    * */

    private ImageLoader loader;
    private Stylist stylist;
    private Context context;
    private String url;
    private String senderName;
    private final String TAG = "Picasso Bitmap Handler";

    //Load profile image
    public PicassoLoadedBitmapHandler(Context context, ImageLoader interfaceHolder, Stylist stylist, String url) {
        this.context = context;
        this.stylist = stylist;
        this.loader = interfaceHolder;
        this.url = url;
    }

    //Load image for chat item
    public PicassoLoadedBitmapHandler(Context context, ImageLoader interfaceHolder, String senderName, String url) {
        this.context = context;
        this.senderName = senderName;
        this.loader = interfaceHolder;
        this.url = url;
    }

    /*
    * There are 2 scenarios to handle a downloaded bitmap:
    *
    * 1. set it as the stylist's profile
    * image (targeted stylist is transferred as reference, no
    * interface required)
    *
    * 2. activate interface implemented in the
    * ChatScreen for further treatment (no stylist object required)
    * */

    protected void handleBitmap(Bitmap bitmap) {
        Log.d(TAG, "Bitmap loaded in Picasso bitmap handler");
        int[] imageSizes = ImageUtils.chooseImageSizes(context, 2, 2);
        Bitmap finalBitmap = Bitmap.createScaledBitmap(bitmap, imageSizes[1], imageSizes[0], true);
        Log.d(TAG, "final image created");
        ImageLoader loader = this.loader;
        try {
            loader.onImageLoaded(senderName, finalBitmap, ChatItem.ItemType.IMAGE_LEFT, null);
            Log.d(TAG, "onImageLoaded interface activated");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            Log.d(TAG, "Interface is null, image is downloaded from ReadingStylists AsyncTask");
        }
        try {
            stylist.setUserImageBitmap(finalBitmap);
            Log.d(TAG, stylist.getName() + "'s downloaded image is set as her bitmap property");
        } catch (NullPointerException e) {
            Log.e(TAG, "stylist is null, chat item is handled. no need to set profile image");
        }
    }

    public ImageLoader getLoader() {
        return loader;
    }

    public Stylist getStylist() {
        return stylist;
    }

    public Context getContext() {
        return context;
    }

    public String getUrl() {
        return url;
    }

    public String getSenderName() {
        return senderName;
    }
}
