package com.example.home.makemebeautiful.contactedusers.model;

import android.content.Context;

import com.example.home.makemebeautiful.dbmanager.DataBaseManager;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by home on 6/24/2016.
 */
public class ContactedUsersRowsHashMap {

    private static ContactedUsersRowsHashMap conversationsRowsArray = new ContactedUsersRowsHashMap();

    private HashMap<String, ContactedUserRow> map = new LinkedHashMap<>();

    public static ContactedUsersRowsHashMap getInstance() {
        return conversationsRowsArray;
    }

    public HashMap<String, ContactedUserRow> getHashMap() {
        return map;
    }

    public boolean IsUserInDataBase(Context context, String addressedUserName) {
        if (map.get(addressedUserName) != null) {
            return true;
        } else {
            ContactedUserRow userRow = DataBaseManager.getInstance(context).getContactedStylistsReader().getContactConversationRow(addressedUserName);
            if (userRow != null) {
                map.put(addressedUserName, userRow);
                return true;
            }
            return false;
        }
    }

    public void put(String name, ContactedUserRow contactedUserRow) {
        if (name == null) {
            return;
        }
        map.put(name, contactedUserRow);
    }
}


