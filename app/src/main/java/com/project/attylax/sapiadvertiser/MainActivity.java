package com.project.attylax.sapiadvertiser;

import android.icu.lang.UCharacter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //it's MAAAAAGIIIIC! ^^

        Post a = new Post("Attila1", "Ez egy szoveg", "Ez");

        DataUploader.uploadPost(a);
    }

}
