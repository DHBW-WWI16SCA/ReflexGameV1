package ersteapp.dietzm.de.reflexgame;

import android.app.Activity;
import android.os.Bundle;

import ersteapp.dietzm.de.reflexgame.R;

public class GameActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }
}
