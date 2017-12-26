package com.project.attylax.sapiadvertiser;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import android.Manifest;
import android.support.v4.app.ShareCompat.IntentBuilder;
import android.widget.Toast;

public class NewPostActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private EditText title;
    private EditText description;
    private EditText time;
    private EditText date;
    private EditText place;

    private final Calendar timeOfEveniment = Calendar.getInstance();

    int PLACE_PICKER_REQUEST = 1;

    private PlacePicker.IntentBuilder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        title = (EditText) findViewById(R.id.postTitle);
        description = (EditText) findViewById(R.id.postDescription);
        time = (EditText) findViewById(R.id.postTime);
        date = (EditText) findViewById(R.id.postDate);
        place = (EditText) findViewById(R.id.postPlace);

        builder = new PlacePicker.IntentBuilder();


        final TimePickerDialog.OnTimeSetListener timeDialog =
                new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute){
                timeOfEveniment.set(Calendar.HOUR_OF_DAY, hourOfDay);
                timeOfEveniment.set(Calendar.MINUTE, minute);
                updateTime();
            }
        };


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(NewPostActivity.this, 4,  timeDialog,
                        timeOfEveniment.get(Calendar.HOUR_OF_DAY),
                        timeOfEveniment.get(Calendar.MINUTE), true).show();
            }
        });

        final DatePickerDialog.OnDateSetListener dateDialog =
                new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                timeOfEveniment.set(Calendar.YEAR, year);
                timeOfEveniment.set(Calendar.MONTH, monthOfYear);
                timeOfEveniment.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }
        };

        date.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                new DatePickerDialog(NewPostActivity.this, 0,dateDialog,
                        timeOfEveniment.get(Calendar.YEAR), timeOfEveniment.get(Calendar.MONTH),
                        timeOfEveniment.get(Calendar.DAY_OF_YEAR)).show();
            }
        });

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();

                try {
                    Intent intent = intentBuilder.build(NewPostActivity.this);

                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                }catch (GooglePlayServicesRepairableException e){
                    Log.d("baj", e.getMessage());
                }catch (GooglePlayServicesNotAvailableException e){
                    Log.d("Baj", e.getMessage());
                }
            }
        });

        checkPermissions();

    }

    void checkPermissions() {
        if (ContextCompat.checkSelfPermission(NewPostActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(NewPostActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED /*||
                ContextCompat.checkSelfPermission(NewPostActivity.this,
                        Manifest.permission.)
                        != PackageManager.PERMISSION_GRANTED*/
                )
        {
            ActivityCompat.requestPermissions(NewPostActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    30);

        }
    }

    private void updateTime(){
    SimpleDateFormat timeFormat = new SimpleDateFormat("HH:MM");
        time.setText(timeFormat.format(timeOfEveniment.getTime()));
    }


    private void updateDate(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        date.setText(dateFormat.format(timeOfEveniment.getTime()));
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place selectedPlace = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", selectedPlace.getName());
                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                place.setText(toastMsg);

            }
        }
    }
}
