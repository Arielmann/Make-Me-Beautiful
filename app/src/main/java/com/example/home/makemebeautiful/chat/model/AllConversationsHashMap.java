package com.example.home.makemebeautiful.chat.model;

import java.util.HashMap;
import java.util.List;

/**
 * Created by home on 7/30/2016.
 */
public class AllConversationsHashMap {

    /*
    * This hash map singleton holds
    * the entire user's conversations. each
    * the singleton is starts being initialized in the AppDataInit
    * processes. the has map is dynamically updated when the user sends a
    * message.
    *
    * The rational is to save message loading time when clicking a ContactUserRow
    * in ContactedUsersScreen. Instead of creating a
    * new array from the database with getSingleConversation
    * Method. the array's column is transferred to the
    * ContactedUsersAdapter.
    * */


    private static AllConversationsHashMap allConversationsHashMap = new AllConversationsHashMap();

    public HashMap<String, List<ChatItem>> getHashMap() {
        return conversationsHashMap;
    }

    private HashMap<String, List<ChatItem>> conversationsHashMap;

    private AllConversationsHashMap() { // Class Constructor
        conversationsHashMap = new HashMap<>();
    }

    public static AllConversationsHashMap getInstance() { // NOT A CONSTRUCTOR
        if (allConversationsHashMap == null) {
            allConversationsHashMap = new AllConversationsHashMap();
        }
        return allConversationsHashMap;
    }


}
