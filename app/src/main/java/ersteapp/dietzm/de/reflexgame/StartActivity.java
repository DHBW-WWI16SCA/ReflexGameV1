package ersteapp.dietzm.de.reflexgame;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import ersteapp.dietzm.de.reflexgame.R;
import ersteapp.dietzm.de.reflexgame.backend.PlayerRegistrationTask;
import ersteapp.dietzm.de.reflexgame.db.DatabaseHelper;

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

        //Send Playername to Server
        try {
            sendPlayerToServer(playername, level);
        } catch(Exception e){
            Toast.makeText(this,"Error during Connection" + e.toString(), Toast.LENGTH_LONG).show();
        }



    }

    private void sendPlayerToServer(String playername, int level) throws Exception {

       // StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
       // StrictMode.setThreadPolicy(policy);

        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            //Call Async Task
            PlayerRegistrationTask registerPlayer = new PlayerRegistrationTask(this);
            registerPlayer.execute(playername, new Integer(level).toString());

        } else {
            Toast.makeText(this,"Connection not available", Toast.LENGTH_LONG).show();
        }

    }

    public void playerIsRegistered(final String result) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(),result, Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(), HighscoreActivity.class);
                startActivity(i);
            }
        });

    }
}
