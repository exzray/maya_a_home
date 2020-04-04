package com.afiq.myapplication.utilities;

import android.graphics.Bitmap;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QrCode {

    public static Bitmap generateBitmapQrCode(String text) {
        BarcodeEncoder encoder = new BarcodeEncoder();
        Bitmap bitmap =  null;

        try {
            bitmap = encoder.encodeBitmap(text, BarcodeFormat.QR_CODE, 400, 400);

        } catch (WriterException e) {
            e.printStackTrace();
        }

        return bitmap;
    }
}
