package com.example.home.makemebeautiful.user_profile.user_info.basic_info;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.home.makemebeautiful.app_data_init.AppDataInit;

public class RegisterBasicProfileScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppDataInit.initCrashesMonitor(this);
        AppDataInit.createDirectories();
        setContentView(com.example.home.makemebeautiful.R.layout.activity_register_basic_profile);
    }
}


