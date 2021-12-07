package com.example.seniorsafety.api;

import com.example.seniorsafety.models.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;


public interface GoogleMapsApi {
            @GET
            Call<SearchResponse> getNearByPlaces(@Url String url);

    }

