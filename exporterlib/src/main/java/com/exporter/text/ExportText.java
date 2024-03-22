package com.exporter.text;

import android.content.Context;

import com.exporter.exception.ExporterExceptions;
import com.exporter.listener.ExporterCallback;
import com.exporter.listener.FileExporter;
import com.exporter.model.ExporterData;
import com.exporter.util.ExFileUtils;
import com.exporter.util.ExShowMessage;

import java.io.File;

public class ExportText implements FileExporter {

    public static final String EXTENSION = ".txt";
    private final Context context;
    private String fileName;
    private ExporterData mData;
    private ExporterCallback<File> listener;
    private File directory;

    public ExportText(Context context) {
        this.context = context;
    }

    @Override
    public ExportText setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public ExportText setData(ExporterData data) {
        this.mData = data;
        return this;
    }

    @Override
    public ExportText setDirectory(File directory) {
        this.directory = directory;
        return this;
    }

    @Override
    public ExportText setListener(ExporterCallback<File> callback) {
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
            new ExportTextTask(getDirectory(), fileName, mData.getText(), listener).execute();
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
