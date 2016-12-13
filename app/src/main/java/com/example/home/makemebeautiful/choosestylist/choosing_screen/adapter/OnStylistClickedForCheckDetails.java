package com.example.home.makemebeautiful.choosestylist.choosing_screen.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.example.home.makemebeautiful.choosestylist.stylist_details_screen.StylistDetailsScreen;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;

import org.greenrobot.eventbus.EventBus;

import java.io.Serializable;

/**
 * Created by home on 8/13/2016.
 */
public class OnStylistClickedForCheckDetails implements View.OnClickListener, Serializable {

    private Stylist addressedStylist;
    private Context context;

    public OnStylistClickedForCheckDetails(Context context, Stylist addressedStylist) {
        this.context = context;
        this.addressedStylist = addressedStylist;
    }

    @Override
    public void onClick(View v) {
        v.setClickable(false);
        Intent stylistDetailsIntent = new Intent(context, StylistDetailsScreen.class);
        stylistDetailsIntent.setFlags (Intent.FLAG_ACTIVITY_CLEAR_TOP);
        EventBus.getDefault().postSticky(addressedStylist);
        context.startActivity(stylistDetailsIntent); //TODO: disable click after first time, else it search database twice (רחמנא ליצלן)
        v.setClickable(true);
    }
}
