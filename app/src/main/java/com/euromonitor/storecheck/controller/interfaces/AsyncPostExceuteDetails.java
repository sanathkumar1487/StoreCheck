package com.euromonitor.storecheck.controller.interfaces;

import com.euromonitor.storecheck.model.StoreCheckDetail;

import java.util.ArrayList;

/**
 * Created by Sanath.Kumar on 26-Apr-16.
 */
public interface AsyncPostExceuteDetails {

  void PostExecute(ArrayList<StoreCheckDetail> data);
}
