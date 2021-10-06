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

public class Activity_people_add extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    Button add_people_btn;
    EditText people_name_obj;
    TextView people_name_warning_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_add);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference();

        add_people_btn=findViewById(R.id.people_add_btn);
        people_name_obj=findViewById(R.id.people_add_field);
        people_name_warning_obj=findViewById(R.id.people_add_warning_field);

        add_people_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (people_name_obj.equals("")){
                    people_name_warning_obj.setText("It is required field!");

                }
                else{
                    people_name_warning_obj.setText("");
                    users.child(Activity_a_login_registration.Username).child(Activity_group_table.chosen_group).child("Names").child(people_name_obj.getText().toString()).setValue(" ");
                    users.child(Activity_a_login_registration.Username).child(Activity_group_table.chosen_group).child(people_name_obj.getText().toString()).child("Required").setValue(" ");
                    startActivity(new Intent(Activity_people_add.this,Activity_people_downloader.class));

                }
            }
        });
        people_name_obj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (people_name_obj.equals("")){
                }
                else{
                    people_name_warning_obj.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}