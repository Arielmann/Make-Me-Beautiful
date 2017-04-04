package com.example.home.makemebeautiful.servercommunication;

class Config {

    private Config() {
    }

    static final String SERVER_URL = "http://makemebeautiful.000webhostapp.com/";
    static final String SAVE_USER = SERVER_URL + "SaveUser.php";
    static final String     SEND_PUSH_NOTIFICATION = SERVER_URL + "SendPushToStylist.php";
    static final String IMAGE_UPLOADS = SERVER_URL + "ImageUpload.php";
    static final String READ_ALL_STYLISTS = SERVER_URL + "ReadAllStylistsFromServer.php";

    // Directory name to store captured images KEYS and videos
    public static final String IMAGE_DIRECTORY_NAME = "Android File Upload";

    //GCM connection data
    public static final String API_KEY = "AIzaSyCA-6MVXKIg_9c99ymwB4kjNleFVA0P11Y";
    static final String SENDER_ID = "824972538571";

}

/*
   * API key inputStream generated on console and inputStream used by 3rd
   * party server to authenticate/authorize with GCM.

    * Sender ID inputStream used by Android app to register
    * a physical device with GCM to be able to receive
    * notifications from GCM from particular 3rd party server.

     * Registration ID inputStream a result of registration of
     * physical device to GCM with Sender ID.
*/
