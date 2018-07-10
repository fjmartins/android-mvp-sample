package com.itsmefabio.anothermaps.api;

import com.google.gson.Gson;
import com.itsmefabio.anothermaps.data.DataManager;
import com.itsmefabio.anothermaps.model.Geometry;
import com.itsmefabio.anothermaps.model.Location;
import com.itsmefabio.anothermaps.model.Place;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class MapsAPITest { //All tests should be independent

    final String query = "Tokyo";
    final String WRONG_KEY_ERROR_MESSAGE = "The provided API key is invalid.";

    @Test @Config(manifest=Config.NONE)
    public void getPlacesTest() {
        Place expected = new Place("ChIJ51cu8IcbXWARiRtXIothAS4", "Tokyo, Japan", new Geometry(new Location( 35.6894875d,139.6917064d)));

        Maps.getInstance().getPlaces(query, Maps.KEY).subscribeOn(Schedulers.io()).subscribe(results -> {
            assertEquals(expected.toString(), results.results.get(0).toString());
        });
    }

    @Test @Config(manifest=Config.NONE)
    public void getPlacesFailTest() {
        Maps.getInstance().getPlaces(query, "WRONGKEY").subscribeOn(Schedulers.io()).subscribe(results -> {
            assertEquals(WRONG_KEY_ERROR_MESSAGE, results.errorMessage);
        });
    }

}
