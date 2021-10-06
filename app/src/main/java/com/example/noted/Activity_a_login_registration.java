package com.example.noted;

import android.content.Intent;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_a_login_registration extends AppCompatActivity {

     FirebaseAuth auth;
     FirebaseDatabase db;
     DatabaseReference users;

     EditText password_obj, email_obj, fio_obj;
     TextView email_warn, password_warn, fio_warning, info_obj;
     String password, email, fio, unusual_fio;

     Boolean email_check, password_check;

     String[] USERS, PASSWORDS;

     Button login_btn, signup_btn;

     public static String Username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alogin_registration);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference("Users");

        password_obj=findViewById(R.id.registration_password_field);
        email_obj=findViewById(R.id.group_add_field);

        login_btn=findViewById(R.id.registration_login_btn);
        signup_btn=findViewById(R.id.registration_sugnup_btn);
        email_warn=findViewById(R.id.registration_email_warning_field);
        password_warn=findViewById(R.id.registration_password_warning_field);

        info_obj=findViewById(R.id.add_group_main_field);

        email_check=false;
        password_check=false;


        USERS=Activity_users_downloader.user_names.toArray(new String[Activity_users_downloader.user_names.size()]);
        PASSWORDS=Activity_users_downloader.passwords.toArray(new String[Activity_users_downloader.passwords.size()]);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                password=password_obj.getText().toString();
                email=email_obj.getText().toString().replace("@","").replace(" ","").replace(".","");

                if (email.length()<=1){
                    email_warn.setText("It is required field");
                    info_obj.setText("Something went wrong!");
                    info_obj.setTextColor(Color.parseColor("#E2474B"));
                }
                else {
                    email_warn.setText("");
                    info_obj.setText("");

                }
                if (password.length()<=1){
                    password_warn.setText("It is required field");
                    info_obj.setText("Something went wrong!");
                    info_obj.setTextColor(Color.parseColor("#E2474B"));
                }
                else {
                    password_warn.setText("");
                    info_obj.setText("");
                }

                for (int c=0; c<=Activity_users_downloader.user_names.size()-1; c++){
                    System.out.println(USERS[c]);
                    System.out.println(email);
                    if (USERS[c].equals(email)){
                        System.out.println(email);
                        email_check=true;
                        if (PASSWORDS[c].equals(password)){
                            password_check=true;
                        }
                    }
                }

                if (password.length()>1 && email.length()>1 &&  email_check==false){
                    email_warn.setText("This email doesn't exist");
                    info_obj.setText("Something went wrong!");
                    info_obj.setTextColor(Color.parseColor("#E2474B"));
                }
                if (password.length()>1 && email.length()>1 &&  email_check==true && password_check==false){
                    password_warn.setText("Password is wrong");
                    info_obj.setText("Something went wrong!");
                    info_obj.setTextColor(Color.parseColor("#E2474B"));
                }

                if ( password_check && email_check){
                    Username=email;
                    startActivity(new Intent(Activity_a_login_registration.this, Activity_group_downloader.class));
                    info_obj.setText("");

                }

            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                password=password_obj.getText().toString();
                email=email_obj.getText().toString().replace("@","").replace(" ","").replace(".","");


                Username=email;

                if (email.length()<=1){
                    email_warn.setText("It is required field");
                    info_obj.setText("Something went wrong!");
                    info_obj.setTextColor(Color.parseColor("#E2474B"));
                }
                else {
                    email_warn.setText("");

                    info_obj.setText("");
                }
                if (password.length()<=1){
                    password_warn.setText("It is required field");
                    info_obj.setText("Something went wrong!");
                    info_obj.setTextColor(Color.parseColor("#E2474B"));
                }
                else {
                    password_warn.setText("");
                    info_obj.setText("");
                }

                for (int c=0; c<=Activity_users_downloader.user_names.size()-1; c++){
                    if (USERS[c]==email){
                        email_check=true;
                    }
                }

                if (email_check){
                    info_obj.setText("This user is already exist.");
                    info_obj.setTextColor(Color.parseColor("#E2474B"));

                }
                else{
                    if (password.length()>1 && email.length()>1 ) {
                        info_obj.setText("");
                        users.child(email).setValue(password);
                        db.getReference().child(email).child("Groups").child("Zero").setValue(" ");
                        Username=email;
                        startActivity(new Intent(Activity_a_login_registration.this, Activity_users_downloader.class));
                    }
                }

            }
        });

        info_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email_obj.getText().toString().equals("")==false || password_obj.getText().toString().equals("")==false) {
                    email_warn.setText("");
                    password_warn.setText("");
                    password_obj.setText("");
                    email_obj.setText("");
                    int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    switch (currentNightMode) {
                        case Configuration.UI_MODE_NIGHT_NO:
                            info_obj.setText("You're welcome!");
                            info_obj.setTextColor(getResources().getColor(R.color.dark_background));
                            break;
                        case Configuration.UI_MODE_NIGHT_YES:
                            info_obj.setText("You're welcome!");
                            info_obj.setTextColor(getResources().getColor(R.color.white_background));
                            break;
                    }
                }
                else {

                    int currentNightMode = getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
                    switch (currentNightMode) {
                        case Configuration.UI_MODE_NIGHT_NO:
                            info_obj.setText("How are you?");
                            info_obj.setTextColor(getResources().getColor(R.color.dark_background));
                            break;
                        case Configuration.UI_MODE_NIGHT_YES:
                            info_obj.setText("How are you?");
                            info_obj.setTextColor(getResources().getColor(R.color.white_background));
                            break;
                    }


                }

            }
        });





    }
}