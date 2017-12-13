package com.project.attylax.sapiadvertiser;

/**
 * Created by attylax on 13.12.2017.
 */

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DataUploader {
    private static DatabaseReference mDatabase;

    private DataUploader(){}

    public static void uploadPost(Post post){
        if(mDatabase == null){
            mDatabase = FirebaseDatabase.getInstance().getReference();
        }
        Gson gson = new Gson();
        String json = gson.toJson(post);

        String key = mDatabase.child("adverts").push().getKey();
        mDatabase.child("adverts").child(key).setValue(json);
    }
}
