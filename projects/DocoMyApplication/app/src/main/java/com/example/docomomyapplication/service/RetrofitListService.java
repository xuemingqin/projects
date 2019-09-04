package com.example.docomomyapplication.service;

import com.example.docomomyapplication.data.Data;

import retrofit2.http.GET;
import java.util.List;

import retrofit2.Call;

public interface RetrofitListService{
    @GET("repos/googlesamples/android-architecture-components/contributors")
    Call<List<Data>> getContributors();
}
