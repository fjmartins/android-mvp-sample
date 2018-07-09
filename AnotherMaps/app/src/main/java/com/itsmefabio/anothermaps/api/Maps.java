package com.itsmefabio.anothermaps.api;

import com.itsmefabio.anothermaps.model.MapsResponse;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class Maps implements MapsAPI {

    public static final String KEY = "AIzaSyBEYS99RyYZoaxI8nEFPgrbPvEJQTGG4wM";
    public static final String URL = "https://maps.googleapis.com/maps/api/geocode/";

    private static Maps maps;
    private MapsAPI api;

    private Maps() {
        api = new Retrofit.Builder()
                .baseUrl(URL)
                .client(new OkHttpClient.Builder().build())
                .addConverterFactory(MoshiConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MapsAPI.class);
    }

    public static Maps getInstance() {
        if (maps == null) maps = new Maps();
        return maps;
    }

    @Override
    public Observable<MapsResponse> getPlaces(String query, String apiKey) {
        return api.getPlaces(query.replace(" ", "+"), apiKey)
                .subscribeOn(Schedulers.io());
    }
}
