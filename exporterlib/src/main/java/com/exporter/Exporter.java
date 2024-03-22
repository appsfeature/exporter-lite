package com.exporter;

import android.content.Context;

import com.exporter.csv.ExportCsv;
import com.exporter.dialog.FileDirectoryDialog;
import com.exporter.excel.ExportExcel;
import com.exporter.listener.ExporterSelector;
import com.exporter.listener.FileExporter;
import com.exporter.text.ExportText;
import com.exporter.util.ExFileUtils;

import java.io.File;

public class Exporter {

    private volatile static Exporter instance;

    private Exporter() {}

    public static Exporter getInstance() {
        if (instance == null) {
            synchronized (Exporter.class) {
                if (instance == null) instance = new Exporter();
            }
        }
        return instance;
    }

    public FileExporter TextBuilder(Context context) {
        return new ExportText(context);
    }


    public FileExporter ExcelBuilder(Context context) {
        return new ExportExcel(context);
    }

    public FileExporter CsvBuilder(Context context) {
        return new ExportCsv(context);
    }

    public void showListOfFiles(Context context, ExporterSelector<File> selector) {
        File rootDirectory = ExFileUtils.getDirectory(context);
        showListOfFiles(context, null, rootDirectory, selector);
    }

    public void showListOfFiles(Context context, String dialogTitle, File rootDirectory, ExporterSelector<File> selector) {
        if(context != null && rootDirectory != null && rootDirectory.isDirectory()) {
            File[] files = rootDirectory.listFiles();
            FileDirectoryDialog dialog = FileDirectoryDialog.newInstance(context, dialogTitle, selector, files);
            dialog.show();
        }
    }

    public void clearListOfFiles(Context context) {
        clearListOfFiles(ExFileUtils.getDirectory(context));
    }
    public void clearListOfFiles(File rootDirectory) {
        ExFileUtils.deleteFiles(rootDirectory);
    }
}
