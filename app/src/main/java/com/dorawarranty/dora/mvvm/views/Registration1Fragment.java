package com.dorawarranty.dora.mvvm.views;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.R;
import com.dorawarranty.dora.databinding.Registration1Binding;


public class Registration1Fragment extends Fragment {
    Registration1Binding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        binding = Registration1Binding.inflate(inflater, container, false);

        View v = binding.getRoot();

        return v;
    }

//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, new Registration2Fragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
