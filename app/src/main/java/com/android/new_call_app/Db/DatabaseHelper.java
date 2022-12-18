package com.android.new_call_app.Db;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import  com.android.new_call_app.models.Images;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Info
    private static final String DATABASE_NAME = "postsDatabase";
    private static final int DATABASE_VERSION = 1;
    private static DatabaseHelper sInstance;

    private static final String TABLE_Skins = "Skins";

    private static final String KEY_POST_ID = "id";
    private static final String KEY_Skins_preview = "preview";
    private static final String KEY_Skins_Skin = "Skin";
    private static final String TABLE_Wallpaper = "Wallpaper";

    private static final String KEY_Wallpaper_preview = "Wallpaper";

    public static synchronized DatabaseHelper getInstance(Context context) {

        if (sInstance == null) {
            sInstance = new DatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    private DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_SKINS_TABLE = "CREATE TABLE " + TABLE_Skins +
                "(" + KEY_POST_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_Skins_preview + " TEXT ," + KEY_Skins_Skin + " TEXT" + ")";
        String CREATE_Wallpaper_TABLE = "CREATE TABLE " + TABLE_Wallpaper +
                "(" + KEY_POST_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_Wallpaper_preview + " TEXT " + ")";
        db.execSQL(CREATE_SKINS_TABLE);
        db.execSQL(CREATE_Wallpaper_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_Skins);
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_Wallpaper);
            onCreate(db);
        }
    }


    public long addOrUpdateSkin(Images skin) {
        SQLiteDatabase db = getWritableDatabase();
        long SkinsId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_Skins_preview, skin.getPreview());
            values.put(KEY_Skins_Skin, skin.getSkin());
            int rows = db.update(TABLE_Skins, values, KEY_Skins_preview + "= ?", new String[]{skin.getPreview()});
            if (rows != 1) {
                SkinsId = db.insertOrThrow(TABLE_Skins, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return SkinsId;
    }

    public long addOrUpdateWallpaper(String Wallpaper) {
        SQLiteDatabase db = getWritableDatabase();
        long SkinsId = -1;

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(KEY_Wallpaper_preview, Wallpaper);
            int rows = db.update(TABLE_Wallpaper, values, KEY_Wallpaper_preview + "= ?", new String[]{Wallpaper});
            if (rows != 1) {
                SkinsId = db.insertOrThrow(TABLE_Wallpaper, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
        } finally {
            db.endTransaction();
        }
        return SkinsId;
    }


    @SuppressLint("Range")
    public List<Images> getAllSkins() {
        List<Images> skins = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_Skins);


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Images newSkin = new Images();
                    newSkin.setPreview(cursor.getString(cursor.getColumnIndex(KEY_Skins_preview)));
                    newSkin.setSkin(cursor.getString(cursor.getColumnIndex(KEY_Skins_Skin)));
                    skins.add(newSkin);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return skins;
    }

    @SuppressLint("Range")
    public List<String> getAllWallpapers() {
        List<String> skins = new ArrayList<>();
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s ",
                        TABLE_Wallpaper);


        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    skins.add(cursor.getString(cursor.getColumnIndex(KEY_Wallpaper_preview)));
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return skins;
    }

    public boolean deleteTitle(String Preview) {
        SQLiteDatabase db = getReadableDatabase();
        return db.delete(TABLE_Skins, KEY_Skins_preview + "=?", new String[]{Preview}) > 0;
    }

    public boolean deleteWallpaper(String Preview) {
        SQLiteDatabase db = getReadableDatabase();
        return db.delete(TABLE_Wallpaper, KEY_Wallpaper_preview + "=?", new String[]{Preview}) > 0;
    }

}