package com.itsmefabio.anothermaps.api;

import com.itsmefabio.anothermaps.model.MapsResponse;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MapsAPI {
    @GET("json")
    Observable<MapsResponse> getPlaces(
            @Query("address") String query,
            @Query("key") String apiKey
    );
}