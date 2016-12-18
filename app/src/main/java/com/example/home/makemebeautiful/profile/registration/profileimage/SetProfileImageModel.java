package com.example.home.makemebeautiful.profile.registration.profileimage;

import android.app.Activity;
import android.app.ProgressDialog;
import android.net.Uri;
import android.view.View;
import android.widget.Toast;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.utils.handlers.GoToScreen;
import com.example.home.makemebeautiful.utils.imageutils.ImageUtils;
import com.example.home.makemebeautiful.utils.imageutils.fragments.Dialogs.ChooseImageSourceDialog;
import com.example.home.makemebeautiful.servercommunication.OnImageUploadedToServer;
import com.example.home.makemebeautiful.servercommunication.OnPushNotificationSent;
import com.example.home.makemebeautiful.servercommunication.SaveUserToServerPushNotification;
import com.example.home.makemebeautiful.servercommunication.UploadImage;
import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;
import com.example.home.makemebeautiful.homescreen.WelcomeScreenActivity;

import java.util.concurrent.ExecutionException;

/**
 * Created by home on 8/9/2016.
 */
public class SetProfileImageModel implements View.OnClickListener, OnImageUploadedToServer, OnPushNotificationSent {

     /*
    * This model handels the saving of the user's profile image
    * */

    private final Activity activity;
    private String profileImageFilePathForLocalSaving = "Profile image is unset";
    private String profileImagePathForUploading = "Profile image is unset";
    private final ChooseImageSourceDialog choicesDialog;
    private final String alertBoxTitle = "Choose Profile Picture!";
    ProgressDialog progressDialog;
    private Uri profileImageUriForUploading;

    public SetProfileImageModel(Activity activity) {
        this.activity = activity;
        choicesDialog = new ChooseImageSourceDialog(activity, alertBoxTitle, ImageUtils.chooseImageAlertBoxItems);
    }

    @Override
    public void onClick(View v) {
        v.setEnabled(false);
        choicesDialog.chooseImageSource();
        v.setEnabled(true);
    }

    //****User has chosen an image. Save it to sharePref and go to welcomeScreen******
    protected View.OnClickListener saveImage = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Progress dialog will appear until server finish uploading
            progressDialog = new ProgressDialog(activity,
                    R.style.AppTheme);
            progressDialog.setIndeterminate(true);
            progressDialog.setMessage("Saving Data");
            progressDialog.show();

            //Save path to Shared Pref
            SharedPrefManager.getInstance(activity).saveStringInfoToSharedPreferences(activity, "profileImageFilePath", profileImageFilePathForLocalSaving);
            try {
                uploadProfileImage(); //FIXME: should not happen here but in the next screen
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public void setProfileImageFilePathForLocalSaving(String profileImageFilePathForLocalSaving) {
        this.profileImageFilePathForLocalSaving = profileImageFilePathForLocalSaving;
    }

    public void setProfileImagePathForUploading(String profileImagePathForUploading) {
        this.profileImagePathForUploading = profileImagePathForUploading;
    }

    public void setProfileImageUriForUploading(Uri profileImageUriForUploading) {
        this.profileImageUriForUploading = profileImageUriForUploading;
    }

    private void uploadProfileImage() throws ExecutionException, InterruptedException {
        UploadImage profileImageUploader = new UploadImage(activity, this, profileImagePathForUploading, profileImageUriForUploading);
        profileImageUploader.execute();
    }

    @Override
    public void handleServerImageUrl(String profileImageUrl, String profileImageFilePath, Uri rotatedImageForDelete) {
        // ImageUtils.retryImageUploading(activity, this, profileImageUrl, profileImageFilePathForLocalSaving);
        ImageUtils.deleteImage(activity, rotatedImageForDelete);
        SharedPrefManager.getInstance(activity).saveStringInfoToSharedPreferences(activity, "profileImageUrl", profileImageUrl);
        saveUserToServer();
    }

    private void saveUserToServer() {
        SaveUserToServerPushNotification saveUserToServer = new SaveUserToServerPushNotification(activity, this);
        saveUserToServer.execute();
    }

    @Override
    public void onPushSuccess() {
        progressDialog.dismiss();
        final GoToScreen goToWelcomeScreen = new GoToScreen(activity, WelcomeScreenActivity.class);
        goToWelcomeScreen.onClick(null);
    }

    @Override
    public void onPushFailure() {
        progressDialog.dismiss();
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(activity, "Oops, something went wrong, please try again",
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}

