package com.euromonitor.storecheck.adapter;

/**
 * Created by Shashwat.Bajpai on 06/05/2016.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.model.Market;

import java.util.ArrayList;
import java.util.List;
public class StoreCheckBrandAdapter extends ArrayAdapter<Market> {
    Context context;
    int resource, textViewResourceId;
    List<Market> items, tempItems, suggestions;

    public StoreCheckBrandAdapter(Context context, int resource, int textViewResourceId, List<Market> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<Market>(items); // this makes the difference.
        suggestions = new ArrayList<Market>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.custom_row_brand_name, parent, false);
        }
        Market market = items.get(position);
        if (market != null) {
            TextView lblName = (TextView) view.findViewById(R.id.brand_name);
            if (lblName != null)
                lblName.setText(market.get_brand());
        }
        return view;
    }
    @Override
    public Filter getFilter()
    {
        return nameFilter;
    }
    Filter nameFilter=new Filter()
    {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((Market) resultValue).get_brand();
            return str;
        }

        @Override
        protected Filter.FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Market market : tempItems) {
                    if (market.get_brand().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        suggestions.add(market);
                    }
                }
                Filter.FilterResults filterResults = new Filter.FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new Filter.FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, Filter.FilterResults results) {
            List<Market> filterList = (ArrayList<Market>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Market market : filterList) {
                    add(market);
                    notifyDataSetChanged();
                }
            }
        }
    };
}