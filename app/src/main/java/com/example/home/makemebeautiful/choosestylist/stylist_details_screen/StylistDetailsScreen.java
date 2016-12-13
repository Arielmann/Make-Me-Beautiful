package com.example.home.makemebeautiful.choosestylist.stylist_details_screen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.home.makemebeautiful.R;

public class StylistDetailsScreen extends AppCompatActivity {
        /*
        * This class is naturally started after
        * a stylist is chosen from the stylist's
        * list on ChooseStylistScreen.
       */

    StylistDetailsFrag stylistDetailsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stylist_details);
        stylistDetailsFrag = (StylistDetailsFrag) getSupportFragmentManager().findFragmentById(R.id.stylistDetailsFrag);
        stylistDetailsFrag.initVars();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        stylistDetailsFrag.initVars();
    }
}

