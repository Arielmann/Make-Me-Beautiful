package com.example.home.makemebeautiful.welcome_screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.app_data_init.AppDataInit;
import com.example.home.makemebeautiful.choose_stylist.choosing_screen.ChooseStylistScreen;
import com.example.home.makemebeautiful.go_to_screen.GoToScreen;
import com.example.home.makemebeautiful.toolbar_frag.ToolbarFrag;
import com.example.home.makemebeautiful.user_profile.SharedPrefrences.SharedPrefManager;
import com.example.home.makemebeautiful.user_profile.user_info.basic_info.RegisterBasicProfileScreen;

import java.util.concurrent.ExecutionException;


public class WelcomeScreen extends AppCompatActivity {

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
                GoToScreen goToChooseStylistAction = new GoToScreen(this, ChooseStylistScreen.class);
                goToChooseStylistButton.setOnClickListener(goToChooseStylistAction);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void goToRegistrationScreen() {
        GoToScreen goToRegistrationBasicProfileScreen = new GoToScreen(this, RegisterBasicProfileScreen.class);
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

