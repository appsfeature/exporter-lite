package com.exporter.model;

import java.util.List;

public class ExporterData {
    private List<List<String>> excelArray;
    private boolean isEnableColumnSerial;

    private StringBuilder text;

    public ExporterData() {}

    public ExporterData(String text) {
        this.text = new StringBuilder(text);
    }

    public ExporterData(List<List<String>> excelArray, boolean isEnableColumnSerial) {
        this.excelArray = excelArray;
        this.isEnableColumnSerial = isEnableColumnSerial;
    }

    public List<List<String>> getExcelArray() {
        return excelArray;
    }

    public void setExcelArray(List<List<String>> excelArray) {
        this.excelArray = excelArray;
    }

    public StringBuilder getText() {
        return text;
    }

    public void setText(String text) {
        this.text = new StringBuilder(text);
    }

    public void setText(StringBuilder text) {
        this.text = text;
    }

    public boolean isEnableColumnSerial() {
        return isEnableColumnSerial;
    }

    public void setEnableColumnSerial(boolean enableColumnSerial) {
        isEnableColumnSerial = enableColumnSerial;
    }
}
