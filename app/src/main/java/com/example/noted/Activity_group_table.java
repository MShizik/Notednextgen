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

public class Activity_group_table extends AppCompatActivity {

    public static String chosen_group;

    EditText search_obj;
    Button add_btn, logout_btn;
    ListView groups_shower;
    TextView group_table_main_fraze_obj;

    String[] GROUPS;
    ArrayAdapter<String> adapter_groups;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_table);

        search_obj=findViewById(R.id.group_table_search_field);
        add_btn=findViewById(R.id.group_table_add_group_btn);
        logout_btn=findViewById(R.id.group_table_delete_group_btn);
        groups_shower=findViewById(R.id.group_table_list_groups_field);
        group_table_main_fraze_obj=findViewById(R.id.group_table_main_fraze);

        GROUPS=Activity_group_downloader.user_groups.toArray(new String[Activity_group_downloader.user_groups.size()]);
        adapter_groups=new ArrayAdapter<String>(this, R.layout.custom_list_view, R.id.autoCompleteItems, GROUPS);
        groups_shower.setAdapter(adapter_groups);

        search_obj.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                (Activity_group_table.this).adapter_groups.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        groups_shower.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                chosen_group=Activity_group_downloader.user_groups.get(position).toString();
                startActivity(new Intent(Activity_group_table.this, Activity_people_downloader.class));
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity_a_login_registration.Username=null;
                startActivity(new Intent(Activity_group_table.this, Activity_users_downloader.class));

            }
        });

        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent( Activity_group_table.this, Activity_group_add.class));
            }
        });

        group_table_main_fraze_obj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Activity_group_table.this, Activity_a_login_registration.class));
            }
        });









    }
}