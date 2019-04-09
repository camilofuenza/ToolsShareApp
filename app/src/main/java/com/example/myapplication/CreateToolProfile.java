package com.example.myapplication;

import android.app.Activity;
import android.content.ClipData;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.DBAccess;
import com.example.myapplication.Model.Costumer;
import com.example.myapplication.Model.Tool;
import com.example.myapplication.App;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class CreateToolProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tool_profile);

        Button saveButton = findViewById(R.id.btnSave);
        Button cancelButton = findViewById(R.id.btnCancel);

        // get current owner
        App currentApp = (App)getApplication();
        Costumer currentUser = currentApp.getUser();

        // set owner field
        TextView ownerField = findViewById(R.id.owner);
        ownerField.setText(currentUser.costumerName);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get user input to title, description and category
                EditText editTitle = findViewById(R.id.title);
                String title = editTitle.getText().toString();

                EditText editCategory = findViewById(R.id.category);
                String category = editCategory.getText().toString();

                EditText editDescription = findViewById(R.id.description);
                String description = editDescription.getText().toString();


                // if any fields empty just cancel
                if (title.equals("") || category.equals("") || description.equals(""))
                {
                    Snackbar mySnackBar = Snackbar.make(v, "Error: Cannot leave the fields blank", LENGTH_LONG);
                    mySnackBar.show();
                    //finish();
                }
                else {


                    // insert the item name, category, and description
                    DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
                    dbAccess.open();
                    Tool insertTool = new Tool(0, title, description, 1,
                            0, currentUser.getIdCostumer(), currentUser.getCostumerName(),
                            category);
                    dbAccess.insertTool(insertTool);
                    dbAccess.close();
                    // exit back to mytools
                    //Snackbar mySnackBar = Snackbar.make(v, "Saved!", LENGTH_LONG);
                    //mySnackBar.show();
                    Toast toast = Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG);
                    toast.show();
                    //
                    Intent intent = new Intent(CreateToolProfile.this, MyTools.class);
                    startActivity(intent);
                }
            }
        });


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
