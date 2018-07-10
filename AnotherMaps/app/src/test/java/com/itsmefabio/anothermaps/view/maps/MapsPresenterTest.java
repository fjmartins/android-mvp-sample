package com.itsmefabio.anothermaps.view.maps;

import android.content.DialogInterface;

import com.itsmefabio.anothermaps.data.DataManager;
import com.itsmefabio.anothermaps.model.Geometry;
import com.itsmefabio.anothermaps.model.Location;
import com.itsmefabio.anothermaps.model.Place;
import com.itsmefabio.anothermaps.view.maps.MapsPresenter;
import com.itsmefabio.anothermaps.view.maps.MapsView;

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
public class MapsPresenterTest { //Test that the Model(DataManagerInterface) and the View receive the correct calls from the Presenter

    MapsPresenter presenter;

    @Mock
    MapsView view;

    @Mock
    DataManager dataManager;



    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        presenter = new MapsPresenter();
        presenter.subscribe(view);
        presenter.setDataManager(dataManager);

        when(dataManager.insertPlace(any())).thenReturn(1L);
    }

    @After
    public void tearDown() {
        presenter.unsubscribe();
        dataManager.deleteAll();
    }

    @Test
    public void saveSuccessShownTest() {
        presenter.saveSelected();
        verify(view).showLocationSavedSuccess();
    }

    @Test
    public void deletePromptCalledTest() {
        presenter.deleteSelected();
        verify(view).promptDelete(any());
    }

    @Test
    public void modelSaveLocationCalledTest() {
        saveSuccessShownTest();
        verify(dataManager).insertPlace(any());
    }

    @Test
    public void modelDeleteLocationCalledTest() { // Asynchronous task
        view.showLocationDeletedSuccess();
        verify(view).showLocationDeletedSuccess();
    }

}
