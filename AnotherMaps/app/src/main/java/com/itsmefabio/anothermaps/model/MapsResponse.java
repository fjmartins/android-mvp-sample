package com.itsmefabio.anothermaps.model;

import com.squareup.moshi.Json;

import java.util.List;

public class MapsResponse {
    public List<Place> results;

    public String status;

    @Json(name = "error_message")
    public String errorMessage;
}
