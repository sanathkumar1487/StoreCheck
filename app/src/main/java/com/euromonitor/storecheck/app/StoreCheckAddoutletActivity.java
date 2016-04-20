package com.euromonitor.storecheck.app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.model.Channel;
import com.euromonitor.storecheck.model.Outlet;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by Shashwat.Bajpai on 19/04/2016.
 */
public class StoreCheckAddoutletActivity extends MainActivity
{
    int Count;
    Spinner spinner;
    TextView textView;
    EditText etDate;
    private DatePickerDialog birthDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    ImageButton imageButton;
    EditText outLet_Name;
    EditText city;
 Context context;


    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        View view =  getLayoutInflater().inflate(R.layout.storecheck_addoutlet,null);
      //  setContentView(R.layout.storecheck_addoutlet);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        textView=(TextView)view.findViewById(R.id.header);
        outLet_Name = (EditText) view.findViewById(R.id.outlet_Name);
        city = (EditText) view.findViewById(R.id.city);
        context=this;
        loadOutletui();
        loadSpinnerData();


        etDate = (EditText)view.findViewById(R.id.etDate);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthDatePickerDialog.show();
            }


        });

        Calendar newCalendar = Calendar.getInstance();
        birthDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(dateFormatter.format(newDate.getTime()));

            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

    }



    public Outlet getOutlet()
    {
        return outlet;
    }

    public void setOutlet(Outlet outlet)
    {
        this.outlet = outlet;
    }

    Outlet outlet;


    private  void loadOutletui(){


        if (outlet != null)

        {
            outLet_Name.setText(outlet.get_outlet_Name());
            city.setText(outlet.get_outlet_city());
        }
    }


    public void SaveOutlet(View view)
    {
        //  imageButton=(ImageButton)view.findViewById(R.id.imageButton);
        outLet_Name =(EditText)view.findViewById(R.id.outlet_Name);
        city =(EditText)view.findViewById(R.id.city);
        spinner=(Spinner)view.getRootView().findViewById(R.id.spinner);
        final Channel user = (Channel)((Spinner)view.findViewById(R.id.spinner)).getSelectedItem();
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                String outlet = outLet_Name.getText().toString();
                String city = StoreCheckAddoutletActivity.this.city.getText().toString();
                String DateTime = etDate.getText().toString();
                //String Channel=spinner.getSelectedItem();
                String s = user.get_chc_code();


                DatabaseHelper db = new DatabaseHelper(context);


                //  List<Outlet> labels = db.getAllOutlets();

//
////                Outlet outlet1obj=new Outlet(labels.get(1).get_outlet_id(),
////                        labels.get(1).get_project_id(),
////                        labels.get(1).get_geo_code(),
////                        labels.get(1).get_geo_name(),
////                        labels.get(1).get_industry(),
////                        outlet,
////                        city,
////                        labels.get(1).get_year(),
////                        s,
////                        DateTime);
//                db.addOutlet(outlet1obj);

                Toast.makeText(getBaseContext(),
                        "New Outlet Added", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void loadSpinnerData()
    {
        DatabaseHelper db = new DatabaseHelper(context);

       // List<Channel> labels = db.getAllChannels();


      //  ArrayAdapter<Channel> dataAdapter = new ArrayAdapter<Channel>(context, android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
       // dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
      //  spinner.setAdapter(dataAdapter);
    }
}
