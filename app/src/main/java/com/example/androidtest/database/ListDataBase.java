package com.example.androidtest.database;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.androidtest.model.Data;
import com.example.androidtest.repository.paging.DBListDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.androidtest.Constants.DATA_BASE_NAME;
import static com.example.androidtest.Constants.NUMBERS_OF_THREADS;

@Database(entities = {Data.class}, version = 1)
public abstract class ListDataBase extends RoomDatabase {

    private static ListDataBase instance;

    public abstract DataDao dataDao();

    private static final Object sLock = new Object();
    private LiveData<PagedList<Data>> listDataPaged;

    public static ListDataBase getInstance(Context context) {
        synchronized (sLock) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(),
                        ListDataBase.class, DATA_BASE_NAME)
                        .build();
                instance.init();

            }
            return instance;
        }
    }

    private void init() {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(Integer.MAX_VALUE).setPageSize(Integer.MAX_VALUE).build();
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        DBListDataSourceFactory dataSourceFactory = new DBListDataSourceFactory(dataDao());
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        listDataPaged = livePagedListBuilder.setFetchExecutor(executor).build();
    }

    public LiveData<PagedList<Data>> getListData() {
        return listDataPaged;
    }


}
