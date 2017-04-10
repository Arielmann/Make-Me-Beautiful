package com.example.home.makemebeautiful.appinit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import com.crittercism.app.Crittercism;
import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.chat.model.AllConversationsHashMap;
import com.example.home.makemebeautiful.choosestylist.model.ChooseStylistModel;
import com.example.home.makemebeautiful.contactedusers.model.ContactedUsersModel;
import com.example.home.makemebeautiful.contactedusers.model.ContactedUsersRowsHashMap;
import com.example.home.makemebeautiful.utils.imageutils.ImageUtils;
import com.example.home.makemebeautiful.servercommunication.ReadStylistsFromServer;
import com.sromku.simple.storage.SimpleStorage;
import com.sromku.simple.storage.Storage;

import java.util.concurrent.ExecutionException;


public class AppDataInit {

/*
* Called when app starts to load data from server and device:
*
* 1. Starts downloding stylists from server DB
* 2. Starts loading conversation rows from device
* 3. Starts loading conversations chat data from device (i.e ChatItems)
* 4. Starts initializing user default profile image
* 5. If app runs for the first time, creates app directories
*/

    public static void readStylistsFromServer(Activity activity) throws ExecutionException, InterruptedException {
        //**********************Start downloading Stylists from server*********//
        if (ChooseStylistModel.getInstance(activity).getStylistsFromServer().isEmpty()) {
            ReadStylistsFromServer reader = new ReadStylistsFromServer(activity, ChooseStylistModel.getInstance(activity));
            reader.start();
        }
    }

    private static void initContactedUsersAdapter(Context context) {
        ContactedUsersModel.getInstance(context).setAdapter(); //Set the adapter for the contacts
    }

    private static void initChatData(Context context) {
        //**************init whole app conversations and contacts data*******//
        if (AllConversationsHashMap.getInstance().getHashMap().isEmpty()) {
            PastChatItemsLoader chatItemsLoader = new PastChatItemsLoader("chatLoadThread", context);
            chatItemsLoader.start(); //It's a thread because expected completion time is MORE then 5 seconds
        }

        if (ContactedUsersRowsHashMap.getInstance().getHashMap().isEmpty()) {
            ContactedUsersRowsLoader loader = new ContactedUsersRowsLoader(context);
            loader.execute();
        }
    }

    public static void createDirectories() {
        Storage storage = SimpleStorage.getExternalStorage();
        storage.createDirectory("Make_Me_Beautiful");
        storage.createDirectory("Make_Me_Beautiful/Contacts");
    }

    public static void initAppData(Context context) {
        //TODO: ReadStylistsFromServer is not yet included within method
        //initProfileImageHeader();
        ImageUtils.initDefaultProfileImage(context);
        initChatData(context);
        initContactedUsersAdapter(context);

    }

    public static void initAppDataWithProgDialog(final Context context, final Object interfaceHolder) {
        final ProgressDialog progressDialog = new ProgressDialog(context,
                R.style.MMBAppTheme);
        initAppData(context);
        //will provoke onImageLoaded OR onImageLoadingError in ToolBarFrag
        ImageUtils.fetchUserProfileImage(context, interfaceHolder);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage(" Initializing");
        progressDialog.show();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        progressDialog.dismiss();
                    }
                }, 2000);
    }

    public static void initCrashesMonitor(Context context) {
        Crittercism.initialize(context, "ae859aabb46846adbcfd2ddce0e0bb3400555300");
    }
}






// public static Stylist mockStylist = new Stylist(1, "Ariel Mann", "A-Style", "Tel-Aviv Yafo", ImageUtils.testImagePath, "This is a long description describing who am I and what am I doing here right now and that I would be glad to help if I can", "www.astyle.com", "Token");;
