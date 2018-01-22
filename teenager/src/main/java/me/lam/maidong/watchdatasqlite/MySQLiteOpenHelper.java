package me.lam.maidong.watchdatasqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**

 * Created by scb on 2016/3/3.
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {

    public MySQLiteOpenHelper(Context context) {
        super(context, "watchdata.db", null, 1);
    }

    public void onCreate(SQLiteDatabase db) {//:CREATE INDEX data on watchdata (DATA)
        db.execSQL("create table watchdata(id integer primary key autoincrement,Token varchar(100),DATA varchar(999999999))");
        db.execSQL("CREATE INDEX DATA ON watchdata (DATA)");

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//CREATE INDEX  ON watchdata ()
}
