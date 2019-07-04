package com.example.biyesdemo.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String CREATE_USERDATA = "create table userData(" +
            "id integer primary key autoincrement,"
            + "nametext text,"
            + "password text)";
    public static final String CREATE_DATA = "create table Data(" +
            "id integer primary key autoincrement,"
            + "username text,"
            + "phone text,"
            + "price text,"
            + "name text,"
            + "number text,"
            + "time text,"
            + "total text)";


    private Context mContext;

    public MyDBHelper(Context context, String name, SQLiteDatabase.CursorFactory cursorFactory, int version) {
        super(context, name, cursorFactory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USERDATA);
        db.execSQL(CREATE_DATA);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists userData");
        db.execSQL("drop table if exists Data");
        onCreate(db);
    }
}
