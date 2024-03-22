package com.exporter.util;

import android.content.Context;
import android.widget.Toast;

public class ExShowMessage {
    public static void toast(Context context, String message) {
        Toast.makeText(context, message,Toast.LENGTH_LONG).show();
    }
}
