package com.nivilive.gps.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nivilive.gps.data.model.ItemNotification;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "NiviDB";
    private static final String TABLE_NOTIFICATION = "notifications";

    // Contacts Table Columns names
    private static final String KEY_ID = "ID";
    private static final String KEY_EVENT_ID = "event_id";
    private static final String KEY_DATE = "date";
    private static final String KEY_STATUS = "status";
    private static final String KEY_ADDRESS = "address";
    Context context;
    Context ctx;

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        ctx = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATION + "(ID INTEGER PRIMARY KEY," + KEY_EVENT_ID + " VARCHAR," + KEY_DATE + " VARCHAR," + KEY_STATUS + " VARCHAR," + KEY_ADDRESS + " VARCHAR);");
        ctx = context;
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        // Create tables again
        onCreate(db);
    }

    public void resetDatabase() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTIFICATION);
        db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATION + "(ID INTEGER PRIMARY KEY," + KEY_EVENT_ID + " VARCHAR," + KEY_DATE + " VARCHAR," + KEY_STATUS + " VARCHAR," + KEY_ADDRESS + " VARCHAR);");
        db.close();
    }

    public String saveNotification(ItemNotification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_EVENT_ID, notification.getEventId());
        values.put(KEY_DATE, notification.getDate());
        values.put(KEY_STATUS, notification.getStatus());
        values.put(KEY_ADDRESS, notification.getAddress());
        // Inserting Row
        long nodeId = db.insert(TABLE_NOTIFICATION, null, values);
        db.close(); // Closing database connection
        return String.valueOf(nodeId);
    }

    public ArrayList<ItemNotification> getAllNotifications() {
        ArrayList<ItemNotification> contactList = new ArrayList<ItemNotification>();
        String selectQuery = "SELECT  * FROM " + TABLE_NOTIFICATION + " ORDER BY ID DESC";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                ItemNotification contact = new ItemNotification();
                contact.setId(cursor.getString(cursor.getColumnIndex(KEY_ID)));
                contact.setEventId(cursor.getString(cursor.getColumnIndex(KEY_EVENT_ID)));
                contact.setDate(cursor.getString(cursor.getColumnIndex(KEY_DATE)));
                contact.setStatus(cursor.getString(cursor.getColumnIndex(KEY_STATUS)));
                contact.setAddress(cursor.getString(cursor.getColumnIndex(KEY_ADDRESS)));

                // Adding notifications to list
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        return contactList;
    }

    public int getUnseenCount() {
        String countQuery = "SELECT  * FROM " + TABLE_NOTIFICATION + " WHERE SeenStatus = 'N'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int cnt = cursor.getCount();
        cursor.close();
        return cnt;
    }

    public void updateSeenStatus() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NOTIFICATION + " SET SeenStatus = 'Y' WHERE SeenStatus = 'N'");
        db.close();
    }

    public void updateSeenSingle(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NOTIFICATION + " SET SeenStatus = 'Y' WHERE ID = '" + id + "'");
        db.close();
    }

    public void updateSeenSingleByNote(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NOTIFICATION + " SET SeenStatus = 'Y' WHERE note_id = '" + id + "'");
        db.close();
    }
}
