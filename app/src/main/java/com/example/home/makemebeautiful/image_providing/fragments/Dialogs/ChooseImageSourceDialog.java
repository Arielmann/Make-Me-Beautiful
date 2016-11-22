package com.example.home.makemebeautiful.image_providing.fragments.Dialogs;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.example.home.makemebeautiful.image_providing.fragments.ChooseFromGalleryFrag;
import com.example.home.makemebeautiful.image_providing.fragments.TakePhotoFrag;
import com.example.home.makemebeautiful.image_providing.ChooseImageProvider;

/**
 * Created by home on 4/29/2016.
 */
public class ChooseImageSourceDialog  implements View.OnClickListener {
    private final Context context;
    private final String title;
    private final CharSequence[] items;
    private final ChooseImageProvider chooseImageProvider;
    private Fragment imageResolverFrag;
    private String fragTag;

    public String getFragTag() {
        return fragTag;
    }

    public Fragment getImageResolverFrag() {
        return imageResolverFrag;
    }


    public ChooseImageSourceDialog(Context context, String title, CharSequence[] items) {
        this.context = context;
        this.title = title;
        this.items = items;
        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            chooseImageProvider = (ChooseImageProvider) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ChooseImageProvider");
        }
    }

    public void chooseImageSource() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    fragTag = "TakePhoto";
                    imageResolverFrag = new TakePhotoFrag();
                    chooseImageProvider.onImageProviderChosen(imageResolverFrag, fragTag); //provokes activity_image_list_screen onsourcechosen
                } else if (items[item].equals("Choose from Gallery")) {
                    fragTag = "ChooseFromGallery";
                    imageResolverFrag = new ChooseFromGalleryFrag();
                    chooseImageProvider.onImageProviderChosen(imageResolverFrag, fragTag);
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }
    @Override
    public void onClick(View v) {
        chooseImageSource();
    }

}


