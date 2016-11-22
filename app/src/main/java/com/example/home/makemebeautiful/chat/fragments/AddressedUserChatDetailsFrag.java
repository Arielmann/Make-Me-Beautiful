/*
package com.example.home.makemebeautiful.chat.fragments;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.choose_stylist.stylist_details_screen.StylistDetailsScreen;
import com.example.home.makemebeautiful.go_to_screen.GoToScreen;
import com.example.home.makemebeautiful.image_providing.ImageUtils;
import com.example.home.makemebeautiful.user_profile.profile_objects.Stylist;

import org.greenrobot.eventbus.EventBus;

import de.hdodenhof.circleimageview.CircleImageView;

*/
/**
 * Created by home on 10/31/2016.
 *//*

public class AddressedUserChatDetailsFrag extends Fragment {

    CircleImageView addressedUserImageView;
    TextView addressedUserNameView;
    TextView addressedUserLocationView;
    View userChatDetailsLayout;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        userChatDetailsLayout = inflater.inflate(R.layout.component_user_chat_details, null);
        addressedUserImageView = (CircleImageView) userChatDetailsLayout.findViewById(R.id.profileImageInChat);
        addressedUserNameView = (TextView) userChatDetailsLayout.findViewById(R.id.userNameInChat);
        addressedUserLocationView = (TextView) userChatDetailsLayout.findViewById(R.id.userLocationInChat);
        return userChatDetailsLayout;
    }

    public void setFragData(Stylist addressedUser){
        EventBus.getDefault().postSticky(addressedUser);
        GoToScreen goToAddressedStylistDetails = new GoToScreen(getActivity(), StylistDetailsScreen.class);
        userChatDetailsLayout.setOnClickListener(goToAddressedStylistDetails);
        addressedUserNameView.setText(addressedUser.getName());
        addressedUserLocationView.setText(addressedUser.getLocation());
        int[] imageSizes = ImageUtils.chooseImageSizes(getContext(), 14, 14);
        int targetImageHeight = imageSizes[0];
        int targetImageWidth = imageSizes[1];
        Bitmap circleProfileImage = Bitmap.createScaledBitmap(addressedUser.getUserImageBitmap(), targetImageWidth, targetImageHeight, false);
        addressedUserImageView.setImageBitmap(circleProfileImage);
    }
}
*/
