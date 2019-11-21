package com.example.roumeliotis.coen242projectapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class Manager extends SQLiteOpenHelper{

    private static final String TAG = "Manager";
    private Context context = null;

    public Manager(Context context) {
        super(context, ManagerConfigs.DATABASE_NAME, null, ManagerConfigs.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"Manager onCreate");

        //Create User Table
        String CREATE_USER_TABLE = "CREATE TABLE " + ManagerConfigs.TABLE_USER + "(" +
                ManagerConfigs.USER_REMOTE_ID + " INTEGER NOT NULL UNIQUE, " +
                ManagerConfigs.USER_NAME_COLUMN + " TEXT PRIMARY KEY AUTOINCREMENT, " +
                ManagerConfigs.USER_EMAIL_COLUMN + " TEXT NOT NULL, " +
                ManagerConfigs.USER_PASSWORD_COLUMN + " TEXT NOT NULL)";

        Log.d(TAG, CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ManagerConfigs.TABLE_USER);
        onCreate(db);
    }

    public long insertUser(User user){
        Log.d(TAG, "insertUser");

        long id = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ManagerConfigs.USER_REMOTE_ID, user.getRemote_id());
        contentValues.put(ManagerConfigs.USER_NAME_COLUMN, user.getName());
        contentValues.put(ManagerConfigs.USER_EMAIL_COLUMN, user.getEmail());
        contentValues.put(ManagerConfigs.USER_PASSWORD_COLUMN, user.getPassword());

        try {
            id = sqLiteDatabase.replaceOrThrow(ManagerConfigs.TABLE_USER, null, contentValues);
        } catch (SQLiteException e){
            Log.d(TAG,"Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        Log.d(TAG, "success");
        return id;
    }


}
