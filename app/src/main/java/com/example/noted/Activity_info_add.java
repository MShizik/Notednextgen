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

public class Activity_info_add extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    Button add_info_btn;
    EditText info_name_obj,info_category_obj;
    TextView info_name_warning_obj, info_category_warning_obj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_add);

        auth= FirebaseAuth.getInstance();
        db= FirebaseDatabase.getInstance();
        users=db.getReference().child(Activity_a_login_registration.Username).child(Activity_group_table.chosen_group).child(Activity_people_table.chosen_person);

        add_info_btn=findViewById(R.id.info_add_btn);
        info_name_obj=findViewById(R.id.info_add_field);
        info_category_obj=findViewById(R.id.info_category_add_field);
        info_name_warning_obj=findViewById(R.id.info_add_warning_field);
        info_category_warning_obj=findViewById(R.id.info_category_add_warning_field);

        add_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (info_name_obj.equals("")){
                    info_name_warning_obj.setText("It is required field!");

                }
                if (info_category_obj.equals("")){
                    info_category_warning_obj.setText(("It is required field"));

                }
                if (!info_category_obj.equals("") && !info_name_obj.equals("")){
                    info_name_warning_obj.setText("");
                    info_category_warning_obj.setText((""));
                    users.child(info_category_obj.getText().toString()).setValue(info_name_obj.getText().toString());

                }
            }
        });
        info_name_obj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (info_name_obj.equals("")){
                }
                else{
                    info_name_warning_obj.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        info_category_obj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (info_name_obj.equals("")){
                }
                else{
                    info_category_warning_obj.setText("");

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}