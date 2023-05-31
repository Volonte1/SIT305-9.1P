package com.example.lostandfoundapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    Context context;
    //dbhelper ssetup
    public DbHelper(Context context) {
        super(context, "item.db", null, 1);
        this.context =context;
    }
    //oncreate function for dbhelper still from last app creation task
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists item (ID integer primary key autoincrement, type text, name text, phone text, description text,  date text, " +
                "location text, l1 text, l2 text );");

    }
    //onupgrade function for dbhelper still from last app creation task
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists item");

    }

    public Boolean insertData(  String type, String name, String phone, String description, String date, String location, Double l1, Double l2 )
    {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("type",type);
        contentValues.put("newArray",name);
        contentValues.put("phone",phone);
        contentValues.put("description",description);
        contentValues.put("date",date);
        contentValues.put("location",location);
        contentValues.put("item1",l1);
        contentValues.put("item2",l2);
        long result = DB.insert("item",null,contentValues);
        if(result == -1){
            return false;
        }else {
            return true;
        }
    }
    //function one that sets the array with the data inputted
    public ArrayList<LatLng> alldata1()
    {
        try {
            ArrayList<LatLng>ArrayList = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if(sqLiteDatabase!=null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from item",null);
                if(cursor.getCount()!=0){
                    while(cursor.moveToNext()){
                        double l1 = cursor.getDouble(7);
                        double l2 = cursor.getDouble(8);
                        ArrayList.add(new LatLng(l1,l2)
                        );
                    }
                    return ArrayList;
                }
                else {
                    Toast.makeText(context, "No data is ", Toast.LENGTH_SHORT).show();
                    return null;
                }
            }
            else {
                Toast.makeText(context, "Data is null", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        catch (Exception e){
            Toast.makeText(context, "getAllData:-"+e.getMessage(), Toast.LENGTH_SHORT).show();
            return null;
        }
    }
    //function twoo that sets the array with the data inputted
    public ArrayList<String> alldata2()
    {
        try {
            ArrayList<String>itemname = new ArrayList<>();
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            if(sqLiteDatabase!=null){
                Cursor cursor = sqLiteDatabase.rawQuery("select * from item",null);
                if(cursor.getCount()!=0){
                    while(cursor.moveToNext()){
                        String name = cursor.getString(2);
                            itemname.add(name);
                    }
                        return itemname;
                }
                else {
                    Toast.makeText(context, "Nothing in the database ", Toast.LENGTH_SHORT).show();
                        return null;
                }
            }
            else {
                Toast.makeText(context, "Data is empty", Toast.LENGTH_SHORT).show();
                    return null;
            }

        }
        catch (Exception e){
            Toast.makeText(context, "Please get all the appropriate data"+e.getMessage(), Toast.LENGTH_SHORT).show();
                return null;
        }
    }


    public String name(int ID){
        SQLiteDatabase MyDB = this.getReadableDatabase();

        Cursor cursor = MyDB.rawQuery("select * from item where ID = ID",null);
        if(cursor != null && cursor.moveToFirst()){
            return cursor.getString(2);}
        else {Toast.makeText(context, "Data is empty", Toast.LENGTH_SHORT).show();
            return null;       }
    }

    public String date(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("select * from item where ID = ID",null);
                cursor.moveToFirst();
                    return cursor.getString(5);
    }
    public String location(int ID){
        SQLiteDatabase MyDB = this.getWritableDatabase();
            Cursor cursor = MyDB.rawQuery("select * from item where ID = ID",null);
                cursor.moveToFirst();
                    return cursor.getString(6);
    }


    public void delete(String name){
        SQLiteDatabase MyDB = this.getWritableDatabase();
            MyDB.delete("item"," newArray=?",new String[]{name});
    }
}