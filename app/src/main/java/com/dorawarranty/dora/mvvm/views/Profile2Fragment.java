package com.dorawarranty.dora.mvvm.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dorawarranty.dora.BarsHelper;
import com.dorawarranty.dora.R;
import com.dorawarranty.dora.databinding.Profile2Binding;
import com.dorawarranty.dora.mvvm.viewModels.UsersViewModel;

public class Profile2Fragment extends Fragment {
    Profile2Binding binding;
    private UsersViewModel mViewModel;

    private static String PROFILE_TAG = "userProfile";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Profile2Binding.inflate(inflater, container, false);
        BarsHelper.createBarsListeners(this, binding.bottomBar.bottomAppBar, binding.fab.qrScan);

        View v = binding.getRoot();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.sendChangePasswordButton.setEnabled(false);

        mViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);

        binding.changePasswordOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mViewModel.setChangePasswordOld(editable.toString());
            }
        });

        binding.changePasswordNew.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mViewModel.setChangePasswordNew(editable.toString());
            }
        });

        binding.changePasswordNewRepeat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mViewModel.setChangePasswordNewRepeat(editable.toString());
            }
        });


        mViewModel.getChangePasswordNewRepeat().observe(getViewLifecycleOwner(), password -> {
            if (!password.equals(mViewModel.getChangePasswordNew().getValue()) || password.isEmpty()
                    || mViewModel.getChangePasswordNew().getValue().isEmpty()) {
                binding.changePasswordNewRepeatLayout.setError(getString(R.string.changePasswordError));
                binding.sendChangePasswordButton.setEnabled(false);
            } else {
                binding.changePasswordNewRepeatLayout.setError(null);
                binding.sendChangePasswordButton.setEnabled(true);
            }
        });

        binding.sendChangePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = mViewModel.getChangePasswordOld().getValue();
                String newPassword = mViewModel.getChangePasswordNew().getValue();
                mViewModel.changePassword(oldPassword, newPassword).observe(getViewLifecycleOwner(), result -> {
                    String message = result.getContentIfNotHandled();
                    if (message != null) {
                        if (!message.equals("ok")) {
                            binding.changePasswordNewRepeatLayout.setError(message);
                        } else {
                            binding.changePasswordNewRepeatLayout.setError(null);
//                            Toast.makeText(getContext(), getResources().getString(R.string.changePasswordSuccess), Toast.LENGTH_SHORT).show();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                            builder.setMessage(getResources().getString(R.string.changePasswordSuccess))
                                    .setTitle("Смена пароля");

                            builder.setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Profile1Fragment profileFragment = (Profile1Fragment) getParentFragmentManager().findFragmentByTag(PROFILE_TAG);
                                    if (profileFragment == null) {
                                        profileFragment = new Profile1Fragment();
                                    }
                                    getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, profileFragment, PROFILE_TAG)
                                            .addToBackStack(null)
                                            .commit();
                                }
                            });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                        }
                    }
                });
            }
        });
    }
}
