package com.example.datatransferapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.UUID;

public class videoCreateActivity extends AppCompatActivity {

    private Uri videouri;
    private static final int REQUEST_CODE = 101;
    private StorageReference videoref;
    Button send;
    int count;
    public class videoUpload extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            if(count==0) {
                count = 1;
                upload(videouri);

            }
            return "finished";

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            videoref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    //FirebaseDatabase.getInstance().getReference().child("URL").setValue( uri.toString());
                    Intent intent=new Intent(videoCreateActivity.this,chooseRecipientActivity3.class);
                    intent.putExtra("videoURL",uri.toString());
                    startActivity(intent);

                }
            });
        }
    }


    public void selectVideo(View view){
        select();
    }
    public void send(View view){



        //upload(videouri);
        if(send.getText().equals("SEND")) {
            send.setText("Uploading...Please Wait a moment");
        }
        send.setEnabled(false);
        videoUpload task = new videoUpload();
        task.execute("abc");







    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_create);
        count=0;
        send =findViewById(R.id.send);
        send.setText("SEND");
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        final String videoName= UUID.randomUUID().toString()+".3gp";
        videoref =storageRef.child("/videos" + "/"+videoName);
    }

    public void upload(final Uri videouri) {
        if (videouri != null) {
            UploadTask uploadTask = videoref.putFile(videouri);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(videoCreateActivity.this, "Upload failed: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    Toast.makeText(videoCreateActivity.this, "Upload complete",
                            Toast.LENGTH_LONG).show();
                    send.setEnabled(true);
                    send.setText("Choose Recipient");

                }

            });
        } else {
            Toast.makeText(videoCreateActivity.this, "Nothing to upload",
                    Toast.LENGTH_LONG).show();
        }
    }

        public void select() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE);
    }

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Video.Media.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        videouri = data.getData();
        final String uuri = getPath(videouri);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {




                VideoView videoView=findViewById(R.id.videoView);


                MediaController mediaController=new MediaController(this);
                mediaController.setAnchorView(videoView);
                videoView.setMediaController(mediaController);
                videoView.setVideoURI(Uri.parse(uuri));
                videoView.start();

            }

            Toast.makeText(this, "Video Selected", Toast.LENGTH_LONG).show();
        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, "Video selection cancelled.",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Failed to select video",
                    Toast.LENGTH_LONG).show();
        }
    }


}