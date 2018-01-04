package com.project.attylax.sapiadvertiser;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The activity which is responsible for showing important information about the event
 * Accessible via tapping on a post in the Main Activity
 */
public class EventDetailsActivity extends AppCompatActivity {
    TextView tvTitle, tvDate, tvTime, tvPlace, tvDesc, tvPrice;
    RecyclerView rvImages;
    ArrayList<ImageView> images;
    List<String> imgPaths = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        Post postData = Objects.requireNonNull(getIntent().getExtras()).getParcelable("POST_DATA");
        tvTitle = findViewById(R.id.tv_post_title);
        tvDate = findViewById(R.id.tv_post_date);
        tvTime = findViewById(R.id.tv_post_time);
        tvPlace = findViewById(R.id.tv_post_place);
        tvDesc = findViewById(R.id.tv_post_desc);
        tvPrice = findViewById(R.id.tv_post_price);
        rvImages = findViewById(R.id.rv_post_images);
        LinearLayoutManager llm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvImages.setLayoutManager(llm);

        if (postData != null) {
            tvTitle.setText(postData.getEventName());
            tvDate.setText(postData.getEventDate());
            tvTime.setText(postData.getEventTime());
            tvPlace.setText(postData.getEventLocation());
            tvDesc.setText(postData.getDescription());
            tvPrice.setText(String.format("Price: %s", String.valueOf(postData.getPrice())));
            imgPaths = postData.getImagesPath();
            images = new ArrayList<>(imgPaths.size());

            rvImages.setAdapter(new SapiAdvImageAdapter(this, imgPaths));

        }
    }
}
