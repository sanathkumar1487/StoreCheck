package com.euromonitor.storecheck.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.euromonitor.storecheck.Constants.NavigationConstants;
import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.adapter.StoreCheckNavigationAdapter;
import com.euromonitor.storecheck.listener.ClickListener;
import com.euromonitor.storecheck.listener.RecyclerTouchListener;
import com.euromonitor.storecheck.model.StoreCheckNavigationDrawerItem;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public  class StoreCheckNavigationFragment extends Fragment {
    private ActionBarDrawerToggle mDrawerToggle;
    public DrawerLayout mDrawerLayout;
    public RecyclerView recyclerView;
    private static View view;
    final static String navDrawerId = "drawerId";
    final static String navToolbarId = "toolbarId";

    int activeView;

    public static final StoreCheckNavigationFragment newInstance(int drawerId, int toolbarId){
        StoreCheckNavigationFragment fragment = new StoreCheckNavigationFragment();

        Bundle bundle = new Bundle(2);
        bundle.putInt(navDrawerId, drawerId);
        bundle.putInt(navToolbarId, toolbarId);

        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
      //  if (view != null) {
          //  ViewGroup parent = (ViewGroup) view.getParent();
          //  if (parent != null)
           //     parent.removeView(view);
       // }
        try {
            view = inflater.inflate(R.layout.storecheck_navigation_drawer, container, false);
        } catch (InflateException e) {
        }

        try {
            Log.e("Oncreate view", "Adapter called");

            setUpDrawer();

            setUpRecyclerView();
        } catch (Exception e) {
            Log.e("Oncreate view", e.getMessage());
        }
        return view;
    }

    public void setUpRecyclerView() {
        recyclerView = (RecyclerView) view.findViewById(R.id.storeCheckDrawerList);

        StoreCheckNavigationAdapter adapter = new StoreCheckNavigationAdapter(getActivity(), StoreCheckNavigationDrawerItem.getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                loadView(position);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }
        ));
    }

    public void loadView(int position) {
        activeView = position;
        this.closeDrawer();
        Context context = getActivity();
        Intent intent=null;
        switch (position) {
            case NavigationConstants.VIEW_DETAILS:
                intent = new Intent(context, StoreCheckDetailsActivity.class);
                startActivity(intent);
                break;
            case NavigationConstants.VIEW_PRODUCT_DETAILS:

                intent = new Intent(context, StoreCheckAddProductDetailsActivity.class);
                startActivity(intent);
                break;
            case NavigationConstants.ADD_BRAND:

                intent = new Intent(context, StoreCheckAddBrandActivity.class);
                startActivity(intent);
                break;
            case NavigationConstants.ADD_OUTLET:

                intent = new Intent(context, OutletDetailsActivity.class);
                startActivity(intent);
                break;
            case NavigationConstants.IMPORT_STORECHECK_DETAILS:

                intent = new Intent(context, StoreCheckImportActivity.class);
                startActivity(intent);
                break;
            case NavigationConstants.EXPORT_STORECHECK_DETAILS:

                intent = new Intent(context, StoreCheckExportActivity.class);
                startActivity(intent);
                break;
        }


    }

    public void setUpDrawer() {
        try {
            mDrawerLayout = (DrawerLayout)getActivity().findViewById(getArguments().getInt(navDrawerId));
            Toolbar toolbar = (Toolbar)getActivity().findViewById(getArguments().getInt(navToolbarId));

            mDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
                @Override
                public void onDrawerOpened(View drawerView) {
                    try {

                        super.onDrawerOpened(drawerView);
                    } catch (Exception e) {
                        Log.e("Drawer open", e.getMessage());
                    }
                }

                @Override
                public void onDrawerClosed(View drawerView) {
                    super.onDrawerClosed(drawerView);
                }

                @Override
                public void onDrawerSlide(View drawerView, float slideOffset) {
                    super.onDrawerSlide(drawerView, slideOffset);
                }
            };

            mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerToggle.syncState();

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
