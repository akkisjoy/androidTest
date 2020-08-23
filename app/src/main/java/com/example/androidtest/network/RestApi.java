package com.example.androidtest.network;

import com.example.androidtest.model.Data;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestApi {

    @GET("kickstarter")
    Call<List<Data>> getList(@Query("page") int page,
                             @Query("perPageCount") int perPageCount);
}
