package com.exporter.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import android.print.PrintManager;
import androidx.core.app.ShareCompat;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;


public class ExporterShare {

//    public static Uri getUriFromFile(Context context, File file) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".provider", file);
//        } else {
//            return Uri.fromFile(file);
//        }
//    }


    public static void sendData(Activity activity, String text, File fileContent) {
        Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                .setType("text/plain")
                .setText(text)
                .setStream(Uri.fromFile(fileContent))
                .getIntent();
        if (shareIntent.resolveActivity(activity.getPackageManager()) != null) {
            activity.startActivity(shareIntent);
        }
    }

    public static void shareTextFile(Context context, Uri uri) {
        Intent in = new Intent(Intent.ACTION_SEND);
        in.putExtra(Intent.EXTRA_TEXT, "Tinder Friends Backup");
        in.putExtra(Intent.EXTRA_STREAM, uri);
        in.setType("text/*");
        in.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(Intent.createChooser(in, "Share With Fiends"));
        } catch (Exception e) {
            showToast(context, e.getMessage());
        }
    }
    public static void openTextViewerActivity(Context context, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "text/plain");
        try {
            context.startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(context,"Text Viewer application not available.",Toast.LENGTH_LONG).show();
        }
    }
    public static void openCSVViewerActivity(Context context, Uri uri){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "text/csv");
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(intent);
        }catch (ActivityNotFoundException e){
            Toast.makeText(context,"CSV Viewer application not available.",Toast.LENGTH_LONG).show();
        }
    }


    public static void shareFile(Context context, File file) {
        Uri uri = ExFileUtils.getUriFromFile(context, file);
        shareFile(context, uri, file.getName());
    }
    public static void shareFile(Context context, Uri uri, String fileName) {
        Intent in = new Intent(Intent.ACTION_SEND);
        in.setDataAndType(uri, "application/TEXT");
        in.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        in.putExtra(Intent.EXTRA_TEXT, fileName);
//        in.putExtra("android.intent.extra.TEXT", fileName);
        in.putExtra(Intent.EXTRA_STREAM, uri);
        try {
            context.startActivity(Intent.createChooser(in, "Share With Cloud"));
        } catch (Exception e) {
            showToast(context, e.getMessage());
        }
    }

    private File shareBackupFile(Context context, File directory, String databaseName) {
        try {
            File currentDB = context.getDatabasePath(databaseName);
            String dbName = "database_backup_file.db";
            File backupDB = new File(directory, dbName);

            FileChannel src = new FileInputStream(currentDB).getChannel();
            FileChannel dst = new FileOutputStream(backupDB).getChannel();
            dst.transferFrom(src, 0, src.size());
            src.close();
            dst.close();
            return backupDB;
        } catch (Exception e) {
            showToast(context, "Backup Failed please select a valid backup file!");
            return null;
        }
    }

    public static void sharePdfFileThroughEmail(Context context, Uri uri, String toEmailId, String subject, String emailBody) {
        try {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("pdf/*");
            List<Intent> targets = new ArrayList<>();
            List<ResolveInfo> candidates = context.getPackageManager().queryIntentActivities(intent, 0);
            for (ResolveInfo candidate : candidates) {
                String packageName = candidate.activityInfo.packageName;

                if (packageName.equals("com.google.android.gm") || packageName.equals("com.yahoo.mobile.client.android.mail") || packageName.equals("com.microsoft.office.outlook") || packageName.equals("com.google.android.apps.inbox")) {
                    Intent iWantThis = new Intent(Intent.ACTION_SEND);
                    iWantThis.setPackage(packageName);
                    iWantThis.setType("pdf/*");
                    String[] recipient = {toEmailId};
                    iWantThis.putExtra(Intent.EXTRA_EMAIL, recipient);
                    iWantThis.putExtra(Intent.EXTRA_SUBJECT, subject);
                    iWantThis.putExtra(Intent.EXTRA_TEXT, emailBody);
                    iWantThis.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    iWantThis.putExtra(Intent.EXTRA_STREAM, uri);
                    targets.add(iWantThis);
                }
            }
            try {
                Intent chooser = Intent.createChooser(targets.remove(0), "Share With Email");
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targets.toArray(new Parcelable[targets.size()]));
                context.startActivity(chooser);
            } catch (Exception e) {
                showToast(context, "No application found to take action. Please download Gmail/Yahoo Mail");
            }
        } catch (ActivityNotFoundException e) {
            showToast(context, "No Pdf reader found please download PDF reader to view Invoice");
        }
    }

    public static void openPdfFile(Context context, Uri uri, String fileName) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri, "application/pdf");
            intent.putExtra(Intent.EXTRA_TEXT, fileName);
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            showToast(context, "No Pdf reader found please download PDF reader to view Invoice");
        }
    }

    public static void openExcelFile(Context context, Uri uri, String fileName) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        intent.putExtra(Intent.EXTRA_TEXT, fileName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        try {
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            showToast(context, "No application found to open Excel file.");
            context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.google.android.apps.docs.editors.sheets")));
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void doPrint(Context context, final String fileName, final File fileToPrint) {
        PrintDocumentAdapter pda = new PrintDocumentAdapter() {
            @Override
            public void onWrite(PageRange[] pages, ParcelFileDescriptor destination, CancellationSignal cancellationSignal, WriteResultCallback callback) {
                InputStream input = null;
                OutputStream output = null;
                try {
                    input = new FileInputStream(fileToPrint);
                    output = new FileOutputStream(destination.getFileDescriptor());
                    byte[] buf = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = input.read(buf)) > 0) {
                        output.write(buf, 0, bytesRead);
                    }
                    callback.onWriteFinished(new PageRange[]{PageRange.ALL_PAGES});
                } catch (Exception e) {
                    //Catch exception
                } finally {
                    try {
                        if (input != null) {
                            input.close();
                        }
                        if (output != null) {
                            output.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onLayout(PrintAttributes oldAttributes, PrintAttributes newAttributes, CancellationSignal cancellationSignal, LayoutResultCallback callback, Bundle extras) {
                if (cancellationSignal.isCanceled()) {
                    callback.onLayoutCancelled();
                    return;
                }
                PrintDocumentInfo pdi = new PrintDocumentInfo.Builder(fileName).setContentType(PrintDocumentInfo.CONTENT_TYPE_DOCUMENT).build();
                callback.onLayoutFinished(pdi, true);
            }
        };
        try {
            PrintManager printManager = (PrintManager) context.getSystemService(Context.PRINT_SERVICE);
            String jobName = "Document " + fileName;
            printManager.print(jobName, pda, null);
        } catch (NullPointerException e) {
            showToast(context, "Printer option not support in this Android version");
        }
    }

    private static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }


}
