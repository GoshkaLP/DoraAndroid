package com.dorawarranty.dora;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dorawarranty.dora.BarsHelper;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, WarrantyServiceCenter.class);
        startActivity(intent);

//        BottomAppBar bottomBar = findViewById(R.id.bottomAppBar);
//        FloatingActionButton fab = findViewById(R.id.qrScan);
//        Toolbar upBar = findViewById(R.id.topAppBar);
//        BarsHelper.createBarsListeners(upBar, bottomBar, fab);


//        LinearLayout scrollElements = findViewById(R.id.scrollElements);
//        setMargins(scrollElements, 0, 10, 0, 100);

//        LayoutInflater inflater = getLayoutInflater();
//        for (int i = 0; i < 10; i++) {
//            TextView text = new TextView(this);
//            text.setText("Текст текст текст текст");
//            text.setTextAppearance(com.google.android.material.R.style.TextAppearance_MaterialComponents_Headline6);
//            scrollElements.addView(text);
//            setMargins(text, 20, 10, 0, 0);
//            View card = inflater.inflate(R.layout.card, null, false);
//            scrollElements.addView(card);
//            setMargins(card, 8, 0, 8, 10);
//        }
    }

    private int convertToDp(int sz) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, sz, getResources()
                        .getDisplayMetrics());
    }

    private void setMargins (View view, int left, int top, int right, int bottom) {
        ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        p.setMargins(convertToDp(left), convertToDp(top), convertToDp(right), convertToDp(bottom));
        view.requestLayout();
    }
}