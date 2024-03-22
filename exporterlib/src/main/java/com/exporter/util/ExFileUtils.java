package com.exporter.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.exporter.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Abhijit Rao Created on 2019/2/19.
 */
public class ExFileUtils {
    public static final String FOLDER_NAME = "Exporter";
    public static final String FILE_NAME = "Exporter";

    public static Uri getUriFromFile(Context context, File file) {
        String fileProvider = context.getPackageName() + context.getString(R.string.file_provider);
        return FileProvider.getUriForFile(context, fileProvider, file);
    }

    public static String getFileName(Context context, Uri uri) {
        String result = null;
        if (uri.getScheme() != null && uri.getScheme().equals("content")) {
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    public static String getPathFromFile(File file) {
        return file.getAbsolutePath();
    }

    public static String getTimeStamp() {
        return new SimpleDateFormat("yyyy_MM_dd_HHmmss", Locale.getDefault()).format(new Date());
    }

    public static File getNewFile(Context context, String fileName) {
        return new File(getDirectory(context), getFileName(fileName));
    }

    public static String getFileName(String name) {
        return getFileName(name, ".jpg");

    }

    public static String getValidFileName(String fileName, String extension) {
        if (TextUtils.isEmpty(fileName)) {
            fileName = ExFileUtils.FILE_NAME;
        }
        return ExFileUtils.getFileName(fileName, extension);
    }

    public static String getFileName(String prefix, String suffix) {
        return prefix + "_" + getTimeStamp() + suffix;
    }

    public static File getDirectory(Context context) {
        return new File(context.getFilesDir(), FOLDER_NAME);
    }

    public static void deleteFiles(File file) {
        if (file != null && file.exists() && file.isDirectory()) {
            String deleteCmd = "rm -r " + file;
            Runtime runtime = Runtime.getRuntime();
            try {
                runtime.exec(deleteCmd);
            } catch (IOException ignored) {
            }
        }

    }
}
