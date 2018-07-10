package com.itsmefabio.anothermaps.view.searchable;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.annotation.Config;

import java.util.ArrayList;

import static org.mockito.Mockito.verify;

@Config(manifest = Config.NONE)
public class SearchableViewTest {

    private SearchableView view;

    @Mock
    private SearchablePresenter presenter;

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
        presenter.selectResult(0, new ArrayList<>());
        verify(presenter).selectResult(0, new ArrayList<>());
    }
}
