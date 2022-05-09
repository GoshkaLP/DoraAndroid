package com.dorawarranty.dora;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.dorawarranty.dora.mvvm.views.QrScanFragment;
import com.dorawarranty.dora.mvvm.views.WarrantiesListFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BarsHelper {
    public BarsHelper() {}

    public static void createBarsListeners(Fragment fragment, BottomAppBar bottomBar, FloatingActionButton fab) {
        bottomBar.setOnMenuItemClickListener(new androidx.appcompat.widget.Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.userProfileBut:
                        Log.wtf("bottom", "profile clicked");
                        break;
                }
                return true;
            }
        });

        bottomBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.wtf("bottom", "warranty clicked");
                fragment.getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, new WarrantiesListFragment())
                        .addToBackStack(null)
                        .commit();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.wtf("bottom", "fab clicked");
                fragment.getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, new QrScanFragment())
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
