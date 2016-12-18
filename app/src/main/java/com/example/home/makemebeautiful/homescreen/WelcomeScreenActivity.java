package com.example.home.makemebeautiful.homescreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.appinit.AppDataInit;
import com.example.home.makemebeautiful.choosestylist.choosing_screen.ChooseStylistActivity;
import com.example.home.makemebeautiful.utils.handlers.GoToScreen;
import com.example.home.makemebeautiful.toolbar.ToolbarFrag;
import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;
import com.example.home.makemebeautiful.profile.registration.basic.RegisterBasicProfileActivity;

import java.util.concurrent.ExecutionException;


public class WelcomeScreenActivity extends AppCompatActivity {

    ToolbarFrag toolbarFrag;
    String WELCOME_SCREEN_TAG = "Welcome screen";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDataInit.initCrashesMonitor(this);
        setContentView(R.layout.activity_welcome_screen);
        toolbarFrag = (ToolbarFrag) getSupportFragmentManager().findFragmentById(R.id.toolbarFragInWelcomeScreen);

        //Check if registration is required
        if (SharedPrefManager.getInstance(this).getUserName().equals("user name error")) {
            goToRegistrationScreen();
        } else {
            Log.d(WELCOME_SCREEN_TAG, SharedPrefManager.getInstance(this).toString());
            try {
                initAppDataFromWelcomeScreen();
                Button goToChooseStylistButton = (Button) findViewById(R.id.goToChooseStylistScreen);
                GoToScreen goToChooseStylistAction = new GoToScreen(this, ChooseStylistActivity.class);
                goToChooseStylistButton.setOnClickListener(goToChooseStylistAction);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void goToRegistrationScreen() {
        GoToScreen goToRegistrationBasicProfileScreen = new GoToScreen(this, RegisterBasicProfileActivity.class);
        goToRegistrationBasicProfileScreen.onClick(null);
    }

    private void initAppDataFromWelcomeScreen() throws ExecutionException, InterruptedException {
        AppDataInit.initAppDataWithProgDialog(this, toolbarFrag);
        try {
            AppDataInit.readStylistsFromServer(this);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

