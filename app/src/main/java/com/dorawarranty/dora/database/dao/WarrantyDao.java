package com.dorawarranty.dora.database.dao;

import android.graphics.Bitmap;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.dorawarranty.dora.mvvm.models.WarrantyUnit;

import java.util.List;

@Dao
public interface WarrantyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(WarrantyUnit[] warrantyUnits);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWarrantyUnit(WarrantyUnit warrantyUnit);

    @Query("UPDATE warrantyunit SET photoBmp= :photo WHERE id=:warrantyId")
    void updatePhoto(int warrantyId, Bitmap photo);

    @Query("UPDATE warrantyunit SET manufacturerName=:manufacturerName," +
            " manufacturerId=:manufacturerId, modelName=:modelName," +
            " modelType=:modelType, serialNumber=:serialNumber," +
            " warrantyEndDate=:warrantyEndDate," +
            " isClaimable=:isClaimable, photoUrl=:photoUrl" +
            " WHERE id=:warrantyId")
    void updateWarrantyInfo(int warrantyId, String manufacturerName, int manufacturerId, String modelName,
                            String modelType, String serialNumber, String warrantyEndDate,
                            boolean isClaimable, String photoUrl);

    @Query("SELECT count(*) FROM warrantyunit")
    int linesCount();

    @Query("SELECT * FROM warrantyunit")
    List<WarrantyUnit> getAll();

    @Query("SELECT count(*) FROM warrantyunit WHERE id=:warrantyId AND photoBmp IS NOT NULL")
    int checkWarrantyImage(int warrantyId);

    @Query("SELECT count(*) FROM warrantyunit WHERE id=:warrantyId AND serialNumber IS NOT NULL")
    int checkWarrantyInfo(int warrantyId);

    @Query("SELECT * FROM warrantyunit WHERE id=:warrantyId")
    WarrantyUnit getWarrantyUnit(int warrantyId);

    @Query("SELECT photoBmp FROM warrantyunit WHERE id=:warrantyId")
    Bitmap getWarrantyImage(int warrantyId);
}
