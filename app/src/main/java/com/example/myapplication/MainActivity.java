package com.example.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.myapplication.Adapters.CostumerAdapter;
import com.example.myapplication.Model.Costumer;
import com.example.myapplication.Database.DBAccess;


import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ListView costumerslist;
    public CostumerAdapter adapter;
    public List<Costumer> list;
    public Button btnSeeTools;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        costumerslist=(ListView) findViewById(R.id.lvCostumer);

        DBAccess dbAccess= DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        list= dbAccess.getCostumerList();
        adapter= new CostumerAdapter(this,list);
        costumerslist.setAdapter(adapter);

        dbAccess.close();

        btnSeeTools=(Button)findViewById(R.id.btnToolsPage);
        btnSeeTools.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,DisplayTools.class));
            }
        });
    }
}
