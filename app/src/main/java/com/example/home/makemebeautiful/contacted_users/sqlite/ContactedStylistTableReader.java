package com.example.home.makemebeautiful.contacted_users.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.home.makemebeautiful.chat.model.AllConversationsHashMap;
import com.example.home.makemebeautiful.chat.model.ChatItem;
import com.example.home.makemebeautiful.contacted_users.model.ContactedUserRow;
import com.example.home.makemebeautiful.contacted_users.model.ContactedUsersRowsHashMap;
import com.example.home.makemebeautiful.db_manager.DataBaseManager;
import com.example.home.makemebeautiful.user_profile.profile_objects.Stylist;

import java.util.List;

/**
 * Created by home on 7/26/2016.
 */
public class ContactedStylistTableReader extends SQLiteOpenHelper {

    /*
    * Reader class is used throughout various scenarios
    * in the application:
    *
    * 1. when the User clicks a ContactedUserConversationRow
    * and loads the AddressedUser details from the old Conversation
    *
    * 2. When the ContactedUsersLoader loads the entire ContactedUsersArray
    * when the application is initialized.
    *
    * 3. When the ChatScreen's controllers has to determine if the
    * Contacted User was ever contacted by the the app's User
    * in the past
    * */

    public ContactedStylistTableReader(Context context, int version) {
        super(context, DataBaseManager.FeedEntry.DB_NAME, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        getTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTED_STYLISTS_TABLE);
        onCreate(db);

    }

    // Contacts table name
    private static final String CONTACTED_STYLISTS_TABLE = DataBaseManager.FeedEntry.CONTACTED_STYLISTS_TABLE;
    private static final String CHAT_ITEMS_TABLE = DataBaseManager.FeedEntry.CHAT_ITEMS_TABLE;

    // Contacts Table Columns names

    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_IMAGE = "image";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_WEBSITE = "website";
    private static final String KEY_TOKEN = "gcm_token";

    //***************************************************************************************************//
    private void getTable(SQLiteDatabase db) {
        //db.execSQL("DROP TABLE IF EXISTS " + CONTACTED_STYLISTS_TABLE);
        String CREATE_MESSAGES_TABLE = "CREATE TABLE IF NOT EXISTS " + CONTACTED_STYLISTS_TABLE +
                " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KEY_NAME + " TEXT_LEFT NOT NULL, " +
                KEY_LOCATION + " TEXT_LEFT NOT NULL, " +
                KEY_IMAGE + " TEXT_LEFT NOT NULL, " +
                KEY_DESCRIPTION + " TEXT_LEFT NOT NULL, " +
                KEY_COMPANY + " TEXT_LEFT NOT NULL, " +
                KEY_WEBSITE + " TEXT_LEFT NOT NULL, " +
                KEY_TOKEN + " TEXT_LEFT NOT NULL)";

        db.execSQL(CREATE_MESSAGES_TABLE);
    }

    //***************************************************************************************************//
    public ContactedUserRow getContactConversationRow(final String name) {
        SQLiteDatabase db = getReadableDatabase();
        getTable(db);
        Cursor cursor = db.query(CONTACTED_STYLISTS_TABLE,
                new String[]{KEY_IMAGE}, KEY_NAME + "=?",
                new String[]{name}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        ContactedUserRow contactedUserConversationRow;
        try {
            String ContactImagePath = cursor.getString(0);
            contactedUserConversationRow = new ContactedUserRow(name,
                    ContactImagePath, "Yesterday", "Last message");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return contactedUserConversationRow;
    }


    //***************************************************************************************************//
        public void initAllContactsInSingleton() {
        SQLiteDatabase db = getReadableDatabase();
        getTable(db);

        // Select All Query
        String selectContactsQuery = "SELECT name,image FROM " + CONTACTED_STYLISTS_TABLE; //TODO: don't reat the item id if you don't use it
        Cursor nameAndImageCursor = db.rawQuery(selectContactsQuery, null);

        // looping through all rows and adding to list
        if (nameAndImageCursor.moveToFirst()) {
            do {
                String name = nameAndImageCursor.getString(0);
                String image = nameAndImageCursor.getString(1);
                String lastMessageDate = "";
                String lastMessage = "";


                List<ChatItem> contactedUserConv = AllConversationsHashMap.getInstance().getHashMap().get(name);

                if (contactedUserConv != null) {
                    lastMessageDate = contactedUserConv.get(contactedUserConv.size() - 1).getMessageDate();
                    ChatItem lastChatItem = contactedUserConv.get(contactedUserConv.size() - 1);
                    lastMessage = createMessageTextOrFilePath(lastChatItem);
                } else {
                    String selectMessagesQuery = "SELECT sender_name,text_message,file_path,message_date " +
                            "FROM " + CHAT_ITEMS_TABLE +
                            " WHERE conversation_name = '" + name + "' ORDER BY message_date DESC LIMIT 1";
                    Cursor messagesCursor = db.rawQuery(selectMessagesQuery, null);
                    if (messagesCursor.moveToFirst()) {
                        lastMessage = createMessageTextOrFilePath(messagesCursor.getString(0),messagesCursor.getString(1), messagesCursor.getString(2));
                        lastMessageDate = messagesCursor.getString(3);
                    }
                }

                ContactedUserRow contactedUserRow = new ContactedUserRow(name, image, lastMessageDate, lastMessage);

                // Adding stylist to list
                ContactedUsersRowsHashMap.getInstance().put(name, contactedUserRow);
                Log.d("Contacted Users Reader", "row added to singleton: " + contactedUserRow.toString());
            } while (nameAndImageCursor.moveToNext());
        }
    }

    //***************************************************************************************************//

    public Stylist getStylist(String name) {
        SQLiteDatabase db = getReadableDatabase();
        getTable(db);
        Cursor cursor = db.query(CONTACTED_STYLISTS_TABLE, new String[]{KEY_ID, KEY_NAME,
                        KEY_LOCATION, KEY_IMAGE,
                        KEY_DESCRIPTION, KEY_COMPANY,
                        KEY_WEBSITE, KEY_TOKEN}, KEY_NAME + "=?",
                new String[]{name}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Stylist stylist = new Stylist(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                cursor.getString(2),
                cursor.getString(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getString(7));
        // return contact
        return stylist;
    }

    //***************************************************************************************************//
    //ChatItem is provided from AllConversationsArray
    private String createMessageTextOrFilePath(ChatItem item) {
        if (item.getImagePath() != null) {
            return "Photo from " + item.getSenderName();
        } else {
            return item.getTextMessage();
        }
    }

    //message and image are provided from DB
    private String createMessageTextOrFilePath(String senderName, String textMessage, String filePath) {
        if (textMessage != null) {
            return textMessage;
        } else {
            return "Photo from " + senderName;
        }
    }

}

//combine MAX() and WHERE() in a query example :
    /*SELECT
    ID, CustomerID, EstimateNo, OrderYear, OrderNo, ProductionNo, AddedBy,
    AddedDate, SizeLength, SizeWidth, HomeModelID, HomeTypeID, DrawingNo,
    CustomerReference, BuildPieces, ProductionPieces, Notes, SpecDocument, OrderID
FROM
    OrderSpecs AS o
WHERE
    (AddedDate = (SELECT MAX(AddedDate) AS Expr1
                  FROM OrderSpecs AS i
                  WHERE (o.OrderID = OrderID)))
    AND (CustomerID = @CustomerID)
    http://stackoverflow.com/questions/11901352/sql-select-last-occurrence-of-row-count-number-of-occurrences
    */

