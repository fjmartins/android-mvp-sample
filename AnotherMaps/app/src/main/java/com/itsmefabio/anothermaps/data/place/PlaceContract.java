package com.itsmefabio.anothermaps.data.place;

import android.provider.BaseColumns;

public class PlaceContract {

    private PlaceContract() {}

    public static class Place implements BaseColumns {
        public static final String TABLE_NAME = "place";
        public static final String COLUMN_NAME_FORMATTED_ADDRESS = "address";
        public static final String COLUMN_NAME_LAT = "lat";
        public static final String COLUMN_NAME_LNG = "lng";
    }
}
