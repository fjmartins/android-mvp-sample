package com.itsmefabio.anothermaps.view.maps;

import android.content.DialogInterface;

import com.itsmefabio.anothermaps.model.Place;
import com.itsmefabio.anothermaps.view.base.IBaseView;
import com.itsmefabio.anothermaps.view.base.IBasePresenter;

import java.util.List;

public interface MapsContract {
    /*
    * Contract that describes the interactions between View and Presenter.
    */
    interface Presenter extends IBasePresenter<View> { //Android Framework-Independent
        void setPlaces(List<Place> places);
        void setSelected(Place place);
        void mapReady();
        void searchRequested();
        void saveSelected();
        void deleteSelected();

        boolean isSearchMode(); //Determines if the map will launch on "Search" mode. (Can search for locations)
        boolean isDisplayAllOnMapMode(); //Determines if the map will launch on "Display All On Map" mode. (Can't save or delete locations)
        boolean isActionSave(); //Determines if the view will show the save or delete button for the selected location.
    }

    interface View extends IBaseView<Presenter> { //Dumb and passive, reduce its behavior to absolute minimum
        void showLocationSavedSuccess();
        void promptDelete(final DialogInterface.OnClickListener listener);
        void showLocationDeletedSuccess();
        void loadMap(Place selected, List<Place> places);
        void search();
    }
}
