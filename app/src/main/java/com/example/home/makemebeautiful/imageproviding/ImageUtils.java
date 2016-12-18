package com.example.home.makemebeautiful.imageproviding;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.chat.adapter.GenericViewHolder;
import com.example.home.makemebeautiful.chat.model.ChatItem;
import com.example.home.makemebeautiful.contactedusers.model.ContactedUserRow;
import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by home on 6/29/2016.
 */
public class ImageUtils {

    private static final String TAG = "ImageUtils";
    public static String testImagePath = Environment.getExternalStorageDirectory() + "/DCIM/camera/me.jpg";
    public static final CharSequence[] chooseImageAlertBoxItems = {"Take Photo", "Choose from Gallery", "Cancel"};
    public static Bitmap defaultProfileImage;
    private static PicassoProfileImageTarget picassoProfileImageTarget;
    private static PicassoChatImageTarget picassoChatImageTarget;

    public static int[] chooseImageSizes(Context context, int screenHeightDivider, int screenWidthDivider) {
        int targetImageHeight = SharedPrefManager.getInstance(context).getUserDeviceScreenHeight() / screenHeightDivider;
        int targetImageWidth = SharedPrefManager.getInstance(context).getUserDeviceScreenWidth() / screenWidthDivider;
        return new int[]{targetImageHeight, targetImageWidth};
    }

    public static String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static Uri createImageUri(Context context, Bitmap bitmap) {
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Make Me Beautiful " + bitmap.toString(), null);
        return Uri.parse(path);
    }

    public static void deleteImage(Context context, Uri imageUri) {
        context.getContentResolver().delete(imageUri, null, null);
    }

    //Called from Camera when choosing images
    public static void createBitmapFromImageSource(final String senderName, final Activity activity, final Uri imageUri, final int targetImageHeight, final int targetImageWidth) {
        Glide.with(activity).load(imageUri).asBitmap().into(new SimpleTarget<Bitmap>(targetImageHeight, targetImageWidth) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                Bitmap scaledBm = Bitmap.createScaledBitmap(resource, targetImageWidth, targetImageHeight, true);
                startImageLoaderInterface(activity, senderName, scaledBm, imageUri);
            }
        });
    }


    //Called from contactedUsersViewHolder
    public static void createBitmapFromImageSource(final String position, final Context context, final GenericViewHolder interfacedHolder, final Uri imageUri, final int targetImageHeight, final int targetImageWidth) { //It's a static method because it doesn't use any class members!
        GlideExceptionsListener listener = new GlideExceptionsListener(interfacedHolder);
        Glide.with(context).load(imageUri).asBitmap().listener(listener).into(new SimpleTarget<Bitmap>(targetImageHeight, targetImageWidth) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                startImageLoaderInterface(interfacedHolder, position, resource, imageUri);
            }
        });
    }


    //Called from ChatImageViewHolder and CustomProfileDrawerItem
    public static void createBitmapFromImageSource(final String position, final Context context, final Object interfacedHolder, final File imageFile, final int targetImageHeight, final int targetImageWidth) { //It's a static method because it doesn't use any class members!
        GlideExceptionsListener listener = new GlideExceptionsListener(interfacedHolder);
        Glide.with(context).load(imageFile).asBitmap().listener(listener).into(new SimpleTarget<Bitmap>(targetImageHeight, targetImageWidth) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                startImageLoaderInterface(interfacedHolder, position, resource, null);
            }
        });
    }

    protected static void startImageLoaderInterface(Object interfaceHolder, String senderName, Bitmap resource, Uri imageUri) {
        ImageLoader loader = (ImageLoader) interfaceHolder;
        try {
            loader.onImageLoaded(senderName, resource, ChatItem.ItemType.IMAGE_RIGHT, imageUri);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //Activity is used as interface, context for downloading (activity is null when downloading from ReadStylistsFromServer thread)
    public static void downloadProfileImage(Context context, final ImageLoader loader, Stylist stylist, String url) {
        Log.d(TAG, "entered imageUtils download profile image method method for " + stylist.getName() + "'s profile image");
        picassoProfileImageTarget = new PicassoProfileImageTarget(context, loader, stylist, url);
        Picasso.with(context).load(url).into(picassoProfileImageTarget);
    }

    public static void downloadChatImage(final Context context, ImageLoader loader, String senderName, final String url) {
        picassoChatImageTarget = new PicassoChatImageTarget(context, senderName, loader, url);
        Picasso.with(context)
                .load(url)
                .into(picassoChatImageTarget);
    }

    public static void initLoadedStylistsImageBitmaps(Context context, List<ContactedUserRow> stylists, int targetImageHeight, int targetImageWidth) {
        for (final ContactedUserRow contact : stylists) {
            Glide.with(context).load(contact.getProfileImagePath()).asBitmap().into(new SimpleTarget<Bitmap>(targetImageHeight, targetImageWidth) {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    contact.setBitmap(resource);
                }
            });
        }
    }

    public static void initDefaultProfileImage(Context context) {
        defaultProfileImage = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.female_icon);
        SharedPrefManager.getInstance(null).setUserImageBitmap(defaultProfileImage);
    }

   /* public static void createImageFromResource(Context context, int res, Bitmap imageTarget, int targetImageHeight, int targetImageWidth) {
        Glide.with(context).load(res).asBitmap().into(new SimpleTarget<Bitmap>(targetImageHeight, targetImageWidth) {
            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                defaultProfileImage = resource;
                //TODO: make more generic by working with imageTarget and using interface
            }
        });
    }*/


    public static File writeBitmapToFile(Bitmap image, String fileDir, String fileName) {

     /*   Storage storage = SimpleStorage.getExternalStorage();
        storage.createFile("Make Me Beautiful/Contacts", fileName, image);*/
        FileOutputStream out = null;
        File imageFile = null;
        try {
            imageFile = new File(fileDir, fileName);
            out = new FileOutputStream(imageFile);
            image.compress(Bitmap.CompressFormat.PNG, 100, out); // bmp is your Bitmap instance
            // PNG is a lossless format, the compression factor (100) is ignored
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return imageFile;
    }

    public static void setUserImageFile(Context context, Stylist addressedUser, Bitmap profileImage, String senderName) {
        String fileDirName = Environment.getExternalStorageDirectory().toString() + SharedPrefManager.getInstance(context).getProfileImagesDir();
        String fileNameWithSpace = "Contact_" + senderName + ".jpg";
        String finalFileName = fileNameWithSpace.replace(' ', '_');
        File newProfileImageFile = ImageUtils.writeBitmapToFile(profileImage, fileDirName, finalFileName);
        addressedUser.setProfileImagePath(newProfileImageFile.getAbsolutePath());
    }


    public static void fetchUserProfileImage(Context context, Object interfaceHolder) {
        int[] imageSizes = ImageUtils.chooseImageSizes(context, 2, 2);
        int targetImageHeight = imageSizes[0];
        int targetImageWidth = imageSizes[1];
        File profileImageFile = new File(SharedPrefManager.getInstance(context).getProfileImagePath());
        ImageUtils.createBitmapFromImageSource("", context, interfaceHolder, profileImageFile, targetImageHeight, targetImageWidth); //create the image from the filepath and activate presentDownloadedImageOnScreen after this method
    }
}