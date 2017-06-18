package com.rizwanmahmood.morsekeyboard.scoredatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

//Everything that happens with the database
public class ScoreDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "scores.db";
    private static final String TABLE_SCORES = "scores";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_LEVEL = "level";
    private static final String COLUMN_SCORE = "score";

    public ScoreDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String escape(int data) {
        return "\"" + data + "\"";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "" +
                "CREATE TABLE " + TABLE_SCORES + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_LEVEL + " INTEGER, " +
                COLUMN_SCORE + " INTEGER " + ")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS " + TABLE_SCORES + ";";
        db.execSQL(query);
        onCreate(db);
    }

    //Method for adding new item to the database "Scores" table
    public void addScore(Score score) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_LEVEL, score.get_level());
        values.put(COLUMN_SCORE, score.get_score());

        db.insert(TABLE_SCORES, null, values);
        db.close();
    }

    //Method for removing score for a level
    public void removeScoreForLevel (int level){
        SQLiteDatabase db = getWritableDatabase();

        String query = "" +
                "DELETE FROM " + TABLE_SCORES + " " +
                "WHERE " + COLUMN_LEVEL + " = " + escape(level) + ";";

        db.execSQL(query);
        db.close();
    }

    public ArrayList<String> getScoresForLevel(int level) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "" +
                "SELECT " + COLUMN_SCORE + " FROM " + TABLE_SCORES + " " +
                "WHERE " + COLUMN_LEVEL + " = " + escape(level) + ";";
        ArrayList<String> result = new ArrayList<>();

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();

        while( !cursor.isAfterLast() ) {
            String cursorScore = cursor.getString( cursor.getColumnIndex( COLUMN_SCORE ) );
            if( cursorScore != null ) {
                result.add(cursorScore);
            }
            cursor.moveToNext();
        }
        db.close();
        cursor.close();
        return result;
    }

    public String getHighScoreForLevel(int level) {
        SQLiteDatabase db = getWritableDatabase();

        String query = "" +
                "SELECT " + COLUMN_SCORE + " FROM " + TABLE_SCORES + " " +
                "WHERE " + COLUMN_LEVEL + " = " + escape(level) + " " +
                "ORDER BY " + COLUMN_SCORE + " DESC;";

        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToFirst();
        if(cursor.isAfterLast()) {
            db.close();
            cursor.close();
            return "0";
        }
        String highScore = cursor.getString( cursor.getColumnIndex( COLUMN_SCORE ) );
        db.close();
        cursor.close();
        return highScore;
    }

    public void removeAll() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "" +
                "DELETE FROM " + TABLE_SCORES + " " +
                "WHERE 1";

        db.execSQL(query);
        db.close();
    }

    //String representation of database
    @Override
    public String toString() {
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_SCORES + " WHERE 1;";
        String result = "";

        Cursor cursor = db.rawQuery(query, null);
        //First row in the result
        cursor.moveToFirst();

        //loop through all of the results
        while( !cursor.isAfterLast() ) {
            String cursorScoreString = cursor.getString( cursor.getColumnIndex( COLUMN_SCORE ) );
            String cursorLevelString = cursor.getString( cursor.getColumnIndex( COLUMN_LEVEL ) );
            if( cursorScoreString != null ) {
                result += "Level: " + cursorLevelString + ", Score: " + cursorScoreString;
                result += "\n";
            }
            cursor.moveToNext();
        }
        if( result.equals("") ) result = "No scores";

        db.close();
        cursor.close();
        return result;
    }
}
