package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Database.DBAccess;
import com.example.myapplication.Model.Costumer;
import com.example.myapplication.Model.Tool;

import static android.support.design.widget.Snackbar.LENGTH_LONG;

public class EditToolProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_tool_profile);


        Intent intent = getIntent();
        int idTool = intent.getIntExtra("IdTool", -1);
        if (idTool == -1 ) {
            //ERROR!
            //TODO!
            finish();
        }
        String toolDescription = intent.getStringExtra("toolDescription");
        String toolName = intent.getStringExtra("toolName");
        String categoryName = intent.getStringExtra("categoryName");

        int idCategory = intent.getIntExtra("IdCategory", 0);
        int idCostumer = intent.getIntExtra("IdCostumer", 0);
        String costumerName = intent.getStringExtra("customerName");

        Button saveButton = findViewById(R.id.btnSave);
        Button cancelButton = findViewById(R.id.btnCancel);
        Button deleteButton = findViewById(R.id.btnDelete);

        // get current owner
        App currentApp = (App)getApplication();
        Costumer currentUser = currentApp.getUser();

        // set owner field
        TextView ownerField = findViewById(R.id.owner);
        ownerField.setText(currentUser.costumerName);

        setTitle("Edit Tool");
        TextView itemName = findViewById(R.id.title);
        itemName.setText(toolName);

        TextView categoryText = (TextView)findViewById(R.id.category);
        categoryText.setText(categoryName);

        TextView descriptionText = (TextView)findViewById(R.id.description);
        descriptionText.setText(toolDescription);


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


                // if all fields empty just cancel
                if (title.equals("") || category.equals("") || description.equals(""))
                {
                    Snackbar mySnackBar = Snackbar.make(v, "Error: Cannot leave the fields blank", LENGTH_LONG);
                    mySnackBar.show();
                }


                Tool fuckingTool = new Tool(idTool, title, description, 0,
                        idCategory, idCostumer, costumerName, category);
                // insert the item name, category, and description
                DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
                dbAccess.open();
                dbAccess.updateTool(idTool, fuckingTool);
                dbAccess.close();

                // exit back to mytools
                Toast toast = Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_LONG);
                toast.show();
                // exit
                Intent intent = new Intent(EditToolProfile.this, MyTools.class);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delete that tool from database
                DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
                dbAccess.open();
                dbAccess.deleteTool(idTool);
                dbAccess.close();

                Toast toast = Toast.makeText(getApplicationContext(), "Item Deleted", Toast.LENGTH_LONG);
                toast.show();
                // exit tool profile
                Intent intent = new Intent(EditToolProfile.this, MyTools.class);
                startActivity(intent);

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
