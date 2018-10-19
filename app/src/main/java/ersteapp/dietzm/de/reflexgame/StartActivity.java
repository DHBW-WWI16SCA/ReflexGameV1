package ersteapp.dietzm.de.reflexgame;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;

import ersteapp.dietzm.de.reflexgame.R;
import ersteapp.dietzm.de.reflexgame.ersteapp.dietzm.de.reflexgame.db.DatabaseHelper;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        db.getReadableDatabase();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        EditText inpPlayername = (EditText) findViewById(R.id.inp_playername);
        SeekBar inpLevel = (SeekBar) findViewById(R.id.inp_level);

        String playername = sharedPref.getString("PLAYER_NAME", "");
        int level = sharedPref.getInt("LEVEL", 0);

        inpPlayername.setText(playername);
        inpLevel.setProgress(level);
    }


    public void readyToStart(View view) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();

        EditText inpPlayername = (EditText) findViewById(R.id.inp_playername);
        SeekBar inpLevel = (SeekBar) findViewById(R.id.inp_level);

        String playername = inpPlayername.getText().toString();
        int level = inpLevel.getProgress();

        editor.putString("PLAYER_NAME", playername);
        editor.putInt("LEVEL", level);

        editor.commit();



        Intent i = new Intent(this, HighscoreActivity.class);
        startActivity(i);

    }
}
