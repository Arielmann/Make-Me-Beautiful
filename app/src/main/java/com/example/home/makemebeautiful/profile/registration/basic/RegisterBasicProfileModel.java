package com.example.home.makemebeautiful.profile.registration.basic;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.support.v4.app.Fragment;
import android.view.Display;

import com.example.home.makemebeautiful.utils.handlers.GoToScreen;
import com.example.home.makemebeautiful.servercommunication.RegisterToGcm;
import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;
import com.example.home.makemebeautiful.profile.registration.GenericSettingsModel;
import com.example.home.makemebeautiful.profile.registration.description.SetDescriptionActivity;
import com.example.home.makemebeautiful.homescreen.WelcomeScreenActivity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by home on 6/6/2016.
 */
public class RegisterBasicProfileModel extends GenericSettingsModel {

    public static final String NAME = "name";
    public static final String PASSWORD = "password";
    public static final String LOCATION = "location";
    public static final String NAME_ERROR = "nameError";

    public RegisterBasicProfileModel(Fragment signUpView) {
        super(signUpView);
        checkIfAlreadySignedUp(); //check if the Stylist is already signed up
    }

    @Override
    public void validate(HashMap<String, String> userInputs) {
        //TODO: check if name OR location already exists in the server's DB
        setUserInputs(userInputs);
        String name = userInputs.get(NAME);
        String password = userInputs.get(PASSWORD);
        String location = userInputs.get(LOCATION);

        if (name.length() < 3) {
            getErrors().put(NAME_ERROR, "At least 3 characters");
            onDataChangeFailed();
            return;
        }

        if(name.equals("user name error")){
            getErrors().put(NAME_ERROR, "Invalid name");
            onDataChangeFailed();
            return;
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            getErrors().put("passwordError", "Must be 4-10 alphanumeric characters");
            onDataChangeFailed();
            return;
        }

        if (location.isEmpty()) {
            getErrors().put("locationError", "Where are you from?");
            onDataChangeFailed();
            return;
        }

        //SIGN UP SUCCEEDED!
        onDataChangeSucceeded();
    }

    @Override
    protected void onDataChangeSucceeded() {
        //*************Register info to databases using Asynctasks*****************
        RegisterToGcm registerToGcm = new RegisterToGcm(getContext());
        registerToGcm.execute(); //get a GCM token
        saveInsertedDataToSharedPref();
        saveScreenSizesToSharedPref((Activity) getContext());
        goToNextScreenWithProgressDialog();
        getErrors().clear();
    }

    protected void onDataChangeFailed() {
        getViewsSetter().setViewUponFailedDataSetting(getErrors());
        getErrors().clear();
    }

    private void goToNextScreenWithProgressDialog() {
        GoToScreen goToSetDescriptionScreen = new GoToScreen((Activity) getContext(), SetDescriptionActivity.class, "Creating Account...");
        goToSetDescriptionScreen.goToScreenWithProgressDialog.onClick(null);
    }

    public void goToNextScreen() {
        Intent goToDescriptionScreen = new Intent(getContext(), SetDescriptionActivity.class);
        getContext().startActivity(goToDescriptionScreen);
    }

    @Override
    public void saveInsertedDataToSharedPref() {
        for (Map.Entry<String, String> userInputsEntry : getUserInputs().entrySet()) {
            SharedPrefManager.getInstance(getContext()).saveStringInfoToSharedPreferences(getContext(), userInputsEntry.getKey(), userInputsEntry.getValue());
        }
    }

    private void checkIfAlreadySignedUp() {
        String userName = SharedPrefManager.getInstance(getContext()).getUserName();
        if (!userName.equals("user name error")) {
            GoToScreen goToWelcomeScreen = new GoToScreen((Activity) getContext(), WelcomeScreenActivity.class);
            goToWelcomeScreen.onClick(null);
        }
    }

    private void saveScreenSizesToSharedPref(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        SharedPrefManager.getInstance(activity).saveIntInfoToSharedPreferences(activity, "deviceScreenHeight", height);
        SharedPrefManager.getInstance(activity).saveIntInfoToSharedPreferences(activity, "deviceScreenWidth", width);
    }
}

//*****Login******//

   /*  loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                finish();
            }
        });
    }*/

