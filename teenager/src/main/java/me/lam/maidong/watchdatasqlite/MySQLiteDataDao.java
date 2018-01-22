package me.lam.maidong.watchdatasqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;
import java.util.List;

import me.lam.maidong.utils.ShareUitls;

/**
 * Created by scb on 2016/3/3.
 */
public class MySQLiteDataDao {

    private static volatile MySQLiteDataDao sInstance = null;
    private MySQLiteOpenHelper helper;
    private String queryString = "";
    private Context context;
    private static String Token;


    private MySQLiteDataDao(Context activity) {
        this.context = activity;
        helper = new MySQLiteOpenHelper(activity);
    }

    public static MySQLiteDataDao getInstance(Context activity) {
        if (sInstance == null) {
            synchronized (MySQLiteDataDao.class) {
                if (sInstance == null) {
                    sInstance = new MySQLiteDataDao(activity);
                    Token = ShareUitls.getString(activity, "TOKEN", "0");
                }
            }
        }
        return sInstance;
    }
    public boolean Insert(String DATA) {
        if (DATA!=null||DATA.length()!=0) {
            return false;
        }
        if (isexist(DATA)) {
            return false;
        }
        SQLiteDatabase db = helper.getWritableDatabase();
        try {
            String sql = "insert into watchdata (Token,DATA) values (?,?)";
            SQLiteStatement stat = db.compileStatement(sql);
            db.beginTransaction();
            stat.bindString(1, Token);
            stat.bindString(2, DATA);
            long result = stat.executeInsert();
            if (result < 0) {
                return false;
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (null != db) {
                    db.endTransaction();
                    db.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


    public void deleteall() {
        SQLiteDatabase db = helper.getWritableDatabase();
        // ; --可以将递增数归零
        db.execSQL("DELETE FROM sqlite_sequence WHERE name = 'watchdata'");
        db.close();
    }


    public void deleteone(String DATA) {
        SQLiteDatabase db = helper.getWritableDatabase();
        db.execSQL("delete from watchdata where DATA ='" + DATA + "'");
        db.close();
    }
    public boolean tabIsExist() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = helper.getReadableDatabase();//
            cursor = db.rawQuery("select * from watchdata", null);
            if (cursor.moveToNext()) {
                int count = cursor.getInt(0);
                if (count > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
        }
        db.close();
        return false;
    }
    public List<String> Query(String Token) {
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from watchdata where where  Token='" + Token+ "'", null);
        List<String> mySQLiteBaseClassArrayList = new ArrayList<>();
        while (cursor.moveToNext()) {
            mySQLiteBaseClassArrayList.add(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return mySQLiteBaseClassArrayList;
    }

    public boolean isexist(String DATA) {
        boolean isexist = false;
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from watchdata where DATA='" + DATA + "'", null);
        if (cursor.getCount() != 0) {
            isexist = true;
        }
        cursor.close();
        db.close();
        return isexist;
    }

}
