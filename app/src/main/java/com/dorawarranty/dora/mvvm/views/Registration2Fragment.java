package com.dorawarranty.dora.mvvm.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dorawarranty.dora.R;
import com.dorawarranty.dora.databinding.Registration1Binding;
import com.dorawarranty.dora.databinding.Registration2Binding;
import com.dorawarranty.dora.mvvm.viewModels.UsersViewModel;

public class Registration2Fragment extends Fragment {

    Registration2Binding binding;
    private UsersViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Registration2Binding.inflate(inflater, container, false);

        View v = binding.getRoot();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);

        binding.registerButton2.setEnabled(false);

        mViewModel.getEmail().observe(getViewLifecycleOwner(), email -> {
            binding.emailLayout.setError(null);
            if (!email.isEmpty()) {
                binding.registerButton2.setEnabled(true);
            } else {
                binding.registerButton2.setEnabled(false);
            }
        });



        binding.email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mViewModel.setEmail(editable.toString());
            }
        });


        binding.registerButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mViewModel.checkEmail(mViewModel.getEmail().getValue()).observe(getViewLifecycleOwner(), result -> {
                    if (result.getStatus() == 0) {
                        mViewModel.resetCheckEmail();
                        getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, new Registration3Fragment())
                                .addToBackStack(null)
                                .commit();
                    } else if (result.getStatus() == 1) {
                        mViewModel.resetCheckEmail();
                        getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, new Registration4Fragment())
                                .addToBackStack(null)
                                .commit();
                    } else if (result.getStatus() == 2) {
                        mViewModel.resetCheckEmail();
                        binding.emailLayout.setError(result.getMessage());
                    }

                });
            }
        });
    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        mViewModel = new ViewModelProvider(this).get(UsersViewModel.class);
//
//        mViewModel.getEmail().observe(getViewLifecycleOwner(), email -> Log.wtf("email", email));
//    }
}
