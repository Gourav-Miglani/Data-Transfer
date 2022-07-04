package com.example.datatransferapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class messageCreateActivity extends AppCompatActivity {
   EditText messageEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_create);
        messageEditText=findViewById(R.id.messageEditText);
    }
    public void chooseRecipient(View view){
        Intent intent=new Intent(this,chooseRecipientActivity.class);
        intent.putExtra("message",messageEditText.getText().toString());
        startActivity(intent);
    }
}