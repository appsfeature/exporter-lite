package com.exporter.util;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import androidx.core.app.ShareCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ExWhatsApp {

    public static Boolean isAvailable(Activity activity) {
        PackageManager pm = activity.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void sendData2(Activity activity, String text, File fileContent) {
        Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setText(text)
                .setStream(Uri.fromFile(fileContent))
                .getIntent();
        if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(shareIntent);
        }
    }

    public static void sendData(Activity activity, String text, File fileContent) {

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(fileContent));
        intent.setType("image/jpeg");
        intent.setPackage("com.whatsapp");
        activity.startActivity(intent);
    }

    /* contact number like: +919876543211
     contact number must be in your contact list
     if using this option putExtra method not working*/
    public static void openWhatsappWithNumber(Activity activity, String contactNo) {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(new StringBuilder("smsTo:").append(contactNo).toString()));
        intent.setPackage("com.whatsapp");
        activity.startActivity(intent);
    }

    public static File getResourceFile(Activity activity, int drawableFile, String filename) {

        Bitmap bmp = BitmapFactory.decodeResource(activity.getResources(), drawableFile);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, bos);
        File f = new File(Environment.getExternalStorageDirectory() + File.separator + filename + ".jpeg");

        try {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            fos.write(bos.toByteArray());
            return f;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


}
