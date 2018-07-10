package com.itsmefabio.anothermaps.view.searchable;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.itsmefabio.anothermaps.R;
import com.itsmefabio.anothermaps.model.MapsResponse;
import com.itsmefabio.anothermaps.model.Place;
import com.itsmefabio.anothermaps.view.maps.MapsView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SearchableView extends AppCompatActivity implements SearchableContract.View {

    public static final String EXTRA_PLACES_RESULT = "EXTRA_PLACES_RESULT";
    public static final String EXTRA_FOCUS_PLACE = "EXTRA_FOCUS_PLACE";

    @BindView(R.id.searchableToolbar)
    Toolbar toolbar;
    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.txtNoResults)
    TextView txtNoResults;

    private SearchableContract.Presenter presenter;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchable);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setPresenter(new SearchablePresenter());

        // Get the intent, verify the action and get the query
        handleIntent(getIntent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        // Get the SearchView and set the searchable configuration
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        // Assumes current activity is the searchable activity
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false); // Do not iconify the widget; expand it by default
        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            presenter.setQuery(query);
            presenter.loadResults();
        }
    }

    @Override
    public void displayResults(MapsResponse response) {
        boolean noResults = response.results.size() == 0;
        final List<String> items = new ArrayList<>();

        for (Place p : response.results) {
            items.add(p.formattedAddress);
        }

        if (response.results.size() > 1)
            items.add(getString(R.string.display_all_on_map));

        runOnUiThread(() -> {
            txtNoResults.setVisibility(noResults ? View.VISIBLE : View.GONE);

            listView.setVisibility(noResults ? View.GONE : View.VISIBLE);
            listView.setAdapter(new SearchableListAdapter(SearchableView.this,
                    android.R.layout.simple_list_item_1, items));
            listView.setOnItemClickListener((AdapterView<?> adapterView, View view, int i, long l) -> {
                    presenter.selectResult(i, response.results);
            });
        });
    }

    @Override
    public void openMap(Place selected, List<Place> places) {
        Intent intent = new Intent(SearchableView.this, MapsView.class);
        intent.putParcelableArrayListExtra(EXTRA_PLACES_RESULT, (ArrayList<Place>) places);
        intent.putExtra(EXTRA_FOCUS_PLACE, selected);
        startActivity(intent);
    }

    @Override
    public void showContentLoading() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(SearchableView.this);
            progressDialog.setMessage(getString(R.string.loading));
        }
        progressDialog.show();
    }

    @Override
    public void hideContentLoading() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    public void setPresenter(SearchableContract.Presenter presenter) {
        presenter.subscribe(this);
        this.presenter = presenter;
    }

    @Override
    protected void onDestroy() {
        presenter.unsubscribe();
        super.onDestroy();
    }
}
