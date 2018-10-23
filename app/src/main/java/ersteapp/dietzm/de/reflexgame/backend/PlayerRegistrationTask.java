package ersteapp.dietzm.de.reflexgame.backend;

import android.os.AsyncTask;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import ersteapp.dietzm.de.reflexgame.StartActivity;

public class PlayerRegistrationTask extends AsyncTask<String, Void, String> {


    private final StartActivity activity;

    public PlayerRegistrationTask(StartActivity activity){
        this.activity = activity;
    }

    @Override
    protected String doInBackground(String... strings) {

        try {

            String player = strings[0];
            String level = strings[1];

            String urlStr = "http://space-labs.appspot.com/repo/465001/player_add.sjs";
            String dataToTransfer = "?name=" + player + "&level=" + level;

            JSONObject createPlayer = new JSONObject();
            createPlayer.put("playername", player);
            createPlayer.put("level", level);
            String jsonString = createPlayer.toString();


            URL url = new URL(urlStr + dataToTransfer);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.connect();

            InputStream is = conn.getInputStream();
            String body = convertStreamToString(is);


            JSONObject json = new JSONObject(body);
            String playerid = json.getString("PlayerID");

            int response = conn.getResponseCode();

            return playerid;

        } catch (Exception e){

            return e.toString();
        }
        //return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        activity.playerIsRegistered(result);
    }

    public static String convertStreamToString(InputStream is) throws Exception {

        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;

        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        is.close();

        return sb.toString();
    }
}
