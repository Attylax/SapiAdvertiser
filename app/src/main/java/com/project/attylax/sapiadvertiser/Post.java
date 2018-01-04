package com.project.attylax.sapiadvertiser;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * The Post structure which contains relevant information about an event,
 * such as its title, the poster's name, its date, time and location, and a description about the event itself
 * Implements the Parcelable interface, for easy transfer between intents
 */
public class Post implements Parcelable{
    private static final String TAG = "POST";
    private String eventName;
    private String writerName;
    private String writerId;
    private String description;
    private String shortDescription;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private double price;


    private List<String> imagesPath = new ArrayList<>();

    Post(String eventName, String writerName, String writerId, String description, String descShort, String eventDate, String eventLocation, String eventTime, double price, List<String> imagesPath) {
        this.eventName = eventName;
        this.writerName = writerName;
        this.writerId = writerId;
        this.description = description;
        this.shortDescription = descShort;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.price = price;
        this.imagesPath = imagesPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(eventName);
        dest.writeString(writerName);
        dest.writeString(writerId);
        dest.writeString(description);
        dest.writeString(shortDescription);
        dest.writeString(eventDate);
        dest.writeString(eventTime);
        dest.writeString(eventLocation);

        dest.writeString(Double.toString(price));
        Log.d("Post", Double.toString(price));
        for(String uri : imagesPath){

            Log.d("Post", uri);
            dest.writeString(uri);
        }

    }

    private Post(Parcel in){
        eventName = in.readString();
        Log.d("Post", eventName);
        writerName = in.readString();
        Log.d("Post", writerName);
        writerId = in.readString();
        description = in.readString();
        Log.d("Post", description);
        shortDescription = in.readString();
        Log.d("Post", shortDescription);

        eventDate = in.readString();
        Log.d("Post", eventDate);
        eventTime = in.readString();
        Log.d("Post", eventTime);
        eventLocation = in.readString();
        Log.d("Post", eventLocation);

        price = Double.parseDouble(in.readString());

        Log.d("Post", Double.toString(price));

        for(int i = 0; i < 3; ++i){
            imagesPath.add(in.readString());
            Log.d("Post", imagesPath.get(i));
        }
    }

    public static final Parcelable.Creator<Post> CREATOR = new Parcelable.Creator<Post>() {
        public Post createFromParcel(Parcel in) {
            return new Post(in);
        }

        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    public String getWriterId() {
        return writerId;
    }

    public void setDownloadLink(int index, String uri){
        imagesPath.set(index, uri);
    }

    public String getImagePath(int index){
        return imagesPath.get(index);
    }

    public int indexOf(String path){
        return imagesPath.indexOf(path);
    }


    public int getSize() {
        return imagesPath.size();
    }

    public String getEventName() {
        return eventName;
    }

    public String getWriterName() {
        return writerName;
}

    public String getDescription() {
        return description;
    }


    public String getShortDescription() {
        return shortDescription;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventTime() {
        return eventTime;
    }

    public double getPrice() {
        return price;
    }


    public List<String> getImagesPath() {
        return imagesPath;

    }
}
