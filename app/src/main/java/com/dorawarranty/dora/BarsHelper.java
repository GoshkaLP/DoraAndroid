package com.dorawarranty.dora;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class BarsHelper {
    public BarsHelper() {};

    public static void createBarsListeners(Toolbar upBar, BottomAppBar bottomBar, FloatingActionButton fab) {
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
                Log.wtf("bottom", "warranty clicked");
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf("bottom", "fab clicked");
            }
        });

        upBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.wtf("up", "back clicked");
            }
        });
    }
}
