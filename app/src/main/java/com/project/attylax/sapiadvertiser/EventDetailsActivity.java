package com.project.attylax.sapiadvertiser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class EventDetailsActivity extends AppCompatActivity {
    private Intent intent = getIntent();
    TextView tvTitle, tvDate, tvTime, tvPlace, tvDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Post postData = intent.getParcelableExtra("POST_DATA");
        tvTitle = findViewById(R.id.tv_post_title);
        tvDate = findViewById(R.id.tv_post_date);
        tvTime = findViewById(R.id.tv_post_time);
        tvPlace = findViewById(R.id.tv_post_place);
        tvDesc = findViewById(R.id.tv_post_desc);

        tvTitle.setText(postData.getEventName());
        tvDate.setText(postData.getEventDate());
        tvTime.setText(postData.getEventTime());
        tvPlace.setText(postData.getEventLocation());
        tvDesc.setText(postData.getDescription());
    }
}
