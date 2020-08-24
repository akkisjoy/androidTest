package com.example.androidtest.repository.paging;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.androidtest.model.Data;

import io.reactivex.subjects.ReplaySubject;

public class NetListDataSourceFactory extends DataSource.Factory {

    private static final String TAG = NetListDataSourceFactory.class.getSimpleName();
    private MutableLiveData<NetListDataPageKeyedDataSource> networkStatus;
    private NetListDataPageKeyedDataSource listDataPageKeyedDataSource;
    public NetListDataSourceFactory() {
        this.networkStatus = new MutableLiveData<>();
        listDataPageKeyedDataSource = new NetListDataPageKeyedDataSource();
    }


    @Override
    public DataSource create() {
        networkStatus.postValue(listDataPageKeyedDataSource);
        return listDataPageKeyedDataSource;
    }

    public MutableLiveData<NetListDataPageKeyedDataSource> getNetworkStatus() {
        return networkStatus;
    }

    public ReplaySubject<Data> getListData() {
        return listDataPageKeyedDataSource.getListData();
    }

}
