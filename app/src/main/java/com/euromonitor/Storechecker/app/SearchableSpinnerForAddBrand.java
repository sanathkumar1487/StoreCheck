package com.euromonitor.Storechecker.app;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Spinner;

import com.euromonitor.Storechecker.model.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shashwat.Bajpai on 11/05/2016.
 */
public class SearchableSpinnerForAddBrand extends Spinner implements View.OnTouchListener,
        SearchableListDialog.SearchableItem
{
    private Context _context;
    private List _items;
    private SearchableListDialog _searchableListDialog;

    public SearchableSpinnerForAddBrand(Context context) {
        super(context);
        this._context = context;
        init();
    }

    public SearchableSpinnerForAddBrand(Context context, AttributeSet attrs) {
        super(context, attrs);
        this._context = context;
        init();
    }

    public SearchableSpinnerForAddBrand(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this._context = context;
        init();
    }

    private void init() {
        _items = new ArrayList();
        _searchableListDialog = SearchableListDialog.newInstance
                (_items);
        _searchableListDialog.setOnSearchableItemClickListener(this);
        setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            StoreCheckAddBrandActivity.ProductAdapter adapter = ( StoreCheckAddBrandActivity.ProductAdapter) getAdapter();

            if (null != adapter) {


                _items.clear();
                for (int i = 0; i < adapter.getCount(); i++) {
                    _items.add(((Product)adapter.getItem(i)).get_product_name());
                }
                _searchableListDialog.show(scanForActivity(_context).getFragmentManager(),"TAG");
            }
        }
        return true;
    }

    @Override
    public void onSearchableItemClicked(Object item, int position) {
        setSelection(_items.indexOf(item));
    }

    public void setTitle(String strTitle) {
        _searchableListDialog.setTitle(strTitle);
    }

    public void setPositiveButton(String strPositiveButtonText) {
        _searchableListDialog.setPositiveButton(strPositiveButtonText);
    }

    public void setPositiveButton(String strPositiveButtonText, DialogInterface.OnClickListener onClickListener) {
        _searchableListDialog.setPositiveButton(strPositiveButtonText, onClickListener);
    }

    private Activity scanForActivity(Context cont) {
        if (cont == null)
            return null;
        else if (cont instanceof Activity)
            return (Activity)cont;
        else if (cont instanceof ContextWrapper)
            return scanForActivity(((ContextWrapper)cont).getBaseContext());

        return null;
    }
}
