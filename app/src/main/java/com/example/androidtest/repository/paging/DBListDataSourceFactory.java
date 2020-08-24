package com.example.androidtest.repository.paging;

import androidx.paging.DataSource;

import com.example.androidtest.database.DataDao;

public class DBListDataSourceFactory extends DataSource.Factory {

    private static final String TAG = DBListDataSourceFactory.class.getSimpleName();
    private DBListPageKeyedDataSource moviesPageKeyedDataSource;

    public DBListDataSourceFactory(DataDao dao) {
        moviesPageKeyedDataSource = new DBListPageKeyedDataSource(dao);
    }

    @Override
    public DataSource create() {
        return moviesPageKeyedDataSource;
    }

}
