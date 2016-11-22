package com.example.home.makemebeautiful.choose_stylist.choosing_screen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.toolbar_frag.ToolbarFrag;

import java.util.concurrent.ExecutionException;

public class ChooseStylistScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_stylist_screen);
        ToolbarFrag toolbar = (ToolbarFrag) getSupportFragmentManager().findFragmentById(R.id.toolbarFragInChooseStylistScreen);
        toolbar.createDrawer();
        ChooseStylistViewFrag chooseStylistFrag = (ChooseStylistViewFrag) getSupportFragmentManager().findFragmentById(R.id.chooseStylistFrag);
        try {
            chooseStylistFrag.setFragData();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
