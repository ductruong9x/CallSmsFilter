package com.truongtvd.callsmsfilter.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.truongtvd.callsmsfilter.model.LogFilter;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by truongtvd on 6/12/14.
 */
public class DataLogHelper extends SQLiteOpenHelper {



    private static final String TABLE_NAME = "Log";
    private static final int VERSION_CODE = 1;
    private static final String FILE_NAME = "FilterCall.db";
    private Context context;

    public DataLogHelper(Context context) {

        super(context, TABLE_NAME, null, VERSION_CODE);

        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (time INT PRIMARY KEY, phone TEXT)";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<LogFilter> getListFilter() {
        ArrayList<LogFilter> list = new ArrayList<LogFilter>();
        SQLiteDatabase db = getReadableDatabase();
        String SQL = "SELECT * FROM " + TABLE_NAME+" ORDER BY time DESC";
        Cursor cursor = db.rawQuery(SQL, null);
        LogFilter logFilter = null;
        while (cursor.moveToNext()) {
            logFilter = new LogFilter();
            logFilter.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            logFilter.setTime(cursor.getInt(cursor.getColumnIndex("time")));
            list.add(logFilter);
        }
        cursor.close();
        db.close();
        return list;
    }


    public void addLog(String number){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("phone",number);
        Calendar c = Calendar.getInstance();

        int unixTime = (int) (c.getTimeInMillis() / 1000L);
        values.put("time", unixTime);
        db.insert(TABLE_NAME,null,values);
    }

    public void deleteFilter(int number) {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, "time=" + number, null);
    }

    public void deleteAll() {
        SQLiteDatabase db = getReadableDatabase();
        db.delete(TABLE_NAME, null, null);
        db.close();
    }
}
