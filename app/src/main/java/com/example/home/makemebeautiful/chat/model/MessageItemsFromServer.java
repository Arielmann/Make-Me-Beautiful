package com.example.home.makemebeautiful.chat.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by home on 10/4/2016.
 */

/*
* NOTE: This class is not yet in use
*/

public class MessageItemsFromServer {
    private static MessageItemsFromServer ourInstance = new MessageItemsFromServer();
    private List<String> messageItems;

    public static MessageItemsFromServer getInstance() {
        return ourInstance;
    }

    private MessageItemsFromServer() {
        messageItems = new ArrayList<>();
    }

    public List<String> getMessageItems() {
        return messageItems;
    }
}
