package com.example.docomomyapplication.fragment;

import com.example.docomomyapplication.service.RetrofitListService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitListClient{
    public RetrofitListService getService(){
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl( "https://api.github.com/" )
                .addConverterFactory( GsonConverterFactory.create() )
                .build();
        return retrofit.create( RetrofitListService.class );
    }
}
