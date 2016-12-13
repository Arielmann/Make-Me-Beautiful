package com.example.home.makemebeautiful.profile.registration.description;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;
import com.example.home.makemebeautiful.profile.registration.GenericSettingsModel;
import com.example.home.makemebeautiful.profile.registration.profileimage.SetProfileImageScreen;

import java.util.HashMap;

/**
 * Created by home on 6/6/2016.
 */
public class SetDescriptionModel extends GenericSettingsModel {
    private String description;

    public SetDescriptionModel(Fragment signUpView) {
        super(signUpView);
        //checkIfAlreadySignedUp(); //check if the Stylist is already signed up
    }

    @Override
    public void validate(HashMap<String, String> userInputs) {
        //TODO: check if description OR email already exists in the server's DB

        //User doesn't have to describe himself.
        description = userInputs.get("description");
        onDataChangeSucceeded();
    }

    @Override
    protected void onDataChangeSucceeded() {
        //*************Register info to databases using Asynctasks*****************
        saveInsertedDataToSharedPref();
        goToNextScreen();
        getErrors().clear();
    }

    protected void onDataChangeFailed() {
        getViewsSetter().setViewUponFailedDataSetting(getErrors());
        getErrors().clear();
    }


    public void goToNextScreen() {
        Intent goToProfileImageEditor = new Intent(getContext(), SetProfileImageScreen.class);
        getContext().startActivity(goToProfileImageEditor);
    }


    @Override
    public void saveInsertedDataToSharedPref() {
        SharedPrefManager.getInstance(getContext()).saveStringInfoToSharedPreferences(getContext(), "description", description);
    }
}
