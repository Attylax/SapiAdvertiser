package com.project.attylax.sapiadvertiser; /**
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
    private String writerName;
    private String writerId;
    private String description;
    private String shortDescription;
    private String date;
    private String time;
    private double price;
    private String place;
    private String title;


    private List<Uri> imagesPath = new ArrayList<>();

    public Post(String title, String writerName, String writerId, String description, String shortDescription, String date, String time, String place, double price, List<Uri> imagesPath) {
        this.title = title;
        this.writerName = writerName;
        this.writerId = writerId;
        this.description = description;
        this.shortDescription = shortDescription;
        this.date = date;
        this.time = time;
        this.price = price;
        this.place = place;
        this.imagesPath = imagesPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(writerName);
        dest.writeString(writerId);
        dest.writeString(description);
        dest.writeString(shortDescription);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(place);
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
        title = in.readString();
        writerName = in.readString();
        Log.d("Post", writerName);
        writerId = in.readString();
        description = in.readString();
        Log.d("Post", description);
        shortDescription = in.readString();
        Log.d("Post", shortDescription);
        date = in.readString();
        Log.d("Post", date);
        time = in.readString();
        Log.d("Post", time);

        place = in.readString();
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

    public int getSize(){
        return imagesPath.size();
    }

    public String getDescription() {
        return description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getPlace() {
        return place;
    }

    public double getPrice() {
        return price;
    }
    public String getTitle(){
        return title;
    }
}
