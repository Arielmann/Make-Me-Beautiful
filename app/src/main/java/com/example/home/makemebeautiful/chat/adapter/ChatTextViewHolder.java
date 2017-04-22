package com.example.home.makemebeautiful.chat.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.chat.model.ChatItem;

import java.util.List;

class ChatTextViewHolder extends GenericViewHolder {

    private final View view;
    private List<ChatItem> dataSet;

    ChatTextViewHolder(View itemView, List<ChatItem> dataSet) {
        super(itemView);
        this.view = itemView;
        this.dataSet = dataSet;
    }

    @Override
    public void setUIDataOnView(int position) {
        int finalViewTypeValue = dataSet.get(position).getFinalViewValue();
        String senderName = dataSet.get(position).getSenderName();
        TextView messageTV = (TextView) view.findViewById(finalViewTypeValue);
        TextView senderNameTV = (TextView) view.findViewById(R.id.textSenderName);
        String message = dataSet.get(position).getTextMessage();
        if (message != null) {
            senderNameTV.setText(senderName);
            messageTV.setText(message);
        }
    }
}

