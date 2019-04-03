package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.sql.SQLClientInfoException;

public class DBHelper extends SQLiteAssetHelper {
    private static final String DB_NAME="TTTools.db";
    private static final int DB_VERSION=1;



    public DBHelper(Context context){
    super(context, DB_NAME,null,DB_VERSION);
    }
}
