package com.dorawarranty.dora.adapters.viewHolders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dorawarranty.dora.databinding.CardBinding;

public class WarrantyUnitHolder extends RecyclerView.ViewHolder {

    TextView cardName;
    TextView cardType;
    ImageView cardPhoto;
    View cardLayout;

    public WarrantyUnitHolder(CardBinding binding) {
        super(binding.getRoot());
        cardName = binding.cardName;
        cardType = binding.cardType;
        cardPhoto = binding.cardPhoto;
        cardLayout = binding.cardView;
    }

    public TextView getCardName() {
        return cardName;
    }

    public TextView getCardType() {
        return cardType;
    }

    public ImageView getCardPhoto() {
        return cardPhoto;
    }

    public View getCardLayout() {
        return cardLayout;
    }
}
