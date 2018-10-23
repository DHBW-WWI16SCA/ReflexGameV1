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

        //Send Playername to Server
        try {
            sendPlayerToServer(playername, level);
        } catch(Exception e){
            Toast.makeText(this,"Error during Connection" + e.toString(), Toast.LENGTH_LONG).show();
        }

        Intent i = new Intent(this, HighscoreActivity.class);
        startActivity(i);

    }

    private void sendPlayerToServer(String playername, int level) throws Exception {

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ConnectivityManager con = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = con.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            String urlStr = "http://space-labs.appspot.com/repo/465001/player_add.sjs";
            String dataToTransfer = "?name=" + playername + "&level=" + level;

            InputStream is = null;

            URL url = new URL( urlStr + dataToTransfer);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.connect();

            int response = conn.getResponseCode();
            System.out.println("RESPONSE CODE FROM HTTP PLAYER ADD: " + response);

        } else {
            Toast.makeText(this,"Connection not available", Toast.LENGTH_LONG).show();
        }

    }
}
