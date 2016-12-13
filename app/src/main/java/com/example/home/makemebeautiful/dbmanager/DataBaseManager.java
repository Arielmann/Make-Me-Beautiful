package com.example.home.makemebeautiful.dbmanager;

import android.content.Context;
import android.provider.BaseColumns;

import com.example.home.makemebeautiful.chat.sqlite.ChatItemsTable;
import com.example.home.makemebeautiful.contactedusers.sqlite.ContactedStylistTableReader;
import com.example.home.makemebeautiful.contactedusers.sqlite.ContactedStylistTableWriter;

/**
 * Created by home on 8/30/2016.
 */
public class DataBaseManager {
    private static DataBaseManager dataBase;


    public static abstract class FeedEntry implements BaseColumns {
        public static final String DB_NAME = "mmbdata_db.sqlite";

        //FILES CONTRACT
        public static final String CONTACTED_STYLISTS_TABLE = "contacted_stylists";
        public static final String CHAT_ITEMS_TABLE = "chat_items";
        public static final String FILE_ID = "entryid";
        public static final String FILE_PATH = "file_path";
        public static final String FILE_BYTES = "file_bytes";
    }

    private ChatItemsTable chatItemsTable;
    private ContactedStylistTableReader contactedStylistsReader;
    private ContactedStylistTableWriter contactedStylistsWriter;

    public static DataBaseManager getInstance(Context context) {
        if (dataBase == null) {
            dataBase = new DataBaseManager(context);
        }
        return dataBase;
    }

    private DataBaseManager(Context context) {
        chatItemsTable = new ChatItemsTable(context, 3);
        contactedStylistsReader = new ContactedStylistTableReader(context, 3);
        contactedStylistsWriter = new ContactedStylistTableWriter(context, 3);
    }

    public ChatItemsTable getChatItemsTable() {
        return chatItemsTable;
    }

    public ContactedStylistTableReader getContactedStylistsReader() {
        return contactedStylistsReader;
    }

    public ContactedStylistTableWriter getContactedStylistsWriter() {
        return contactedStylistsWriter;
    }
}
