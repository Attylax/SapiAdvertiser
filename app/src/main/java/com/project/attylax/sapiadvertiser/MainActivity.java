package com.project.attylax.sapiadvertiser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * The list of current posts can be seen here.
 * Tapping a post opens the EventDetailsActivity
 * Tapping the "+" button in the bottom right corner opens the NewPostActivity
 */
public class MainActivity extends AppCompatActivity {
    private Intent intent;
    private SapiAdvPostAdapter adapter;
    private List<Post> listOfEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        RecyclerView rvPosts = findViewById(R.id.rv_posts);
        FloatingActionButton fabAdd = findViewById(R.id.fab_add);
        rvPosts.setHasFixedSize(true);

        LinearLayoutManager postLayoutManager = new LinearLayoutManager(this);
        rvPosts.setLayoutManager(postLayoutManager);




        adapter = new SapiAdvPostAdapter(listOfEvents, this);
        rvPosts.setAdapter(adapter);

        refreshList();

        intent = new Intent(this, NewPostActivity.class);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }

    public void refreshList(){


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("posts");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Map<String, String> posts = dataSnapshot.getValue(Map.class);
                        listOfEvents.clear();


                        for(DataSnapshot post : dataSnapshot.getChildren()){
                            String value = post.getValue(String.class);
                            Log.d("json", value);
                            listOfEvents.add(generatePost(value));
                        }



                        adapter.notifyDataSetChanged();
                    }

                    private Post generatePost(String value) {
                        Gson gson = new Gson();
                        return gson.fromJson(value, Post.class);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        adapter.notifyDataSetChanged();
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshList();
    }
}
