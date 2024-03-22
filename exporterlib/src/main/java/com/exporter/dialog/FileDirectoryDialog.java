package com.exporter.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import android.text.TextUtils;

import com.exporter.R;
import com.exporter.listener.ExporterSelector;

import java.io.File;

public class FileDirectoryDialog {

    private final Context context;
    private final File[] files;
    private final ExporterSelector<File> selector;
    private String dialogTitle;
    private Dialog dialog;

    private FileDirectoryDialog(Context context, String dialogTitle, ExporterSelector<File> selector, File[] files) {
        this.context = context;
        this.files = files;
        this.dialogTitle = dialogTitle;
        this.selector = selector;
        this.buildDialog();
    }

    public static FileDirectoryDialog newInstance(Context context, String dialogTitle, ExporterSelector<File> selector, File[] files) {
        return new FileDirectoryDialog(context,dialogTitle, selector, files);
    }

    private void buildDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(TextUtils.isEmpty(dialogTitle)){
            dialogTitle = context.getString(R.string.select_file);
        }
        builder.setTitle(dialogTitle)
                .setItems(getList(files), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int position) {
                        selector.onSelect(files[position]);
                        hide();
                    }
                });
        dialog = builder.create();
    }

    private String[] getList(File[] files) {
        String[] list = new String[files.length];
        for (int i = 0; i < files.length; i++) {
            list[i] = files[i].getName();
        }
        return list;
    }

    public void show() {
        if (context != null) {
            dialog.show();
        }
    }

    public void hide() {
        if (context != null) {
            dialog.dismiss();
        }
    }

}