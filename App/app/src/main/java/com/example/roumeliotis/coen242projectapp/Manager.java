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
                ManagerConfigs.USER_ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ManagerConfigs.USER_NAME_COLUMN + " TEXT NOT NULL, " +
                ManagerConfigs.USER_EMAIL_COLUMN + " TEXT PRIMARY KEY NOT NULL UNIQUE, " +
                ManagerConfigs.USER_PASSWORD_COLUMN + " TEXT NOT NULL," +
                ManagerConfigs.USER_TOKEN_COLUMN + "TEXT NOT NULL)";

        Log.d(TAG, CREATE_USER_TABLE);
        db.execSQL(CREATE_USER_TABLE);

        // Create Order Table
        String CREATE_ORDER_TABLE = "CREATE TABLE " + ManagerConfigs.TABLE_ORDER + "(" +
                ManagerConfigs.ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                ManagerConfigs.ORDER_REMOTE_ID + " INTEGER NOT NULL UNIQUE, " +
                ManagerConfigs.ORDER_KEY + "INTEGER NOT NULL UNIQUE, " +
                ManagerConfigs.ORDER_MACHINE_ID + "INTEGER NOT NULL UNIQUE, " +
                ManagerConfigs.ORDER_MIXER_COLUMN + "TEXT NOT NULL," +
                ManagerConfigs.ORDER_ALCOHOL_COLUMN + "TEXT NOT NULL," +
                ManagerConfigs.ORDER_DOUBLE_COLUMN + "TEXT NOT NULL, " +
                ManagerConfigs.ORDER_PRICE_COLUMN + "REAL NOT NULL, " +
                ManagerConfigs.ORDER_STATE_COLUMN + "TEXT NOT NULL, " +
                ManagerConfigs.ORDER_PAID_COLUMN + "TEXT NOT NULL)";

        Log.d(TAG, CREATE_ORDER_TABLE);
        db.execSQL(CREATE_ORDER_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ManagerConfigs.TABLE_USER);
        onCreate(db);
    }


    // Insert user
    public long insertUser(User user){
        Log.d(TAG, "insertUser");

        long id = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ManagerConfigs.USER_NAME_COLUMN, user.getName());
        contentValues.put(ManagerConfigs.USER_EMAIL_COLUMN, user.getEmail());
        contentValues.put(ManagerConfigs.USER_PASSWORD_COLUMN, user.getPassword());
        contentValues.put(ManagerConfigs.USER_PASSWORD_COLUMN, user.getToken());

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

    // Insert order
    public long insertOrder(Order order){
        Log.d(TAG, "insertOrder");

        long id = -1;
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ManagerConfigs.ORDER_ID, order.getId());
//        contentValues.put(ManagerConfigs.USER_REMOTE_ID, order.getRemote_id());
        contentValues.put(ManagerConfigs.ORDER_KEY, order.getOrder_key());
        contentValues.put(ManagerConfigs.ORDER_MACHINE_ID, order.getMachine_id());
        contentValues.put(ManagerConfigs.ORDER_MIXER_COLUMN, order.getMixer());
        contentValues.put(ManagerConfigs.ORDER_ALCOHOL_COLUMN, order.getAlcohol());
        contentValues.put(ManagerConfigs.ORDER_DOUBLE_COLUMN, order.getDoubleAlcohol());
        contentValues.put(ManagerConfigs.ORDER_PRICE_COLUMN, order.getPrice());
        contentValues.put(ManagerConfigs.ORDER_STATE_COLUMN, order.getState());
        contentValues.put(ManagerConfigs.ORDER_PAID_COLUMN, order.getPaid());

        try {
            id = sqLiteDatabase.replaceOrThrow(ManagerConfigs.TABLE_ORDER, null, contentValues);
        } catch (SQLiteException e){
            Log.d(TAG,"Exception: " + e.getMessage());
            Toast.makeText(context, "Operation failed: " + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            sqLiteDatabase.close();
        }
        Log.d(TAG, "success");
        return id;
    }

    // Set token

    // Get token




}