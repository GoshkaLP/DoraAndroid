package com.dorawarranty.dora.mvvm.viewModels;

import android.app.Application;
import android.util.Log;
import android.util.Size;

import androidx.annotation.NonNull;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dorawarranty.dora.mvvm.models.CameraModel;
import com.dorawarranty.dora.qranalyzer.QrAnalyzer;
import com.dorawarranty.dora.qranalyzer.listeners.QrListener;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

public class CameraXViewModel extends AndroidViewModel {

    private MutableLiveData<CameraModel> mCameraModel = new MutableLiveData<>();
    private Executor cameraExecutor = ContextCompat.getMainExecutor(getApplication());

    public CameraXViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<CameraModel> processCameraProvider(PreviewView previewView) {
        ListenableFuture<ProcessCameraProvider> cameraProviderFuture = ProcessCameraProvider.getInstance(getApplication());
        cameraProviderFuture.addListener(() -> {
            try {
                Preview preview = new Preview.Builder().build();

                CameraSelector cameraSelector = new CameraSelector.Builder()
                        .requireLensFacing(CameraSelector.LENS_FACING_BACK).build();

                preview.setSurfaceProvider(previewView.getSurfaceProvider());


                ImageAnalysis imageAnalysis = new ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

                imageAnalysis.setAnalyzer(cameraExecutor, new QrAnalyzer(new QrListener() {
                    @Override
                    public void QrOnSuccess(String result) {
                        Log.wtf("qrResult", result);
                    }

                    @Override
                    public void QrOnFailure() {
                        Log.wtf("qrResult", "fail");
                    }
                }));

                mCameraModel.setValue(new CameraModel(cameraProviderFuture.get(), cameraSelector, preview, imageAnalysis));
            } catch (Exception e) {
                Log.wtf("camera", "error");
            }
        }, cameraExecutor);
        return mCameraModel;
    }


    public Executor getCameraExecutor() {
        return cameraExecutor;
    }
}
