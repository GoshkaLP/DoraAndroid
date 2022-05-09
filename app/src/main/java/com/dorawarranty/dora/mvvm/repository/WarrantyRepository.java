package com.dorawarranty.dora.mvvm.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.Event;
import com.dorawarranty.dora.adapters.WarrantyUnitAdapter;
import com.dorawarranty.dora.mvvm.models.WarrantyClaim;
import com.dorawarranty.dora.mvvm.models.WarrantyUnit;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WarrantyRepository {
    private Context context;
    private ServiceLocator mServiceLocator;
    private MutableLiveData<ArrayList<WarrantyUnit>> mWarrantyUnits = new MutableLiveData<>();
    private MutableLiveData<Event<WarrantyUnit>> mWarrantyUnit = new MutableLiveData<>();
    private MutableLiveData<Event<WarrantyClaim>> mWarrantyClaim = new MutableLiveData<>();

    public WarrantyRepository(Context context) {
        this.context = context;
        mServiceLocator = ServiceLocator.getInstance();
    }

    public MutableLiveData<ArrayList<WarrantyUnit>> getUnits() {
        mServiceLocator.getNetworkLogic().getUnits(result -> {
                String message = result.getMessage();
                if (message.equals("SUCCESS")) {
                    ArrayList<WarrantyUnit> res = new ArrayList<>();
                    List data = result.getData();
                    for (Object val : data) {
                        Map<String, Object> unit_req = (Map<String, Object>) val;
                        res.add(new WarrantyUnit(
                                (int) ((Double) unit_req.get("id")).doubleValue(),
                                (String) unit_req.get("manufacturer"),
                                (String) unit_req.get("model"),
                                (String) unit_req.get("modelType")
                        ));

                    }
                    mWarrantyUnits.setValue(res);
                }
            });
        return mWarrantyUnits;
    }

    public MutableLiveData<Event<WarrantyUnit>> getUnit(int unitId) {
        mServiceLocator.getNetworkLogic().getUnit(unitId, result -> {
            String message = result.getMessage();
            if (message.equals("SUCCESS")) {
                Map<String, Object> unit = result.getData();
                mWarrantyUnit.setValue(new Event<>(
                        new WarrantyUnit(
                            (int) ((Double) unit.get("id")).doubleValue(),
                            (String) unit.get("manufacturer"),
                            (int) ((Double) unit.get("manufacturerId")).doubleValue(),
                            (String) unit.get("model"),
                            (String) unit.get("modelType"),
                            (String) unit.get("serialNumber"),
                            (String) unit.get("warrantyEndDate"),
                            (boolean) unit.get("claimable"),
                            (String) unit.get("photo")
                )));
            }
        });
        return mWarrantyUnit;
    }

    public MutableLiveData<Event<WarrantyClaim>> getClaimStatus(int unitId) {
        mServiceLocator.getNetworkLogic().getClaimStatus(unitId, result -> {
            String message = result.getMessage();
            if (message.equals("SUCCESS")) {
                String status = (String) result.getData().get("status");
                mWarrantyClaim.setValue(new Event<>(new WarrantyClaim(status)));
            } else if (message.equals("WARRANTY_CLAIM_NOT_EXISTS")) {
                mWarrantyClaim.setValue(new Event<>(new WarrantyClaim("no")));
            }
        });
        return mWarrantyClaim;
    }
}
