package com.example.androidtest.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.androidtest.model.Data;

import java.util.List;

@Dao
public interface DataDao {

    @Query("SELECT * FROM LISTDATA")
    List<Data> getListData();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertData(Data data);

    @Query("DELETE FROM LISTDATA")
    abstract void deleteAllData();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertDataList(List<Data> dataList);


}
