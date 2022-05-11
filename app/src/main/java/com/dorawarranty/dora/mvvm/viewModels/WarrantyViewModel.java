package com.dorawarranty.dora.mvvm.viewModels;

import android.app.Application;
import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.Event;
import com.dorawarranty.dora.adapters.WarrantyUnitAdapter;
import com.dorawarranty.dora.mvvm.models.ServiceCenter;
import com.dorawarranty.dora.mvvm.models.WarrantyClaim;
import com.dorawarranty.dora.mvvm.models.WarrantyUnit;
import com.dorawarranty.dora.mvvm.repository.WarrantyRepository;

import java.util.ArrayList;

public class WarrantyViewModel extends AndroidViewModel {

    private WarrantyRepository mRepository;

    private MutableLiveData<WarrantyUnit> selectedUnit = new MutableLiveData<>();
    private MutableLiveData<ServiceCenter> selectedServiceCenter = new MutableLiveData<>();
    private MutableLiveData<String> problem = new MutableLiveData<>("");


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

    public MutableLiveData<WarrantyUnit> getSelectedUnit() {
        return selectedUnit;
    }

    public void setSelectedUnit(WarrantyUnit unit) {
        selectedUnit.setValue(unit);
    }

    public MutableLiveData<ArrayList<ServiceCenter>> getServiceCenters(int manufacturerId) {
        return mRepository.getServiceCenters(manufacturerId);
    }

    public MutableLiveData<ServiceCenter> getSelectedServiceCenter() {
        return selectedServiceCenter;
    }

    public void setSelectedServiceCenter(ServiceCenter serviceCenter) {
        selectedServiceCenter.setValue(serviceCenter);
    }

    public MutableLiveData<String> getProblem() {
        return problem;
    }

    public void setProblem(String problem) {
        this.problem.setValue(problem);
    }

    public MutableLiveData<Event<String>> createClaim(int unitId, int serviceCenterId, String problem) {
        return mRepository.createClaim(unitId, serviceCenterId, problem);
    }
}
