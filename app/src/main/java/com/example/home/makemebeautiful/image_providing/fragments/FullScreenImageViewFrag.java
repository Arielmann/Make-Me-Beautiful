package com.example.home.makemebeautiful.image_providing.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.home.makemebeautiful.R;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by home on 10/17/2016.
 */
public class FullScreenImageViewFrag extends Fragment {

    private SubsamplingScaleImageView imageView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View fullScreenImageViewLayout = inflater.inflate(R.layout.frag_full_screen_image_view, null);
        imageView = (SubsamplingScaleImageView) fullScreenImageViewLayout.findViewById(R.id.fullScreenImageView);
        Bitmap image = EventBus.getDefault().removeStickyEvent(Bitmap.class);
        imageView.setImage(ImageSource.bitmap(image));
        return fullScreenImageViewLayout;
    }
}
