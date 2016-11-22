package com.example.home.makemebeautiful.choose_stylist.stylist_details_screen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.user_profile.profile_objects.Stylist;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by home on 8/16/2016.
 */
public class StylistDetailsFrag extends Fragment {

    private View stylistDetailsLayout;
    private StylistDetailsModel model;
    private ImageView stylistImageView;
    private Button goToChatScreen;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        stylistDetailsLayout = inflater.inflate(R.layout.frag_stylist_details, null);
        goToChatScreen = (Button) stylistDetailsLayout.findViewById(R.id.goToChatScreen);
        return stylistDetailsLayout;
    }

    protected void initVars() {
        Stylist addressedStylist = EventBus.getDefault().removeStickyEvent(Stylist.class);
        model = new StylistDetailsModel(this, addressedStylist);
        setImageInView(stylistDetailsLayout);
        goToChatScreen.setOnClickListener(model.getOnStylistClickedForSendImages());
        writeTextInTextViews(stylistDetailsLayout);
    }


    private void writeTextInTextViews(View stylistDetailsLayout) {
        for (TextViewDetails textDetails : model.getTextViewsDetails()) {
            TextView textView = (TextView) stylistDetailsLayout.findViewById(textDetails.getRIndexNumber());
            textView.setText(textDetails.getText());
        }
    }

    private void setImageInView(View stylistDetailsLayout) {
        stylistImageView = (ImageView) stylistDetailsLayout.findViewById(R.id.stylistImageInDetailsScreen);
        try {
            Bitmap userProfileImage = model.getAddressedStylist().getUserImageBitmap();
            Bitmap finalBitmap = generateScreenWidthSizedBitmap();
            drawProfileImageIntoEmptyBitmap(userProfileImage, finalBitmap);
            stylistImageView.setImageBitmap(finalBitmap);
            // stylistImageView.setImageBitmap(model.getAddressedStylist().getUserImageBitmap());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private Bitmap generateScreenWidthSizedBitmap() {
        Point screenSize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(screenSize);
        Bitmap bitmap = Bitmap.createBitmap(screenSize.x, screenSize.x, Bitmap.Config.ARGB_8888);
        return bitmap;
    }

    private void drawProfileImageIntoEmptyBitmap(Bitmap userProfileImage, Bitmap emptyBitmap){
        Canvas canvas = new Canvas(emptyBitmap);
        canvas.drawBitmap(userProfileImage, null, new Rect(0, 0, emptyBitmap.getWidth(), emptyBitmap.getHeight()), null);
    }
}

