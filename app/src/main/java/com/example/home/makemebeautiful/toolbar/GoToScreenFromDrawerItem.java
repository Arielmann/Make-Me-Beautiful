package com.example.home.makemebeautiful.toolbar;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

public class GoToScreenFromDrawerItem implements Drawer.OnDrawerItemClickListener {


    public Intent getIntent() {
        return goToScreen;
    }

    private Activity activity;
    private Intent goToScreen;
    private Drawer drawer;

    public GoToScreenFromDrawerItem(Activity activity, Class screen) {
        this.activity = activity;
        this.goToScreen = new Intent(activity, screen);
        goToScreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    }

    @Override
    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
        drawer.closeDrawer();
        activity.startActivity(goToScreen);
        return true;
    }

    public void setDrawer(Drawer drawer) {
        this.drawer = drawer;
    }
}

