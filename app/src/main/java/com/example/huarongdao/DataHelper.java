package com.example.huarongdao;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME="Rank.db";
    private static final int VERSION = 1;

    public DataHelper(Context context)
    {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table rank(checkid INT PRIMARY KEY , steps TEXT)";
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table rank");
        onCreate(db);
    }
}
