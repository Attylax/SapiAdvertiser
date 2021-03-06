package com.project.attylax.sapiadvertiser;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.DatePicker;
import android.widget.TimePicker;


import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.gson.Gson;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import android.Manifest;
import android.widget.Toast;

/**
 * Class for create new post.
 */
public class NewPostActivity extends AppCompatActivity {
    private DatabaseReference mDatabase;

    private EditText title;
    private EditText description;
    private EditText time;
    private EditText date;
    private EditText place;
    private EditText price;
    private EditText shortDescription;

    private List<ImageView> images = new ArrayList<>(3);
    private FloatingActionButton submitButton;
    private ImageView actualImage;

    private BroadcastReceiver broadcastReceiver;

    private final Calendar timeOfEvent = Calendar.getInstance();

    private final int PLACE_PICKER_REQUEST = 1;
    private final int IMAGE_PICKER_REQUEST = 2;

    private List<String> imagesPath = new ArrayList<>(3);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        searchViews();

        setOnClickLiseners();

        checkPermissions();

        /*File path = getExternalStoragePublicDirectory(DIRECTORY_PICTURES);
        Log.d("hely",path.getAbsolutePath());*/


        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                CharSequence text;
                int duration;
                Toast toast;

                Log.d("uzenet", "megjott valami");

                switch (Objects.requireNonNull(intent.getAction())) {

                    case PostUploaderService.POST_UPLOAD_COMPLETED:
                        text = "Upload Successful!";
                        duration = Toast.LENGTH_SHORT;

                        toast = Toast.makeText(context, text, duration);
                        toast.show();
                        Handler h = new Handler();
                        h.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    NewPostActivity.this.finish();
                                }
                            }, 6000);
                        break;
                    case PostUploaderService.POST_UPLOAD_ERROR:
                        text = "Error while upload...!";
                        duration = Toast.LENGTH_SHORT;

                        toast = Toast.makeText(context, text, duration);
                        toast.show();
                        break;
                }
            }
        };
    }


    @Override
    public void onStart(){
        super.onStart();

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);

        manager.registerReceiver(broadcastReceiver, PostUploaderService.getIntentFilter());
    }

    @Override
    public void onStop() {
        super.onStop();

        // Unregister download receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    /**
     * This function is responsible for add onClickLiseners on the views
     */
    private void setOnClickLiseners(){
        final TimePickerDialog.OnTimeSetListener timeDialog =
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        timeOfEvent.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        timeOfEvent.set(Calendar.MINUTE, minute);
                        updateTime();
                    }
                };


        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePickerDialog(NewPostActivity.this, 4, timeDialog,
                        timeOfEvent.get(Calendar.HOUR_OF_DAY),
                        timeOfEvent.get(Calendar.MINUTE), true).show();
            }
        });

        final DatePickerDialog.OnDateSetListener dateDialog =
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        timeOfEvent.set(Calendar.YEAR, year);
                        timeOfEvent.set(Calendar.MONTH, monthOfYear);
                        timeOfEvent.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDate();
                    }
                };

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NewPostActivity.this, 0, dateDialog,
                        timeOfEvent.get(Calendar.YEAR), timeOfEvent.get(Calendar.MONTH),
                        timeOfEvent.get(Calendar.DAY_OF_YEAR)).show();
            }
        });

        place.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder intentBuilder = new PlacePicker.IntentBuilder();

                try {
                    Intent intent = intentBuilder.build(NewPostActivity.this);

                    startActivityForResult(intent, PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    Log.d("baj", e.getMessage());
                } catch (GooglePlayServicesNotAvailableException e) {
                    Log.d("Baj", e.getMessage());
                }
            }
        });

        View.OnClickListener selectImage = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualImage = (ImageView) v;

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), IMAGE_PICKER_REQUEST);
            }
        };

        for(ImageView image : images){
            image.setOnClickListener(selectImage);
        }

        for(int i = 0; i < 3; ++i){
            imagesPath.add(null);
        }

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Log.d("Post", "Meghivva");
                if(checkInputs()) {
                    Log.d("check", "bement");
                    submitButton.setVisibility(View.GONE);
                    startService(new Intent(NewPostActivity.this, PostUploaderService.class)
                            .putExtra(PostUploaderService.EXTRA_POST_OBJECT, createPost())
                            .setAction(PostUploaderService.POST_ACTION_UPLOAD));
                }
            }
        });
    }

    /**
     * This function is responsible for find views
     */
    private void searchViews(){
        title = findViewById(R.id.postTitle);
        description = findViewById(R.id.postDescription);
        time = findViewById(R.id.postTime);
        date = findViewById(R.id.postDate);
        place = findViewById(R.id.postPlace);
        price = findViewById(R.id.postPrice);
        shortDescription = findViewById(R.id.post_short);

        images.add((ImageView) findViewById(R.id.postImage1));
        images.add((ImageView) findViewById(R.id.postImage2));
        images.add((ImageView) findViewById(R.id.postImage3));
        //builder = new PlacePicker.IntentBuilder();
        submitButton = findViewById(R.id.fab_submit_post);

    }

    /**
     * This function is responsible for check if the application has the rights for working correctly
     */
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
                ) {
            ActivityCompat.requestPermissions(NewPostActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    30);

        }
    }

    /**
     * Update the EditText which display chosen time
     */
    private void updateTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        time.setText(timeFormat.format(timeOfEvent.getTime()));
    }

    /**
     * Update the EditText which display chosen date
     */
    private void updateDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-dd");
        date.setText(dateFormat.format(timeOfEvent.getTime()));
    }

    /**
     * This funtion is responsable to process data from another activitys which one were started for result
     * This Activities were: PlacePicker and ImagePicker
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place selectedPlace = PlacePicker.getPlace(data, this);
                String toastMsg = String.format("Place: %s", selectedPlace.getName());
                //Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show();
                place.setText(toastMsg);

            }
        } else {
            if (requestCode == IMAGE_PICKER_REQUEST) {
                if (resultCode == RESULT_OK && data != null && data.getData() != null) {
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                        // Log.d(TAG, String.valueOf(bitmap));

                        actualImage.setImageBitmap(bitmap);
                        imagesPath.set(images.indexOf(actualImage), uri.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        }
    }

    /**
     *
     * @return a post object which one is created by data from EditTexts
     */
    private Post createPost(){
        Post post = new Post(title.getText().toString(), FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), FirebaseAuth.getInstance().getCurrentUser().getUid(), description.getText().toString(), shortDescription.getText().toString(), date.getText().toString(), place.getText().toString(), time.getText().toString(), Double.parseDouble(price.getText().toString()), imagesPath);
        Gson gson = new Gson();
        String json = gson.toJson(post);
        Log.d("Post", json);
        return post;
    }

    /**
     *
     * @return a boolean value. It's true if any input field is fill in correctly.
     */
    private boolean checkInputs() {
        boolean ok = true;
        if (title.getText().toString().trim().equals("")) {
            title.setError("Title is required!");
            ok = false;
        }
        if (shortDescription.getText().toString().trim().equals("")) {
            shortDescription.setError("Short description is required!");
            ok = false;
        }
        if (description.getText().toString().trim().equals("")) {
            description.setError("Description is required!");
            ok = false;
        }
        if (date.getText().toString().trim().equals("")) {
            date.setError("Date is required!");
            ok = false;
        }
        if (time.getText().toString().trim().equals("")) {
            time.setError("Time is required!");
            ok = false;
        }
        if (place.getText().toString().trim().equals("")) {
            place.setError("Place is required!");
            ok = false;
        }
        if (price.getText().toString().trim().equals("")) {
            price.setError("Price is required! (write 0 if the event is free)");
            ok = false;
        }

        CharSequence text;

        if(imagesPath.get(0) == null || imagesPath.get(1) == null || imagesPath.get(2) == null){
            text = "Have to upload 3 photos!";

            Toast.makeText(NewPostActivity.this, text, Toast.LENGTH_SHORT)
                    .show();
            ok = false;
        }else{
            if(imagesPath.get(0).equals(imagesPath.get(1)) || imagesPath.get(0).equals(imagesPath.get(2)) ||
                    imagesPath.get(1).equals(imagesPath.get(2))){
                text = "The images have to be different!";

                Toast.makeText(NewPostActivity.this, text, Toast.LENGTH_LONG)
                        .show();
                ok = false;
            }
        }

        Log.d("Check", ok == true ? "true" : "false");

        return ok;
    }
}
