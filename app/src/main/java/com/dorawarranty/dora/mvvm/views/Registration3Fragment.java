package com.dorawarranty.dora.mvvm.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.ContextThemeWrapper;
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
import com.dorawarranty.dora.mvvm.viewModels.UsersViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class Registration3Fragment extends Fragment {

    Registration3Binding binding;

    private UsersViewModel mViewModel;
    private String WARRANTY_TAG = "warrantyList";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Registration3Binding.inflate(inflater, container, false);

        View v = binding.getRoot();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.registerButton3.setEnabled(false);

        mViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);

//        mViewModel.getPassword().observe(getViewLifecycleOwner(), password -> Log.wtf("password", password));

        mViewModel.getPasswordRegisterRepeat().observe(getViewLifecycleOwner(), password -> {
            if (!password.equals(mViewModel.getPasswordRegister().getValue()) || password.isEmpty()
            || mViewModel.getPasswordRegister().getValue().isEmpty()) {
                binding.passwordRegisterRepeatLayout.setError(getString(R.string.changePasswordError));
                binding.registerButton3.setEnabled(false);
            } else {
                binding.passwordRegisterRepeatLayout.setError(null);
                binding.registerButton3.setEnabled(true);
            }
        });

//        Log.wtf("email2", mViewModel.getEmail().getValue());

        binding.passwordRegister.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mViewModel.setPasswordRegister(editable.toString());
            }
        });

        binding.passwordRegisterRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mViewModel.setPasswordRegisterRepeat(editable.toString());
            }
        });

        binding.registerButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mViewModel.getEmail().getValue();
                String password = mViewModel.getPasswordRegister().getValue();
                mViewModel.register(email, password);
                mViewModel.checkToken().observe(getViewLifecycleOwner(), result -> {
                    WarrantiesListFragment warrantiesFragment = (WarrantiesListFragment) getParentFragmentManager().findFragmentByTag(WARRANTY_TAG);
                    if (warrantiesFragment == null) {
                        warrantiesFragment = new WarrantiesListFragment();
                    }
                    getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, warrantiesFragment)
                            .addToBackStack(null)
                            .commit();
                });
            }
        });

    }
}
