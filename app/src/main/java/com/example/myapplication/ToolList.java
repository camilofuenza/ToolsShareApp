package com.example.myapplication;

import com.example.myapplication.Database.DBAccess;
import com.example.myapplication.Model.Tool;
import com.example.myapplication.Adapters.ToolsAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ToolList extends AppCompatActivity {

    private TextView mTextMessage;

    private List<Tool> list;
    private List<Tool> newList;
    private ListView toolList;
    private ToolsAdapter adapter;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:

                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(getApplicationContext(), MyTools.class));
                    return true;
            }
            return false;
        }
    };

    @Override
    public void onBackPressed() {
        moveTaskToBack(false);
    }

    @Override
    public void onRestart() {
        super.onRestart();

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_list);

        List<Tool> newList= new ArrayList<>();
        int userId = ((App)getApplication()).getUser().getIdCostumer();

        toolList = (ListView)findViewById(R.id.toolList);

        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        list = dbAccess.getToolList(userId);
        dbAccess.close();
        adapter = new ToolsAdapter(this, list);


        toolList.setAdapter(adapter);
        setTitle("Tools In Your Area");


        EditText toolNameFilter = (EditText) findViewById(R.id.toolNameFilter);




        toolNameFilter.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                newList.clear();
                if (s.length() > 0) {
                    Iterator<Tool> toolIterator = list.iterator();

                    while(toolIterator.hasNext()) {
                        Tool tool = toolIterator.next();
                        if (tool.getToolName().toLowerCase().contains(s.toString().toLowerCase())) {
                            newList.add(tool);
                        }
                    }

                    adapter = new ToolsAdapter(getApplicationContext(), newList);
                    toolList.setAdapter(adapter);
                } else {
                    adapter = new ToolsAdapter(getApplicationContext(), list);
                    toolList.setAdapter(adapter);
                }

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });





        toolList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Tool o = (Tool)toolList.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), ToolProfile.class);

                intent.putExtra("customerName", o.getCostumerName());
                intent.putExtra("isAvailable", o.getIsAvailable());
                intent.putExtra("toolDescription", o.getToolDescription());
                intent.putExtra("toolName", o.getToolName());
                intent.putExtra("categoryName", o.getCategoryName());

                startActivity(intent);
            }
        });






        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
    }

}
