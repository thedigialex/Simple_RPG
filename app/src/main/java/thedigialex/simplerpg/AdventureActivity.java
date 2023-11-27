package thedigialex.simplerpg;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Random;

public class AdventureActivity extends AppCompatActivity {
    PlayerControls playerControls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adventure);
        Intent intent = getIntent();
        int playerId = intent.getIntExtra("playerId", -1);
        playerControls = new PlayerControls(playerId, findViewById(R.id.header), findViewById(R.id.footer),this, getApplicationContext(), "Adventure");
    }
    public void Step(View view) {
        ConstraintLayout TextEvent = findViewById(R.id.TextEvent);
        TextEvent.setVisibility(View.GONE);
        ConstraintLayout BattleEvent = findViewById(R.id.BattleEvent);
        BattleEvent.setVisibility(View.GONE);
        playerControls.player.setSteps(playerControls.player.getSteps()+1);
        Random random = new Random();
        int eventChance = random.nextInt(100) + 1;
        int cumulativeRate = 0;
        int eventIndex = 0;
        int duration = 5000;
        switch (eventIndex) {
            case 1:
                ItemEVENT();
                break;
            case 2:
                BattleEVENT();
                duration = 10000;
                break;
            default:
                TextEVENT();
                duration = 2500;
                break;
        }
        LockStepButton(view, duration);
    }
    public void LockStepButton(View view, int duration) {
        view.setClickable(false);
        ProgressBar loadingProgressBar = findViewById(R.id.loadingProgressBar);
        loadingProgressBar.setMax(duration);
        new CountDownTimer(duration, 100) {
            @Override
            public void onTick(long millisUntilFinished) {
                int progress = (int) (duration - millisUntilFinished);
                loadingProgressBar.setProgress(progress);
            }
            @Override
            public void onFinish() {
                loadingProgressBar.setProgress(duration);
                view.setClickable(true);
            }
        }.start();
    }
    public void TextEVENT() {
        ConstraintLayout Event = findViewById(R.id.TextEvent);
        Event.setVisibility(View.VISIBLE);
        TextView eventText = findViewById(R.id.EventText);
        eventText.setText("TEXT event");

    }
    public void ItemEVENT() {
        ConstraintLayout Event = findViewById(R.id.TextEvent);
        Event.setVisibility(View.VISIBLE);
        TextView eventText = findViewById(R.id.EventText);
        eventText.setText("You found a skill");

    }
    public void BattleEVENT() {
        ConstraintLayout Event = findViewById(R.id.BattleEvent);
        Event.setVisibility(View.VISIBLE);
    }

}

