package com.exporter.exception;

public interface ExporterExceptions {
    String ERROR = "Error";
    String ERROR_SDK_INITIALIZATION = ERROR + ":401- Camera SDK not initialize properly.";
    String ERROR_LISTENER_INITIALIZATION = ERROR + ":402- Camera listener not initialize properly.";
    String ERROR_PERMISSION_DENIED = ERROR +":405- Storage permission denied.";

    //Text Library
    String ERROR_FILE_BODY_INITIALIZATION = ERROR +":406- File Body not initialize properly.";
}
