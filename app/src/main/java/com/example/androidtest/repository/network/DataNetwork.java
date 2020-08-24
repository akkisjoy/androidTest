package com.example.androidtest.repository.network;

import androidx.arch.core.util.Function;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.androidtest.model.Data;
import com.example.androidtest.model.NetworkState;
import com.example.androidtest.repository.paging.NetListDataPageKeyedDataSource;
import com.example.androidtest.repository.paging.NetListDataSourceFactory;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.example.androidtest.Constants.LOADING_PAGE_SIZE;
import static com.example.androidtest.Constants.NUMBERS_OF_THREADS;

public class DataNetwork {

    final private static String TAG = DataNetwork.class.getSimpleName();
    final private LiveData<PagedList<Data>> listDataPaged;
    final private LiveData<NetworkState> networkState;

    public DataNetwork(NetListDataSourceFactory dataSourceFactory, PagedList.BoundaryCallback<Data> boundaryCallback) {
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(LOADING_PAGE_SIZE).setPageSize(LOADING_PAGE_SIZE).build();
        networkState = Transformations.switchMap(dataSourceFactory.getNetworkStatus(),
                (Function<NetListDataPageKeyedDataSource, LiveData<NetworkState>>)
                        NetListDataPageKeyedDataSource::getNetworkState);
        Executor executor = Executors.newFixedThreadPool(NUMBERS_OF_THREADS);
        LivePagedListBuilder livePagedListBuilder = new LivePagedListBuilder(dataSourceFactory, pagedListConfig);
        listDataPaged = livePagedListBuilder.
                setFetchExecutor(executor).
                setBoundaryCallback(boundaryCallback).
                build();

    }


    public LiveData<PagedList<Data>> getPagedData() {
        return listDataPaged;
    }


    public LiveData<NetworkState> getNetworkState() {
        return networkState;
    }

}
