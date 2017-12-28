package com.project.attylax.sapiadvertiser; /**
 * Created by attylax on 04.12.2017.
 */

import java.util.ArrayList;

public class Post {
    private static final String TAG = "com.project.attylax.sapiadvertiser.Post";
    private String eventName;
    private String writerName;
    private String description;
    private String descShort;
    private String eventLocation;
    private String eventDate;
    private ArrayList<String> imagesPath = new ArrayList<>();

    public Post(String eventName, String writerName, String description, String descShort, String eventLocation, String eventDate) {
        this.eventName = eventName;
        this.writerName = writerName;
        this.description = description;
        this.descShort = descShort;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
    }

    public String getWriterName() {
        return writerName;
    }

    public String getDescription() {
        return description;
    }

    public String getDescShort() {
        return descShort;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventName() {
        return eventName;
    }

}
