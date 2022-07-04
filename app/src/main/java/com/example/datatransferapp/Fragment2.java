package com.example.datatransferapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Fragment2 extends Fragment {
    ListView imagesListView;
    ArrayList<String> emails=new ArrayList<String>();
    FirebaseAuth auth;
    ArrayAdapter arrayAdapter;
    ArrayList<DataSnapshot> images=new ArrayList<DataSnapshot>();
    ArrayList<String> imagesUrl=new ArrayList<String>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment2,container,false);

              emails.clear();
              images.clear();
              imagesUrl.clear();
                auth= FirebaseAuth.getInstance();
            imagesListView=view.findViewById(R.id.imagesListView);
            arrayAdapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,emails);
            imagesListView.setAdapter(arrayAdapter);
            FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("images").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                    emails.add("From:"+snapshot.child("From").getValue().toString());
                    images.add(snapshot);

                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                    int index=0;
                    for(DataSnapshot snap:images){
                        if(snap.getKey()==snapshot.getKey()){
                            images.remove(index);
                            emails.remove(index);
                            break;
                        }
                        index++;
                    }
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            imagesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    DataSnapshot snapshot=images.get(position);
                    String count=snapshot.child("Count").getValue().toString();
                    int ccount= Integer.parseInt(count);
                    imagesUrl.clear();
                    for(int i=0;i<ccount;i++){
                        imagesUrl.add(snapshot.child(Integer.toString(i+1)).getValue().toString());
                    }

                    Intent intent=new Intent(getActivity().getApplicationContext(),showImages.class);
                    // intent.putExtra("imageName",snapshot.child("imageName").value as String)
                    intent.putStringArrayListExtra("images",imagesUrl);
                    intent.putExtra("from",snapshot.child("From").getValue().toString());
                    // intent.putExtra("snapKey",snapshot.key)
                    startActivity(intent);


                }
            });

            imagesListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                  final int pos=position;

                    new AlertDialog.Builder(getActivity())
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setTitle("Are u Sure!!")
                            .setMessage(" You want to delete this image?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    FirebaseDatabase.getInstance().getReference().child("users").child(auth.getCurrentUser().getUid()).child("images").child(images.get(pos).getKey()).removeValue();

                                }
                            })
                            .setNegativeButton("No",null)
                            .show();









                    return true;
                }
            });
            return view;
        }

    }








