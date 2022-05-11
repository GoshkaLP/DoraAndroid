package com.dorawarranty.dora.mvvm.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dorawarranty.dora.BarsHelper;
import com.dorawarranty.dora.R;
import com.dorawarranty.dora.adapters.WarrantyServiceCenterAdapter;
import com.dorawarranty.dora.adapters.listeners.ServiceCenterOnClickListener;
import com.dorawarranty.dora.databinding.WarrantyServiceCenterBinding;
import com.dorawarranty.dora.mvvm.models.ServiceCenter;
import com.dorawarranty.dora.mvvm.models.WarrantyUnit;
import com.dorawarranty.dora.mvvm.viewModels.WarrantyViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class WarrantyServiceCenterFragment extends Fragment implements OnMapReadyCallback {
    WarrantyServiceCenterBinding binding;
    private WarrantyViewModel mViewModel;
    private WarrantyServiceCenterAdapter mAdapter;
    private WarrantyUnit mUnit;
    private ArrayList<ServiceCenter> mServiceCenters;

    private GoogleMap mMap;
    private UiSettings mUiSettings;
    private ArrayList<Marker> markers = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WarrantyServiceCenterBinding.inflate(inflater, container, false);

        binding.serviceCenterRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        BarsHelper.createBarsListeners(this, binding.bottomBar.bottomAppBar, binding.fab.qrScan);

        View v = binding.getRoot();


        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(WarrantyViewModel.class);
        mUnit = mViewModel.getSelectedUnit().getValue();

        binding.serviceCenterHeader.setText("Гарантийные центры " + mUnit.getManufacturerName());

        mAdapter = new WarrantyServiceCenterAdapter(this);

        binding.serviceCenterRecycler.setAdapter(mAdapter);

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);



        mViewModel.getServiceCenters(mUnit.getManufacturerId()).observe(getViewLifecycleOwner(), serviceCenters -> {
            mAdapter.initData(serviceCenters);

            for (ServiceCenter serviceCenter : serviceCenters) {
//                Log.wtf("coords", serviceCenter.getLatitude() + " " + serviceCenter.getLongitude());
                LatLng coords = new LatLng(serviceCenter.getLatitude(), serviceCenter.getLongitude());
                Marker marker = mMap.addMarker(new MarkerOptions()
                    .position(coords)
                    .title(serviceCenter.getName()));
                marker.setTag(serviceCenter.getServiceCenterId());
                markers.add(marker);
                mMap.moveCamera(CameraUpdateFactory.newLatLng(coords));
            }

        });
        googleMap.setMinZoomPreference(10.0f);


        mAdapter.setCallback(new ServiceCenterOnClickListener() {
            @Override
            public void onClick(ServiceCenter serviceCenter) {
                for (Marker marker : markers) {
                    if ((int) marker.getTag() == serviceCenter.getServiceCenterId()) {
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(
                                serviceCenter.getLatitude(), serviceCenter.getLongitude()
                        )));
                        mViewModel.setSelectedServiceCenter(serviceCenter);
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Записаться на гарантийное обслуживание?")
                                .setTitle("Гарантийное обслуживание");

                        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, new WarrantyClaimFragment())
                                        .addToBackStack(null)
                                        .commit();
                            }
                        });

                        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        break;
                    }
                }
            }
        });

    }
}
