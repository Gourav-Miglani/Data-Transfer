package com.example.datatransferapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class ShowMessage extends AppCompatActivity {
    TextView textViewSentby;
    TextView textViewMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_message);
        textViewSentby=findViewById(R.id.textViewSentby);
        textViewMessage=findViewById(R.id.textViewMessage);
        Intent intent=getIntent();
        textViewMessage.setText(intent.getStringExtra("message"));
        textViewSentby.setText("Sent By:"+intent.getStringExtra("from"));
    }
}