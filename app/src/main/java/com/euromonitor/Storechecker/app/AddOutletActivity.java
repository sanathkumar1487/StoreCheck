package com.euromonitor.Storechecker.app;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.euromonitor.Storechecker.R;
import com.euromonitor.Storechecker.model.Channel;
import com.euromonitor.Storechecker.model.Outlet;
import com.euromonitor.Storechecker.utility.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Sanath.Kumar on 4/20/2016.
 */
public class AddOutletActivity extends AppCompatActivity
{
    String[] item = new String[] {"Please search..."};
    int Count;
    Spinner spinner;
    TextView textView;
    EditText etDate;
    private DatePickerDialog datePickerDialog;
    private SimpleDateFormat dateFormatter;
    ImageButton imageButton;
    AutoCompleteTextView outLet_Name;
    EditText city;
    ArrayList<Channel> labels;
    boolean isvalueChanged = false;
    boolean isNew = false;
    DatabaseHelper db;
    ArrayAdapter<String> adapter;
    DrawerLayout mDrawerLayout;
    android.support.v7.widget.Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        View view =  getLayoutInflater().inflate(R.layout.storecheck_addoutlet,null);
        setContentView(view);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.addOutlet_Drawer);

        setupToolbar();
        setUpNavigationView();
        db = new DatabaseHelper(this);

            item=db.getOutletName();
            spinner = (Spinner) view.findViewById(R.id.spinner);
            textView = (TextView) view.findViewById(R.id.header);
            outLet_Name = (AutoCompleteTextView) view.findViewById(R.id.outlet_Name);
            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, item);
            outLet_Name.setAdapter(adapter);
            outLet_Name.setThreshold(1);
            city = (EditText) view.findViewById(R.id.city);
            setOutlet((Outlet) getIntent().getSerializableExtra("outlet"));
            isNew = (boolean) getIntent().getSerializableExtra("isnew");
            loadSpinnerData();
            loadOutletui();
            etDate = (EditText) view.findViewById(R.id.etDate);
            dateFormatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
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

            outLet_Name.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Toast.makeText(getBaseContext(), "This outlet is already existed", Toast.LENGTH_LONG).show();
                }
            }) ;


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

    public void setupToolbar(){

        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.addOutletToolBar);
        setSupportActionBar(toolbar);
        // actionbar.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Welcome !");
        toolbar.setSubtitle("Add Outlet");
        toolbar.setTitle("StoreChecker");
        toolbar.inflateMenu(R.menu.storecheck_menu);
    }

    private void setUpNavigationView() {
        try {
            Fragment navFragment = StoreCheckNavigationFragment.newInstance(mDrawerLayout.getId(), toolbar.getId());
            FragmentManager fragmentManager = this.getFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.navDrawerFrame, navFragment, "Nav");
            ft.commit();

        } catch (Exception e) {
            throw e;
        }
    }


    Outlet outlet;
    public Outlet getOutlet() {
        return outlet;
    }

    public void setOutlet(Outlet outlet)
    {
        this.outlet = outlet;
    }

    private void loadOutletui() {
        if (outlet != null)
        {
            outLet_Name.setText(outlet.get_outlet_Name());
            city.setText(outlet.get_outlet_city());

            for (int i = 0; i < labels.size(); i++) {
                Channel channel = labels.get(i);
                if (channel.get_chc_name() != null
                        && outlet.get_channel_name()!= null
                        && channel.get_chc_name().toString().trim().equals(outlet.get_channel_name().toString().trim())) {

                    spinner.setSelection(i);
                    break;
                }
            }

        }
        else outlet = new Outlet();
    }

    private void loadSpinnerData() {
        labels = db.getAllChannels();
        ChannelAdapter channelAdapter = new ChannelAdapter( labels);
        spinner.setAdapter(channelAdapter);
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
                outlet.set_outlet_Name(outLet_Name.getText().toString());
                outlet.set_outlet_city(city.getText().toString());
                outlet.set_outlet_date(etDate.getText().toString());

                if(spinner.getSelectedItem()!=null){
                    Channel selectedChannel = (Channel)spinner.getSelectedItem();
                    outlet.set_channel_name(selectedChannel.get_chc_name());
                    outlet.set_chccode(selectedChannel.get_chc_code());
                }


                outlet.set_updated(1);
                if (isNew)
                {

                    db.insertOutlet(outlet);
                    Toast.makeText(this.getBaseContext(), "New outlet saved", Toast.LENGTH_LONG).show();
                }
                else
                {
                    db.updateOutlet(outlet);
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

    public class ChannelAdapter extends BaseAdapter implements SpinnerAdapter{

        public ChannelAdapter(ArrayList<Channel> channels) {
            this.channels = channels;
        }

        ArrayList<Channel> channels;

        @Override
        public int getCount() {
            return channels.size();
        }

        @Override
        public Object getItem(int position) {
            return channels.get(position);
        }

        @Override
        public long getItemId(int position) {
            return channels.get(position).get_id();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View channelView;
            if (convertView != null) {
                channelView = convertView;
            } else {
                channelView = getLayoutInflater().inflate(R.layout.storecheck_productitem, parent, false);
            }

            TextView outletItem = (TextView) channelView.findViewById(R.id.productItem);
            outletItem.setText(channels.get(position).get_chc_name());

            return channelView;
        }
    }

}
