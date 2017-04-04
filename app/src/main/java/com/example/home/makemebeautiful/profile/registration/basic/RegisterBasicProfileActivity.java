package com.example.home.makemebeautiful.profile.registration.basic;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.home.makemebeautiful.appinit.AppDataInit;

public class RegisterBasicProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //AppDataInit.initCrashesMonitor(this);
        AppDataInit.createDirectories();
        setContentView(com.example.home.makemebeautiful.R.layout.activity_register_basic_profile);
    }
}


