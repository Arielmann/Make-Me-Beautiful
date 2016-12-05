package com.example.home.makemebeautiful.contacted_users.model;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.home.makemebeautiful.contacted_users.adapter.ContactedUsersAdapter;
import com.example.home.makemebeautiful.contacted_users.events.OnContactedUsersLoadedEvent;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by home on 7/22/2016.
 */
public class ContactedUsersModel {

    /*
    * This singleton model manages the loading of
    * contacted users and response to runtime changes
    *
    * The model is INDIRECTLY responsible to set the
    * OnContactedUserClickedMethod, by setting the ContactedUsersAdapter
    * the will directly set it.
    */

    private static ContactedUsersAdapter adapter;
    private static LinearLayoutManager layoutManager;
    private static ContactedUsersModel contactedUsersModel;
    private static List<ContactedUserRow> dataSet = new ArrayList<>();
    private static Context context;

    public static ContactedUsersModel getInstance(Context insideMethodContext) {
        if (contactedUsersModel == null) {
            context = insideMethodContext;
            contactedUsersModel = new ContactedUsersModel(context);
        }
        layoutManager = (new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        return contactedUsersModel;
    }

    private ContactedUsersModel(Context context) {
        layoutManager = (new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public ContactedUsersAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter() {
        if(adapter == null) {
            initDataSet();
            adapter = new ContactedUsersAdapter(context, dataSet);
        }
    }

    public static List<ContactedUserRow> initDataSet() { //Since singleton hashMap is used to determine dataSet, each change in the hashMap has to provoke this method
        dataSet.clear();
        HashMap<String, ContactedUserRow> hashMap = ContactedUsersRowsHashMap.getInstance().getHashMap();
        if (!hashMap.isEmpty()) {
            for (Map.Entry<String, ContactedUserRow> map : hashMap.entrySet()) {
                dataSet.add(map.getValue());
            }
        }
        EventBus.getDefault().post(new OnContactedUsersLoadedEvent(dataSet));
        return dataSet;
    }

    public List getDataSet() {
        return dataSet;
    }
}
