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
import androidx.fragment.app.FragmentManager;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.mvvm.views.Registration1Fragment;
import com.dorawarranty.dora.mvvm.views.SplashFragment;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ServiceLocator.init(this.getApplicationContext());

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_fragment, new SplashFragment())
                .addToBackStack(null)
                .commit();
    }
}
