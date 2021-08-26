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

public class Activity_info_table extends AppCompatActivity {
    public static String chosen_info;

    EditText search_obj;
    Button add_btn, logout_btn;
    ListView info_shower;

    String[] INFO;
    ArrayAdapter<String> adapter_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_table);

        search_obj=findViewById(R.id.group_table_search_field);
        add_btn=findViewById(R.id.group_table_add_group_btn);
        logout_btn=findViewById(R.id.group_table_delete_group_btn);
        info_shower=findViewById(R.id.group_table_list_groups_field);

        INFO=Activity_info_downloader.user_info.toArray(new String[Activity_group_downloader.user_groups.size()]);
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
                chosen_info=Activity_group_downloader.user_groups.get(position).toString();
                startActivity(new Intent(Activity_info_table.this, Activity_info_change.class));
            }
        });
    }
}