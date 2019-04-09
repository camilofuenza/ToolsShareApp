package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapters.OwnerToolsAdapter;
import com.example.myapplication.Adapters.ToolsAdapter;
import com.example.myapplication.Database.DBAccess;
import com.example.myapplication.Model.Tool;

import java.util.List;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class MyTools extends AppCompatActivity {


    public ListView toolList;
    public OwnerToolsAdapter adapter;
    public List<Tool> list;
    public TextView noToolsMessage;

    private int _userID;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(MyTools.this, ToolList.class));
                    return true;
                case R.id.navigation_dashboard:
                    return true;
            }
            return false;
        }
    };


    public interface CallbackInterface {
        void doTask(Tool tool);
    }

    private CallbackInterface deleteCallback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_tools);



        App app = (App) getApplication();

        if (app.getUser() != null) {

            _userID = app.getUser().getIdCostumer();

        } else {
            //ERROR!
            //TODO: display error message.
            _userID = 1;
        }

        //Set up delete callback
        this.deleteCallback = new CallbackInterface() {
            @Override
            public void doTask(Tool tool) {
                //nothing...
                Intent intent = new Intent(getApplicationContext(), EditToolProfile.class);
                intent.putExtra("IdTool", tool.getIdTool());
                intent.putExtra("customerName", tool.getCostumerName());
                intent.putExtra("isAvailable", tool.getIsAvailable());
                intent.putExtra("toolDescription", tool.getToolDescription());
                intent.putExtra("toolName", tool.getToolName());
                intent.putExtra("categoryName", tool.getCategoryName());
                intent.putExtra("IdCategory", tool.getIdCategory());
                intent.putExtra("IdCostumer", tool.getCostumerName());

                startActivity(intent);
            }
        };

        //not really sure what this does... TODO: remove?
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu = navigation.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);

        //---

        SetUpToolList(fetchToolsListFromDB());

        //Set up FAB
        FloatingActionButton fab = findViewById(R.id.addToolFAB);
        fab.setOnClickListener(view -> {
            //startActivity(new Intent(MyTools.this, ToolsProfile.class);
            startActivity(new Intent(MyTools.this, CreateToolProfile.class));
        });


    }

    private void SetUpToolList(List<Tool> list) {
        //Set up Tool List

        adapter = new OwnerToolsAdapter(this, list);
        adapter.SetDeleteCallback(deleteCallback);

        toolList = findViewById(R.id.lvTools);
        toolList.setAdapter(adapter);

        //Handle Item Click
        toolList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) view.getParent();
                if (listView != null) {

                    Tool tool = list.get(position);
                    if (tool != null) {


                        //startActivity: toolProfile. TODO
                        Intent intent = new Intent(getApplicationContext(), EditToolProfile.class);

                        intent.putExtra("toolDescription", tool.getToolDescription());
                        intent.putExtra("toolName", tool.getToolName());
                        intent.putExtra("categoryName", tool.getCategoryName());

                        startActivity(intent);

                    }
                }
            }
        });
    }

    private List<Tool> fetchToolsListFromDB() {
        List<Tool> output;
        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        output = dbAccess.getToolListForUser(_userID);
        dbAccess.close();

        return output;
    }

    private class RefreshToolsList extends AsyncTask<Void, Void, List<Tool>> {

        @Override
        protected List<Tool> doInBackground(Void... voids) {

            return fetchToolsListFromDB();
        }

        @Override
        protected void onPostExecute(List<Tool> result) {

            SetUpToolList(result);
        }
    }

}
