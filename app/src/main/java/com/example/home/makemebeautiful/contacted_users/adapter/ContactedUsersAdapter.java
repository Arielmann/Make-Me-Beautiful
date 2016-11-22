package com.example.home.makemebeautiful.contacted_users.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.chat.adapter.GenericViewHolder;
import com.example.home.makemebeautiful.contacted_users.model.ContactedUserRow;

import java.util.List;

/**
 * Created by home on 7/2/2016.
 */
public class ContactedUsersAdapter extends RecyclerView.Adapter<GenericViewHolder> {

    private List<ContactedUserRow> dataSet;
    private Context context;

    public ContactedUsersAdapter(Context context, List dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    @Override
    public GenericViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.component_conversation_row, parent, false);
        return new ContactedUserViewHolder(context, view, dataSet);
    }

    @Override
    public void onBindViewHolder(GenericViewHolder holder, int position) {
        holder.setUIDataOnView(position);
        OnContactedUserClicked onConversationClicked = new OnContactedUserClicked(context, new String[]{dataSet.get(position).getAddressedUserName()});
        holder.setCustomClickListener(holder.itemView, onConversationClicked);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public List<ContactedUserRow> getDataSet() {
        return dataSet;
    }
}
