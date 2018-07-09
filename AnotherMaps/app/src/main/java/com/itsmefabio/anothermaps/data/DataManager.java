package com.itsmefabio.anothermaps.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.itsmefabio.anothermaps.data.place.PlaceContract;
import com.itsmefabio.anothermaps.model.Geometry;
import com.itsmefabio.anothermaps.model.Location;
import com.itsmefabio.anothermaps.model.Place;

public class DataManager implements DataManagerInterface {

    private DataHelper dataHelper;

    public DataManager(Context context) {
        dataHelper = new DataHelper(context);
    }

    public void close() {
        if(dataHelper.getDb() != null)
            dataHelper.getDb().close();
    }

    public long insertPlace(Place place) { //Returns inserted rows
        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(PlaceContract.Place._ID, place.id);
        values.put(PlaceContract.Place.COLUMN_NAME_FORMATTED_ADDRESS, place.formattedAddress);
        values.put(PlaceContract.Place.COLUMN_NAME_LAT, place.geometry.location.lat);
        values.put(PlaceContract.Place.COLUMN_NAME_LNG, place.geometry.location.lng);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = dataHelper.getDb().insert(PlaceContract.Place.TABLE_NAME, null, values);

        return newRowId;
    }

    public int updatePlace(Place place) { //Returns the number of updated rows
        // New value for one column

        ContentValues values = new ContentValues();
        values.put(PlaceContract.Place.COLUMN_NAME_FORMATTED_ADDRESS, place.formattedAddress);
        values.put(PlaceContract.Place.COLUMN_NAME_LAT, place.geometry.location.lat);
        values.put(PlaceContract.Place.COLUMN_NAME_LNG, place.geometry.location.lng);

        // Which row to update, based on the title
        String selection = PlaceContract.Place._ID + " LIKE ?";
        String[] selectionArgs = {place.id};

        int count = dataHelper.getDb().update(
                PlaceContract.Place.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return count;
    }

    public int deletePlace(Place place) { // Returns the number of deleted rows
        // Define 'where' part of query.
        String selection = PlaceContract.Place._ID + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = {place.id};
        // Issue SQL statement.
        int deletedRows = dataHelper.getDb().delete(PlaceContract.Place.TABLE_NAME, selection, selectionArgs);

        return deletedRows;
    }

    public Place readPlace(String id) { //Returns a Place object
        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                PlaceContract.Place._ID,
                PlaceContract.Place.COLUMN_NAME_FORMATTED_ADDRESS,
                PlaceContract.Place.COLUMN_NAME_LAT,
                PlaceContract.Place.COLUMN_NAME_LNG,
        };

        // Filter results WHERE "id" = 'MY ID'
        String selection = PlaceContract.Place._ID + " = ?";
        String[] selectionArgs = {id};

        // How you want the results sorted in the resulting Cursor (Sorted by the address)
        String sortOrder = PlaceContract.Place.COLUMN_NAME_FORMATTED_ADDRESS + " DESC";

        Cursor cursor = dataHelper.getDb().query(
                PlaceContract.Place.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        Place place = null;

        while(cursor.moveToNext()) {
            try {
                place = new Place(cursor.getString(0), cursor.getString(1), new Geometry(new Location(cursor.getDouble(2), cursor.getDouble(3))));
            } catch (Exception e){
                //Whoops
            }
        }

        return place;
    }
}
