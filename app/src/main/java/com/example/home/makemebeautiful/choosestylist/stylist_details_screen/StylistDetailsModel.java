package com.example.home.makemebeautiful.choosestylist.stylist_details_screen;

import android.support.v4.app.Fragment;
import com.example.home.makemebeautiful.R;

import com.example.home.makemebeautiful.profile.profilemodels.Stylist;
import com.example.home.makemebeautiful.utils.handlers.FontsManager.FontLoader;
import com.example.home.makemebeautiful.utils.imageutils.ImageUtils;

public class StylistDetailsModel {

        /*
        * This model is INDIRECTLY responsible to create a ChatScreen
        * (by creating a OnStylistClickedForSendImages which does
        * that directly). the Stylist's name
        * will be transferred to the ChatScreen from OnStylistClickedForSendImages
        * in order for it to be able to write to the ChatItemsTable.
        */

    private final OnStylistClickedForSendImages onStylistClicked;
    private final Stylist addressedStylist;
    private final TextViewDetails[] textViewsDetails = new TextViewDetails[5];

    public TextViewDetails[] getTextViewsDetails() {
        return textViewsDetails;
    }

    public StylistDetailsModel(Fragment fragment, Stylist addressedStylist) {
        this.addressedStylist = addressedStylist;
        onStylistClicked = new OnStylistClickedForSendImages(fragment.getActivity(), addressedStylist);
        initTextViewsInfo();
    }

    protected OnStylistClickedForSendImages getOnStylistClickedForSendImages() {
        return onStylistClicked;
    }

    protected Stylist getAddressedStylist() {
        return addressedStylist;
    }

    private void initTextViewsInfo(){
        textViewsDetails[0] = new TextViewDetails(R.id.stylistNameInDetailsScreen, FontLoader.MONTSERRAT_BOLD, addressedStylist.getName());
        textViewsDetails[1] = new TextViewDetails(R.id.companyInDetailsScreen, FontLoader.MONTSERRAT_REGULAR, addressedStylist.getCompany());
        textViewsDetails[2] = new TextViewDetails(R.id.locationInDetailsScreen, FontLoader.MONTSERRAT_REGULAR, addressedStylist.getLocation());
        textViewsDetails[3] = new TextViewDetails(R.id.websiteInDetailsScreen, FontLoader.MONTSERRAT_REGULAR, addressedStylist.getWebsite());
        textViewsDetails[4] = new TextViewDetails(R.id.descriptionInDetailsScreen, FontLoader.MONTSERRAT_REGULAR, addressedStylist.getDescription());
    }
}
