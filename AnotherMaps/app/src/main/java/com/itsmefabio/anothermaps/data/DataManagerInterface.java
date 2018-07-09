package com.itsmefabio.anothermaps.data;

import com.itsmefabio.anothermaps.model.Place;

public interface DataManagerInterface {
    long insertPlace(Place place);
    int updatePlace(Place place);
    int deletePlace(Place place);
    Place readPlace(String id);
}
