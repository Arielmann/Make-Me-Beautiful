package com.example.home.makemebeautiful.chat.controllers;

import android.content.Context;
import android.view.View;

import com.example.home.makemebeautiful.imageproviding.ImageUtils;
import com.example.home.makemebeautiful.imageproviding.fragments.Dialogs.ChooseImageSourceDialog;

/**
 * Created by home on 8/21/2016.
 */
public class AddChatImageItemController implements View.OnClickListener {

    private final ChooseImageSourceDialog choicesDialog;
    private final String alertBoxTitle = "Send Image!";

    public AddChatImageItemController(Context context) {
        choicesDialog = new ChooseImageSourceDialog(context, alertBoxTitle, ImageUtils.chooseImageAlertBoxItems);
    }

    @Override
    public void onClick(View v) {
        v.setEnabled(false);
        choicesDialog.chooseImageSource();
        v.setEnabled(true);
    }
}
