package com.example.myapplication.Database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBHelper extends SQLiteAssetHelper {
    private static final String DB_NAME="TTTools.db";
    private static final int DB_VERSION=1;



    public DBHelper(Context context){
    super(context, DB_NAME,null,DB_VERSION);
    }
}
