package com.example.noted;


import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Activity_people_table extends AppCompatActivity {

    public static String chosen_person;

    EditText search_obj;
    Button add_btn, delete_btn;
    ListView people_shower;
    TextView people_table_main_fraze_obj;

    String[] PEOPLE;
    ArrayAdapter<String> adapter_people;

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_table);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference();

        search_obj=findViewById(R.id.people_table_search_field);
        add_btn=findViewById(R.id.people_table_add_group_btn);
        delete_btn=findViewById(R.id.people_table_delete_group_btn);
        people_shower=findViewById(R.id.people_table_list_people_field);
        people_table_main_fraze_obj=findViewById(R.id.people_table_main_fraze);

        PEOPLE=Activity_people_downloader.user_people.toArray(new String[Activity_people_downloader.user_people.size()]);
        adapter_people=new ArrayAdapter<>(this,R.layout.custom_list_view, R.id.autoCompleteItems, PEOPLE);
        people_shower.setAdapter(adapter_people);

        search_obj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                (Activity_people_table.this).adapter_people.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        people_shower.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chosen_person=Activity_people_downloader.user_people.get(position).toString();
                startActivity(new Intent(Activity_people_table.this, Activity_info_downloader.class));
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.child(Activity_a_login_registration.Username).child(Activity_group_table.chosen_group).setValue(null);
                users.child(Activity_a_login_registration.Username).child("Groups").child(Activity_group_table.chosen_group).setValue(null);
                Activity_group_table.chosen_group=null;
                startActivity(new Intent (Activity_people_table.this, Activity_group_downloader.class));

            }
        });

        add_btn.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent (Activity_people_table.this, Activity_people_add.class));
            }
        }));
        people_table_main_fraze_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_people_table.this, Activity_group_downloader.class));
            }
        });
    }
}