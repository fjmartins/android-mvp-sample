package com.itsmefabio.anothermaps.model;

import com.itsmefabio.anothermaps.data.DataManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static junit.framework.Assert.assertEquals;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class PlaceModelTest { //All tests should be independent

    private DataManager dataManager;

    final Place place = new Place("ChIJ2eUgeAK6j4ARbn5u_wAGqWA", "1600 Amphitheatre Pkwy, Mountain View, CA 94043, USA", new Geometry(new Location(37.4215421d, -122.0840106d)));

    @Before
    public void setUp() {
        dataManager = new DataManager(RuntimeEnvironment.application);
        dataManager.deleteAll();
    }

    @After
    public void tearDown() {
        dataManager.deleteAll();
    }

    @Test
    public void insertTest() { //Inserting object
        dataManager.insertPlace(place);
        Place p = dataManager.readPlace(place.id);
        assertEquals(place.toString(), p.toString());
    }

    @Test
    public void insertFailTest() { //Inserting same object twice
        dataManager.insertPlace(place);
        assertEquals(-1l, dataManager.insertPlace(place));
    }

    @Test
    public void readTest() { //Reading object
        dataManager.insertPlace(place);
        Place p = dataManager.readPlace(place.id);
        assertEquals(place.toString(), p.toString());
    }

    @Test
    public void readFailTest() { //Reading inexistent object
        Place p = dataManager.readPlace(place.id);
        boolean isNull = p == null;
        assertEquals(true, isNull);
    }

    @Test
    public void deleteTest() { //Deleting object
        dataManager.insertPlace(place);
        dataManager.deletePlace(place);
        Place p = dataManager.readPlace(place.id);
        boolean isNull = p == null;

        assertEquals(true, isNull);
    }

    @Test
    public void deleteFailTest() { //Deleting inexistent object
        int deletedRows = dataManager.deletePlace(place);
        assertEquals(0, deletedRows);
    }

    @Test
    public void updateTest() { //Updating object
        dataManager.insertPlace(place);
        place.formattedAddress = "Somewhere over the rainbow";
        dataManager.updatePlace(place);
        Place p = dataManager.readPlace(place.id);
        assertEquals(place.toString(), p.toString());
    }

    @Test
    public void updateFailTest() { //Updating inexistent object
        int updatedRows = dataManager.updatePlace(place);
        assertEquals(0, updatedRows);
    }

}
