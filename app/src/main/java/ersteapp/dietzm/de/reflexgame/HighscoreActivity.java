package ersteapp.dietzm.de.reflexgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import ersteapp.dietzm.de.reflexgame.db.DatabaseHelper;

import ersteapp.dietzm.de.reflexgame.db.model.HighscoreEntry;

public class HighscoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
    }

    @Override
    protected void onStart() {
        super.onStart();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        int level = sharedPref.getInt("LEVEL", 0);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        HighscoreEntry[] entries = db.getHighscoreTop10(level);
        String [] values = new String[entries.length];

        for(int i = 0 ; i< entries.length; i++){
            values[i] = entries[i].getPlayer() + " " + entries[i].getScore() + "P";
        }

        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>
                (getApplicationContext(), R.layout.lineitem, values);

        ListView list = (ListView) findViewById(R.id.lv_highscore);
        list.setAdapter(adapter);

        //Set Total Points into Header
        TextView lblHighscore = (TextView) findViewById(R.id.lbl_highscore);
        lblHighscore.setText("Highscore of " + db.getTotalPoints() + "P");
    }

    public void startGame(View view) {

        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);

    }
}
