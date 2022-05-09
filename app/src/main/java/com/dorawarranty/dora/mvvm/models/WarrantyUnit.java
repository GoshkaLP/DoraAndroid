package com.dorawarranty.dora.mvvm.models;

public class WarrantyUnit {

    private int id;
    private String manufacturerName;
    private int manufacturerId;
    private String modelName;
    private String modelType;
    private String serialNumber;
    private String warrantyEndDate;
    private boolean isClaimable;
    private String photoUrl;

    public WarrantyUnit(int id, String manufacturerName, String modelName,
                        String modelType) {
        this.id = id;
        this.manufacturerName = manufacturerName;
        this.modelName = modelName;
        this.modelType = modelType;
    }

    public WarrantyUnit(int id, String manufacturerName, int manufacturerId, String modelName,
                        String modelType, String serialNumber, String warrantyEndDate,
                        boolean isClaimable, String photoUrl) {
        this.id = id;
        this.manufacturerName = manufacturerName;
        this.manufacturerId = manufacturerId;
        this.modelName = modelName;
        this.modelType = modelType;
        this.serialNumber = serialNumber;
        this.warrantyEndDate = warrantyEndDate;
        this.isClaimable = isClaimable;
        this.photoUrl = photoUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getManufacturerName() {
        return manufacturerName;
    }

    public void setManufacturerName(String manufacturerName) {
        this.manufacturerName = manufacturerName;
    }

    public int getManufacturerId() {
        return manufacturerId;
    }

    public void setManufacturerId(int manufacturerId) {
        this.manufacturerId = manufacturerId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelType() {
        return modelType;
    }

    public void setModelType(String modelType) {
        this.modelType = modelType;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getWarrantyEndDate() {
        return warrantyEndDate;
    }

    public void setWarrantyEndDate(String warrantyEndDate) {
        this.warrantyEndDate = warrantyEndDate;
    }

    public boolean isClaimable() {
        return isClaimable;
    }

    public void setClaimable(boolean claimable) {
        isClaimable = claimable;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }
}
