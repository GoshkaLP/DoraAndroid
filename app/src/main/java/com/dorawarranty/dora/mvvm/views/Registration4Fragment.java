package com.dorawarranty.dora.mvvm.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dorawarranty.dora.databinding.Registration2Binding;
import com.dorawarranty.dora.databinding.Registration3Binding;
import com.dorawarranty.dora.databinding.Registration4Binding;

public class Registration4Fragment extends Fragment {

    Registration4Binding binding;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Registration4Binding.inflate(inflater, container, false);

        View v = binding.getRoot();

        return v;
    }
}
