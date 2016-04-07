package com.euromonitor.storecheck.listener;

import android.view.View;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public interface ClickListener {
    void onClick(View view, int position);
    void onLongClick(View view, int position);
}
