package com.example.androidtest.repository;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.paging.PagedList;

import com.example.androidtest.database.ListDataBase;
import com.example.androidtest.model.Data;
import com.example.androidtest.model.NetworkState;
import com.example.androidtest.repository.network.DataNetwork;
import com.example.androidtest.repository.paging.NetListDataSourceFactory;

import io.reactivex.schedulers.Schedulers;

public class DataRepository {

    private static final String TAG = DataRepository.class.getSimpleName();
    private static DataRepository instance;
    public final DataNetwork network;
    final private ListDataBase database;
    final private MediatorLiveData liveDataMerger;

    private DataRepository(Context context) {

        NetListDataSourceFactory dataSourceFactory = new NetListDataSourceFactory();

        network = new DataNetwork(dataSourceFactory, boundaryCallback);
        database = ListDataBase.getInstance(context.getApplicationContext());
        // when we get new movies from net we set them into the database
        liveDataMerger = new MediatorLiveData<>();
        liveDataMerger.addSource(network.getPagedData(), value -> {
            liveDataMerger.setValue(value);
            Log.d(TAG, value.toString());
        });

        // save the movies into db
        dataSourceFactory.getListData().
                observeOn(Schedulers.io()).
                subscribe(data -> {
                    database.dataDao().insertData(data);
                });

    }

    private PagedList.BoundaryCallback<Data> boundaryCallback = new PagedList.BoundaryCallback<Data>() {
        @Override
        public void onZeroItemsLoaded() {
            super.onZeroItemsLoaded();
            liveDataMerger.addSource(database.getListData(), value -> {
                liveDataMerger.setValue(value);
                liveDataMerger.removeSource(database.getListData());
            });
        }
    };

    public static DataRepository getInstance(Context context) {
        if (instance == null) {
            instance = new DataRepository(context);
        }
        return instance;
    }

    public LiveData<PagedList<Data>> getListData() {
        return liveDataMerger;
    }

    public LiveData<NetworkState> getNetworkState() {
        return network.getNetworkState();
    }

}
