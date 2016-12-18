package com.example.home.makemebeautiful.utils.handlers;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.example.home.makemebeautiful.R;

/**
 * Created by home on 5/3/2016.
 */
public class FragmentBuilder {

    private Context context;
    private Bundle args;

    public FragmentBuilder(Context context) {
        this.context = context;
    }

    public FragmentBuilder(Context context, Bundle args) {
        this.context = context;
        this.args = args;
    }

    public Fragment buildFrag(Fragment frag, String fragTag) {
        if(args != null) {
            frag.setArguments(args);
        }
        ((FragmentActivity) context).getSupportFragmentManager().
                beginTransaction().
                add(frag, fragTag)
                .commit();
        return frag;
    }

    public Fragment buildFrag(int containerId, Fragment frag, String fragTag) {
        if(args != null) {
            frag.setArguments(args);
        }
        ((FragmentActivity) context).getSupportFragmentManager().
                beginTransaction().
                add(containerId, frag, fragTag)
                .commit();
        return frag;
    }
}
