package com.example.datatransferapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MyAdapter1 extends RecyclerView.Adapter<MyAdapter1.myViewHolder> {


    private ArrayList<String> images1;
    public MyAdapter1(ArrayList<String> images1){


        this.images1=images1;
    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_layout_images,parent,false);
        return new myViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        imageDownloader task=new imageDownloader();
        Bitmap myImage;
        try {
            myImage=task.execute(images1.get(position)).get();
            holder.imageViewRecycle.setImageBitmap(myImage);

        }catch(Exception e){
            e.printStackTrace();
        }


    }
    public class imageDownloader extends AsyncTask<String,Void, Bitmap> {


        @Override
        protected Bitmap doInBackground(String... urls) {
            try {
                URL url=new URL(urls[0]);
                HttpURLConnection connection=(HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream in=connection.getInputStream();
                Bitmap myBitmap= BitmapFactory.decodeStream(in);
                return myBitmap;
            }catch(Exception e){
                e.printStackTrace();
                return null;

            }
        }

    }
    @Override
    public int getItemCount() {

        return images1.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewRecycle;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewRecycle=itemView.findViewById(R.id.imageViewRecycle);
        }
    }

}

