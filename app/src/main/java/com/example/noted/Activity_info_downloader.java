package com.example.noted;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Activity_info_downloader extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    public static ArrayList<String> штащ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_downloader);
    }
}