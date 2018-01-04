package com.project.attylax.sapiadvertiser;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;

public class FileUploader extends Service {

    /** Intent Actions **/
    public static final String ACTION_UPLOAD = "action_upload";
    public static final String UPLOAD_COMPLETED = "upload_completed";
    public static final String UPLOAD_ERROR = "upload_error";

    /** Intent Extras **/
    public static final String EXTRA_FILE_URI = "extra_file_uri";
    public static final String EXTRA_DOWNLOAD_URL = "extra_download_url";

    private int mNumTasks = 0;
    private StorageReference mStorageRef;

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
            stopSelf();
        }
    }

    public FileUploader() {
    }

    @Override
    public void onCreate(){
        super.onCreate();
        mStorageRef = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (ACTION_UPLOAD.equals(intent.getAction())) {
            Uri fileUri = Uri.parse(intent.getStringExtra(EXTRA_FILE_URI));
            uploadFromUri(fileUri);
        }

        return START_REDELIVER_INTENT;
    }

    private void uploadFromUri(final Uri fileUri) {

        // [START_EXCLUDE]
        taskStarted();
        // [END_EXCLUDE]

        // [START get_child_ref]
        // Get a reference to store file at photos/<FILENAME>.jpg
        final StorageReference photoRef = mStorageRef.child("photos")
                .child(fileUri.getLastPathSegment());
        // [END get_child_ref]

        // Upload file to Firebase Storage
        photoRef.putFile(fileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Upload succeeded

                        // Get the public download URL
                        Uri downloadUri = Objects.requireNonNull(taskSnapshot.getMetadata()).getDownloadUrl();

                        // [START_EXCLUDE]
                        broadcastUploadFinished(downloadUri, fileUri);

                        taskCompleted();
                        // [END_EXCLUDE]
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Upload failed


                        // [START_EXCLUDE]
                        broadcastUploadFinished(null, fileUri);

                        taskCompleted();
                        // [END_EXCLUDE]
                    }
                });
    }

    private boolean broadcastUploadFinished(@Nullable Uri downloadUrl, @Nullable Uri fileUri) {
        boolean success = downloadUrl != null;

        String action = success ? UPLOAD_COMPLETED : UPLOAD_ERROR;

        Log.d("Post", downloadUrl.toString());

        Intent broadcast = new Intent(action)
                .putExtra(EXTRA_DOWNLOAD_URL, downloadUrl.toString())
                .putExtra(EXTRA_FILE_URI, fileUri.toString());
        return LocalBroadcastManager.getInstance(getApplicationContext())
                .sendBroadcast(broadcast);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public static IntentFilter getIntentFilter() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(UPLOAD_COMPLETED);
        filter.addAction(UPLOAD_ERROR);

        return filter;
    }
}
