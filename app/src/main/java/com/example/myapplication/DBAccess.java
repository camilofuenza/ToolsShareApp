package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBAccess {

    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DBAccess instance;

    private DBAccess(Context context){
    this.openHelper=new  DBHelper(context);

    }

    public static DBAccess getInstance(Context context){
        if(instance==null){
            instance= new DBAccess(context);
        }
        return instance;
    }

    public void open(){
        this.db=openHelper.getWritableDatabase();

    }

    public void close(){
        if(db!=null){
            db.close();
        }
    }

    public List<Costumer> getCostumerList(){
        Costumer costumer= null;
        List<Costumer> costumerList= new ArrayList<>();
        open();
        Cursor cursor= db.rawQuery("select * from costumer",null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            costumer= new Costumer(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getInt(3));
            costumerList.add(costumer);
            cursor.moveToNext();

        }
        cursor.close();
        close();
        return costumerList;
    }
}
