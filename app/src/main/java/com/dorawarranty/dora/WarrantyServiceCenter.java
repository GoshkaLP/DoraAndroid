package com.dorawarranty.dora;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class WarrantyServiceCenter extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private UiSettings mUiSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.warranty_service_center);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mUiSettings = mMap.getUiSettings();
        mUiSettings.setZoomControlsEnabled(true);

        // Add a marker in Sydney and move the camera
        LatLng mirea = new LatLng(55.67005115775762, 37.48033411020338);
        LatLng point1 = new LatLng(55.6702216698207, 37.48045815107625);
        LatLng point2 = new LatLng(55.66960519962412, 37.480334110196665);
        LatLng point3 = new LatLng(55.670221669818126, 37.47941930869233);
        mMap.addMarker(new MarkerOptions()
                .position(mirea)
                .title("RTU MIREA"));
        mMap.addMarker(new MarkerOptions()
                .position(point1)
                .title("RTU MIREA"));
        mMap.addMarker(new MarkerOptions()
                .position(point2)
                .title("RTU MIREA"));
        mMap.addMarker(new MarkerOptions()
                .position(point3)
                .title("RTU MIREA"));
        googleMap.setMinZoomPreference(15.0f);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mirea));


//        ListView listView = findViewById(R.id.serviceCenterList);

        final String[] catNames = new String[] {
                "Рыжик", "Барсик", "Мурзик", "Мурка", "Васька",
                "Томасина", "Кристина", "Пушок", "Дымка", "Кузя",
                "Китти", "Масяня", "Симба"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, catNames);

//        listView.setAdapter(adapter);
    }
}