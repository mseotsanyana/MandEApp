package com.me.mseotsanyana.mande.OLD.PL.ui;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import java.util.Objects;

/**
 * Created by Aki on 1/7/2017.
 */


public class cViewUtils {
    private static final String TAG = cViewUtils.class.getSimpleName();

    public static String getFilePath(Context context, Uri uri) {
        String actualFilepath = "";
        String filePath = "";
        try {
            /* find the actual path of the file */
            if (Objects.requireNonNull(uri.getAuthority()).equals("media")) {
                String tempID = uri.toString();
                tempID = tempID.substring(tempID.lastIndexOf("/") + 1);
                Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                String selector = MediaStore.Images.Media._ID + "=?";
                actualFilepath = getColumnData(context, contentUri, selector, new String[]{tempID});
            } else if (uri.getAuthority().equals("com.android.providers.media.documents")) {
                String tempID = DocumentsContract.getDocumentId(uri);
                String[] split = tempID.split(":");
                String type = split[0];
                String id = split[1];
                Uri contentUri = null;
                switch (type) {
                    case "image":
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "video":
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        break;
                    case "audio":
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        break;
                }
                String selector = "_id=?";
                actualFilepath = getColumnData(context, contentUri, selector, new String[]{id});
            } else if (uri.getAuthority().equals("com.android.providers.downloads.documents")) {
                String tempID = uri.toString();
                tempID = tempID.substring(tempID.lastIndexOf("/") + 1);
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.parseLong(tempID));
                actualFilepath = getColumnData(context, contentUri, null, null);
            } else if (uri.getAuthority().equals("com.android.externalstorage.documents")) {
                String tempID = DocumentsContract.getDocumentId(uri);
                String[] split = tempID.split(":");
                String type = split[0];
                String id = split[1];
                if (type.equals("primary")) {
                    actualFilepath = Environment.getExternalStorageDirectory() + "/" + id;
                }
            }

            String tempPath = uri.getPath();
            assert tempPath != null;
            if (tempPath.contains("//")) {
                tempPath = tempPath.substring(tempPath.indexOf("//") + 1);
            }

            if (actualFilepath.equals("") || actualFilepath.equals(" ")) {
                filePath = tempPath;
            } else {
                filePath = actualFilepath;
            }
        } catch (Exception e) {
            Log.e(TAG, " Error Occurred " + e.toString());
        }
        return filePath;
    }

    public static String getColumnData(Context context, Uri uri, String selection, String[] selectArg) {
        String filepath = "";
        String column = "_data";
        String[] projection = {column};
        Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectArg,
                null);
        if (cursor != null) {
            cursor.moveToFirst();
            //Log.e(TAG, " file path is " + cursor.getString(cursor.getColumnIndex(column)));
            filepath = cursor.getString(cursor.getColumnIndex(column));
        }
        if (cursor != null)
            cursor.close();
        return filepath;
    }

//    public void readfile(File file) {
//        // File file = new File(Environment.getExternalStorageDirectory(), "mytextfile.txt");
//        StringBuilder builder = new StringBuilder();
//        Log.e("main", "read start");
//        try {
//            BufferedReader br = new BufferedReader(new FileReader(file));
//            String line;
//            while ((line = br.readLine()) != null) {
//                builder.append(line);
//                builder.append("\n");
//            }
//            br.close();
//        } catch (Exception e) {
//            Log.e("main", " error is " + e.toString());
//        }
//        Log.e("main", " read text is " + builder.toString());
//        //textView.setText(builder.toString());
//    }
//
//
//    public static String loadImageFromAsset(String assetPath, AssetManager assetManager) {
//        String imageFile;
//        try {
//            InputStream is = assetManager.open(assetPath);
//
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            imageFile = new String(buffer, StandardCharsets.UTF_8);
//
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//
//        //Log.e(TAG, "Json response " + json);
//        return imageFile;
//    }
//
//
//    private static String getFileExtension(Uri uri, Context context) {
//        ContentResolver cR = context.getContentResolver();
//        MimeTypeMap mime = MimeTypeMap.getSingleton();
//        return mime.getExtensionFromMimeType(cR.getType(uri));
//    }
}