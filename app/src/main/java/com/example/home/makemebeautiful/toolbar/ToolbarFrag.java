
package com.example.home.makemebeautiful.toolbar;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.chat.model.ChatItem;
import com.example.home.makemebeautiful.choosestylist.choosing_screen.ChooseStylistActivity;
import com.example.home.makemebeautiful.contactedusers.ContactedUsersActivity;
import com.example.home.makemebeautiful.utils.imageutils.ImageLoader;
import com.example.home.makemebeautiful.utils.imageutils.OnImageLoadingError;
import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import java.util.concurrent.ExecutionException;

/**
 * Created by home on 7/26/2016.
 */
public class ToolbarFrag extends Fragment implements ImageLoader, OnImageLoadingError {

    //TODO: test this frag when profile image is default or badly defined
    //TODO: make less choppy when going between screens

    private Toolbar toolbar;
    private GoToScreenFromDrawerItem goToChooseStylistScreen;
    private GoToScreenFromDrawerItem goToContactedUsersScreen;
    private View toolbarLayout;
    private Drawer drawer;
    private ProfileDrawerItem profileDrawerItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return toolbarLayout = inflater.inflate(R.layout.component_toolbar_main, null);
    }

    public void createDrawer() {
        goToChooseStylistScreen = new GoToScreenFromDrawerItem(getActivity(), ChooseStylistActivity.class);
        goToContactedUsersScreen = new GoToScreenFromDrawerItem(getActivity(), ContactedUsersActivity.class);
        toolbar = (Toolbar) toolbarLayout.findViewById(R.id.customToolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setSubtitle(null);
        buildDrawer();
    }

    @Override
    public void onImageLoaded(String senderName, Bitmap scaledBitmap, ChatItem.ItemType itemType, Uri imageUri) throws ExecutionException, InterruptedException {
        SharedPrefManager.getInstance(null).setUserImageBitmap(scaledBitmap);
        createDrawer();
    }

    @Override
    public void onImageLoadingError() {
        createDrawer();
    }

    private AccountHeader createAccountHeader() {
        String userName = SharedPrefManager.getInstance(getContext()).getUserName();
        String location = SharedPrefManager.getInstance(getContext()).getUserLocation();
        Bitmap profileImage = SharedPrefManager.getInstance(null).getUserImageBitmap();
        AccountHeader header = new AccountHeaderBuilder()
                .withActivity(getActivity())
                .withHeaderBackground(R.drawable.blue_layer)
                .withAccountHeader(R.layout.material_drawer_compact_persistent_header)
                .withThreeSmallProfileImages(false)
                .withSelectionListEnabled(false)
                .addProfiles(
                        new ProfileDrawerItem().withName(userName).withEmail(location).withIcon(profileImage)
                )
                .build();
        return header;
    }

    private void buildDrawer(){
        SecondaryDrawerItem sendPhotoItem = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_sendPhoto);
        SecondaryDrawerItem messagesItem = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_messages);
        //SecondaryDrawerItem settingsItem = (SecondaryDrawerItem) new SecondaryDrawerItem().withIdentifier(2).withName(R.string.drawer_item_settings);
        sendPhotoItem.withOnDrawerItemClickListener(goToChooseStylistScreen);
        messagesItem.withOnDrawerItemClickListener(goToContactedUsersScreen);
        drawer = new DrawerBuilder()
                .withActivity(getActivity())
                .withToolbar(toolbar)
                .withAccountHeader(createAccountHeader())
                //TODO: implement account header in code
                .addDrawerItems(sendPhotoItem, messagesItem).
                        build();
        setOnDrawerItemsClickListeners();
    }


    private void setOnDrawerItemsClickListeners() {
        goToChooseStylistScreen.setDrawer(drawer);
        goToContactedUsersScreen.setDrawer(drawer);
    }
}
