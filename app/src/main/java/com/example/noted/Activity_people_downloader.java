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

public class Activity_people_downloader extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    public static ArrayList<String> user_people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_downloader);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference().child(Activity_a_login_registration.Username).child(Activity_group_table.chosen_group).child("Names");

        user_people=new ArrayList<>();

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {


                if (Activity_people_table.chosen_person != null) {
                    startActivity(new Intent(Activity_people_downloader.this, Activity_info_downloader.class));
                }
                else {
                    for (DataSnapshot data : snapshot.getChildren()) {
                        System.out.println(data.getKey().toString());
                        user_people.add(data.getKey().toString());
                        startActivity(new Intent(Activity_people_downloader.this, Activity_people_table.class));
                    }
                }
            }


            @Override
            public void onCancelled( DatabaseError error) {

            }
        });


    }
}