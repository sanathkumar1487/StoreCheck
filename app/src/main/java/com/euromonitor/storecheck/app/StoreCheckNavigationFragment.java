package com.euromonitor.storecheck.app;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.adapter.StoreCheckNavigationAdapter;
import com.euromonitor.storecheck.model.StoreCheckNavigationDrawerItem;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckNavigationFragment extends Fragment {
    private ActionBarDrawerToggle mDrawerToggle;
    public DrawerLayout mDrawerLayout;
    public RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.storecheck_navigation_drawer, container, false);
        try {
            Log.e("Oncreate view", "Adapter called");
            setUpRecyclerView(view);
        } catch (Exception e) {
            Log.e("Oncreate view", e.getMessage());
        }
        return view;
    }

    private void setUpRecyclerView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.storeCheckDrawerList);

        StoreCheckNavigationAdapter adapter = new StoreCheckNavigationAdapter(getActivity(), StoreCheckNavigationDrawerItem.getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    public void setUpDrawer(int fragmentId, DrawerLayout drawerLayout, Toolbar toolbar) {
        try {
            mDrawerLayout = drawerLayout;
            mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
//                getActivity().invalidateOptionsMenu();
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
//                getActivity().invalidateOptionsMenu();
                }

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, slideOffset);
                    // Do something of Slide of Drawer
                }
            };

            mDrawerLayout.setDrawerListener(mDrawerToggle);

            mDrawerLayout.post(new Runnable() {
                @Override
                public void run() {
                    mDrawerToggle.syncState();
                }
            });
        } catch (Exception e) {
            Log.i("setup drawer exception", e.getMessage());
        }
    }

    public void closeDrawer(){
        try{
            Log.i("Draw", "Close Called");


            mDrawerLayout.closeDrawers();
        }
        catch (Exception e){
            Log.i("Exception", e.getMessage());
        }
    }

}
