package com.example.home.makemebeautiful.servercommunication;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.home.makemebeautiful.R;
import com.example.home.makemebeautiful.profile.sharedprefrences.SharedPrefManager;
import com.example.home.makemebeautiful.resources.AppStrings;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class UploadImage extends AsyncTask<Void, String, String> {

    private String regId;
    private String TAG = "Upload data";
    private File sourceFile;
    private String imageUrlFromServer = null;
    private String uploadImagePath;
    private Object interfaceHolder;
    private Uri imageUri;

    public UploadImage(Context context, Object interfaceHolder, String uploadFilePath, Uri imageUri) {
        this.regId = SharedPrefManager.getInstance(context).getUserGcmToken(); //SharedPrefManager inputStream a LOCAL class
        this.sourceFile = new File(uploadFilePath);
        this.interfaceHolder = interfaceHolder;
        this.uploadImagePath = uploadFilePath;
        this.imageUri = imageUri;
        Log.d(TAG, "Object created");
    }


        @Override
        protected String doInBackground (Void...params){
            Log.d(TAG, "Starting parameters init");

            try {
                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(sourceFile);
                String upLoadServerUrl = Config.IMAGE_UPLOADS;
                URL url = new URL(upLoadServerUrl);

                // Open a HTTP  connection to  the URL
                //Doesn't send the push notification but definetly should do it
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs from server
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive"); //keep connection alive after headers were sent
                conn.setRequestProperty("ENCTYPE", "multipart/form-data"); //multipart indicates that server should read it AS BYTES
                String boundary = "*****";
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary); //serve expects to get Stream of BYTES
                String fileName = sourceFile.getName(); //ADD USER NAME!!
                conn.setRequestProperty("uploaded_file", sourceFile.getName());
                conn.setRequestProperty("user_id", "1"); //php need to connect image to userId
                conn.setRequestProperty("msg", "TextMessage from HTTPUrlConntection");
                conn.setRequestProperty("reg_id", regId);
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                String twoHyphens = "--", lineEnd = "\r\n";
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);

                dos.writeBytes(lineEnd);
                Log.d(TAG, "parameters were written as bytes");
                Log.d(TAG, "file size: " + sourceFile.length());

                // create a buffer of  maximum size
                int bytesAvailable = fileInputStream.available();

                int maxBufferSize = 1024;
                int bufferSize = Math.min(bytesAvailable, maxBufferSize);
                byte[] buffer = new byte[bufferSize];

                // read file and write it into form...
                int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);
                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd); //USE DEFENSIVE PROGRAMMING HERE
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                int serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.d(TAG, "HTTP Response inputStream : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200) {
                }

                //close the streams //
                fileInputStream.close();

                InputStream is = conn.getInputStream(); //the inputstream inputStream the server response to client call
                int counter = 0;
                StringBuilder builder = new StringBuilder();
                while ((counter = is.read(buffer)) != -1) {
                    builder.append(new String(buffer, 0, counter));
                }
                Log.d(TAG, "inputStream: " + builder.toString());
                imageUrlFromServer = builder.toString();
                dos.flush();
                Log.d(TAG, "upload started");
                dos.close();

            } catch (MalformedURLException ex) {

                ex.printStackTrace();

                Log.e(TAG, "Cancel Async. error: " + ex.getMessage(), ex);
                cancel(true);
            } catch (Exception e) {

                Log.e(TAG, "Cancel Async. Exception: "
                        + e.getMessage(), e);
                cancel(true);
            }
            Log.d(TAG, "Final url: " + Config.SERVER_URL + imageUrlFromServer);
            return Config.SERVER_URL + imageUrlFromServer;
        }

        @Override
        protected void onPostExecute (String imageUrl){
            ((OnImageUploadedToServer) interfaceHolder).handleServerImageUrl(imageUrl, uploadImagePath, imageUri);
            Log.d(TAG, "interface was activated with url: " + imageUrl);
        }
    }



