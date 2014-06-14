package com.truongtvd.callsmsfilter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.truongtvd.callsmsfilter.model.CallFilter;

import java.util.ArrayList;

/**
 * Created by truongtvd on 6/10/14.
 */
public class DataCallFilterHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME = "CallFilter";
    private static final int VERSION_CODE = 1;
    private static final String FILE_NAME = "FilterCall.db";
    private Context context;

    public DataCallFilterHelper(Context context) {
        super(context, FILE_NAME, null, VERSION_CODE);
        this.context = context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (phone TEXT PRIMARY KEY,name TEXT,title TEXT,content TEXT,show INT)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<CallFilter> getListFilter() {
        ArrayList<CallFilter> list = new ArrayList<CallFilter>();
        SQLiteDatabase db = getReadableDatabase();
        String SQL = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(SQL, null);
        CallFilter callFilter = null;
        while (cursor.moveToNext()) {
            callFilter = new CallFilter();
            callFilter.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            callFilter.setName(cursor.getString(cursor.getColumnIndex("name")));
            list.add(callFilter);
        }
        cursor.close();
        db.close();
        return list;
    }

    public int getFilterCall(String phone) {
        int count = 0;
        SQLiteDatabase db = getReadableDatabase();
        String SQL = "SELECT * FROM " + TABLE_NAME + " WHERE phone='" + phone
                + "'";
        Cursor cursor = db.rawQuery(SQL, null);
        count = cursor.getCount();
        db.close();
        return count;

    }


    public void addFilterCall(String phone, String name, String tilte, String content, int show) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("phone", phone);
        values.put("name", name);
        values.put("title", tilte);
        values.put("content", content);
        values.put("show", show);
        db.insert(TABLE_NAME, null, values);

    }

    public void deleteFilter(String number) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "phone='" + number + "'", null);
    }

    public int getShowNotification(String phone) {
        SQLiteDatabase db = getReadableDatabase();
        int show = 0;
        String SQL = "SELECT * FROM " + TABLE_NAME + " WHERE phone='" + phone
                + "'";
        Cursor cursor = db.rawQuery(SQL, null);
        while (cursor.moveToNext()) {

            show = cursor.getInt(cursor.getColumnIndex("show"));

        }
        cursor.close();
        db.close();
        return show;
    }

    public String getTitleNotification(String phone) {
        SQLiteDatabase db = getReadableDatabase();
        String title = null;
        String SQL = "SELECT * FROM " + TABLE_NAME + " WHERE phone='" + phone
                + "'";
        Cursor cursor = db.rawQuery(SQL, null);
        while (cursor.moveToNext()) {

            title = cursor.getString(cursor.getColumnIndex("title"));

        }
        cursor.close();
        db.close();
        return title;
    }

    public String getContentNotification(String phone) {
        SQLiteDatabase db = getReadableDatabase();
        String content = null;
        String SQL = "SELECT * FROM " + TABLE_NAME + " WHERE phone='" + phone
                + "'";
        Cursor cursor = db.rawQuery(SQL, null);
        while (cursor.moveToNext()) {
            content = cursor.getString(cursor.getColumnIndex("content"));
        }
        cursor.close();
        db.close();
        return content;
    }
}
