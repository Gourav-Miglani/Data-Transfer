package com.example.datatransferapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.UUID;

public class imagesCreateActivity extends AppCompatActivity{
    RecyclerView recyclerView;
    private ArrayList<Uri> images=new ArrayList<>();
    ArrayList<String> Url=new ArrayList<>();
    ArrayList<String> names=new ArrayList<>();
    Button singleButton;

    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images_create);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        singleButton=findViewById(R.id.button5);
         singleButton.setText("SEND");
    }
    public void selectImages(View view){
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {


            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*"); //allows any image file type. Change * to specific extension to limit it
            //**The following line is the important one!
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1) {
            if(resultCode == Activity.RESULT_OK) {
                if(data.getClipData() != null) {
                    int count = data.getClipData().getItemCount(); //evaluate the count before the for loop --- otherwise, the count is evaluated every loop.
                    for(int i = 0; i < count; i++)
                        images.add(data.getClipData().getItemAt(i).getUri());
                    //do something with the image (save it to some directory or whatever you need to do with it here)
                    recyclerView.setAdapter(new MyAdapter(images));

                          }
            } else if(data.getData() != null) {
                String imagePath = data.getData().getPath();
                //do something with the image (save it to some directory or whatever you need to do with it here)
            }
        }
    }
  public void sendImages(){





             // Log.i("Info",Integer.toString(Url.size()));
             Intent intent=new Intent(imagesCreateActivity.this,chooseRecipientActivity2.class);

             intent.putStringArrayListExtra("imagesURL",Url);
             intent.putStringArrayListExtra("imageName",names);
             startActivity(intent);



  }
    public void upload(final ArrayList<Uri> imageUri) {
        if (imageUri.size() != 0) {
            for(i=0;i<images.size();i++) {
                final String imageName=UUID.randomUUID().toString()+".jpg";
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
              final StorageReference imageRef=storageRef.child("images/"+imageName);
                UploadTask uploadTask = imageRef.putFile(imageUri.get(i));


                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(imagesCreateActivity.this, "Upload failed: " + e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        Toast.makeText(imagesCreateActivity.this, "Upload complete",
                                Toast.LENGTH_LONG).show();
                        singleButton.setText("Choose Recipient..");
                        singleButton.setEnabled(true);

                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Url.add(uri.toString());
                                names.add(imageName);
                           //     Log.i("Info",uri.toString());
                            }
                        });

                    }

                });


            }
        } else {
            Toast.makeText(imagesCreateActivity.this, "Nothing to upload",
                    Toast.LENGTH_LONG).show();
        }
    }
    public void upload(View view){
        if(singleButton.getText().equals("SEND")) {
            singleButton.setText("Uploading... Please wait a moment!!!");




            upload(images);

        }else{
            sendImages();

        }
        singleButton.setEnabled(false);
        }

}