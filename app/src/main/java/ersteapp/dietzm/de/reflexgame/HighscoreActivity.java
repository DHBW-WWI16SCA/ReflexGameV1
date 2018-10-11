package ersteapp.dietzm.de.reflexgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import ersteapp.dietzm.de.reflexgame.R;

public class HighscoreActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
    }

    @Override
    protected void onStart() {
        super.onStart();

        String [] values = new String[]{
                "Hans 100P",
                "Peter 98P",
                "John 92P"
        };

        ArrayAdapter<String> adapter
                = new ArrayAdapter<String>
                (getApplicationContext(), R.layout.lineitem, values);

        ListView list = (ListView) findViewById(R.id.lv_highscore);
        list.setAdapter(adapter);

    }

    public void startGame(View view) {

        Intent i = new Intent(this, GameActivity.class);
        startActivity(i);

    }
}
