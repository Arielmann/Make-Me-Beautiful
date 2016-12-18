package com.example.home.makemebeautiful.choosestylist.choosing_screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.toolbar.ToolbarFrag;

public class ChooseStylistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_stylist);
        ToolbarFrag toolbar = (ToolbarFrag) getSupportFragmentManager().findFragmentById(R.id.toolbarFragInChooseStylistScreen);
        toolbar.createDrawer();
        ChooseStylistViewFrag chooseStylistFrag = (ChooseStylistViewFrag) getSupportFragmentManager().findFragmentById(R.id.chooseStylistFrag);
        chooseStylistFrag.setFragData();
    }
}
