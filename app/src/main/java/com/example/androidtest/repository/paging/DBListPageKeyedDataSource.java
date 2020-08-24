package com.example.androidtest.repository.paging;


import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.example.androidtest.database.DataDao;
import com.example.androidtest.model.Data;

import java.util.List;

/**
 * Created by Elad on 6/25/2018.
 */

public class DBListPageKeyedDataSource extends PageKeyedDataSource<String, Data> {

    public static final String TAG = DBListPageKeyedDataSource.class.getSimpleName();
    private final DataDao dataDao;
    public DBListPageKeyedDataSource(DataDao dao) {
        dataDao = dao;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Data> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);
        List<Data> movies = dataDao.getListData();
        if(movies.size() != 0) {
            callback.onResult(movies, "0", "1");
        }
    }

    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Data> callback) {
    }

    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Data> callback) {
    }
}
