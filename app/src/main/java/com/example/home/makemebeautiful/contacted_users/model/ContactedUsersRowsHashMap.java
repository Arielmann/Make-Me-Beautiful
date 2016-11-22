package com.example.home.makemebeautiful.contacted_users.model;

import android.content.Context;

import com.example.home.makemebeautiful.db_manager.DataBaseManager;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * Created by home on 6/24/2016.
 */
public class ContactedUsersRowsHashMap {

    private static ContactedUsersRowsHashMap conversationsRowsArray;

    LinkedHashMap<String, ContactedUserRow> insideClassContactedUsersHashMap;

    public static ContactedUsersRowsHashMap getInstance() {
        if (conversationsRowsArray == null) {
            conversationsRowsArray = new ContactedUsersRowsHashMap();
        }
        return conversationsRowsArray;
    }

    private ContactedUsersRowsHashMap() {
        insideClassContactedUsersHashMap = new LinkedHashMap<>();
    }

    public HashMap<String, ContactedUserRow> getHashMap() {
        return insideClassContactedUsersHashMap;
    }

    public boolean userIsInDataBase(Context context, String addressedUserName) {
        if (insideClassContactedUsersHashMap.get(addressedUserName) != null) {
            return true;
        } else {
            ContactedUserRow userRow = DataBaseManager.getInstance(context).getContactedStylistsReader().getContactConversationRow(addressedUserName);
            if (userRow != null) {
                insideClassContactedUsersHashMap.put(addressedUserName, userRow);
                return true;
            }
            return false;
        }
    }

}


