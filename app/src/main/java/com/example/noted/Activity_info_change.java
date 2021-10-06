package com.example.noted;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_info_change extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    TextView name_place;
    EditText category_place, info_place;
    Button change_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_change);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference().child(Activity_a_login_registration.Username).child(Activity_group_table.chosen_group).child(Activity_people_table.chosen_person);

        name_place=findViewById(R.id.info_change_name_place);
        category_place=findViewById(R.id.info_change_category_field);
        info_place=findViewById(R.id.info_change_new_info_field);
        change_btn=findViewById(R.id.info_change_btn);

        name_place.setText(Activity_people_table.chosen_person);
        category_place.setText(Activity_info_table.chosen_category_info);
        info_place.setText(Activity_info_table.chosen_value_info);

        Intent back_to_downloader= new Intent(Activity_info_change.this, Activity_info_downloader.class);

        change_btn.setOnClickListener(v -> {
            if (!category_place.getText().toString().equals(Activity_info_table.chosen_category_info) || (!info_place.getText().toString().equals(Activity_info_table.chosen_value_info))){
                if (!category_place.getText().toString().equals(Activity_info_table.chosen_category_info) && info_place.getText().toString().equals(Activity_info_table.chosen_value_info )){
                    users.child(Activity_info_table.chosen_category_info).setValue(null);
                    users.child(category_place.getText().toString()).setValue(Activity_info_table.chosen_value_info);
                    startActivity(back_to_downloader);
                }
                if(category_place.getText().toString().equals(Activity_info_table.chosen_category_info) && !info_place.getText().toString().equals(Activity_info_table.chosen_value_info)) {
                    users.child(Activity_info_table.chosen_category_info).setValue(info_place.getText().toString());
                    startActivity(back_to_downloader);
                }
                if (!category_place.getText().toString().equals(Activity_info_table.chosen_category_info) &&!info_place.getText().toString().equals(Activity_info_table.chosen_value_info)){
                    users.child(Activity_info_table.chosen_category_info).setValue(null);
                    users.child(category_place.getText().toString()).setValue(info_place.getText().toString());
                    startActivity(back_to_downloader);
                }
            }
            else{
                startActivity(back_to_downloader);
            }
        });

        name_place.setOnClickListener(v -> startActivity(back_to_downloader));

    }
}