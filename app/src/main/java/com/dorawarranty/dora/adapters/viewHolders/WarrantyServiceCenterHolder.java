package com.dorawarranty.dora.adapters.viewHolders;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dorawarranty.dora.databinding.ServiceCenterBinding;

public class WarrantyServiceCenterHolder extends RecyclerView.ViewHolder {

    TextView serviceCenterHeader;
    LinearLayout serviceCenterLayout;

    public WarrantyServiceCenterHolder(ServiceCenterBinding binding) {
        super(binding.getRoot());

        serviceCenterHeader = binding.serviceCenterHeader;
        serviceCenterLayout = binding.serviceCenterLayout;
    }

    public TextView getServiceCenterHeader() {
        return serviceCenterHeader;
    }

    public LinearLayout getServiceCenterLayout() {
        return serviceCenterLayout;
    }
}
