package com.example.islam.sendemail;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.islam.sendemail.Data.Constance;
import com.example.islam.sendemail.model.MyEmail;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by islam on 09/10/2016.
 */
public class DataBase extends SQLiteOpenHelper {

    private final ArrayList<MyEmail> emailList = new ArrayList<>();

    public DataBase(Context context) {
        super(context, Constance.DATABASE_NAME, null, Constance.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_EMAIL_TABLE = "CREATE TABLE " + Constance.TABLE_NAME +
                "(" + Constance.KEY_ID + " INTEGER PRIMARY KEY," + Constance.EMAIL +
                " TEXT, " + Constance.CUSTUMER_NAME + " TEXT," + Constance.CUSTUM_EMAIL + " TEXT);";
        db.execSQL(CREATE_EMAIL_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void deleteEmail(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(Constance.TABLE_NAME, Constance.KEY_ID + " =? ",
                new String[]{String.valueOf(id)});
    }

    public void deleteAllEmail() {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from " + Constance.TABLE_NAME);
        // db.delete(Constance.TABLE_NAME,null,null);
    }

    public void addEmail(MyEmail email) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constance.EMAIL, email.getEmail());
        values.put(Constance.CUSTUMER_NAME, email.getName());

        db.insert(Constance.TABLE_NAME, null, values);
        db.close();
    }

    public String getAllEmailAndName() {

        String selectQuery = " SELECT * FROM " + Constance.DATABASE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constance.TABLE_NAME, new String[]{Constance.KEY_ID, Constance.EMAIL,
                Constance.CUSTUMER_NAME,}, null, null, null, null, Constance.CUSTUMER_NAME);
        String data1 = null;
        String data2 = null;
        StringBuffer data = null;
        if (cursor.moveToFirst()) {
            do {
                data = new StringBuffer();
                data.append(cursor.getString(cursor.getColumnIndex(Constance.EMAIL)));
                data.append(" ");
                data.append(cursor.getString(cursor.getColumnIndex(Constance.CUSTUMER_NAME)));

            } while ((cursor.moveToNext()));
        }
        return String.valueOf(data);
    }

    public String getAllEmail() {

        // String selectQuery = " SELECT * FROM " + Constance.DATABASE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constance.TABLE_NAME,
                new String[]{Constance.KEY_ID, Constance.EMAIL}, null, null, null, null, Constance.KEY_ID);

        String data2 = "";
        // StringBuffer data = null;
        if (cursor.moveToFirst()) {
            do {
                String data1 = cursor.getString(cursor.getColumnIndex(Constance.EMAIL));
                data2 += "\" " + data1 + " \" " + "\n";
                Log.v("data:1", data2);
            } while ((cursor.moveToNext()));

        }

        return data2;
    }

    public String getAllName() {

        String selectQuery = " SELECT * FROM " + Constance.DATABASE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constance.TABLE_NAME, new String[]{Constance.KEY_ID,
                Constance.CUSTUMER_NAME,}, null, null, null, null, Constance.CUSTUMER_NAME);
        String data1 = null;
        String data2 = null;
        StringBuffer data = null;
        if (cursor.moveToFirst()) {
            do {

                data1 = cursor.getString(cursor.getColumnIndex(Constance.CUSTUMER_NAME));
                data2 += "\n" + data1;
//                data = new StringBuffer();
//                data.append(cursor.getString(cursor.getColumnIndex(Constance.EMAIL)));
//                data.append("\n");
                Log.v("data:1", data2);
            } while ((cursor.moveToNext()));

        }
        return data2;
    }

    public ArrayList<MyEmail> getAllDetails() {

        //  String selectQuery = " SELECT * FROM " + Constance.DATABASE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constance.TABLE_NAME, new String[]{Constance.KEY_ID, Constance.EMAIL,
                Constance.CUSTUMER_NAME}, null, null, null, null, Constance.KEY_ID);

        if (cursor.moveToFirst()) {
            do {
                MyEmail email = new MyEmail();
                email.setEmail(cursor.getString(cursor.getColumnIndex(Constance.EMAIL)));
                email.setName(cursor.getString(cursor.getColumnIndex(Constance.CUSTUMER_NAME)));
                email.setItemId(cursor.getInt(cursor.getColumnIndex(Constance.KEY_ID)));

                emailList.add(email);

            } while ((cursor.moveToNext()));
        }

        return emailList;
    }

    public void addCustumEmail(String email) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Constance.CUSTUM_EMAIL, email);

        db.insert(Constance.TABLE_NAME, null, values);
        db.close();
    }

    public String getCustumEmail() {
        String selectQuery = " SELECT * FROM " + Constance.DATABASE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constance.TABLE_NAME,
                new String[]{Constance.KEY_ID, Constance.CUSTUM_EMAIL}, null, null, null, null, Constance.KEY_ID);

        String data2 = null;
        // StringBuffer data = null;
        if (cursor.moveToFirst()) {
            do {

                String data1 = cursor.getString(cursor.getColumnIndex(Constance.CUSTUM_EMAIL));
                data2 += "\n" + " \" " + data1 + " \" ";
//                data = new StringBuffer();
//                data.append(cursor.getString(cursor.getColumnIndex(Constance.EMAIL)));
//                data.append("\n");
                Log.v("data:1", data2);
            } while ((cursor.moveToNext()));

        }

        return data2;

    }

    String data2 = "";
    public String custumEmailChoose(String custum) {

        data2 += "\" " + custum + " \"" +"\n";
        Log.v("data:10", data2);
        return data2;
    }

}
