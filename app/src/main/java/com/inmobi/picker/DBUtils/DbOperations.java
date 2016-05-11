package com.test.kuluk.DBUtils;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by siddharth.maheshwari on 02/12/14.
 */

public final class DbOperations {

    private static final String TAG = DbOperations.class.getSimpleName();

    private SQLiteDatabase mDatabase;

    public DbOperations(Activity activity) {
        DbHelper dbHelper = new DbHelper(activity.getApplicationContext());
    }

    public int delete(final String tableName, final String whereClause, final String[] whereArgs) {
        return mDatabase.delete(tableName, whereClause, whereArgs);
    }

    public List<ContentValues> getRows(final String tableName, final String[] columns, final String whereClause, final String[] whereArgs, final String groupBy, final String having, final String orderBy, final String limit) {
        Cursor cursor = mDatabase.query(tableName, columns, whereClause, whereArgs, groupBy, having, orderBy, limit);
        List<ContentValues> contentVals = new ArrayList<>();
        ContentValues contentValues;
        if (cursor.moveToFirst()) {
            do {
                contentValues = new ContentValues();
                DatabaseUtils.cursorRowToContentValues(cursor, contentValues);
                contentVals.add(contentValues);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return contentVals;
    }

    public int getCount(final String tableName) {
        String countQuery = "SELECT COUNT(*) FROM " + tableName + " ; ";
        Cursor cursor = mDatabase.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }

    public int getCount(final String tableName, final String whereClause, final String[] whereArgs) {
        String countQuery = "SELECT COUNT(*) FROM " + tableName + " WHERE " + whereClause + " ; ";
        Cursor cursor = mDatabase.rawQuery(countQuery, whereArgs);
        cursor.moveToFirst();
        int result = cursor.getInt(0);
        cursor.close();
        return result;
    }

    public void dropTable(final String tableName) {
        String query = "DROP TABLE IF EXISTS \"" + tableName + "\"";
        mDatabase.execSQL(query);
    }

    public void close() {
       mDatabase.close();
    }
}
