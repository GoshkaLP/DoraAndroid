package com.dorawarranty.dora.mvvm.views;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.util.Size;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraProvider;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dorawarranty.dora.R;
import com.dorawarranty.dora.mvvm.models.CameraModel;
import com.dorawarranty.dora.mvvm.viewModels.CameraXViewModel;
import com.dorawarranty.dora.mvvm.viewModels.UsersViewModel;
import com.dorawarranty.dora.qranalyzer.QrAnalyzer;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.mlkit.common.MlKitException;


import com.dorawarranty.dora.databinding.QrScanBinding;


import java.util.ArrayList;
import java.util.List;

public class QrScanFragment extends Fragment {
    QrScanBinding binding;
    private ActivityResultLauncher<String> mPermissionsResult;
    private CameraXViewModel mViewModel;

    private static String WARRANTY_TAG = "warrantyList";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = QrScanBinding.inflate(inflater, container, false);

        View v = binding.getRoot();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(CameraXViewModel.class);

        mPermissionsResult = registerForActivityResult(new ActivityResultContracts.RequestPermission(),
                new ActivityResultCallback<Boolean>() {
                    @Override
                    public void onActivityResult(Boolean result) {
                        if (result) {
                            Log.wtf("permission", "granted");
                            startCamera();
                        } else {
                            Log.wtf("permission", "not granted");
                        }
                    }
                });

        requestCamera();


    }

    private void requestCamera() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            startCamera();
        } else {
            mPermissionsResult.launch(Manifest.permission.CAMERA);
        }
    }

    private void startCamera() {
        mViewModel.processCameraProvider(binding.previewView).observe(getViewLifecycleOwner(), cameraModel -> {
            if (cameraModel != null) {
                ProcessCameraProvider cameraProvider = cameraModel.getCameraProvider();
                try {
                    cameraProvider.unbindAll();
                    cameraProvider.bindToLifecycle(getViewLifecycleOwner(),
                            cameraModel.getCameraSelector(), cameraModel.getPreview());
                    cameraProvider.bindToLifecycle(getViewLifecycleOwner(),
                            cameraModel.getCameraSelector(), cameraModel.getImageAnalysis());

                    mViewModel.scanQrUnit().observe(getViewLifecycleOwner(), result -> {
                        String message = result.getContentIfNotHandled();
                        if (message != null) {
                            if (!message.equals("ok")) {
                                Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "Товар успешно добавлен!", Toast.LENGTH_SHORT).show();
                                WarrantiesListFragment warrantiesFragment = (WarrantiesListFragment) getParentFragmentManager().findFragmentByTag(WARRANTY_TAG);
                                if (warrantiesFragment == null) {
                                    warrantiesFragment = new WarrantiesListFragment();
                                }
                                getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, warrantiesFragment, WARRANTY_TAG)
                                        .addToBackStack(null)
                                        .commit();
                            }
                        }
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mViewModel.setCanProcess();
                            }
                        }, 3000);

                    });

                } catch (Exception e) {}
            }
        });
    }

}
