package com.itsmefabio.anothermaps.view.maps;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.itsmefabio.anothermaps.R;
import com.itsmefabio.anothermaps.data.DataManager;
import com.itsmefabio.anothermaps.model.Place;
import com.itsmefabio.anothermaps.view.searchable.SearchableView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapsView extends AppCompatActivity implements OnMapReadyCallback, MapsContract.View {

    @BindView(R.id.mapsToolbar)
    Toolbar toolbar;

    private MapsContract.Presenter presenter;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setPresenter(new MapsPresenter());
        presenter.setDataManager(new DataManager(MapsView.this));
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        handleIntent(getIntent());
    }

    private void handleIntent(Intent intent) {
        presenter.setPlaces(intent.getParcelableArrayListExtra(SearchableView.EXTRA_PLACES_RESULT));
        presenter.setSelected(intent.getParcelableExtra(SearchableView.EXTRA_FOCUS_PLACE));

        if(!presenter.isSearchMode())
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.maps_menu, menu);

        menu.findItem(R.id.action_save_or_delete).setIcon(presenter.isActionSave() ? android.R.drawable.ic_menu_save : android.R.drawable.ic_menu_delete);
        menu.findItem(R.id.action_search).setVisible(presenter.isSearchMode());
        menu.findItem(R.id.action_save_or_delete).setVisible(!presenter.isDisplayAllOnMapMode()); //Only display those items if we're not on "display all on map" mode
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search: // User chose the "Search" action
                presenter.searchRequested();
                return true;
            case R.id.action_save_or_delete: // User chose the "Save or Delete" action
                if (presenter.isActionSave()) {
                    presenter.saveSelected();
                } else {
                    presenter.deleteSelected();
                }
                return true;
            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }

    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        presenter.mapReady();
    }

    @Override
    public boolean onSearchRequested() {
        return super.onSearchRequested();
    }

    @Override
    protected void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }

    @Override
    public void showLocationSavedSuccess() {
        Snackbar.make(findViewById(R.id.activity_settings),
                R.string.location_saved, Snackbar.LENGTH_SHORT).show();

        invalidateOptionsMenu(); //Recreate Options Menu to update the icon
    }

    @Override
    public void showLocationDeletedSuccess() {
        Snackbar.make(findViewById(R.id.activity_settings),
                R.string.location_deleted, Snackbar.LENGTH_SHORT).show();

        invalidateOptionsMenu(); //Recreate Options Menu to update the icon
    }

    @Override
    public void promptDelete(DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new AlertDialog.Builder(MapsView.this, android.R.style.Theme_Material_Dialog_Alert);
        } else {
            builder = new AlertDialog.Builder(MapsView.this);
        }

        builder.setTitle(R.string.delete_location)
                .setMessage(R.string.delete_location_prompt)
                .setPositiveButton(R.string.delete, listener)
                .setNegativeButton(R.string.cancel, (dialogInterface, i) -> {})
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    @Override
    public void loadMap(Place selected, List<Place> places) {
        if (places != null) {
            for (Place p : places) {
                LatLng location = new LatLng(p.geometry.location.lat, p.geometry.location.lng);
                mMap.addMarker(new MarkerOptions().position(location).title(p.formattedAddress + ": " + p.geometry.location.lat + ", " + p.geometry.location.lng));
            }

            if (selected != null) //Move Camera to Focus
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(selected.geometry.location.lat, selected.geometry.location.lng), 13));
        }
    }

    @Override
    public void search() {
        onSearchRequested();
    }

    @Override
    public void setPresenter(MapsContract.Presenter presenter) {
        presenter.subscribe(this);
        this.presenter = presenter;
    }
}
