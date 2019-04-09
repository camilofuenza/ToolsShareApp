package com.example.myapplication.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Model.Costumer;
import com.example.myapplication.Model.Tool;

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
        Cursor cursor= db.rawQuery("select C.idCostumer, C.costumerName, C.idLocation, " +
                "L.postalCode from costumer C join Location L on (C.idLocation = L.idLocation)"
                ,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            costumer= new Costumer(cursor.getInt(0),cursor.getString(1),
                    cursor.getInt(2), cursor.getString(3));
            costumerList.add(costumer);
            cursor.moveToNext();

        }
        cursor.close();
        close();
        return costumerList;
    }

    public List<Tool> getToolList(int userId){
        Tool tool= null;
        List<Tool> toolList= new ArrayList<>();
        open();
        Cursor cursor= db.rawQuery("select A.idTool, " +
                " A.toolName, "+
                " A.toolDescription, " +
                " A.isAvailable, "+
                " A.idCategory, "+
                " B.idCostumer, "+
                " B.costumerName, "+
                " C.categoryName "+
                "from TOOL A join COSTUMER B on (A.idCostumer = B.idCostumer) " +
                "join CATEGORY C on (A.idCategory=C.idCategory)" +
                "where A.isAvailable = 1 AND A.idCostumer != ?", new String[] { String.valueOf(userId)});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            tool= new Tool(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getString(6),
                    cursor.getString(7));
            toolList.add(tool);
            cursor.moveToNext();

        }
        cursor.close();
        close();
        return toolList;
    }

    public List<Tool> getToolListForUser(int UserID){
        Tool tool= null;
        List<Tool> toolList= new ArrayList<>();
        open();
        Cursor cursor= db.rawQuery("select A.idTool, " +
                        " A.toolName, "+
                        " A.toolDescription, " +
                        " A.isAvailable, "+
                        " A.idCategory, "+
                        " B.idCostumer, "+
                        " B.costumerName, "+
                        " C.categoryName "+
                        "from TOOL A join COSTUMER B on (A.idCostumer = B.idCostumer) " +
                        "join CATEGORY C on (A.idCategory=C.idCategory) where A.idCostumer = ?",
                new String[] {String.valueOf(UserID)});
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            tool= new Tool(cursor.getInt(0),cursor.getString(1),cursor.getString(2),
                    cursor.getInt(3),cursor.getInt(4),cursor.getInt(5),cursor.getString(6),
                    cursor.getString(7));
            toolList.add(tool);
            cursor.moveToNext();

        }
        cursor.close();
        close();
        return toolList;
    }

    public void deleteTool(int toolID) {
        open();
        db.delete("Tool", "idTool = ?", new String[]{String.valueOf(toolID)});
        close();
    }


    public Costumer InsertUser(String name, String postalCode)
    {
        Costumer output = null;
        int postalcodeID = GetPostalCodeID(postalCode);

        if(postalcodeID == -1)
    {
        ContentValues insertvalues = new ContentValues();
        insertvalues.put("postalCode", postalCode);
        open();
        postalcodeID = (int)db.insert("Location", "", insertvalues);
        close();
    }

        ContentValues insertvalues = new ContentValues();
        insertvalues.put("costumerName", name);
        insertvalues.put("idLocation", postalcodeID);
        open();
        int customerID = (int)db.insert("Costumer", "", insertvalues);
        close();

        output = GetUser(name, postalCode);
        return output;
    }


    // check if input postalcode exists in database
    public int GetPostalCodeID(String postalcode)
    {
        int output = -1;
        open();
        Cursor cursor= db.rawQuery("SELECT Location.IDLocation "+
                        "from LOCATION Where Location.postalCode = ?",
                new String[] {postalcode});
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            output = cursor.getInt(0);
        }

        cursor.close();
        close();
        return output;
    }


    public Costumer GetUser(String name, String postalCode)
    {
        Costumer output = null;

        open();
        Cursor cursor= db.rawQuery("SELECT Costumer.idCostumer, Costumer.costumerName, Costumer.idLocation, " +
                        "Location.postalCode "+
                        "from Costumer Inner Join Location ON " +
                        "(Costumer.IDLocation = Location.IDLocation) where Costumer.CostumerName = ? AND Location.postalCode = ?",
                new String[] {name, postalCode});
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            output = new Costumer(cursor.getInt(0), cursor.getString(1),
                    cursor.getInt(2), cursor.getString(3));
        }

        cursor.close();
        close();
        return output;
    }

    public boolean insertTool(Tool tool){

        int categoryID = GetCategoryID(tool.getCategoryName());

        if(categoryID == -1)
        {
            ContentValues insertvalues = new ContentValues();
            insertvalues.put("categoryName", tool.getCategoryName());
            open();
            categoryID = (int)db.insert("Category", "", insertvalues);
            close();
        }

        open();
        ContentValues values= new ContentValues();
        values.put("toolName",tool.getToolName());
        values.put("toolDescription",tool.getToolDescription());
        values.put("isAvailable",tool.getIsAvailable());
        values.put("idCategory", categoryID);
        values.put("idCostumer",tool.getIdCostumer());

        long transaction= db.insert("Tool",null,values);
        if(transaction == -1){
            close();
            return false;

        }
        else{
            close();
            return true;
        }


    }

    // check if input postalcode exists in database
    public int GetCategoryID(String categoryName)
    {
        int output = -1;
        open();
        Cursor cursor= db.rawQuery("SELECT Category.idCategory "+
                        "from Category Where Category.categoryName = ?",
                new String[] {categoryName});
        cursor.moveToFirst();

        if (cursor.getCount() > 0) {
            output = cursor.getInt(0);
        }

        cursor.close();
        close();
        return output;
    }

//    public boolean deleteTool(int idTool){
//        open();
//        db.execSQL("DELETE FROM " +  "TOOL" + "WHERE idTool = " + idTool + ";");
//
//        close();
//        return true;
//
//
//    }

    public void updateTool(int idTool, Tool tool){
        open();
//        String query= "UPDATE Tool SET toolName = " + tool.getToolName() + ", toolDescription = " + tool.getToolDescription() +
//                 ", idCategory = " + tool.getIdCategory() + ", idCostumer = " + tool.getIdCostumer() + " WHERE idTool = " + idTool;
//
//        db.execSQL(query);

        ContentValues contentValues = new ContentValues();
        contentValues.put("toolName", tool.getToolName());
        contentValues.put("toolDescription", tool.getToolDescription());
        contentValues.put("idCategory", tool.getIdCategory());


        db.update("Tool", contentValues, "idTool = ?", new String[]{String.valueOf(tool.getIdTool())});
        close();

    }

//    public String getPostalCode(int idPostalCode) {
//        String output = null;
//
//        open();
//
//        Cursor cursor = db.rawQuery("select postalCode, ")
//    }
}
