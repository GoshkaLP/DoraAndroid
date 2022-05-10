package com.dorawarranty.dora.adapters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dorawarranty.dora.R;
import com.dorawarranty.dora.adapters.viewHolders.WarrantyUnitHolder;
import com.dorawarranty.dora.databinding.CardBinding;
import com.dorawarranty.dora.mvvm.models.WarrantyUnit;
import com.dorawarranty.dora.mvvm.views.Registration1Fragment;
import com.dorawarranty.dora.mvvm.views.WarrantiesListFragment;
import com.dorawarranty.dora.mvvm.views.WarrantyDetailFragment;

import java.util.ArrayList;
import java.util.List;

public class WarrantyUnitAdapter extends RecyclerView.Adapter<WarrantyUnitHolder> {

    private WarrantiesListFragment mFragment;
    private ArrayList<WarrantyUnit> mUnits = new ArrayList<>();

    public WarrantyUnitAdapter(WarrantiesListFragment fragment) {
        mFragment = fragment;
    }

    public void initData(ArrayList<WarrantyUnit> units) {
        mUnits = units;
        notifyDataSetChanged();
    }

    public void addData(WarrantyUnit unit) {
        mUnits.add(0, unit);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WarrantyUnitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardBinding binding = CardBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new WarrantyUnitHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull WarrantyUnitHolder holder, int position) {
        WarrantyUnit unit = mUnits.get(position);
        holder.getCardName().setText(unit.getManufacturerName() + ' ' + unit.getModelName());
        holder.getCardType().setText(unit.getModelType());
//        holder.getCardPhoto().setImageBitmap(unit.getPhotoBmp());
        holder.getCardLayout().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("unitId", unit.getId());
                WarrantyDetailFragment newFragment = new WarrantyDetailFragment();
                newFragment.setArguments(bundle);
                mFragment.getParentFragmentManager().beginTransaction().replace(R.id.main_fragment, newFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mUnits.size();
    }

}
