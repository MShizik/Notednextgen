 package com.example.noted;


import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Activity_group_downloader extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    public static ArrayList<String> user_groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_downloader);


        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference().child(Activity_a_login_registration.Username).child("Groups");

        user_groups= new ArrayList<>();

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                user_groups.clear();


                if (Activity_group_table.chosen_group != null) {
                    startActivity(new Intent(Activity_group_downloader.this, Activity_people_downloader.class));
                }
                else {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        System.out.println(data.getKey().toString());
                        if(data.getKey().equals("Zero")==false) {
                            user_groups.add(data.getKey().toString());
                        }
                        startActivity(new Intent(Activity_group_downloader.this, Activity_group_table.class));
                    }
                }
            }


            @Override
            public void onCancelled( DatabaseError error) {

            }
        });



    }
}