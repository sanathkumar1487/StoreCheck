package com.euromonitor.storecheck.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.model.StoreCheckNavigationDrawerItem;

import java.util.Collections;
import java.util.List;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckNavigationAdapter extends RecyclerView.Adapter<StoreCheckNavigationAdapter.ItemsViewHolder> {
    private List<StoreCheckNavigationDrawerItem> drawerItems = Collections.emptyList();
    private LayoutInflater inflater;
    private Context context;

    public StoreCheckNavigationAdapter(Context context, List<StoreCheckNavigationDrawerItem> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.drawerItems = data;
    }

    public StoreCheckNavigationAdapter.ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.storecheck_navigation_item, parent, false);
        ItemsViewHolder holder = new ItemsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ItemsViewHolder holder, int position){
        StoreCheckNavigationDrawerItem current = drawerItems.get(position);

        holder.title.setText(current.getTitle());
        Log.e("Menu item", current.getTitle());
    }

    @Override
    public int getItemCount() {
        return drawerItems.size();
    }

    class ItemsViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        // ImageView imgIcon;

        public ItemsViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
            //imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);

        }
    }
}
