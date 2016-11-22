package com.example.home.makemebeautiful.chat.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.home.makemebeautiful.choose_stylist.choosing_screen.adapter.OnStylistClickedForCheckDetails;

import java.util.List;

/**
 * Created by home on 6/28/2016.
 */
public abstract class GenericViewHolder extends RecyclerView.ViewHolder {
    public GenericViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void setUIDataOnView(int position);

    public void setCustomClickListener(View view, View.OnClickListener onClickListener) {
        view.setOnClickListener(onClickListener);
    }
}

