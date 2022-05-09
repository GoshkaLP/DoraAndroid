package com.dorawarranty.dora.mvvm.models;

import androidx.camera.core.CameraProvider;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;

public class CameraModel {

    private CameraSelector cameraSelector;
    private Preview preview;
    private ProcessCameraProvider cameraProvider;
    private ImageAnalysis imageAnalysis;

    public CameraModel(ProcessCameraProvider cameraProvider, CameraSelector cameraSelector,
                       Preview preview, ImageAnalysis imageAnalysis) {
        this.cameraProvider = cameraProvider;
        this.cameraSelector = cameraSelector;
        this.preview = preview;
        this.imageAnalysis = imageAnalysis;

    }

    public ProcessCameraProvider getCameraProvider() {
        return cameraProvider;
    }

    public CameraSelector getCameraSelector() {
        return cameraSelector;
    }

    public Preview getPreview() {
        return preview;
    }

    public ImageAnalysis getImageAnalysis() {
        return imageAnalysis;
    }
}
