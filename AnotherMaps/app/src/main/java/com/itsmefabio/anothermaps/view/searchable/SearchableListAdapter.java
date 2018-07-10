package com.itsmefabio.anothermaps.view.searchable;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.itsmefabio.anothermaps.R;

import java.util.List;

public class SearchableListAdapter extends ArrayAdapter<String> {

    private List<String> items;

    public SearchableListAdapter(Context context, int textViewResourceId,
                                 List<String> items) {
        super(context, textViewResourceId, items);
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Nullable
    @Override
    public String getItem(int position) {
        return items.get(position);
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        boolean showSeparator;
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(R.layout.row_layout, null);

        // Set values
        TextView formattedTextView = (TextView) view.findViewById(R.id.formattedAddress);
        formattedTextView.setText(getItem(position));

        // Separator logic
        showSeparator = (position == items.size() - 1 && items.size() > 1); //If this is the last item, and there are more than one items in the list, then show separator
        view.findViewById(R.id.separator).setVisibility(showSeparator ? View.VISIBLE : View.GONE);

        return view;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
