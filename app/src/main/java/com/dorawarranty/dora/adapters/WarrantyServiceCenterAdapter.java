package com.dorawarranty.dora.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dorawarranty.dora.adapters.listeners.ServiceCenterOnClickListener;
import com.dorawarranty.dora.adapters.viewHolders.WarrantyServiceCenterHolder;
import com.dorawarranty.dora.databinding.ServiceCenterBinding;
import com.dorawarranty.dora.mvvm.models.ServiceCenter;
import com.dorawarranty.dora.mvvm.models.WarrantyUnit;
import com.dorawarranty.dora.mvvm.views.WarrantyServiceCenterFragment;

import java.util.ArrayList;

public class WarrantyServiceCenterAdapter extends RecyclerView.Adapter<WarrantyServiceCenterHolder> {
    private WarrantyServiceCenterFragment mFragment;
    private ArrayList<ServiceCenter> mServiceCenters = new ArrayList<>();
    private ServiceCenterOnClickListener mCallback;

    public WarrantyServiceCenterAdapter(WarrantyServiceCenterFragment fragment) {
        mFragment = fragment;
    }

    public void initData(ArrayList<ServiceCenter> serviceCenters) {
        mServiceCenters = serviceCenters;
        notifyDataSetChanged();
    }

    public void setCallback(ServiceCenterOnClickListener callback) {
        mCallback = callback;
    }


    @NonNull
    @Override
    public WarrantyServiceCenterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ServiceCenterBinding binding = ServiceCenterBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WarrantyServiceCenterHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WarrantyServiceCenterHolder holder, int position) {
        ServiceCenter serviceCenter = mServiceCenters.get(position);
        String header = serviceCenter.getName() + ": " + serviceCenter.getAddress();
        holder.getServiceCenterHeader().setText(header);
        holder.getServiceCenterLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCallback != null) {
                    mCallback.onClick(serviceCenter);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mServiceCenters.size();
    }
}
