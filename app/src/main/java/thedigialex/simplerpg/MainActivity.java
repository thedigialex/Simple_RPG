package thedigialex.simplerpg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private AppDatabase appDatabase;
    private LinearLayout playerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "simpleRPGDatabase").build();
        playerContainer = findViewById(R.id.playerContainer);
    }
    @Override
    protected void onResume() {
        super.onResume();
        loadExistingPlayers();
    }
    public void createNewPlayer(View view){
        Intent intent = new Intent(this, CreateNewPlayerActivity.class);
        startActivity(intent);
    }
    private void loadExistingPlayers() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<Player> players = appDatabase.playerDao().getAllPlayers();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        playerContainer.removeAllViews();
                        for (Player player : players) {
                            createPlayerButton(player);
                        }
                    }
                });
            }
        }).start();
    }
    private void createPlayerButton(Player player) {
        Button playerButton = new Button(this);
        playerButton.setText(player.getName());
        playerButton.setOnClickListener(v -> sendToTown(player));
        playerContainer.addView(playerButton);
    }
    private void sendToTown(Player player) {
        Intent intent = new Intent(this, TownActivity.class);
        intent.putExtra("playerId", player.getPlayerId());
        startActivity(intent);
    }
}