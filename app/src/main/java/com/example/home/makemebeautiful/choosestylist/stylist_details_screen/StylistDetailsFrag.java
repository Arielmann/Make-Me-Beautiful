package com.example.home.makemebeautiful.choosestylist.stylist_details_screen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;
import com.example.home.makemebeautiful.resources.AppStrings;
import com.example.home.makemebeautiful.utils.handlers.FontsManager;

import org.greenrobot.eventbus.EventBus;
import org.parceler.Parcels;

import de.hdodenhof.circleimageview.CircleImageView;

public class StylistDetailsFrag extends Fragment {

    private View stylistDetailsLayout;
    private StylistDetailsModel model;
    private Button goToChatScreen;
    private Stylist addressedStylist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        stylistDetailsLayout = inflater.inflate(R.layout.frag_stylist_details, null);
        goToChatScreen = (Button) stylistDetailsLayout.findViewById(R.id.goToChatScreen);
        if(savedInstanceState != null){
            Stylist addressedUser = Parcels.unwrap(savedInstanceState.getParcelable(AppStrings.ADDRESSED_USER));
            EventBus.getDefault().postSticky(addressedUser);
        }
        return stylistDetailsLayout;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Parcelable wrappedAddressedUser = Parcels.wrap(addressedStylist);
        outState.putParcelable(AppStrings.ADDRESSED_USER, wrappedAddressedUser);
    }

    protected void initVars() {
        addressedStylist = EventBus.getDefault().removeStickyEvent(Stylist.class);
        model = new StylistDetailsModel(this, addressedStylist);
        setImageInViews(stylistDetailsLayout);
        goToChatScreen.setOnClickListener(model.getOnStylistClickedForSendImages());
        setTextViewData(stylistDetailsLayout);
    }

    private void setTextViewData(View stylistDetailsLayout) {
        for (TextViewDetails textDetails : model.getTextViewsDetails()) {
            TextView textView = (TextView) stylistDetailsLayout.findViewById(textDetails.getRIndexNumber());
            textView.setText(textDetails.getText());
            FontsManager.setUpFontOnTV(getActivity().getAssets(), textDetails.getFont(), textView);
        }
    }

    private void setImageInViews(View stylistDetailsLayout) {
        ImageView stylistImageView = (ImageView) stylistDetailsLayout.findViewById(R.id.stylistImageInDetailsScreen);
        CircleImageView stylistCircularImageView = (CircleImageView) stylistDetailsLayout.findViewById(R.id.stylistCircularProfileImageInStylistDetailsScreen);
        try {
            Bitmap userProfileImage = model.getAddressedStylist().getUserImageBitmap();
            Bitmap finalBitmap = generateScreenWidthSizedBitmap();
            drawProfileImageIntoEmptyBitmap(userProfileImage, finalBitmap);
            stylistImageView.setImageBitmap(finalBitmap);
            stylistCircularImageView.setImageBitmap(finalBitmap);
            // stylistImageView.setImageBitmap(model.getAddressedStylist().getUserImageBitmap());
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private Bitmap generateScreenWidthSizedBitmap() {
        Point screenSize = new Point();
        getActivity().getWindowManager().getDefaultDisplay().getSize(screenSize);
        return Bitmap.createBitmap(screenSize.x, screenSize.x, Bitmap.Config.ARGB_8888);
    }

    private void drawProfileImageIntoEmptyBitmap(Bitmap userProfileImage, Bitmap emptyBitmap){
        Canvas canvas = new Canvas(emptyBitmap);
        canvas.drawBitmap(userProfileImage, null, new Rect(0, 0, emptyBitmap.getWidth(), emptyBitmap.getHeight()), null);
    }
}

