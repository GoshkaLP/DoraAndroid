package com.dorawarranty.dora.mvvm.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dorawarranty.dora.BarsHelper;
import com.dorawarranty.dora.R;
import com.dorawarranty.dora.databinding.Profile1Binding;
import com.dorawarranty.dora.mvvm.viewModels.UsersViewModel;

public class Profile1Fragment extends Fragment {
    Profile1Binding binding;
    private UsersViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = Profile1Binding.inflate(inflater, container, false);
        BarsHelper.createBarsListeners(this, binding.bottomBar.bottomAppBar, binding.fab.qrScan);

        View v = binding.getRoot();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);

        binding.logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.logout().observe(getViewLifecycleOwner(), result -> {
                    Boolean isLogOuted = result.getContentIfNotHandled();
                    if (Boolean.TRUE.equals(isLogOuted)) {
                        getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, new Registration1Fragment())
                                .addToBackStack(null)
                                .commit();
                    }
                });
            }
        });

        binding.changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, new Profile2Fragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }
}
