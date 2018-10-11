package ersteapp.dietzm.de.reflexgame;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class GameActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
    }

    private Button[] buttons = new Button[4];
    private TextView tvCountdown;
    private TextView tvScore;

    private int score;
    private int countdown;
    private int activeButton = -1;

    private ScheduledFuture<?> buttonChecker;
    private int buttonTimeToBeat = 1;

    private static final int COUNTDOWN_START = 30;

    @Override
    protected void onResume() {
        super.onResume();

        score = 0;
        countdown = COUNTDOWN_START;

        tvCountdown = findViewById(R.id.tv_countdown);
        tvScore = findViewById(R.id.tv_score);

        buttons[0] = (Button) findViewById(R.id.button1);
        buttons[1] = (Button) findViewById(R.id.button2);
        buttons[2] = (Button) findViewById(R.id.button3);
        buttons[3] = (Button) findViewById(R.id.button4);

        activateNewButton();
        startCountdown();

    }

    private void startCountdown() {
        ScheduledExecutorService ste = Executors.newScheduledThreadPool(5);
        ste.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                countdown--;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvCountdown.setText(countdown + " seconds left");
                    }
                });
            }
        }, 1, 1, TimeUnit.SECONDS);
    }

    private void updateButtonRendering() {
        for(int i = 0; i < buttons.length; i++){
            buttons[i].setEnabled(false);
            buttons[i].setBackgroundColor(Color.rgb(210,210,210));
        }

        buttons[activeButton].setBackgroundColor(Color.rgb(170,170,255));
        buttons[activeButton].setEnabled(true);
    }

    public void buttonHit(View view){
        score += 10;
        buttonChecker.cancel(false);
        tvScore.setText("Score: " + score);
        activateNewButton();
    }


    private void activateNewButton() {

        //Define new active button index
        int newActiveId = 0;
        do {
            newActiveId = new Random().nextInt(4);
        } while(newActiveId == activeButton);
        activeButton = newActiveId;

        //Update the rendering
        updateButtonRendering();

        //Schedule end of button lifetime
        ScheduledExecutorService ste = Executors.newScheduledThreadPool(5);
        buttonChecker = ste.schedule(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        activateNewButton();
                    }
                });
            }
        },buttonTimeToBeat,TimeUnit.SECONDS);
    }


}
