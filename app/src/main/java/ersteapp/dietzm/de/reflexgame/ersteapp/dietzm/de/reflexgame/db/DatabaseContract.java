package ersteapp.dietzm.de.reflexgame.ersteapp.dietzm.de.reflexgame.db;

import android.provider.BaseColumns;

public class DatabaseContract {

    public static String DB_NAME = "REFLEX_DB";

    public class Highscore implements BaseColumns {

        public static final String TABLE_NAME = "Highscore";

        public static final String COLUMN_PLAYER = "Player";
        public static final String COLUMN_SCORE = "Score";
        public static final String COLUMN_LEVEL = "Level";
        public static final String COLUMN_DATE = "Playdate";
        public static final String COLUMN_WRONGCLICKCNT = "WrongClickCount";

    }

}
