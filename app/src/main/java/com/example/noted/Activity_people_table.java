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

import androidx.appcompat.app.AppCompatActivity;

public class Activity_people_table extends AppCompatActivity {

    public static String chosen_person;

    EditText search_obj;
    Button add_btn, delete_btn;
    ListView people_shower;

    String[] PEOPLE;
    ArrayAdapter<String> adapter_people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_table);

        search_obj=findViewById(R.id.people_table_search_field);
        add_btn=findViewById(R.id.people_table_add_group_btn);
        delete_btn=findViewById(R.id.people_table_delete_group_btn);
        people_shower=findViewById(R.id.people_table_list_people_field);

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
                chosen_person=Activity_group_downloader.user_groups.get(position).toString();
                startActivity(new Intent(Activity_people_table.this, Activity_info_downloader.class));
            }
        });
    }
}