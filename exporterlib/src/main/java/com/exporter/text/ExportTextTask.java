package com.exporter.text;

import com.exporter.listener.ExporterCallback;
import com.exporter.task.AsyncThread;
import com.exporter.util.ExFileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ExportTextTask extends AsyncThread<Void, Void, File> {

    private final File directory;
    private final String fileName;
    private final StringBuilder fileBody;
    private final ExporterCallback<File> listener;
    private String errorMessage = "";

    public ExportTextTask(File directory, String fileName, StringBuilder fileBody, ExporterCallback<File> listener) {
        this.fileName = ExFileUtils.getValidFileName(fileName,ExportText.EXTENSION);
        this.fileBody = fileBody;
        this.directory = directory;
        this.listener = listener;
    }

    @Override
    protected File doInBackground(Void... voids) {

        try {
            return saveTextFile(fileName, fileBody);
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


    private File saveTextFile(String sFileName, StringBuilder fileBody) throws IOException {
        if (!directory.exists()) {
            boolean status = directory.mkdirs();
        }
        File textFile = new File(directory, sFileName);
        FileWriter writer = new FileWriter(textFile);
        writer.append(fileBody);
        writer.flush();
        writer.close();
        return textFile;
    }


}
