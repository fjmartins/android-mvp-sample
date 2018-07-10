package com.itsmefabio.anothermaps.view.maps;

import com.itsmefabio.anothermaps.data.DataManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@Config(manifest = Config.NONE)
@RunWith(RobolectricTestRunner.class)
public class MapsPresenterTest { //Test that the Model(DataManagerInterface) and the View receive the correct calls from the Presenter

    private MapsPresenter presenter;

    @Mock
    private MapsView view;

    @Mock
    private DataManager dataManager;

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
