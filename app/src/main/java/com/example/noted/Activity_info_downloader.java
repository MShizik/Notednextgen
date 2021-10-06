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

public class Activity_info_downloader extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    public static ArrayList<String> user_info, user_category_info, user_value_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_downloader);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference().child(Activity_a_login_registration.Username).child(Activity_group_table.chosen_group).child(Activity_people_table.chosen_person);

        user_info=new ArrayList<>();
        user_category_info=new ArrayList<>();
        user_value_info=new ArrayList<>();

        users.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot snapshot) {

                user_info.clear();
                user_category_info.clear();
                user_value_info.clear();

                    for (DataSnapshot data : snapshot.getChildren()) {
                        System.out.println(data.getKey().toString());
                        if (!data.getKey().equals("Required")) {
                            user_info.add(data.getKey().toString() + ":" + "\n" + data.getValue().toString());
                            user_category_info.add(data.getKey().toString());
                            user_value_info.add(data.getValue().toString());
                        }
                        startActivity(new Intent(Activity_info_downloader.this, Activity_info_table.class));
                    }
                }
            @Override
            public void onCancelled( DatabaseError error) {

            }
        });


    }
}