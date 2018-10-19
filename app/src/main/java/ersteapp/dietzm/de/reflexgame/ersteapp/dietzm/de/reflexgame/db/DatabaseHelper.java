package ersteapp.dietzm.de.reflexgame.ersteapp.dietzm.de.reflexgame.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import ersteapp.dietzm.de.reflexgame.ersteapp.dietzm.de.reflexgame.db.model.HighscoreEntry;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static int DB_VERSION = 1;



    public DatabaseHelper(Context context) {

        super(context, DatabaseContract.DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableHighscore = "";
        createTableHighscore += "CREATE TABLE " + DatabaseContract.Highscore.TABLE_NAME + " ( ";
        createTableHighscore += DatabaseContract.Highscore._ID + " INTEGER PRIMARY KEY, ";
        createTableHighscore += DatabaseContract.Highscore.COLUMN_PLAYER + " NVARCHAR(255), ";
        createTableHighscore += DatabaseContract.Highscore.COLUMN_LEVEL + " INTEGER, ";
        createTableHighscore += DatabaseContract.Highscore.COLUMN_SCORE + " INTEGER ) ";

        db.execSQL(createTableHighscore);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createHighscoreEntry(HighscoreEntry entry){

        ContentValues cv = new ContentValues();
        cv.put(DatabaseContract.Highscore.COLUMN_PLAYER, entry.getPlayer());
        cv.put(DatabaseContract.Highscore.COLUMN_LEVEL, entry.getLevel());
        cv.put(DatabaseContract.Highscore.COLUMN_SCORE, entry.getScore());

        SQLiteDatabase dbWriter = getWritableDatabase();
        dbWriter.insert(DatabaseContract.Highscore.TABLE_NAME, null, cv);

        dbWriter.close();
    }


    public HighscoreEntry[] getHighscoreTop10(int forLevel){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.query(
                DatabaseContract.Highscore.TABLE_NAME,
                new String[] { DatabaseContract.Highscore.COLUMN_PLAYER,
                        DatabaseContract.Highscore.COLUMN_SCORE },
                DatabaseContract.Highscore.COLUMN_LEVEL + " = ?",
                new String[]{ "" + forLevel },
                null,
                null,
                DatabaseContract.Highscore.COLUMN_SCORE + " DESC",
                "10"
        );

        HighscoreEntry[] highscoreEntries = new HighscoreEntry[cursor.getCount()];
        int idx = 0;

        while(cursor.moveToNext()){
            HighscoreEntry entry = new HighscoreEntry();
            entry.setPlayer(cursor.getString(0));
            entry.setScore(cursor.getInt(1));

            highscoreEntries[idx] = entry;
            idx++;
        }

        db.close();
        return highscoreEntries;

    }

    public int getTotalPoints(){

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT SUM(" + DatabaseContract.Highscore.COLUMN_SCORE + ") FROM "
                + DatabaseContract.Highscore.TABLE_NAME, null);

        int totalPoints = 0;

        if(cursor.moveToNext()){
            totalPoints = cursor.getInt(0);
        }

        db.close();
        return totalPoints;

    }






}
