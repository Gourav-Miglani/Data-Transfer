package com.example.datatransferapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import java.util.ArrayList;

public class showImages extends AppCompatActivity {
    RecyclerView recyclerView2;
    private ArrayList<String> images=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);
        images.clear();
        recyclerView2=findViewById(R.id.recyclerView2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this));
        Intent intent=getIntent();
        images=intent.getStringArrayListExtra("images");
        recyclerView2.setAdapter(new MyAdapter1(images));


    }
}