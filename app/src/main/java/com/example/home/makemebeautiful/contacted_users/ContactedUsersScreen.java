package com.example.home.makemebeautiful.contacted_users;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.contacted_users.fragments.ContactedUsersPresenterFrag;
import com.example.home.makemebeautiful.handlers.FragmentBuilder;
import com.example.home.makemebeautiful.toolbar_frag.ToolbarFrag;

public class ContactedUsersScreen extends AppCompatActivity {

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

