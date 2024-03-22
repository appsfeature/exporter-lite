package com.appsfeature.exporter;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.exporter.Exporter;
import com.exporter.listener.ExporterCallback;
import com.exporter.listener.ExporterSelector;
import com.exporter.model.ExporterData;
import com.exporter.sample.ExportSample;
import com.exporter.util.ExporterShare;

import java.io.File;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void exportDataInTextFile() {
        Exporter.getInstance().TextBuilder(this)
                .setFileName("SampleText")
                .setData(new ExporterData("Hello World!"))
                .setListener(new ExporterCallback<File>() {
                    @Override
                    public void onSuccess(File result) {
                        ExporterShare.shareFile(MainActivity.this, result);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).export();
    }

    private void exportDataInExcelFile(List<List<String>> body) {
        Exporter.getInstance().ExcelBuilder(this)
                .setFileName("SampleExcel")
                .setData(new ExporterData(body, true))
                .setListener(new ExporterCallback<File>() {
                    @Override
                    public void onSuccess(File result) {
                        ExporterShare.shareFile(MainActivity.this, result);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }).export();
    }

    private void exportDataInCsvFile(List<List<String>> body) {
        Exporter.getInstance().CsvBuilder(this)
                .setFileName("SampleCsv")
                .setData(new ExporterData(body, false))
                .setListener(new ExporterCallback<File>() {
                    @Override
                    public void onSuccess(File result) {
                        ExporterShare.shareFile(MainActivity.this, result);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }).export();
    }

    public void clickExcel(View view) {
        List<List<String>> excelBody = ExportSample.getExporterList();
        exportDataInExcelFile(excelBody);
    }

    public void clickCSV(View view) {
        List<List<String>> excelBody = ExportSample.getExporterList();
        exportDataInCsvFile(excelBody);
    }

    public void clickText(View view) {
        exportDataInTextFile();
    }

    public void clickList(View view) {
        Exporter.getInstance().showListOfFiles(this, new ExporterSelector<File>() {
            @Override
            public void onSelect(File result) {
                ExporterShare.shareFile(MainActivity.this, result);
            }
        });
    }

    public void clickClearList(View view) {
        Exporter.getInstance().clearListOfFiles(this);
    }
}
