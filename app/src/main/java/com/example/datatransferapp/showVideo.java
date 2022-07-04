package com.example.datatransferapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

public class showVideo extends AppCompatActivity {

      TextView textView7;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        final VideoView videoView2=findViewById(R.id.videoView2);
         textView7=findViewById(R.id.textView7);
        Intent intent=getIntent();
        String url=intent.getStringExtra("videoURL");
         String from=intent.getStringExtra("from");
         textView7.setText(from);

        MediaController mediaController=new MediaController(this);
        mediaController.setAnchorView(videoView2);
        videoView2.setMediaController(mediaController);
        videoView2.setVideoURI(Uri.parse(url));
        videoView2.start();

    }
}