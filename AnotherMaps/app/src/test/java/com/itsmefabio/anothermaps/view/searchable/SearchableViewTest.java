package com.itsmefabio.anothermaps.view.searchable;

import com.itsmefabio.anothermaps.view.maps.MapsView;
import com.itsmefabio.anothermaps.view.searchable.SearchablePresenter;
import com.itsmefabio.anothermaps.view.searchable.SearchableView;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SearchableViewTest {

    SearchableView view;

    @Mock
    SearchablePresenter presenter;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        view = new SearchableView();
        view.setPresenter(presenter);

    }

    @After
    public void tearDown() {
        presenter.unsubscribe();
    }

    @Test
    public void subscribeTest() {
        verify(presenter).subscribe(view);
    }

    @Test
    public void selectResultTest() { // Asynchronous task
        presenter.selectResult(any(), any());
        verify(presenter).selectResult(any(), any());
    }
}
