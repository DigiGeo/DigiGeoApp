package org.digigeo.digigeo.Database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import org.digigeo.digigeo.Entity.Cache;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface CacheDao {
    @Query("SELECT * FROM Cache")
    List<org.digigeo.digigeo.Entity.Cache> getMyCaches();

    @Query("SELECT * FROM Cache WHERE id = :id")
    List<org.digigeo.digigeo.Entity.Cache> getCacheById(int id);

    @Update void updateCachew(org.digigeo.digigeo.Entity.Cache... caches);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(org.digigeo.digigeo.Entity.Cache... caches);

    @Delete
    void delete(org.digigeo.digigeo.Entity.Cache... caches);
}
