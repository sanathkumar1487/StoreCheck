package com.euromonitor.storecheck.app;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.euromonitor.storecheck.R;
import com.euromonitor.storecheck.controller.ImportDataTask;
import com.euromonitor.storecheck.controller.interfaces.AsyncPostExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncPreExecute;
import com.euromonitor.storecheck.controller.interfaces.AsyncProgressReport;
import com.github.developerpaul123.filepickerlibrary.FilePickerActivity;
import com.github.developerpaul123.filepickerlibrary.enums.Request;
import com.github.developerpaul123.filepickerlibrary.enums.Scope;

import me.zhanghai.android.materialprogressbar.MaterialProgressBar;
import com.github.developerpaul123.filepickerlibrary.FilePickerActivity;
import com.github.developerpaul123.filepickerlibrary.enums.MimeType;
import com.github.developerpaul123.filepickerlibrary.enums.Request;
import com.github.developerpaul123.filepickerlibrary.enums.Scope;
import com.github.developerpaul123.filepickerlibrary.enums.ThemeType;

/**
 * Created by Sanath.Kumar on 4/7/2016.
 */
public class StoreCheckImportFragment extends Fragment implements AsyncPostExecute,AsyncProgressReport,AsyncPreExecute {

    ImportDataTask importDataController ;
    Button browseFile;
    Button sqlLiteMonitor;
    MaterialProgressBar progressBar;

    private static final int REQUEST_PICK_FILE = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.storecheck_import, container, false);

        progressBar = (MaterialProgressBar) view.findViewById(R.id.progbar);

        int color = 0xFF00FF00;
        progressBar.getIndeterminateDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        progressBar.getProgressDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);

        browseFile = (Button)view.findViewById(R.id.browseFile);
        browseFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectFile(v);
            }
        });

        sqlLiteMonitor = (Button)view.findViewById(R.id.sqlMonitor);
        sqlLiteMonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loaddbmanager(v);
            }
        });

        return view;
    }

    /** Called when the user touches the button */
    public void selectFile(View view) {

        Intent filePicker = new Intent(getActivity(), FilePickerActivity.class);
        filePicker.putExtra(FilePickerActivity.SCOPE, Scope.ALL);
        filePicker.putExtra(FilePickerActivity.REQUEST, Request.FILE);
        filePicker.putExtra(FilePickerActivity.INTENT_EXTRA_COLOR_ID, android.R.color.holo_blue_dark);
        startActivityForResult(filePicker, 10);
    }



    public  void loaddbmanager(View view)
    {
        Intent dbmanager = new Intent(getActivity(),AndroidDatabaseManager.class);
        startActivity(dbmanager);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == 10) && (resultCode == getActivity().RESULT_OK)) {
            Toast.makeText(this.getActivity(), "File Selected: " + data.getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH), Toast.LENGTH_LONG).show();
            try
            {

                importDataController = new ImportDataTask(data.getStringExtra(FilePickerActivity.FILE_EXTRA_DATA_PATH), getActivity(), this);
                importDataController.postExecute = this;
                importDataController.preExecute = this;
                importDataController.progressReport = this;
                importDataController.execute();
            } catch (Exception e)
            {
                messageBox("onActivityResult",e.getMessage());
            }
        }
    }

    public void messageBox(String method, String message)
    {
        Log.d("EXCEPTION: " + method, message);

        AlertDialog.Builder messageBox = new AlertDialog.Builder(this.getActivity());
        messageBox.setTitle(method);
        messageBox.setMessage(message);
        messageBox.setCancelable(false);
        messageBox.setNeutralButton("OK", null);
        messageBox.show();
    }

    @Override
    public void PostExecute(String message) {
      //  Toast.makeText(, "PostExecute :: " + message, Toast.LENGTH_LONG).show();
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void progressReport(String message) {

       // Toast.makeText(this, "progressReport  :: " + message, Toast.LENGTH_LONG).show();

    }

    @Override
    public void preExecute(String message) {

        progressBar.setVisibility(View.VISIBLE);
    }


}
