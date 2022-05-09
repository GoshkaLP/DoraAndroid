package com.dorawarranty.dora.mvvm.views;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dorawarranty.dora.BarsHelper;
import com.dorawarranty.dora.R;
import com.dorawarranty.dora.databinding.WarrantyDetailBinding;
import com.dorawarranty.dora.mvvm.models.WarrantyClaim;
import com.dorawarranty.dora.mvvm.models.WarrantyUnit;
import com.dorawarranty.dora.mvvm.viewModels.WarrantyViewModel;

public class WarrantyDetailFragment extends Fragment {
    WarrantyDetailBinding binding;
    private WarrantyViewModel mViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WarrantyDetailBinding.inflate(inflater, container, false);

        BarsHelper.createBarsListeners(this, binding.bottomBar.bottomAppBar, binding.fab.qrScan);

        View v = binding.getRoot();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mViewModel = new ViewModelProvider(requireActivity()).get(WarrantyViewModel.class);

        Bundle arguments = getArguments();
        if (arguments != null && !arguments.isEmpty()) {
            int unitId = arguments.getInt("unitId");
            mViewModel.getUnit(unitId).observe(getViewLifecycleOwner(), result -> {
                WarrantyUnit unit = result.getContentIfNotHandled();
                if (unit != null) {
                    binding.productName.setText(
                            unit.getManufacturerName() + " " + unit.getModelName());
                    binding.productType.setText(
                            "Тип продукта: " + unit.getModelType());
                    binding.productManufacturer.setText(
                            "Производитель: " + unit.getManufacturerName());
                    binding.productWarrantyEnd.setText(
                            "Дата окончания гарантии: " + unit.getWarrantyEndDate());

                    binding.productButton.setEnabled(unit.isClaimable());

                    mViewModel.getClaimStatus(unitId).observe(getViewLifecycleOwner(), statusEvent -> {
                        WarrantyClaim claim = statusEvent.getContentIfNotHandled();
                        if (claim != null) {
                            if (claim.getStatus().equals("no")) {
                                binding.productButton.setText(R.string.showServiceCentres);
                                binding.productButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Log.wtf("service centres", "");
                                    }
                                });
                            } else {
                                binding.productButton.setText(R.string.showClaimStatus);
                                binding.productButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Log.wtf("claim status", "");
                                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                        builder.setMessage(claim.getStatus())
                                                .setTitle("Статус вашей заявки");

                                        builder.setPositiveButton("Понятно", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                            }
                                        });
                                        AlertDialog dialog = builder.create();
                                        dialog.show();
                                    }
                                });
                            }
                            binding.warrantyDetailLayout.setVisibility(View.VISIBLE);
                        }
                    });

                }
            });
        }
    }
}
