package com.euromonitor.storecheck.app;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.adapter.StoreCheckOutletDetailAdapter;
import com.euromonitor.storecheck.databinding.StorecheckoutletDetailsBinding;
import com.euromonitor.storecheck.databinding.StorecheckoutletItemBinding;
import com.euromonitor.storecheck.listener.ClickListener;
import com.euromonitor.storecheck.listener.RecyclerTouchListener;
import com.euromonitor.storecheck.model.Channel;
import com.euromonitor.storecheck.model.Outlet;
import com.euromonitor.storecheck.model.StoreCheckDetail;
import com.euromonitor.storecheck.utility.DatabaseHelper;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Sanath.Kumar on 4/20/2016.
 */
public class AddOutletActivity extends MainActivity
{
    int Count;
    Spinner spinner;
    TextView textView;
    EditText etDate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    ImageButton imageButton;
    EditText outLet_Name;
    EditText city;
    ArrayList<Channel> labels;
    boolean isvalueChanged = false;
    boolean isNew = false;




    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        View view =  getLayoutInflater().inflate(R.layout.storecheck_addoutlet,null);
        setContentView(view);
        spinner = (Spinner) view.findViewById(R.id.spinner);
        textView = (TextView) view.findViewById(R.id.header);
        outLet_Name = (EditText) view.findViewById(R.id.outlet_Name);
        city = (EditText) view.findViewById(R.id.city);
        setOutlet((Outlet) getIntent().getSerializableExtra("outlet"));

        isNew = (boolean)getIntent().getSerializableExtra("isnew");
        loadSpinnerData();
        loadOutletui();

        etDate = (EditText) view.findViewById(R.id.etDate);
        dateFormatter = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        etDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();
            }


        });

        Calendar newCalendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                etDate.setText(dateFormatter.format(newDate.getTime()));

            }

        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH),
                newCalendar.get(Calendar.DAY_OF_MONTH));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent,
                                       View view, int pos, long id) {
                Channel g = (Channel) parent.getItemAtPosition(pos);

            }

            public void onNothingSelected(AdapterView parent) {
                // Do nothing.
            }

        });


        outLet_Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isvalueChanged = true;
            }
        });


        city.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isvalueChanged = true;
            }
        });

        etDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                isvalueChanged = true;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
               isvalueChanged = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    Outlet outlet;
    public Outlet getOutlet() {
        return outlet;
    }

    public void setOutlet(Outlet outlet) {
        this.outlet = outlet;
    }

    private void loadOutletui() {

        if (outlet != null)

        {
            outLet_Name.setText(outlet.get_outlet_Name());
            city.setText(outlet.get_outlet_city());
            int index = -1;

            for (int i = 0; i < labels.size(); i++) {
                Channel channel = labels.get(i);

                if (channel.get_chc_name() != null && outlet.get_channel_name()!= null  && channel.get_chc_name().toString().trim().equals(outlet.get_channel_name().toString().trim())) {
                    index = i;
                }
            }
            spinner.setSelection(index);
        }
        else outlet = new Outlet();
    }

    private void loadSpinnerData() {
        DatabaseHelper db = new DatabaseHelper(this);
        labels = db.getAllChannels();
        ArrayAdapter<Channel> dataAdapter = new ArrayAdapter<Channel>(this, android.R.layout.simple_spinner_item, labels);
        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        getMenuInflater().inflate(R.menu.addoutlet_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.Save:
                     SaveOutlet();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private  void SaveOutlet()
    {

        if (isvalueChanged)
        {
            Log.i("save ::", "save is clicked");
            Log.i("outletname", outLet_Name.getText().toString());
            Log.i("city", city.getText().toString());
            Log.i("etDate", etDate.getText().toString());
            if (outLet_Name.getText().toString().length() != 0 && city.getText().toString().length() != 0 && etDate.getText().toString().length() != 0)
            {
                 DatabaseHelper databaseHelper = new DatabaseHelper(this);

                outlet.set_outlet_Name(outLet_Name.getText().toString());
                outlet.set_outlet_city(city.getText().toString());
                outlet.set_outlet_date(etDate.getText().toString());
                outlet.set_channel_name(spinner.getSelectedItem().toString());

                if (isNew)
                {
                    databaseHelper.insertOutlet(outlet);
                    Toast.makeText(this.getBaseContext(), "New outlet saved", Toast.LENGTH_LONG).show();
                }
                else
                {
                    databaseHelper.updateOutlet(outlet);
                    Toast.makeText(this.getBaseContext(), "Outlet updated", Toast.LENGTH_LONG).show();
                }

            }
            else
            {
                Toast.makeText(this.getBaseContext(), "Values cannot be empty", Toast.LENGTH_LONG).show();
            }

            isvalueChanged = false;
        }
        else
        {
            Toast.makeText(this.getBaseContext(), "No changes to save", Toast.LENGTH_LONG).show();
        }


    }


}
