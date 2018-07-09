package com.itsmefabio.anothermaps.data.place;

public class PlaceSchema {
    public static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + PlaceContract.Place.TABLE_NAME + " (" +
                    PlaceContract.Place._ID + " TEXT PRIMARY KEY," +
                    PlaceContract.Place.COLUMN_NAME_FORMATTED_ADDRESS + " TEXT," +
                    PlaceContract.Place.COLUMN_NAME_LAT + " REAL," +
                    PlaceContract.Place.COLUMN_NAME_LNG + " REAL)";

    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + PlaceContract.Place.TABLE_NAME;
}
