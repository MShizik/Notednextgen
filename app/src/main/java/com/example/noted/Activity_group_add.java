package com.example.noted;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_group_add extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    Button add_group_btn;
    EditText group_name_obj;
    TextView group_name_warning_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_add);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference();

        add_group_btn=findViewById(R.id.group_add_btn);
        group_name_obj=findViewById(R.id.group_add_field);
        group_name_warning_obj=findViewById(R.id.group_add_warning_field);

        add_group_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (group_name_obj.equals("")){
                    group_name_warning_obj.setText("It is required field!");

                }
                else{
                    group_name_warning_obj.setText("");
                    users.child(Activity_a_login_registration.Username).child("Groups").child(group_name_obj.getText().toString()).setValue(" ");
                    users.child(Activity_a_login_registration.Username).child(group_name_obj.getText().toString()).child("Names").child("Test").setValue(" ");
                    startActivity(new Intent(Activity_group_add.this,Activity_group_downloader.class));

                }
            }
        });
        group_name_obj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (group_name_obj.equals("")){
                }
                else{
                    group_name_warning_obj.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }
}