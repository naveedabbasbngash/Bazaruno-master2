package com.example.bazaruno;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Search_Database extends SQLiteOpenHelper {

  private static final String db_name="Search.db";
  private static final int db_version=1;
  private static final String db_table="History";
  private static final String col1="Names";
  private static final String col2="Date";

    public Search_Database(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String queery="create table "+db_table+"(Names TEXT,Date date default (current_date))";
        db.execSQL(queery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+db_table);
        onCreate(db);
    }

    public boolean insert(String name)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(col1,name);
        Long result=db.insert(db_table,null,values);
        if(result == -1)
        {
            return false;
        }
        else
        {
            return true;
        }

    }

    public void clear()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("delete from "+db_table);
    }

    public Cursor result()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery("Select * from "+db_table,null);
        return cursor;
    }

}
