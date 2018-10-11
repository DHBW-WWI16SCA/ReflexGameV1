package ersteapp.dietzm.de.reflexgame;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ersteapp.dietzm.de.reflexgame.R;

public class StartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void readyToStart(View view) {

        Intent i = new Intent(this, HighscoreActivity.class);
        startActivity(i);

    }
}
