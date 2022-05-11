package com.dorawarranty.dora.mvvm.views;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dorawarranty.dora.BarsHelper;
import com.dorawarranty.dora.R;
import com.dorawarranty.dora.adapters.WarrantyUnitAdapter;
import com.dorawarranty.dora.adapters.listeners.UnitOnClickListener;
import com.dorawarranty.dora.databinding.WarrantiesListBinding;
import com.dorawarranty.dora.mvvm.models.WarrantyUnit;
import com.dorawarranty.dora.mvvm.viewModels.UsersViewModel;
import com.dorawarranty.dora.mvvm.viewModels.WarrantyViewModel;
import com.google.android.material.card.MaterialCardView;

public class WarrantiesListFragment extends Fragment {
    WarrantiesListBinding binding;
    private WarrantyViewModel mViewModel;
    private WarrantyUnitAdapter mAdapter;
//    private UnitOnClickListener callback;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WarrantiesListBinding.inflate(inflater, container, false);

        View v = binding.getRoot();

        binding.warrantyListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));

        BarsHelper.createBarsListeners(this, binding.bottomBar.bottomAppBar, binding.fab.qrScan);

        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_DOWN) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        return true;
                    }
                }
                return false;
            }
        });

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(WarrantyViewModel.class);

        mAdapter = new WarrantyUnitAdapter(this, new UnitOnClickListener() {
            @Override
            public void onClick(WarrantyUnit unit) {
                if (unit != null) {
                    mViewModel.setSelectedUnit(unit);
                    getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, new WarrantyDetailFragment())
                        .addToBackStack(null)
                        .commit();
                }
            }
        });
        binding.warrantyListRecycler.setAdapter(mAdapter);

        mViewModel.getUnits().observe(getViewLifecycleOwner(), result -> {
//            for (WarrantyUnit unit : result) {
//                mViewModel.getUnitPhoto(unit.getId()).observe(getViewLifecycleOwner(), photoResult -> {
//                    Bitmap photo = photoResult.getContentIfNotHandled();
//                    if (photo != null) {
//                        unit.setPhotoBmp(photo);
//                    }
//                });
//            }
            mAdapter.initData(result);
        });

        mViewModel.getNewUnit().observe(getViewLifecycleOwner(), result -> {
            WarrantyUnit unit = result.getContentIfNotHandled();
            if (unit != null) {
                mAdapter.addData(unit);
            }
        });
    }
}
