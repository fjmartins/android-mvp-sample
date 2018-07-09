package com.itsmefabio.anothermaps.view.searchable;

import android.support.annotation.NonNull;

import com.itsmefabio.anothermaps.model.MapsResponse;
import com.itsmefabio.anothermaps.model.Place;
import com.itsmefabio.anothermaps.view.base.IBasePresenter;
import com.itsmefabio.anothermaps.view.base.IBaseView;

import java.util.List;

public interface SearchableContract {
    /*
     * Contract that describes the interactions between View and Presenter.
     */
    interface Presenter extends IBasePresenter<View> { //Android Framework-Independent
        void setQuery(String query);
        void loadResults();
        void selectResult(int index, List<Place> place);
    }

    interface View extends IBaseView<Presenter> { //Dumb and passive, reduce its behavior to absolute minimum
        void showContentLoading();
        void hideContentLoading();
        void displayResults(@NonNull MapsResponse searchResults);
        void openMap(Place selected, List<Place> places);
    }
}
