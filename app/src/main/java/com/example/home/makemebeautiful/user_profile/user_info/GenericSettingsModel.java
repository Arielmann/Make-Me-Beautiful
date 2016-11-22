package com.example.home.makemebeautiful.user_profile.user_info;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.util.HashMap;

/**
 * Created by home on 8/7/2016.
 */
public abstract class GenericSettingsModel {

    private Context context;
    private SetViewsAfterSignUpClicked viewsSetter;
    private HashMap<String, String> errors;
    private HashMap<String, String> userInputs;

    public GenericSettingsModel(Fragment registrationView) {
        this.context = registrationView.getContext();
        this.viewsSetter = (SetViewsAfterSignUpClicked) registrationView;
        this.errors = new HashMap<>();
    }

    public abstract void validate(HashMap<String, String> userInputs);
    protected abstract void onDataChangeSucceeded();
    protected abstract void onDataChangeFailed();
    public abstract void goToNextScreen();
    public abstract void saveInsertedDataToSharedPref();

    public Context getContext() {
        return context;
    }

    public SetViewsAfterSignUpClicked getViewsSetter() {
        return viewsSetter;
    }

    public HashMap<String, String> getErrors() {
        return errors;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setUserInputs(HashMap<String, String> userInputs) {
        this.userInputs = userInputs;
    }

    public HashMap<String, String> getUserInputs() {
        return userInputs;
    }
}
