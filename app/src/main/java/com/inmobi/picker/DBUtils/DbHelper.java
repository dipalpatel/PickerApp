package com.test.kuluk.DBUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by siddharth.maheshwari on 02/12/14.
 */
public class DbHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "com.im.db";
    private static final int DB_VERSION = 1;

    public DbHelper(final Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(final SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {

    }
}
