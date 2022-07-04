package com.example.datatransferapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {


    private ArrayList<Uri> images1;
    public MyAdapter(ArrayList<Uri> images1){


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

        holder.imageViewRecycle.setImageURI(images1.get(position));

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

