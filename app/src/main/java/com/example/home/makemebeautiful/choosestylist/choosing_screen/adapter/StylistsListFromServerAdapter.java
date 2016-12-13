package com.example.home.makemebeautiful.choosestylist.choosing_screen.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.chat.adapter.GenericViewHolder;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;

import java.util.List;

/**
 * Created by home on 8/13/2016.
 */
public class StylistsListFromServerAdapter extends RecyclerView.Adapter<GenericViewHolder> {

    private List<Stylist> dataSet;
    private Context context;

    public StylistsListFromServerAdapter(List<Stylist> dataSet) {
        this.dataSet = dataSet;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_stylist_quick_details_view, parent, false);
        return new StylistListFromServerViewHolder(context, view, dataSet);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.setUIDataOnView(position);
        OnStylistClickedForCheckDetails onStylistClicked = new OnStylistClickedForCheckDetails(context, dataSet.get(position)); //FIXME: why passing the first stylist in the dataset every time?
        holder.setCustomClickListener(holder.itemView, onStylistClicked);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
