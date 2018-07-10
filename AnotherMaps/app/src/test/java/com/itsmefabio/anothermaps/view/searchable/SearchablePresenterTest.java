package com.itsmefabio.anothermaps.view.searchable;

import com.itsmefabio.anothermaps.data.DataManager;
import com.itsmefabio.anothermaps.model.Geometry;
import com.itsmefabio.anothermaps.model.Location;
import com.itsmefabio.anothermaps.model.Place;
import com.itsmefabio.anothermaps.view.maps.MapsPresenter;
import com.itsmefabio.anothermaps.view.searchable.SearchablePresenter;
import com.itsmefabio.anothermaps.view.searchable.SearchableView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(RobolectricTestRunner.class)
public class SearchablePresenterTest {

    SearchablePresenter presenter;

    @Mock
    SearchableView view;

    Place germany = new Place("ChIJa76xwh5ymkcRW-WRjmtd6HU", "Germany", new Geometry(new Location(51.165691d,10.451526d)));
    Place italy = new Place("ChIJA9KNRIL-1BIRb15jJFz1LOI", "Italy", new Geometry(new Location(41.87194d, 12.56738d)));
    Place spain = new Place("ChIJi7xhMnjjQgwR7KNoB5Qs7KY", "Spain", new Geometry(new Location(40.46366700000001d, -3.74922d)));

    List<Place> places = new ArrayList<>();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        places.add(germany);
        places.add(italy);
        places.add(spain);

        presenter = new SearchablePresenter();
        presenter.subscribe(view);
    }

    @After
    public void tearDown() {
        presenter.unsubscribe();
    }

    @Test
    public void showContentLoadingCalledTest() {
        presenter.setQuery("La La Land");
        presenter.loadResults();
        verify(view).showContentLoading();
    }

    @Test
    public void hideContentLoadingCalledTest() { // Asynchronous task
        view.hideContentLoading();
        verify(view).hideContentLoading();
    }

    @Test
    public void displayResultsCalledTest() { // Asynchronous tasks
        view.displayResults(any());
        verify(view).displayResults(any());
    }

    @Test
    public void openMapCalledTest() {
        presenter.selectResult(0, places);
        verify(view).openMap(any(), any());
    }

}
