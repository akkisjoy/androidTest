package com.example.androidtest.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.androidtest.model.Data;
import com.example.androidtest.network.RestClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DataViewModel extends ViewModel {

    private MutableLiveData<List<Data>> listMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<Boolean> isError = new MutableLiveData<>();

    public LiveData<List<Data>> getListLiveData() {
        if (listMutableLiveData == null) {
            return new MutableLiveData<>();
        } else
            return listMutableLiveData;
    }

    public void callTheApi(int page, int count) {

        Call<List<Data>> listCall = RestClient.getInstance().getList(page, count);

        if (listCall != null) {
            listCall.enqueue(new Callback<List<Data>>() {
                @Override
                public void onResponse(retrofit2.Call<List<Data>> call, Response<List<Data>> response) {
                    if (response.body() != null && response.code() == 200) {
                        List<Data> dataList = response.body();
                        if (dataList.size() > 0) {
                            listMutableLiveData.setValue(dataList);
                            Log.d("dataList", dataList.toString());
                        }
                    } else {
                        isError.setValue(true);
                    }
                }

                @Override
                public void onFailure(retrofit2.Call<List<Data>> call, Throwable t) {
                    isError.setValue(true);
                }
            });
        }
    }
}
