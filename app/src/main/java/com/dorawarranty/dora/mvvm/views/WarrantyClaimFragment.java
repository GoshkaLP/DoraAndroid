package com.dorawarranty.dora.mvvm.views;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.dorawarranty.dora.BarsHelper;
import com.dorawarranty.dora.R;
import com.dorawarranty.dora.databinding.WarrantyClaimBinding;
import com.dorawarranty.dora.mvvm.viewModels.WarrantyViewModel;

public class WarrantyClaimFragment extends Fragment {
    WarrantyClaimBinding binding;
    private WarrantyViewModel mViewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = WarrantyClaimBinding.inflate(inflater, container, false);

        BarsHelper.createBarsListeners(this, binding.bottomBar.bottomAppBar, binding.fab.qrScan);

        View v = binding.getRoot();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(requireActivity()).get(WarrantyViewModel.class);

        binding.claimProblem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                mViewModel.setProblem(editable.toString());
            }
        });

        mViewModel.getProblem().observe(getViewLifecycleOwner(), problem -> {
            if (!problem.isEmpty()) {
                binding.sendClaim.setEnabled(true);

                binding.sendClaim.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int unitId = mViewModel.getSelectedUnit().getValue().getId();
                        int serviceCenterId = mViewModel.getSelectedServiceCenter().getValue().getServiceCenterId();
                        mViewModel.createClaim(unitId, serviceCenterId, problem).observe(getViewLifecycleOwner(), result -> {
                            String message = result.getContentIfNotHandled();
                            if (message != null) {
                                if (message.equals("ok")) {
                                    Toast.makeText(getContext(), "Ваша заявка была успешно создана!", Toast.LENGTH_SHORT).show();
                                    getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, new WarrantyDetailFragment())
                                            .addToBackStack(null)
                                            .commit();
                                } else {
                                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
            } else {
                binding.sendClaim.setEnabled(false);
            }
        });
    }
}
