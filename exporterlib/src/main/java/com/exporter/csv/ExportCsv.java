package com.exporter.csv;

import android.content.Context;

import com.exporter.exception.ExporterExceptions;
import com.exporter.listener.ExporterCallback;
import com.exporter.listener.FileExporter;
import com.exporter.model.ExporterData;
import com.exporter.util.ExFileUtils;
import com.exporter.util.ExShowMessage;

import java.io.File;

public class ExportCsv implements FileExporter {

    public static final String EXTENSION = ".csv";
    private final Context context;
    private String fileName;
    private ExporterCallback<File> listener;
    private File directory;
 
    private ExporterData mData;

    public ExportCsv(Context context) {
        this.context = context;
    }

    @Override
    public ExportCsv setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public ExportCsv setData(ExporterData data) {
        this.mData = data;
        return this;
    }


    @Override
    public ExportCsv setDirectory(File directory) {
        this.directory = directory;
        return this;
    }

    @Override
    public ExportCsv setListener(ExporterCallback<File> callback) {
        this.listener = callback;
        return this;
    }

    @Override
    public File getDirectory() {
        if (directory == null) {
            directory = ExFileUtils.getDirectory(context);
        }
        return directory;
    }


    @Override
    public void export() {
        if (isValidFields()){
            new ExportCsvTask(getDirectory(), fileName, mData.getExcelArray(), listener).execute();
        }
    }

    private boolean isValidFields() {
        if (listener != null) {
            if (mData == null) {
                listener.onFailure(new Exception(ExporterExceptions.ERROR_FILE_BODY_INITIALIZATION));
                return false;
            }
            return true;
        } else {
            ExShowMessage.toast(context, ExporterExceptions.ERROR_LISTENER_INITIALIZATION);
            return false;
        }
    }

}
