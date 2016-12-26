package com.example.home.makemebeautiful.utils.imageutils.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.example.home.makemebeautiful.utils.imageutils.ImageUtils;
import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;

import java.io.Serializable;

/**
 * Created by home on 4/24/2016.
 */
public class TakePhotoFrag extends Fragment implements Serializable{
    private static final int CAM_REQUEST = 1;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null)
            startActivityForResult(takePictureIntent, CAM_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //****The actual camera inbuilt application opens here****///
        try {
            String senderName = SharedPrefManager.getInstance(getContext()).getUserName();
            if (requestCode == CAM_REQUEST && resultCode == getActivity().RESULT_OK) {
                Uri selectedImageUri = data.getData();
                int squareImageSize = ImageUtils.chooseImageSizesForSquare(getActivity(), 2);
                ImageUtils.createBitmapFromImageSource(senderName, getActivity(), selectedImageUri, squareImageSize, squareImageSize);
                return;
                //Adding Image to array method occurs on parent class

            } else if (requestCode == CAM_REQUEST && resultCode == getActivity().RESULT_CANCELED) {


                return;
            }
        } catch (Exception e) {
            Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
        }
    }
}








