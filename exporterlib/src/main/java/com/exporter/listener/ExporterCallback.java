package com.exporter.listener;

public interface ExporterCallback<T> {

    void onSuccess(T result);

    void onFailure(Exception e);

}
