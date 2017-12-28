package com.project.attylax.sapiadvertiser;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private SapiAdvPostAdapter adapter;
    private List<Post> listOfEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvPosts = findViewById(R.id.rv_posts);
        rvPosts.setHasFixedSize(true);

        LinearLayoutManager postLayoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(postLayoutManager);

        Post a = new Post("Post1", "Attila1", "Ez egy szoveg", "szoveg", "itt", "most");
        Post b = new Post("Post2", "Bence1", "Do you know what I like more than a Lamborghini?", "NNAWLEGE", "ott", "akkor");
        Post c = new Post("Post3", "Bence2", "Protecc Earth-chan", "Recycle 4 Earth-chan", "mindenhol", "mindig");
        listOfEvents.add(a);
        listOfEvents.add(b);
        listOfEvents.add(c);

        adapter = new SapiAdvPostAdapter(listOfEvents);
        rvPosts.setAdapter(adapter);

        DataUploader.uploadPost(a);
    }

}
