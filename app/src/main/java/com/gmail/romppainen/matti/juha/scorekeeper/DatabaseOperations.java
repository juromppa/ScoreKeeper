package com.gmail.romppainen.matti.juha.scorekeeper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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
                "created INTEGER NOT NULL, " +
                "favorite INTEGER DEFAULT 0, " +
                "selected INTEGER DEFAULT 0, " +
                "last_game INTEGER);";
        db.execSQL(CREATE_PLAYERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS players");
        onCreate(db);
    }

    void addPlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", player.getName());
        values.put("created", System.currentTimeMillis() / 1000L);

        db.insert("players", null, values);
        db.close();
    }

    Player getPlayer(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM players WHERE id = " + id + "", null);
        if (cursor != null) {
            cursor.moveToFirst();
        }

        Player player = new Player(cursor.getInt(cursor.getColumnIndex("id")),
                cursor.getString(cursor.getColumnIndex("name")),
                cursor.getInt(cursor.getColumnIndex("created")),
                cursor.getInt(cursor.getColumnIndex("favorite")) > 0,
                cursor.getInt(cursor.getColumnIndex("selected")) > 0,
                cursor.getInt(cursor.getColumnIndex("last_game")));
        cursor.close();
        db.close();

        return player;
    }

    List<Player> getAllPlayers() {
        List<Player> playerList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM players ORDER BY favorite DESC, name ASC", null);

        if (cursor.moveToFirst()) {
            do {
                Player player = new Player(cursor.getInt(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("name")),
                        cursor.getInt(cursor.getColumnIndex("created")),
                        cursor.getInt(cursor.getColumnIndex("favorite")) > 0,
                        cursor.getInt(cursor.getColumnIndex("selected")) > 0,
                        cursor.getInt(cursor.getColumnIndex("last_game")));

                playerList.add(player);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return playerList;
    }

    int updatePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name", player.getName());
        values.put("created", player.getCreated());
        values.put("favorite", player.getFavorite());
        values.put("selected", player.getSelected());
        values.put("last_game", player.getLastGame());

        return db.update("players", values, "id = ?", new String[]{String.valueOf(player.getId())});
    }

    void deletePlayer(Player player) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("players", "id = ?", new String[] { String.valueOf(player.getId()) });
        db.close();
    }
}
