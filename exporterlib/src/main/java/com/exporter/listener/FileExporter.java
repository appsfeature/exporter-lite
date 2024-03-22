package com.exporter.listener;

import com.exporter.model.ExporterData;

import java.io.File;

public interface FileExporter {
    FileExporter setFileName(String fileName);
    FileExporter setData(ExporterData excelData);
    FileExporter setDirectory(File directory);
    FileExporter setListener(ExporterCallback<File> callback);
    File getDirectory();
    void export();
}
