package com.itsmefabio.anothermaps.view.maps;

import android.support.v7.view.menu.ActionMenuItem;
import android.view.MenuItem;

import com.itsmefabio.anothermaps.BuildConfig;
import com.itsmefabio.anothermaps.R;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/*
 * Test that:
 * The View gets correctly created.
 * User interactions get to the Presenter.
 * */
@Config(constants = BuildConfig.class)
public class MapsViewTest {

    @Mock
    private MapsPresenter presenter;

    private MapsView view;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        view = new MapsView();
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
    public void mapReadyTest() {
        view.onMapReady(null);
        verify(presenter).mapReady();
    }

    @Test
    public void searchTest() {
        MenuItem search = new ActionMenuItem(RuntimeEnvironment.application, 0, R.id.action_search, 0, 0, "");
        view.onOptionsItemSelected(search);
        verify(presenter).searchRequested();
    }

    @Test
    public void saveLocationTest() {
        MenuItem save = new ActionMenuItem(RuntimeEnvironment.application, 0, R.id.action_save_or_delete, 0, 0, "");
        when(presenter.isActionSave()).thenReturn(true);
        view.onOptionsItemSelected(save);
        verify(presenter).saveSelected();
    }

    @Test
    public void deleteLocationTest() {
        MenuItem save = new ActionMenuItem(RuntimeEnvironment.application, 0, R.id.action_save_or_delete, 0, 0, "");
        when(presenter.isActionSave()).thenReturn(false);
        view.onOptionsItemSelected(save);
        verify(presenter).deleteSelected();
    }

}
