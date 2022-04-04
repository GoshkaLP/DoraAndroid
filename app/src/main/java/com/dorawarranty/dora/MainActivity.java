package com.dorawarranty.dora;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.dorawarranty.dora.BarsHelper;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomAppBar bottomBar = findViewById(R.id.bottomAppBar);
        FloatingActionButton fab = findViewById(R.id.qrScan);
        Toolbar upBar = findViewById(R.id.topAppBar);
        BarsHelper.createBarsListeners(upBar, bottomBar, fab);
    }
}