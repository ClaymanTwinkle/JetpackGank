package com.kesar.jetpackgank.data;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/**
 * GankDao
 *
 * @author andy <br/>
 * create time: 2019/2/22 11:28
 */
@Dao
public interface GankDao {
    @Query("SELECT * FROM Gank ORDER BY createdAt")
    LiveData<List<Gank>> getGanks();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Gank ganks);
}
