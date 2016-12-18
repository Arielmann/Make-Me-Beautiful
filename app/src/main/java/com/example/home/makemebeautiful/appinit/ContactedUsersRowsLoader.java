package com.example.home.makemebeautiful.appinit;

import android.content.Context;
import android.os.AsyncTask;

import com.example.home.makemebeautiful.contactedusers.model.ContactedUsersModel;
import com.example.home.makemebeautiful.dbmanager.DataBaseManager;
import com.example.home.makemebeautiful.utils.imageutils.ImageUtils;

/**
 * Created by home on 7/30/2016.
 */
public class ContactedUsersRowsLoader extends AsyncTask<Void, Void, Void> {
    private Context context;
    private int[] imageSizes = ImageUtils.chooseImageSizes(context, 13, 13);
    private int targetImageHeight;
    private int targetImageWidth;

    public ContactedUsersRowsLoader(Context context) {
        this.context = context;
        this.imageSizes = ImageUtils.chooseImageSizes(context, 13, 13);
        this.targetImageHeight = imageSizes[0];
        this.targetImageWidth  = imageSizes[1];
    }

    @Override
    protected Void doInBackground(Void... params) {
        DataBaseManager.getInstance(context)
                .getContactedStylistsReader().initAllContactsInSingleton();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        ContactedUsersModel model = ContactedUsersModel.getInstance(context);
        //Start creating bitmap images to save loading time when user opens ContactedUsersScreen
        ImageUtils.initLoadedStylistsImageBitmaps(context,  model.initDataSet(), targetImageHeight, targetImageWidth);
        if(model.getAdapter() != null){
            model.getAdapter().notifyDataSetChanged();
        }
    }
}

