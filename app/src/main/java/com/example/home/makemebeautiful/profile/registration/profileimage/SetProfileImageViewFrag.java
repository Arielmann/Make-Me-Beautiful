package com.example.home.makemebeautiful.profile.registration.profileimage;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.imageproviding.ImageUtils;

/**
 * Created by home on 8/5/2016.
 */
public class SetProfileImageViewFrag extends Fragment {
    private ImageButton openImagesPickerButton;
    private Button saveImageButton;
    private SetProfileImageModel model;
    protected ImageView presentingImageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View setProfileImageLayout = inflater.inflate(R.layout.frag_set_profile_image, null);
        model = new SetProfileImageModel(getActivity());
        presentingImageView = (ImageView) setProfileImageLayout.findViewById(R.id.profileImageView);
        openImagesPickerButton = (ImageButton) setProfileImageLayout.findViewById(R.id.cameraImageButton);
        openImagesPickerButton.setOnClickListener(model);
        saveImageButton = (Button) setProfileImageLayout.findViewById(R.id.saveImage);
        saveImageButton.setOnClickListener(model.saveImage); //TODO: put alert message if user hasn't chose any image (need to build seperate classes for alerts and combine it in a GoToScreen's listener)
        presentingImageView.setImageResource(R.drawable.female_icon);
        return setProfileImageLayout;
    }

    public void saveImageDetails(Uri imageUriForUploading, Uri imageUriForLocalSaving) {
        String profileImagePathForLocalSaving = ImageUtils.getRealPathFromURI(getActivity(), imageUriForLocalSaving);
        String profileImagePathForUploading = ImageUtils.getRealPathFromURI(getActivity(), imageUriForUploading);
        model.setProfileImageUriForUploading(imageUriForUploading);
        model.setProfileImagePathForUploading(profileImagePathForUploading);
        model.setProfileImageFilePathForLocalSaving(profileImagePathForLocalSaving);
    }
}

