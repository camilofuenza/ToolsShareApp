// File         : Login.java
// Program      : TinderBox
// Programmer   : Lucas Roes
// Date         : April 2019
// Purpose      : java code for login screen

package com.example.myapplication;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.Snackbar;
import android.widget.Button;
import android.widget.EditText;

import com.example.myapplication.Database.DBAccess;
import com.example.myapplication.App;
import com.example.myapplication.Model.Costumer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.security.Permission;
import java.util.List;

import static android.provider.Telephony.Mms.Part.FILENAME;

import static android.support.design.widget.Snackbar.LENGTH_LONG;


public class Login extends AppCompatActivity {

    private EditText emailInput;
    private EditText postalInput;

    //Camilo
    private static final int PERMISSION_STORAGE_CODE = 1000;
    public Button btnUrl;
    public Button btnDownload;
    String file_url = "https://fitenaccion.cl/TinderBoxPDF.pdf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //check and create permissions to make use of external memory of the phone
        btnDownload = (Button) findViewById(R.id.btnDownload);
        btnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                            PackageManager.PERMISSION_DENIED) {

                        String[] permission = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_STORAGE_CODE);

                    } else {
                        DownloadFile download = new DownloadFile();
                        download.execute(file_url);
                    }
                } else {
                    DownloadFile download = new DownloadFile();
                    download.execute(file_url);
                }
            }


        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_STORAGE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    DownloadFile download = new DownloadFile();
                    download.execute(file_url);
                } else {
                    Toast.makeText(this, "Permision denied", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

    //Async task and progress bar to donload pdf

    class DownloadFile extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;

        //create and set progress bar
        protected void onPreExecute() {
            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setTitle("Download in Progress..");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }
        // download and locate pdf
        protected String doInBackground(String... params) {
            try {
                String path = params[0];
                URL url = new URL(path);
                URLConnection connection = url.openConnection();
                connection.connect();
                int lengthOfFile = connection.getContentLength();


                File new_folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "TinderBox");
                if (!new_folder.exists()) {
                    if (new_folder.mkdir()) {
                        Log.i("Info", "Folder succesfully created");
                    } else {
                        Log.i("Info", "Failed to create folder");
                    }
                } else {
                    Log.i("Info", "Folder already exists");
                }


                InputStream in = new BufferedInputStream(url.openStream(), 8192);
                byte buffer[] = new byte[1024];
                int total = 0;
                int count = 0;

                File outputFile = new File(new_folder, "TinderBox.pdf");
                OutputStream output = new FileOutputStream(outputFile);

                while ((count = in.read(buffer)) != -1) {
                    total += count;

                    // writing data to file
                    output.write(buffer, 0, count);
                    int progress = total * 100 / lengthOfFile;
                    publishProgress(progress);
                }


                // closing streams
                in.close();
                output.close();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "Download complete...";
        }

        //set progress to progressbar
        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        //close progress bar and send msg
        protected void onPostExecute(String result) {
            progressDialog.hide();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

        }
    }


    public void OnLoginClick(View view)
    {
        emailInput = findViewById(R.id.EmailTextBox);
        postalInput = findViewById(R.id.PostalTextBox);

        // get user input
        String email = emailInput.getText().toString();
        String postalcode = postalInput.getText().toString();

        //check for blank input
        if (email.equals(""))
        {
            Snackbar mySnackBar = Snackbar.make(view, "Error: Cannot leave Email blank", LENGTH_LONG);
            mySnackBar.show();
            return;
        }

        if (postalcode.equals(""))
        {
            Snackbar mySnackBar = Snackbar.make(view, "Error: Cannot leave Postal Code blank", LENGTH_LONG);
            mySnackBar.show();
            return;
        }

        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();


        // check if user exists in database
        // create user object
        Costumer TinderBoxUser = dbAccess.GetUser(email, postalcode);

        // if not, create user in database
        if (TinderBoxUser == null)
        {
            TinderBoxUser = dbAccess.InsertUser(email, postalcode);
        }

        dbAccess.close();

        // save current user to preferences
        App currentApp = (App)getApplication();
        currentApp.setUser(TinderBoxUser);


        Snackbar mySnackBar = Snackbar.make(view, "Logged on" + TinderBoxUser.getCostumerName(), LENGTH_LONG);
        mySnackBar.show();


        // go to next activity

        Intent intent = new Intent(this, ToolList.class);
        startActivity(intent);


    }

}
