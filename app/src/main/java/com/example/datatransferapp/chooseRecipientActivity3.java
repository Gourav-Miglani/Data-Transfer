package com.example.datatransferapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class chooseRecipientActivity3 extends AppCompatActivity {

    ListView chooseRecipientListView3;
    ArrayList<String> emails = new ArrayList<String>();
    ArrayList<String> keys = new ArrayList<String>();
    String url1;


    ArrayAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_recipient3);

        chooseRecipientListView3 = findViewById(R.id.chooseRecipientListView3);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, emails);
        chooseRecipientListView3.setAdapter(adapter);


        FirebaseDatabase.getInstance().getReference().child("users").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                String email = snapshot.child("email").getValue().toString();
                emails.add(email);
                keys.add(snapshot.getKey());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        chooseRecipientListView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, String> snapMap = new HashMap<String, String>();
                snapMap.put("From", FirebaseAuth.getInstance().getCurrentUser().getEmail());

                Intent intent = getIntent();
                url1=intent.getStringExtra("videoURL");

                    snapMap.put("URL",url1);


                FirebaseDatabase.getInstance().getReference().child("users").child(keys.get(position)).child("videos").push().setValue(snapMap);
                Intent intent1=new Intent(chooseRecipientActivity3.this,HomePageActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);




            }
        });





    }
}