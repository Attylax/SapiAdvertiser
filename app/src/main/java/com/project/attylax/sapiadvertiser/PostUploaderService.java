package com.project.attylax.sapiadvertiser;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

/**
 * This class is responsible for upload post to firebase database
 */
public class PostUploaderService extends Service {

    /**
     * Intent Actions
     **/
    public static final String POST_ACTION_UPLOAD = "post_action_upload";
    public static final String POST_UPLOAD_COMPLETED = "post_upload_completed";
    public static final String POST_UPLOAD_ERROR = "post_upload_error";

    /**
     * Intent Extras
     **/
    public static final String EXTRA_POST_OBJECT = "extra_Post_object";
    public static final String EXTRA_POST_ID = "extra_post_id";

    private DatabaseReference mDatabase;

    private BroadcastReceiver broadcastReceiver;
    private int mNumTasks = 0;
    private int counter = 0;
    private Post post;
    public void taskStarted() {
        changeNumberOfTasks(1);
    }

    public void taskCompleted() {
        changeNumberOfTasks(-1);
    }

    private synchronized void changeNumberOfTasks(int delta) {
        mNumTasks += delta;

        // If there are no tasks left, stop the service
        if (mNumTasks <= 0) {
            stop();
            stopSelf();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String id = null;
        if (POST_ACTION_UPLOAD.equals(intent.getAction())) {
            Log.d("Post", "Megjott");
            post = intent.getParcelableExtra(EXTRA_POST_OBJECT);
            Gson gson = new Gson();
            String json = gson.toJson(post);
            Log.d("Post", json);
            if(intent.hasExtra(EXTRA_POST_ID)){
                id = intent.getStringExtra(EXTRA_POST_ID);
            }
            uploadPost(post, id);
        }

        return START_REDELIVER_INTENT;
    }

    /**
     * This function inserts post into database with ID which one got as parameter.
     * @param post
     * @param id
     */
    private void uploadPost(Post post, String id) {
        taskStarted();

        final String key;

        if(id == null) {
            key = mDatabase.child("posts").push().getKey();
        }else{
            key = id;
        }

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {


                switch (intent.getAction()) {

                    case FileUploader.UPLOAD_COMPLETED:
                            String stringPath = intent.getStringExtra(FileUploader.EXTRA_DOWNLOAD_URL);
                            Uri path = null;
                            if(stringPath != null) {
                                path = Uri.parse(stringPath);
                                Log.d("Post", path.toString());
                            }
                            Post post = getPost();
                            stringPath = intent.getStringExtra(FileUploader.EXTRA_FILE_URI);
                            if(stringPath != null) {
                                Log.i("uzenetek",stringPath);
                                int index = post.indexOf(stringPath);

                                post.setDownloadLink(index, path.toString());
                            }
                            ++counter;
                            if(counter == post.getSize()){
                                Gson gson = new Gson();
                                String json = gson.toJson(post);
                                mDatabase.child("posts").child(key).setValue(json);

                                broadcastUploadFinished(true);
                                taskCompleted();
                            }

                        break;
                    case FileUploader.UPLOAD_ERROR:
                        broadcastUploadFinished(false);
                        taskCompleted();
                        break;
                }
            }
        };
        start();
        for(int i = 0; i < post.getSize(); ++i){
            startService(new Intent(this, FileUploader.class)
                    .putExtra(FileUploader.EXTRA_FILE_URI, post.getImagePath(i))
                    .setAction(FileUploader.ACTION_UPLOAD));

        }
    }

    private Post getPost(){
        return post;
    }

    public void start() {

        LocalBroadcastManager manager = LocalBroadcastManager.getInstance(this);

        manager.registerReceiver(broadcastReceiver, FileUploader.getIntentFilter());
    }


    public void stop() {

        // Unregister download receiver
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    /**
     * This function sends back if this upload are successful or not.
     * @param success
     * @return
     */
    private boolean broadcastUploadFinished(boolean success) {

        String action = success ? POST_UPLOAD_COMPLETED : POST_UPLOAD_ERROR;

        Intent broadcast = new Intent(action);
        return LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(broadcast);


    }

    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(POST_UPLOAD_COMPLETED);
        filter.addAction(POST_UPLOAD_ERROR);

        return filter;
    }
}