package com.example.home.makemebeautiful.chat.adapter;

import android.view.View;
import android.widget.TextView;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.chat.model.ChatItem;

import java.util.List;

/**
 * Created by home on 6/28/2016.
 */
public class ChatTextViewHolder extends GenericViewHolder {
    private final View view;
    private TextView chatTextView;
    private List<ChatItem> dataSet;

    public ChatTextViewHolder(View itemView, List<ChatItem> dataSet) {
        super(itemView);
        this.view = itemView;
        this.dataSet = dataSet;
    }

    @Override
    public void setUIDataOnView(int position) {
        int finalViewTypeValue = dataSet.get(position).getFinalViewValue();
        chatTextView = (TextView) view.findViewById(finalViewTypeValue);
        String message = dataSet.get(position).getTextMessage();
        if (message != null) {
            this.chatTextView.setText(message);
        }
    }
}

