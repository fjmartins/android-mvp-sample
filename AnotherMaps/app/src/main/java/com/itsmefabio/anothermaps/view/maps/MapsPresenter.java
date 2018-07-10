package com.itsmefabio.anothermaps.view.maps;

import android.content.DialogInterface;

import com.itsmefabio.anothermaps.data.DataManagerInterface;
import com.itsmefabio.anothermaps.model.Place;
import com.itsmefabio.anothermaps.view.base.BasePresenter;

import java.util.List;

public class MapsPresenter extends BasePresenter implements MapsContract.Presenter {

    private MapsContract.View view;
    private Place selected;
    private List<Place> places;

    @Override
    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    @Override
    public void setSelected(Place place) {
        this.selected = place;
    }

    @Override
    public void mapReady() {
        view.loadMap(selected, places);
    }

    @Override
    public void searchRequested() {
        view.search();
    }

    @Override
    public void saveSelected() {
        boolean success;
        success = model.insertPlace(selected) > 0;
        if (success)
            view.showLocationSavedSuccess();
    }

    @Override
    public void deleteSelected() {
        view.promptDelete((DialogInterface dialogInterface, int i) -> {
            if (model.deletePlace(selected) > 0) {
                view.showLocationDeletedSuccess();
            }
        });
    }

    @Override
    public boolean isActionSave() {
        boolean actionSave = false;
        if (selected != null) { //Checks if the location is already saved to the Database
            Place place = model.readPlace(selected.id);
            actionSave = place == null;
        }
        return actionSave;
    }

    @Override
    public boolean isDisplayAllOnMapMode() {
        return (this.selected == null);
    }

    public boolean isSearchMode() {
        return (places == null);
    }

    @Override
    public void subscribe(MapsContract.View view) {
        this.view = view;
    }

    @Override
    public void setDataManager(DataManagerInterface dataManager) {
        this.model = dataManager;
    }

    @Override
    public void unsubscribe() {
        this.view = null;
        this.model = null;
        this.places = null;
        this.selected = null;
    }
}
