package com.truongtvd.callsmsfilter.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by truongtvd on 6/10/14.
 */
public class DataCallFilterHelper extends SQLiteOpenHelper {

    private static final String TABLE_NAME="CallFilter";
    private static final int VERSION_CODE=1;
    private static final String FILE_NAME="FilterCall.db";
    private Context context;

    public DataCallFilterHelper(Context context) {
        super(context, FILE_NAME,null, VERSION_CODE);
        this.context=context;

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE="CREATE TABLE "+TABLE_NAME+" (phone TEXT PRIMARY KEY, time INT)";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
