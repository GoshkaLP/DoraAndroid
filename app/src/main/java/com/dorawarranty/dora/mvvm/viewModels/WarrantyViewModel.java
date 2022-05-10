package com.dorawarranty.dora.mvvm.viewModels;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.Event;
import com.dorawarranty.dora.adapters.WarrantyUnitAdapter;
import com.dorawarranty.dora.mvvm.models.WarrantyClaim;
import com.dorawarranty.dora.mvvm.models.WarrantyUnit;
import com.dorawarranty.dora.mvvm.repository.WarrantyRepository;

import java.util.ArrayList;

public class WarrantyViewModel extends AndroidViewModel {

    private WarrantyRepository mRepository;


    public WarrantyViewModel(@NonNull Application application) {
        super(application);

        mRepository = ServiceLocator.getInstance().getWarrantyRepository();
    }

    public MutableLiveData<ArrayList<WarrantyUnit>> getUnits() {
        return mRepository.getUnits();
    }

    public MutableLiveData<Event<WarrantyUnit>> getUnit(int unitId) {
        return mRepository.getUnit(unitId);
    }

    public MutableLiveData<Event<Bitmap>> getUnitPhoto(int unitId) {
        return mRepository.getUnitPhoto(unitId);
    }

    public MutableLiveData<Event<WarrantyClaim>> getClaimStatus(int unitId) {
        return mRepository.getClaimStatus(unitId);
    }

    public MutableLiveData<Event<WarrantyUnit>> getNewUnit() {
        return mRepository.getNewUnit();
    }

}
