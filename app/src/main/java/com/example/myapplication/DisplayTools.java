package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.Adapters.ToolsAdapter;
import com.example.myapplication.Database.DBAccess;
import com.example.myapplication.Model.Tool;

import java.util.List;

public class DisplayTools extends AppCompatActivity {


    public ListView toolList;
    public ToolsAdapter adapter;
    public List<Tool> list;
    public Button btnUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tools);


        toolList = (ListView) findViewById(R.id.lvTools);

        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        list = dbAccess.getToolList(0);
        adapter = new ToolsAdapter(this, list);
        toolList.setAdapter(adapter);


        dbAccess.close();



       btnUrl=(Button)findViewById(R.id.btnUrl);
        btnUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse= new Intent(Intent.ACTION_VIEW, Uri.parse("https:www.gmail.com"));
                startActivity(browse);
            }
        });
    }

    }

