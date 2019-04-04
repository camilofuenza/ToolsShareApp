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

import com.example.myapplication.Adapters.CostumerAdapter;
import com.example.myapplication.Adapters.ToolsAdapter;
import com.example.myapplication.Database.DBAccess;
import com.example.myapplication.Model.Costumer;
import com.example.myapplication.Model.Tool;

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

public class DisplayTools extends AppCompatActivity {

    private static final int PERMISSION_STORAGE_CODE = 1000;
    public ListView toolList;
    public ToolsAdapter adapter;
    public List<Tool> list;
    public Button btnUrl;
    public Button btnDownload;
    String file_url = "https://static1.squarespace.com/static/5761c870e4fcb510bfb26cbe/t/581225739f7456092ce219bf/1477590761207/Rock+Climbing+Fundamentals-15+meg+file.pdf";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_tools);


        toolList = (ListView) findViewById(R.id.lvTools);

        DBAccess dbAccess = DBAccess.getInstance(getApplicationContext());
        dbAccess.open();
        list = dbAccess.getToolList();
        adapter = new ToolsAdapter(this, list);
        toolList.setAdapter(adapter);


        dbAccess.close();


        btnUrl = (Button) findViewById(R.id.btnUrl);
        btnUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse("https:www.gmail.com"));
                startActivity(browse);
            }
        });


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


    class DownloadFile extends AsyncTask<String, Integer, String> {

        ProgressDialog progressDialog;

        protected void onPreExecute() {
            progressDialog = new ProgressDialog(DisplayTools.this);
            progressDialog.setTitle("Download in Progress..");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(100);
            progressDialog.setProgress(0);
            progressDialog.show();
        }

        protected String doInBackground(String... params) {
            try {
                String path = params[0];
                URL url = new URL(path);
                URLConnection connection = url.openConnection();
                connection.connect();
                int lengthOfFile = connection.getContentLength();


                File new_folder = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "Climbing");
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

                File outputFile = new File(new_folder, "Climbing.pdf");
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

        protected void onProgressUpdate(Integer... values) {
            progressDialog.setProgress(values[0]);
        }

        protected void onPostExecute(String result) {
            progressDialog.hide();
            Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
            String path = "sdcard/Climbing/Climbing.jpg";
        }
    }
}


