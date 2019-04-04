package com.example.myapplication;

import com.example.myapplication.Database.DBAccess;
import com.example.myapplication.Model.Tool;
import com.example.myapplication.Adapters.ToolsAdapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ToolList extends AppCompatActivity {

    private TextView mTextMessage;

    private List<Tool> list;
    private ListView toolList;
    private ToolsAdapter adapter;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_list);






        toolList = (ListView)findViewById(R.id.toolList);

        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        list = dbAccess.getToolList();
        dbAccess.close();

        adapter = new ToolsAdapter(this, list);
        toolList.setAdapter(adapter);




        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
