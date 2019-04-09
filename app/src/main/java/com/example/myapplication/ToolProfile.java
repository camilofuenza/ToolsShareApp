package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ToolProfile extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    startActivity(new Intent(getApplicationContext(), ToolList.class));
                    return true;
                case R.id.navigation_dashboard:
                    startActivity(new Intent(getApplicationContext(), MyTools.class));
                    return true;

            }
            return false;
        }
    };

    public void borrowButtonClick(View v)
    {
        Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("https:www.gmail.com"));
        startActivity(browse);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tool_profile);

        assert getSupportActionBar() != null;   //null check
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //show back button

        Button borrowButton = (Button)findViewById(R.id.borrowButton);


        Intent intent = getIntent();
        String customerName = intent.getStringExtra("customerName");
        String isAvailable = intent.getStringExtra("isAvailable");
        String toolDescription = intent.getStringExtra("toolDescription");
        String toolName = intent.getStringExtra("toolName");
        String categoryName = intent.getStringExtra("categoryName");


        setTitle(toolName);



        TextView ownerText = (TextView)findViewById(R.id.owner);
        ownerText.setText(customerName);

        TextView categoryText = (TextView)findViewById(R.id.category);
        categoryText.setText(categoryName);

        TextView descriptionText = (TextView)findViewById(R.id.description);
        descriptionText.setText(toolDescription);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }

}
