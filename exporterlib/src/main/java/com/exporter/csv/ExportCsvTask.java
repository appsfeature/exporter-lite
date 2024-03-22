package com.exporter.csv;

import com.exporter.listener.ExporterCallback;
import com.exporter.task.AsyncThread;
import com.exporter.util.ExFileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class ExportCsvTask extends AsyncThread<Void, Void, File> {

    private final File directory;
    private final String fileName;
    private final List<List<String>> csvData;
    private final ExporterCallback<File> listener;
    private String errorMessage = "";

    public ExportCsvTask(File directory, String fileName, List<List<String>> csvData, ExporterCallback<File> listener) {
        this.fileName = ExFileUtils.getValidFileName(fileName, ExportCsv.EXTENSION);
        this.csvData = csvData;
        this.directory = directory;
        this.listener = listener;
    }

    @Override
    protected File doInBackground(Void... voids) {
        try {
            return exportDataInCsvFile();
        } catch (IOException e) {
            e.printStackTrace();
            this.errorMessage = e.getMessage();
            return null;
        }
    }

    @Override
    protected void onPostExecute(File result) {
        super.onPostExecute(result);
        if (result != null) {
            listener.onSuccess(result);
        } else {
            listener.onFailure(new Exception("Error:101 " + errorMessage));
        }
    }


    private File exportDataInCsvFile() throws IOException {

        if (!directory.exists()) {
            boolean status = directory.mkdirs();
        }
        File csvFile = new File(directory, fileName);
        FileWriter writer = new FileWriter(csvFile);

        for (int i = 0; i < csvData.size(); i++) {
            List<String> rowList = csvData.get(i);
            for (int j = 0; j < rowList.size(); j++) {
                writer.append(rowList.get(j));
                writer.append(',');
            }
            if (i != csvData.size() - 1)
                writer.append('\n');
        }
        writer.flush();
        writer.close();
        return csvFile;
    }


}
