package com.itsmefabio.anothermaps.api;

import com.itsmefabio.anothermaps.model.Geometry;
import com.itsmefabio.anothermaps.model.Location;
import com.itsmefabio.anothermaps.model.Place;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import io.reactivex.schedulers.Schedulers;

import static junit.framework.Assert.assertEquals;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class MapsAPITest { //All tests should be independent

    private final String query = "Tokyo";
    private final String WRONG_KEY_ERROR_MESSAGE = "The provided API key is invalid.";

    @Test
    public void getPlacesAssert() {
        Place expected = new Place("ChIJ51cu8IcbXWARiRtXIothAS4", "Tokyo, Japan", new Geometry(new Location(35.6894875d, 139.6917064d)));

        Maps.getInstance().getPlaces(query, Maps.KEY).subscribeOn(Schedulers.io()).subscribe(results -> {
            assertEquals(expected.toString(), results.results.get(0).toString());
        });
    }

    @Test
    public void getPlacesFailAssert() {
        Maps.getInstance().getPlaces(query, "WRONGKEY").subscribeOn(Schedulers.io()).subscribe(results -> {
            assertEquals(WRONG_KEY_ERROR_MESSAGE, results.errorMessage);
        });
    }

}
