package com.mmit.uc_assignment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class DBManager {

    private DatabaseHelper dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    public void insert(String name) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DatabaseHelper.SUBJECT, name);
        database.insert(DatabaseHelper.TABLE_NAME, null, contentValue);
    }

    public Cursor fetch() {
        String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SUBJECT};
        Cursor cursor = database.query(DatabaseHelper.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }

   public ArrayList<String> listItems() {
        String sql = "select * from " + DatabaseHelper.TABLE_NAME;
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ArrayList<String> storeItems = new ArrayList<>();
    //   String[] columns = new String[] { DatabaseHelper._ID, DatabaseHelper.SUBJECT};
        Cursor cursor = db.rawQuery(sql, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                int id = Integer.parseInt(cursor.getString(0));
                String name = cursor.getString(1);
                storeItems.add(name);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return storeItems;
    }

    public int update(long _id, String name) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseHelper.SUBJECT, name);
        int i = database.update(DatabaseHelper.TABLE_NAME, contentValues, DatabaseHelper._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        String query = "DELETE FROM "+ DatabaseHelper.TABLE_NAME +" WHERE " + DatabaseHelper._ID+" = "+_id;
        database.execSQL(query);
      //  database.delete(DatabaseHelper.TABLE_NAME, DatabaseHelper._ID + "=" + _id,null);

    }

}
