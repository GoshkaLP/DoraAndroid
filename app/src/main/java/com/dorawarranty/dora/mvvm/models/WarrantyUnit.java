package com.dorawarranty.dora.mvvm.models;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.dorawarranty.dora.database.MapConverter;

@Entity
public class WarrantyUnit {
    @PrimaryKey
    @ColumnInfo
    private int id;

    @ColumnInfo
    private String manufacturerName;

    @ColumnInfo
    private int manufacturerId;

    @ColumnInfo
    private String modelName;

    @ColumnInfo
    private String modelType;

    @ColumnInfo
    private String serialNumber;

    @ColumnInfo
    private String warrantyEndDate;

    @ColumnInfo
    private boolean isClaimable;

    @ColumnInfo
    private String photoUrl;

    @ColumnInfo
    @TypeConverters({MapConverter.class})
    private Bitmap photoBmp;

    @Ignore
    public WarrantyUnit(int id, String manufacturerName, int manufacturerId, String modelName,
                        String modelType) {
        this.id = id;
        this.manufacturerName = manufacturerName;
        this.manufacturerId = manufacturerId;
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

    public Bitmap getPhotoBmp() {
        return photoBmp;
    }

    public void setPhotoBmp(Bitmap photoBmp) {
        this.photoBmp = photoBmp;
    }
}
