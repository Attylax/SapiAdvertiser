package com.project.attylax.sapiadvertiser;
/**
 * Created by attylax on 04.12.2017.
 */

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class Post implements Parcelable{
    private static final String TAG = "POST";
    private String eventName;
    private String writerName;
    private String writerId;
    private String description;
    private String descShort;
    private String eventLocation;
    private String eventDate;
    private String eventTime;
    private double price;
    private List<Uri> imagesPath = new ArrayList<>();

    public Post(String eventName, String writerName, String writerId, String description, String descShort, String eventDate, String eventLocation, String eventTime, double price, List<Uri> imagesPath) {
        this.eventName = eventName;
        this.writerName = writerName;
        this.writerId = writerId;
        this.description = description;
        this.descShort = descShort;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventTime = eventTime;
        this.price = price;
        this.imagesPath = imagesPath;
    }

    public Post(){

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
        dest.writeString(descShort);
        dest.writeString(eventDate);
        dest.writeString(eventTime);
        dest.writeString(eventLocation);
        dest.writeString(Double.toString(price));
        Log.d("Post", Double.toString(price));
        //dest.write
        //List<String> uriInString = new ArrayList<>(3);
        for(Uri uri : imagesPath){

            //uriInString.add(uri.toString());
            Log.d("Post", uri.toString());
            dest.writeString(uri.toString());
        }
        //dest.writeList(uriInString);

    }

    public Post(Parcel in){
        eventName = in.readString();
        Log.d("Post", eventName);
        writerName = in.readString();
        Log.d("Post", writerName);
        writerId = in.readString();
        description = in.readString();
        Log.d("Post", description);
        descShort = in.readString();
        Log.d("Post", descShort);
        eventDate = in.readString();
        Log.d("Post", eventDate);
        eventTime = in.readString();
        Log.d("Post", eventTime);
        eventLocation = in.readString();
        Log.d("Post", eventLocation);
        price = Double.parseDouble(in.readString());

        Log.d("Post", Double.toString(price));
        //List<String> uriInString = new ArrayList<>(3);
        //uriInString = in.readArrayList(null);
        for(int i = 0; i < 3; ++i){
            imagesPath.add(Uri.parse(in.readString()));
            Log.d("Post", imagesPath.get(i).toString());
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

    public void setDownloadLink(int index, Uri uri){
        imagesPath.set(index, uri);
    }

    public Uri getImagePath(int index){
        return imagesPath.get(index);
    }

    public int indexOf(Uri path){
        return imagesPath.indexOf(path);
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

    public String getDescShort() {
        return descShort;
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

    public List<Uri> getImagesPath() {
        return imagesPath;
    }
}
