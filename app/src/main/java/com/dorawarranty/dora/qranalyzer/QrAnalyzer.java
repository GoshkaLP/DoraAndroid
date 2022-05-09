package com.dorawarranty.dora.qranalyzer;

import android.annotation.SuppressLint;
import android.media.Image;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;

import com.dorawarranty.dora.qranalyzer.listeners.QrListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class QrAnalyzer implements ImageAnalysis.Analyzer {
    private QrListener listener;

    public QrAnalyzer(QrListener listener) {
        this.listener = listener;
    }

    @Override
    public void analyze(ImageProxy imageProxy) {
        @SuppressLint("UnsafeOptInUsageError") Image mediaImage = imageProxy.getImage();

        if (mediaImage != null) {
            InputImage image = InputImage.fromMediaImage(mediaImage, imageProxy.getImageInfo().getRotationDegrees());

            BarcodeScannerOptions scannerOptions = new BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_QR_CODE).build();
            BarcodeScanner scanner = BarcodeScanning.getClient(scannerOptions);

            scanner.process(image).addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                @Override
                public void onSuccess(List<Barcode> barcodes) {
                    if (!barcodes.isEmpty()) {
                        listener.QrOnSuccess(barcodes.get(0).getRawValue());
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    listener.QrOnFailure();
                }
            }).addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                @Override
                public void onComplete(@NonNull Task<List<Barcode>> task) {
                    imageProxy.close();
                }
            });
        }
    }
}
