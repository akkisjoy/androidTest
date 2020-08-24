package com.example.androidtest.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.paging.PagedList;

import com.example.androidtest.model.Data;
import com.example.androidtest.model.NetworkState;
import com.example.androidtest.repository.DataRepository;

public class DataListViewModel extends AndroidViewModel {
    private DataRepository repository;

    public DataListViewModel(@NonNull Application application) {
        super(application);
        repository = DataRepository.getInstance(application);
    }
    public LiveData<PagedList<Data>> getListData() {
        return repository.getListData();
    }

    public LiveData<NetworkState> getNetworkState() {
        return repository.getNetworkState();
    }

}
