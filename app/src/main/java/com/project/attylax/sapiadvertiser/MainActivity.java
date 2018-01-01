package com.project.attylax.sapiadvertiser;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private SapiAdvPostAdapter adapter;
    private List<Post> listOfEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        List<Uri> DummyList = new ArrayList<>(3);
        DummyList.add(Uri.parse("https://vignette.wikia.nocookie.net/austinally/images/1/14/Random_picture_of_shark.png"));
        DummyList.add(Uri.parse("https://vignette.wikia.nocookie.net/austinally/images/1/14/Random_picture_of_shark.png"));
        DummyList.add(Uri.parse("https://vignette.wikia.nocookie.net/austinally/images/1/14/Random_picture_of_shark.png"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvPosts = findViewById(R.id.rv_posts);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        rvPosts.setHasFixedSize(true);

        LinearLayoutManager postLayoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(postLayoutManager);

        Post a = new Post("Post1", "Attila1", "0", "Ez egy szoveg", "szoveg", "itt", "most", "ekkor", 100, DummyList);
        Post b = new Post("Post2", "Bence1", "1", "Do you know what I like more than a Lamborghini?", "NNAWLEGE", "ott", "akkor", "valamikor", 120, DummyList);
        Post c = new Post("Post3", "Bence2", "1", "Protecc Earth-chan", "Recycle 4 Earth-chan", "mindenhol", "mindig", "mindig", 50, DummyList);
        listOfEvents.add(a);
        listOfEvents.add(b);
        listOfEvents.add(c);

        adapter = new SapiAdvPostAdapter(listOfEvents, this);
        rvPosts.setAdapter(adapter);

        DataUploader.uploadPost(a);
        DataUploader.uploadPost(b);
        DataUploader.uploadPost(c);

        intent = new Intent(this, NewPostActivity.class);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }
}
