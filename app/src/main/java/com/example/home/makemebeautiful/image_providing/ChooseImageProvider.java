package com.example.home.makemebeautiful.image_providing;

import android.support.v4.app.Fragment;

/**
 * Created by home on 4/29/2016.
 */
public interface ChooseImageProvider {
    void onImageProviderChosen(Fragment imageResolverFrag, String fragTag);
}
