package com.example.a2019mad.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "UserInfo.db";
    public DBHandler( Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ENTRIES = "CREATE TABLE " + UsersMaster.Users.TABLE_NAME + " ("+
                UsersMaster.Users._ID + " INTEGER PRIMARY KEY,"+
                UsersMaster.Users.COLUMN_NAME_USERNAME + " TEXT," +
                UsersMaster.Users.COLUMN_NAME_DOB + " TEXT," +
                UsersMaster.Users.COLUMN_NAME_PASSWORD + " TEXT," +
                UsersMaster.Users.COLUMN_NAME_GENDER + " TEXT)";

        db.execSQL(SQL_CREATE_ENTRIES);

    }

    public long addInfo(String username, String dob, String password, String gender){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_USERNAME,username);
        values.put(UsersMaster.Users.COLUMN_NAME_DOB,dob);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD,password);
        values.put(UsersMaster.Users.COLUMN_NAME_GENDER,gender);

        long newRowId =db.insert(UsersMaster.Users.TABLE_NAME,null,values);
        return newRowId;
    }

    public boolean updateInfo(String username, String dob, String password, String gender){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UsersMaster.Users.COLUMN_NAME_DOB,dob);
        values.put(UsersMaster.Users.COLUMN_NAME_PASSWORD,password);
        values.put(UsersMaster.Users.COLUMN_NAME_GENDER,gender);

        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = {username};

        int count = db.update(
                UsersMaster.Users.TABLE_NAME,
                values,
                selection,
                selectionArgs
        );

        if(count >= 1 ){
            return true;
        }
        else{
            return false;
        }

    }

    public void deleteInfo(String username){
        SQLiteDatabase db = getWritableDatabase();

        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = {username};

        db.delete(UsersMaster.Users.TABLE_NAME, selection, selectionArgs);

    }

    public List readAllInfo(){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_DOB,
                UsersMaster.Users.COLUMN_NAME_PASSWORD,
                UsersMaster.Users.COLUMN_NAME_GENDER
        };

        String sortOrder = UsersMaster.Users._ID + " ASC";

        Cursor cursor =  db.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                sortOrder
        );

        List item = new ArrayList<>();

        while(cursor.moveToNext()){
            String username = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            item.add(username);
        }
        cursor.close();

        return item;
    }

    public List readAll(String username){
        SQLiteDatabase db = this.getReadableDatabase();

        String[] projection = {
                BaseColumns._ID,
                UsersMaster.Users.COLUMN_NAME_USERNAME,
                UsersMaster.Users.COLUMN_NAME_DOB,
                UsersMaster.Users.COLUMN_NAME_PASSWORD,
                UsersMaster.Users.COLUMN_NAME_GENDER
        };

        String selection = UsersMaster.Users.COLUMN_NAME_USERNAME + " LIKE ?";
        String[] selectionArgs = {username};

        String sortOrder = UsersMaster.Users._ID + " ASC";

        Cursor cursor = db.query(
                UsersMaster.Users.TABLE_NAME,
                projection,
                selection,
                selectionArgs,
                null,
                null,
                sortOrder
        );

        List userInfo = new ArrayList<>();
        while(cursor.moveToNext()){
            String userName = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_USERNAME));
            String dob = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_DOB));
            String password = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_PASSWORD));
            String gender = cursor.getString(cursor.getColumnIndexOrThrow(UsersMaster.Users.COLUMN_NAME_GENDER));

            userInfo.add(userName);//0
            userInfo.add(dob);//1
            userInfo.add(password);//2
            userInfo.add(gender);//3
        }
        cursor.close();
        return userInfo;

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + UsersMaster.Users.TABLE_NAME;

        db.execSQL(SQL_DROP_TABLE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }



}
