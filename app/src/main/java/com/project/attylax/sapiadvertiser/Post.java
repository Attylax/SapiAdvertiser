package com.project.attylax.sapiadvertiser; /**
 * Created by attylax on 04.12.2017.
 */

import java.util.ArrayList;

public class Post {
    private static final String TAG = "com.project.attylax.sapiadvertiser.Post";
    private String writerName;
    private String description;
    private String raw;
    private ArrayList<String> imagesPath = new ArrayList<>();

    public Post(String writerName, String description, String raw) {
        this.writerName = writerName;
        this.description = description;
        this.raw = raw;
    }
}
