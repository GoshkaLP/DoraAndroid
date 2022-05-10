package com.dorawarranty.dora.mvvm.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dorawarranty.dora.R;
import com.dorawarranty.dora.databinding.Registration2Binding;
import com.dorawarranty.dora.databinding.Registration3Binding;
import com.dorawarranty.dora.databinding.Registration4Binding;
import com.dorawarranty.dora.mvvm.viewModels.UsersViewModel;

public class Registration4Fragment extends Fragment {

    Registration4Binding binding;
    private UsersViewModel mViewModel;
    private String WARRANTY_TAG = "warrantyList";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Registration4Binding.inflate(inflater, container, false);

        View v = binding.getRoot();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.registerButton4.setEnabled(false);
        mViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);


        binding.passwordAuth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mViewModel.setPasswordAuth(editable.toString());
            }
        });

        mViewModel.getPasswordAuth().observe(getViewLifecycleOwner(), password -> {
            binding.passwordAuthLayout.setError(null);
            if (!password.isEmpty()) {
                binding.registerButton4.setEnabled(true);
            } else {
                binding.registerButton4.setEnabled(false);
            }
        });


        binding.registerButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mViewModel.getEmail().getValue();
                String password = mViewModel.getPasswordAuth().getValue();
                Log.wtf(email, password);
                mViewModel.auth(email, password).observe(getViewLifecycleOwner(), result -> {
                    String message = result.getContentIfNotHandled();
                    if (message != null) {
                        if (message.equals("ok")) {
                            WarrantiesListFragment warrantiesFragment = (WarrantiesListFragment) getParentFragmentManager().findFragmentByTag(WARRANTY_TAG);
                            if (warrantiesFragment == null) {
                                warrantiesFragment = new WarrantiesListFragment();
                            }
                            getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, warrantiesFragment)
                                    .addToBackStack(null)
                                    .commit();
                        } else {
                            binding.passwordAuthLayout.setError(message);
                        }
                    }

                });
            }
        });

    }
}
