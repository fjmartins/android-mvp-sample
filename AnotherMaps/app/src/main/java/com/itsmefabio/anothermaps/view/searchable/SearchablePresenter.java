package com.itsmefabio.anothermaps.view.searchable;

import android.annotation.SuppressLint;

import com.itsmefabio.anothermaps.api.Maps;
import com.itsmefabio.anothermaps.data.DataManagerInterface;
import com.itsmefabio.anothermaps.model.Place;
import com.itsmefabio.anothermaps.view.base.BasePresenter;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

public class SearchablePresenter extends BasePresenter implements SearchableContract.Presenter {

    private SearchableContract.View view;
    private String query;

    @Override
    public void setQuery(String query) {
        this.query = query;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadResults() {
        view.showContentLoading();
        Maps.getInstance().getPlaces(query, Maps.KEY).subscribeOn(Schedulers.io()).subscribe(results -> {
            view.hideContentLoading();
            view.displayResults(results);
        });
    }

    @Override
    public void selectResult(int index, List<Place> places) {
        Place selected = null;
        if (index < places.size()) //"Display All On Map" was not selected.
            selected = places.get(index);

        view.openMap(selected, places);
    }

    @Override
    public void subscribe(SearchableContract.View view) {
        this.view = view;
    }

    @Override
    public void setDataManager(DataManagerInterface dataManager) {
        this.model = dataManager;
    }

    @Override
    public void unsubscribe() {
        this.view = null;
    }
}
