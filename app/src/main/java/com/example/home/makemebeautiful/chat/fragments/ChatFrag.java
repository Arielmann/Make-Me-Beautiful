package com.example.home.makemebeautiful.chat.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.chat.controllers.IChatController;
import com.example.home.makemebeautiful.chat.model.ChatDataModel;

import java.util.List;

/**
 * Created by home on 6/19/2016.
 */
public class ChatFrag extends Fragment {

    private static final String TAG = "Chat frag";
    private RecyclerView recyclerView;
    private String CHAT_FRAG_TAG = "chat frag";

    public void setFragData(ChatDataModel model, IChatController controller) {
        Log.d(CHAT_FRAG_TAG, "Controller: " + controller.toString());
        recyclerView.setLayoutManager(model.getLayoutManager());
        recyclerView.setAdapter(model.getAdapter());
        recyclerView.scrollToPosition(model.getChatItems().size() - 1);

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View recyclerViewLayout = inflater.inflate(R.layout.frag_recycler_view, null);
        recyclerView = (RecyclerView) recyclerViewLayout.findViewById(R.id.recyclerView);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);
        return recyclerViewLayout;
    }

    public void scrollChatToBottom(ChatDataModel model) {
        try {
            Log.d(CHAT_FRAG_TAG, "Recycler view: " + recyclerView.toString());
            Log.d(CHAT_FRAG_TAG, "Chat items array size: " + model.getChatItems().size());
            List items = model.getChatItems();
            recyclerView.scrollToPosition(items.size() - 1);

        }catch(NullPointerException e){
            Log.e(TAG, "Scrolling failed");
        }
    }
}

