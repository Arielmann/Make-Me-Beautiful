package com.example.home.makemebeautiful.client_user;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.home.makemebeautiful.R;


public class GetAppInfo extends AppCompatActivity {

    /*
    * NOTE: This class is not yet in use.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_app_info);

    }
    public void startSecondActivity(View view) {

            //Open the specific App Info page:
            Intent intent = new Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.setData(Uri.parse("package:" + "com.example.home.makemebeautiful.PrivateData.profiles.User"));
            startActivity(intent);
    }

}
