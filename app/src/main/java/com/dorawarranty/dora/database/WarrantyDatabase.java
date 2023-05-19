package com.dorawarranty.dora.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.dorawarranty.dora.database.dao.WarrantyDao;
import com.dorawarranty.dora.mvvm.models.WarrantyUnit;

@Database(entities = {WarrantyUnit.class}, version = 1)
@TypeConverters({MapConverter.class})
public abstract class WarrantyDatabase extends RoomDatabase {
    public abstract WarrantyDao warrantyDao();

    public static WarrantyDatabase getDatabase(final Context context) {
        return Room.databaseBuilder(context.getApplicationContext(),
                            WarrantyDatabase.class, "warranty_database.db")
                .allowMainThreadQueries()
                .build();
    }
}
