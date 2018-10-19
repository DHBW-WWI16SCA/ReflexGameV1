package ersteapp.dietzm.de.reflexgame.ersteapp.dietzm.de.reflexgame.db;

import android.content.ContentValues;
import android.content.Context;
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









}
