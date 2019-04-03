package com.example.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    public ListView costumerslist;
    public CostumerAdapter adapter;
    public List<Costumer> list;

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

    }
}
