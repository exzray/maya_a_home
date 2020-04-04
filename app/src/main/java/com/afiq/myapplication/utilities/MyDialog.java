package com.afiq.myapplication.utilities;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

public class MyDialog {

    public static void showDialog(Context context, String message) {
        AlertDialog dialog = new AlertDialog
                .Builder(context)
                .setMessage(message)
                .setPositiveButton("Thanks", null)
                .create();

        dialog.show();
    }
}
