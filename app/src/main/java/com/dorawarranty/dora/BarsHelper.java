package com.dorawarranty.dora;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.dorawarranty.dora.mvvm.views.Profile1Fragment;
import com.dorawarranty.dora.mvvm.views.QrScanFragment;
import com.dorawarranty.dora.mvvm.views.WarrantiesListFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BarsHelper {
    private static String WARRANTY_TAG = "warrantyList";
    private static String QR_TAG = "qrScanner";
    private static String PROFILE_TAG = "userProfile";

    public BarsHelper() {}

    public static void createBarsListeners(Fragment fragment, BottomAppBar bottomBar, FloatingActionButton fab) {

        bottomBar.setOnMenuItemClickListener(new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.userProfileBut:

                        Profile1Fragment profileFragment = (Profile1Fragment) fragment.getParentFragmentManager().findFragmentByTag(PROFILE_TAG);
                        if (profileFragment == null) {
                            profileFragment = new Profile1Fragment();
                        }
                        fragment.getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, profileFragment, PROFILE_TAG)
                                .addToBackStack(null)
                                .commit();


                        break;
                }
                return true;
            }
        });

        bottomBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.wtf("bottom", "warranty clicked");
                WarrantiesListFragment warrantiesFragment = (WarrantiesListFragment) fragment.getParentFragmentManager().findFragmentByTag(WARRANTY_TAG);
                if (warrantiesFragment == null) {
                    warrantiesFragment = new WarrantiesListFragment();
                }
                fragment.getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, warrantiesFragment, WARRANTY_TAG)
                        .addToBackStack(null)
                        .commit();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.wtf("bottom", "fab clicked");
                QrScanFragment qrScanFragment = (QrScanFragment) fragment.getParentFragmentManager().findFragmentByTag(QR_TAG);
                if (qrScanFragment == null) {
                    qrScanFragment = new QrScanFragment();
                }
                fragment.getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, qrScanFragment, QR_TAG)
                        .addToBackStack(null)
                        .commit();
            }
        });

//        upBar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.wtf("up", "back clicked");
//            }
//        });
    }
}
