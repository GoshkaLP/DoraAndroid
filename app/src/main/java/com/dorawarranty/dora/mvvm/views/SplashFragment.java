package com.dorawarranty.dora.mvvm.views;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dorawarranty.dora.R;
import com.dorawarranty.dora.databinding.SplashBinding;
import com.dorawarranty.dora.mvvm.viewModels.UsersViewModel;

public class SplashFragment extends Fragment {
    SplashBinding binding;
    private UsersViewModel mViewModel;
    private String WARRANTY_TAG = "warrantyList";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = SplashBinding.inflate(inflater, container, false);

        View v = binding.getRoot();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(UsersViewModel.class);

        mViewModel.checkToken().observe(getViewLifecycleOwner(), result -> {
            if (result) {
                WarrantiesListFragment warrantiesFragment = (WarrantiesListFragment) getParentFragmentManager().findFragmentByTag(WARRANTY_TAG);
                if (warrantiesFragment == null) {
                    warrantiesFragment = new WarrantiesListFragment();
                }
                getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, warrantiesFragment, WARRANTY_TAG)
                        .addToBackStack(null)
                        .commit();
            } else {
                getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, new Registration1Fragment())
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

}
