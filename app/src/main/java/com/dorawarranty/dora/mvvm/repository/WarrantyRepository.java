package com.dorawarranty.dora.mvvm.repository;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.dorawarranty.dora.DI.ServiceLocator;
import com.dorawarranty.dora.Event;
import com.dorawarranty.dora.adapters.WarrantyUnitAdapter;
import com.dorawarranty.dora.database.WarrantyDatabase;
import com.dorawarranty.dora.database.dao.WarrantyDao;
import com.dorawarranty.dora.mvvm.models.ServiceCenter;
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
    private MutableLiveData<Event<Bitmap>> mWarrantyUnitPhoto = new MutableLiveData<>();
    private MutableLiveData<Event<WarrantyClaim>> mWarrantyClaim = new MutableLiveData<>();
    private Event<String> mCameraAddUnit;
    private MutableLiveData<Event<WarrantyUnit>> mCustomerAddUnit = new MutableLiveData<>();
    private MutableLiveData<ArrayList<ServiceCenter>> mServiceCenters = new MutableLiveData<>();
    private MutableLiveData<Event<String>> createClaimResult = new MutableLiveData<>();

    private boolean canProcess = true;


    private WarrantyDao mWarrantyDao;

    public WarrantyRepository(Context context) {
        this.context = context;
        mServiceLocator = ServiceLocator.getInstance();

        WarrantyDatabase db = WarrantyDatabase.getDatabase(context);
        mWarrantyDao = db.warrantyDao();
    }

    public MutableLiveData<ArrayList<WarrantyUnit>> getUnits() {
        if (mWarrantyDao.linesCount() > 0 && !mServiceLocator.getNetworkLogic().isNetworkAvailable(context)) {
            Log.d("Loading units", "all units from DB");
            ArrayList<WarrantyUnit> warrantyUnits = new ArrayList<WarrantyUnit>(mWarrantyDao.getAll());
            mWarrantyUnits.setValue(warrantyUnits);
        } else {
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
                                (int) ((Double) unit_req.get("manufacturerId")).doubleValue(),
                                (String) unit_req.get("model"),
                                (String) unit_req.get("modelType")
                        ));
                    }
                    mWarrantyUnits.setValue(res);
                    try {
                        if (mWarrantyDao.linesCount() == 0) {
                            WarrantyUnit[] warrantyUnitDb = res.toArray(new WarrantyUnit[res.size()]);
                            mWarrantyDao.insertAll(warrantyUnitDb);
                        }
                    } catch (Exception e) {
                        Log.e("error db", "add all warranties", e);
                    }
                }
                Log.d("Loading units", "all units from API");
            });
        }
        return mWarrantyUnits;
    }

    public MutableLiveData<Event<WarrantyUnit>> getUnit(int unitId) {
        if (mWarrantyDao.checkWarrantyInfo(unitId) != 0 && !mServiceLocator.getNetworkLogic().isNetworkAvailable(context)) {
            WarrantyUnit warrantyUnit = mWarrantyDao.getWarrantyUnit(unitId);
            mWarrantyUnit.setValue(new Event<>(warrantyUnit));
            Log.d("Loading unit", "info from DB");
        } else {
            mServiceLocator.getNetworkLogic().getUnit(unitId, result -> {
                String message = result.getMessage();
                if (message.equals("SUCCESS")) {
                    Map<String, Object> unit = result.getData();
                    String manufacturerName = (String) unit.get("manufacturer");
                    int manufacturerId = (int) ((Double) unit.get("manufacturerId")).doubleValue();
                    String modelName = (String) unit.get("model");
                    String modelType = (String) unit.get("modelType");
                    String serialNumber = (String) unit.get("serialNumber");
                    String warrantyEndDate = (String) unit.get("warrantyEndDate");
                    boolean isClaimable = (boolean) unit.get("claimable");
                    String photoUrl = (String) unit.get("photo");
                    mWarrantyUnit.setValue(new Event<>(
                            new WarrantyUnit(
                                    unitId, manufacturerName, manufacturerId, modelName, modelType, serialNumber,
                                    warrantyEndDate, isClaimable, photoUrl
                            )));
                    try {
                        mWarrantyDao.updateWarrantyInfo(unitId, manufacturerName, manufacturerId, modelName,
                                modelType, serialNumber, warrantyEndDate, isClaimable, photoUrl);
                    } catch (Exception e) {
                        Log.e("error db", "add warranty info", e);
                    }
                }
                Log.d("Loading unit", "info from API");
            });
        }
        return mWarrantyUnit;
    }

    public MutableLiveData<Event<Bitmap>> getUnitPhoto(int unitId) {
        if (mWarrantyDao.checkWarrantyImage(unitId) != 0 && !mServiceLocator.getNetworkLogic().isNetworkAvailable(context)) {
            Bitmap bmp = mWarrantyDao.getWarrantyImage(unitId);
            mWarrantyUnitPhoto.setValue(new Event<>(bmp));
            Log.d("Loading unit photo", "photo from DB");
        } else {
            mServiceLocator.getNetworkLogic().getUnitPhoto(unitId, result -> {
                Bitmap bmp = BitmapFactory.decodeStream(result.byteStream());
                mWarrantyUnitPhoto.setValue(new Event<>(bmp));
                try {
                    mWarrantyDao.updatePhoto(unitId, bmp);
                } catch (Exception e) {
                    Log.e("error db", "add photo", e);
                }
            });
            Log.d("Loading unit photo", "photo from API");
        }
        return mWarrantyUnitPhoto;
    }


    public MutableLiveData<Event<WarrantyClaim>> getClaimStatus(int unitId) {
        if (!mServiceLocator.getNetworkLogic().isNetworkAvailable(context)) {
           mWarrantyClaim.setValue(new Event<>(new WarrantyClaim("no")));
        } else {
            mServiceLocator.getNetworkLogic().getClaimStatus(unitId, result -> {
                String message = result.getMessage();
                if (message.equals("SUCCESS")) {
                    String status = (String) result.getData().get("status");
                    mWarrantyClaim.setValue(new Event<>(new WarrantyClaim(status)));
                } else if (message.equals("WARRANTY_CLAIM_NOT_EXISTS")) {
                    mWarrantyClaim.setValue(new Event<>(new WarrantyClaim("no")));
                }
            });
        }
        return mWarrantyClaim;
    }


    public Event<String> scanQrUnit(String code) {
        if (canProcess) {
            canProcess = false;
            mServiceLocator.getNetworkLogic().scanQrUnit(code, result -> {
                String message = result.getMessage();
                if (message.equals("WRONG_QR_CODE")) {
                    mCameraAddUnit = new Event<>("Неверный QR-код!");
                } else if (message.equals("UNIT_NOT_EXISTS")) {
                    mCameraAddUnit = new Event<>("Данного товара не существует!");
                } else if (message.equals("UNIT_ALREADY_ASSIGNED")) {
                    mCameraAddUnit = new Event<>("Данный товар уже привязан!");
                } else if (message.equals("SUCCESS")) {
                    mCameraAddUnit = new Event<>("ok");
                    Map<String, Object> unit = (Map<String, Object>) result.getData();
                    WarrantyUnit newUnit = new WarrantyUnit(
                            (int) ((Double) unit.get("id")).doubleValue(),
                            (String) unit.get("manufacturer"),
                            (int) ((Double) unit.get("manufacturerId")).doubleValue(),
                            (String) unit.get("model"),
                            (String) unit.get("modelType")
                    );
                    mCustomerAddUnit.setValue(new Event<>(newUnit));
                    try {
                        mWarrantyDao.insertWarrantyUnit(newUnit);
                    } catch (Exception e) {
                        Log.e("error db", "add new unit to db");
                    }
                }
            });
        }

        return mCameraAddUnit;
    }

    public void setCanProcess() {
        canProcess = true;
    }

    public MutableLiveData<Event<WarrantyUnit>> getNewUnit() {
        return mCustomerAddUnit;
    }

    public MutableLiveData<ArrayList<ServiceCenter>> getServiceCenters(int manufacturerId) {
        mServiceLocator.getNetworkLogic().getServiceCenters(manufacturerId, result -> {
            String message = result.getMessage();
            if (message.equals("SUCCESS")) {
                ArrayList<ServiceCenter> res = new ArrayList<>();
                List data = result.getData();
                for (Object val : data) {
                    Map<String, Object> serviceCenter = (Map<String, Object>) val;
                    res.add(new ServiceCenter(
                            (int) ((Double) serviceCenter.get("id")).doubleValue(),
                            (String) serviceCenter.get("name"),
                            (String) serviceCenter.get("address"),
                            Double.parseDouble((String) serviceCenter.get("latitude")),
                            Double.parseDouble((String) serviceCenter.get("longitude"))
                    ));

                }
                mServiceCenters.setValue(res);
            }
        });
        return mServiceCenters;
    }

    public MutableLiveData<Event<String>> createClaim(int unitId, int serviceCenterId, String problem) {
        mServiceLocator.getNetworkLogic().createClaim(unitId, serviceCenterId, problem, result -> {
            String message = result.getMessage();
            if (message.equals("WARRANTY_PERIOD_EXPIRED")) {
                createClaimResult.setValue(new Event<>("Гарантийный период данного товара истек!"));
            } else if (message.equals("SUCCESS")) {
                createClaimResult.setValue(new Event<>("ok"));
            }
        });
        return createClaimResult;
    }
}
