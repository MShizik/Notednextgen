package com.example.noted;


import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_users_downloader extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    public static ArrayList<String> user_names, passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_downloader);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference("Users");

        user_names=new ArrayList<>();
        passwords=new ArrayList<>();

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                if (Activity_a_login_registration.Username!=null){
                    startActivity(new Intent(Activity_users_downloader.this, Activity_group_downloader.class));
                }
                else {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        user_names.add(data.getKey());
                        passwords.add(data.getValue().toString());
                    }
                    startActivity(new Intent(Activity_users_downloader.this, Activity_a_login_registration.class));
                }

            }

            @Override
            public void onCancelled( DatabaseError error) {

            }
        });


    }
}