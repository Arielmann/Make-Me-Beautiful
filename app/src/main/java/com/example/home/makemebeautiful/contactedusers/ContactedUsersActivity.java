package com.example.home.makemebeautiful.contactedusers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.contactedusers.fragments.ContactedUsersPresenterFrag;
import com.example.home.makemebeautiful.utils.handlers.FragmentBuilder;
import com.example.home.makemebeautiful.toolbar.ToolbarFrag;

public class ContactedUsersActivity extends AppCompatActivity {

    /*
    * will manage the whole process of
    * presenting the conversations that the user created
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacted_users);
        ToolbarFrag toolbar = (ToolbarFrag) getSupportFragmentManager().findFragmentById(R.id.toolbarFragInContactedUsers);
        toolbar.createDrawer();
        FragmentBuilder builder = new FragmentBuilder(this);
        builder.buildFrag(R.id.contactedUsersInnerRelativeLayout, new ContactedUsersPresenterFrag(), "Contacted Users Presenter");
    }
}

