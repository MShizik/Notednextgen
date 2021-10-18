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

public class Activity_info_table extends AppCompatActivity {
    public static String chosen_category_info, chosen_value_info;

    EditText search_obj;
    Button add_btn, delete_btn;
    ListView info_shower;
    TextView info_table_main_fraze_obj;

    String[] INFO;
    ArrayAdapter<String> adapter_info;

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_table);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        users=db.getReference();

        search_obj=findViewById(R.id.info_table_search_field);
        add_btn=findViewById(R.id.info_table_add_info_btn);
        delete_btn=findViewById(R.id.info_table_delete_info_btn);
        info_shower=findViewById(R.id.info_table_list_info_field);
        info_table_main_fraze_obj=findViewById(R.id.info_table_main_fraze);

        INFO=Activity_info_downloader.user_info.toArray(new String[Activity_info_downloader.user_info.size()]);
        adapter_info=new ArrayAdapter<String>(this, R.layout.custom_list_view, R.id.autoCompleteItems, INFO);
        info_shower.setAdapter(adapter_info);


        search_obj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                (Activity_info_table.this).adapter_info.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        info_shower.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chosen_category_info=Activity_info_downloader.user_category_info.get(position).toString();
                chosen_value_info=Activity_info_downloader.user_value_info.get(position).toString();
                startActivity(new Intent(Activity_info_table.this, Activity_info_change.class));
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                users.child(Activity_a_login_registration.Username).child(Activity_group_table.chosen_group).child(Activity_people_table.chosen_person).setValue(null);
                users.child(Activity_a_login_registration.Username).child(Activity_group_table.chosen_group).child("Names").child(Activity_people_table.chosen_person).setValue(null);
                Activity_people_table.chosen_person=null;
                startActivity(new Intent (Activity_info_table.this, Activity_people_downloader.class));
            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_info_table.this, Activity_info_add.class));

            }

        });

        info_table_main_fraze_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_info_table.this, Activity_people_downloader.class));
            }
        });

    }
}