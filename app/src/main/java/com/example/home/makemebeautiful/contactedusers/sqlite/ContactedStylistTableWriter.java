package com.example.home.makemebeautiful.contactedusers.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.example.home.makemebeautiful.contactedusers.events.OnContactedUsersLoadedEvent;
import com.example.home.makemebeautiful.contactedusers.model.ContactedUserRow;
import com.example.home.makemebeautiful.contactedusers.model.ContactedUsersModel;
import com.example.home.makemebeautiful.contactedusers.model.ContactedUsersRowsHashMap;
import com.example.home.makemebeautiful.dbmanager.DataBaseManager;
import com.example.home.makemebeautiful.image_providing.ImageUtils;
import com.example.home.makemebeautiful.profile.profilemodels.Stylist;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by home on 7/26/2016.
 */
public class ContactedStylistTableWriter extends SQLiteOpenHelper {

    /*
    * The Writer is called when a the user sends
    * his first message to a newly chosen Stylist
    * within the ChatScreen OR when he get's a new
    * message from a stylist.(for now, checks if
    * its a new stylist saveAddressedUserToTable() method located
    * within the ChatScreen controllers is called every time the user
    * sends any kind of message)
    * */

    private static final String CONTACTED_STYLISTS_TABLE = DataBaseManager.FeedEntry.CONTACTED_STYLISTS_TABLE;
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_LOCATION = "location";
    private static final String KEY_IMAGE_PATH = "image";
    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_COMPANY = "company";
    private static final String KEY_WEBSITE = "website";
    private static final String KEY_TOKEN = "gcm_token";

    public ContactedStylistTableWriter(Context context, int version) {
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

    private void getTable(SQLiteDatabase db) {
        // db.execSQL("DROP TABLE IF EXISTS " + CONTACTED_STYLISTS_TABLE);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + CONTACTED_STYLISTS_TABLE + //TODO: SHOULD BE CALLED IN ONCREATE()!
                "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT_LEFT NOT NULL, " +
                "location TEXT_LEFT, " +
                "image TEXT_LEFT NOT NULL, " +
                "description TEXT_LEFT, " +
                "company TEXT_LEFT, " +
                "website TEXT_LEFT, " +
                "token TEXT_LEFT NOT NULL)");
    }

    public void addContactToTable(final Context context, final Stylist[] stylists, final String lastMessageDate, final String lastMessage) {

        new AsyncTask<Stylist, Void, Stylist>() {

            @Override
            protected Stylist doInBackground(Stylist... stylistInArray) {
                SQLiteDatabase db = getWritableDatabase(); //TODO: DO WITH AsyncTask
                db.execSQL("CREATE TABLE IF NOT EXISTS " + CONTACTED_STYLISTS_TABLE + //TODO: SHOULD BE CALLED IN ONCREATE()!
                        "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "name TEXT_LEFT NOT NULL, " +
                        "location TEXT_LEFT, " +
                        "image TEXT_LEFT NOT NULL, " +
                        "description TEXT_LEFT, " +
                        "company TEXT_LEFT, " +
                        "website TEXT_LEFT, " +
                        "token TEXT_LEFT NOT NULL)");

                ContentValues values = new ContentValues();

                try {
                    values.put(KEY_NAME, stylistInArray[0].getName());
                } catch (NullPointerException e) {
                    throw new ClassCastException(getClass().toString()
                            + "Addressed stylist passed from contactedUsersFrag is NULL!,  ");
                }
                //Create the profile image file on device, based on stylist bitmap
                ImageUtils.setUserImageFile(context,
                        stylistInArray[0],
                        stylistInArray[0].getUserImageBitmap(),
                        stylistInArray[0].getName());

                values.put(KEY_LOCATION, stylistInArray[0].getLocation());
                values.put(KEY_IMAGE_PATH, stylistInArray[0].getProfileImagePath());
                values.put(KEY_DESCRIPTION, stylistInArray[0].getDescription());
                values.put(KEY_COMPANY, stylistInArray[0].getCompany());
                values.put(KEY_WEBSITE, stylistInArray[0].getWebsite());
                values.put(KEY_TOKEN, stylistInArray[0].getGcmToken());
                db.insert(CONTACTED_STYLISTS_TABLE, null, values);
                EventBus.getDefault().postSticky(db);
                return stylistInArray[0];
            }

            @Override
            protected void onPostExecute(Stylist stylist) {
                //add contact row for ContactedUsesHashMap
                ContactedUsersModel contactedUsersModel = ContactedUsersModel.getInstance(context);
                ContactedUserRow contactedUserRow = new ContactedUserRow(stylist.getName(), stylist.getProfileImagePath(), lastMessageDate, lastMessage);
                contactedUserRow.setBitmap(stylist.getUserImageBitmap()); // bitmap is already defined
                ContactedUsersRowsHashMap.getInstance().getHashMap().put(stylist.getName(), contactedUserRow);
                contactedUsersModel.getDataSet().add(contactedUserRow);
                EventBus.getDefault().post(new OnContactedUsersLoadedEvent(contactedUsersModel.getDataSet()));
                ContactedUsersModel.getInstance(context).getAdapter().notifyDataSetChanged();
                //TODO: find a way to close the db without crashing
            }
        }.execute(stylists);
    }
}