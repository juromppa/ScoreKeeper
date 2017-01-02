package com.gmail.romppainen.matti.juha.scorekeeper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by Juha on 31.12.2016.
 */

class DatabaseOperations extends SQLiteOpenHelper {

    private static final int database_version = 1;
    private static final String DATABASE_NAME = "ScoreKeeper.db";

    DatabaseOperations(Context context) {
        super(context, DATABASE_NAME, null, database_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_PLAYERS = "CREATE TABLE IF NOT EXISTS players (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT NOT NULL, " +
                "favorite INTEGER DEFAULT 0);";
        db.execSQL(CREATE_PLAYERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS players");
    }

    ArrayList<Integer> getAllPlayers() {
        ArrayList<Integer> array_list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("select * from players", null);
        cursor.moveToFirst();

        while(!cursor.isAfterLast()){
            array_list.add(cursor.getInt(cursor.getColumnIndex("id")));
            cursor.moveToNext();
        }

        cursor.close();
        db.close();
        return array_list;
    }

    public String[] getPlayerData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor =  db.rawQuery("SELECT * FROM players WHERE id = " + id + "", null );
        String[] result = new String[3];

        if (cursor.moveToFirst()) {
            result[0] = cursor.getString(cursor.getColumnIndex("id"));
            result[1] = cursor.getString(cursor.getColumnIndex("name"));
            result[2] = cursor.getString(cursor.getColumnIndex("favorite"));
        }

        cursor.close();
        db.close();
        return result;
    }
}
