package com.euromonitor.storecheck.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.model.Channel;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckAddOutletFragment extends Fragment {
    Spinner spinner;
    CalendarView calender;
    TextView textView;

    /*@Override
    public void onCreateView(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.storecheck_addoutlet);
        spinner = (Spinner) findViewById(R.id.spinner);
        loadSpinnerData();



    }*/

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.storecheck_addoutlet, container, false);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        calender = (CalendarView) view.findViewById(R.id.calenderView);
        textView=(TextView)view.findViewById(R.id.header);
        loadSpinnerData();

        calender.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
             textView.setText("Date: " + dayOfMonth + "/" + month + "/" +year);
                Toast.makeText(getActivity(),"Selected Date is\n\n"
                                +dayOfMonth+" : "+month+" : "+year ,
                        Toast.LENGTH_LONG).show();
            }
        });


        return view;


    }
    private void loadSpinnerData()
    {
        DatabaseHelper db = new DatabaseHelper(this.getActivity());

        ArrayList<String> labels = db.getAllChannels();

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }


}