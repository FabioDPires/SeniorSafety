package com.example.seniorsafety.api;

import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitGoogleMaps {


    public static GoogleMapsApi getApi() {
        return getRetrofit().create(GoogleMapsApi.class);
    }

    private static retrofit2.Retrofit getRetrofit() {
        return new retrofit2.Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}