package com.example.androidtest.repository.paging;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.androidtest.model.Data;
import com.example.androidtest.model.NetworkState;
import com.example.androidtest.repository.network.RestClient;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.subjects.ReplaySubject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetListDataPageKeyedDataSource extends PageKeyedDataSource<String, Data> {

    private static final String TAG = NetListDataPageKeyedDataSource.class.getSimpleName();
    private final MutableLiveData networkState;
    private final ReplaySubject<Data> listDataObservable;

    NetListDataPageKeyedDataSource() {
        networkState = new MutableLiveData();
        listDataObservable = ReplaySubject.create();
    }

    public MutableLiveData getNetworkState() {
        return networkState;
    }

    public ReplaySubject<Data> getListData() {
        return listDataObservable;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<String> params, @NonNull final LoadInitialCallback<String, Data> callback) {
        Log.i(TAG, "Loading Initial Rang, Count " + params.requestedLoadSize);

        networkState.postValue(NetworkState.LOADING);
        Call<List<Data>> callBack = RestClient.getInstance().getList(1);
        callBack.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(@NotNull Call<List<Data>> call, @NotNull Response<List<Data>> response) {
                if (response.isSuccessful()) {
                    Log.d("onResponse", response.body().toString());
                    callback.onResult(response.body(), Integer.toString(1), Integer.toString(2));
                    networkState.postValue(NetworkState.LOADED);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        response.body().forEach(listDataObservable::onNext);
                    }
                } else {
                    Log.e("API CALL", response.message());
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                }
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(), Integer.toString(1), Integer.toString(2));
            }
        });
    }


    @Override
    public void loadAfter(@NonNull LoadParams<String> params, final @NonNull LoadCallback<String, Data> callback) {
        Log.i(TAG, "Loading page " + params.key);
        networkState.postValue(NetworkState.LOADING);
        final AtomicInteger page = new AtomicInteger(0);
        try {
            page.set(Integer.parseInt(params.key));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        Call<List<Data>> callBack = RestClient.getInstance().getList(page.get());
        callBack.enqueue(new Callback<List<Data>>() {
            @Override
            public void onResponse(Call<List<Data>> call, Response<List<Data>> response) {
                if (response.isSuccessful()) {
                    callback.onResult(response.body(), Integer.toString(page.get() + 1));
                    networkState.postValue(NetworkState.LOADED);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        response.body().forEach(listDataObservable::onNext);
                    }
                } else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, response.message()));
                    Log.e("API CALL", response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Data>> call, Throwable t) {
                String errorMessage;
                if (t.getMessage() == null) {
                    errorMessage = "unknown error";
                } else {
                    errorMessage = t.getMessage();
                }
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, errorMessage));
                callback.onResult(new ArrayList<>(), Integer.toString(page.get()));
            }
        });
    }


    @Override
    public void loadBefore(@NonNull LoadParams<String> params, @NonNull LoadCallback<String, Data> callback) {

    }
}