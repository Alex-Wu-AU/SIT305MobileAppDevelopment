package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AdvertDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Adverts.db";
    private static final int DATABASE_VERSION = 1;

    public AdvertDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Adverts (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "postType TEXT," +
                "name TEXT," +
                "phone TEXT," +
                "description TEXT," +
                "date TEXT," +
                "location TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Handle upgrades here if needed
    }

    public void insertAdvert(Advert advert) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("postType", advert.getPostType());
        values.put("name", advert.getName());
        values.put("phone", advert.getPhone());
        values.put("description", advert.getDescription());
        values.put("date", advert.getDate());
        values.put("location", advert.getLocation());
        db.insert("Adverts", null, values);
        db.close();
    }

    public List<Advert> getAllAdverts() {
        List<Advert> advertList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Adverts", null, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String postType = cursor.getString(cursor.getColumnIndex("postType"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                String phone = cursor.getString(cursor.getColumnIndex("phone"));
                String description = cursor.getString(cursor.getColumnIndex("description"));
                String date = cursor.getString(cursor.getColumnIndex("date"));
                String location = cursor.getString(cursor.getColumnIndex("location"));

                Advert advert = new Advert(postType, name, phone, description, date, location);
                advertList.add(advert);
            } while (cursor.moveToNext());
            cursor.close();
        }
        db.close();
        return advertList;
    }

    public void deleteAdvertByName(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Adverts", "name = ?", new String[]{name});
        db.close();
    }

}

