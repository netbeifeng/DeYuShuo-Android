package com.deyushuo.connect;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class MyDatabaseHelper_Life extends SQLiteOpenHelper {
    private Context mContext;
    public static final String CREATE_LIFE = "create table LIFE (" +
            "titel text, " +
            "link text , " +
            "date text ," +
            "time text"+
            ")";

    public MyDatabaseHelper_Life(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_LIFE);
        Toast.makeText(mContext, "创建Life数据库成功", Toast.LENGTH_SHORT);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
