package thedigialex.simplerpg;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;

public class CreateNewPlayerActivity extends AppCompatActivity {

    private AppDatabase appDatabase;
    int[] STATS = {1,1,1,1};
    int StatPoints = STATS.length * 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_player);
        appDatabase = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "simpleRPGDatabase").build();
    }
    public void createNewPlayer(View view) {
        EditText editTextName = findViewById(R.id.editTextName);
        String playerName = editTextName.getText().toString().trim();
        if (playerName.isEmpty()) {
            Toast.makeText(this, "Please enter a name.", Toast.LENGTH_SHORT).show();
            return;
        }
        RadioGroup radioGroupGender = findViewById(R.id.radioGroupGender);
        if(radioGroupGender.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StatPoints != 0) {
            Toast.makeText(this, "All Attribute points must be used.", Toast.LENGTH_SHORT).show();
            return;
        }
        Player newPlayer = new Player();
        newPlayer.setName(playerName);
        newPlayer.setLevel(1);
        newPlayer.setExp(0);
        newPlayer.setSteps(0);
        newPlayer.setInventorySize(10);
        newPlayer.setStrength(STATS[0]);
        newPlayer.setDexterity(STATS[1]);
        newPlayer.setIntelligence(STATS[2]);
        newPlayer.setEndurance(STATS[3]);
        String gender;
        switch (radioGroupGender.getCheckedRadioButtonId()) {
            case 0:
                gender = "Male";
                break;
            case 1:
                gender = "female";
                break;
            default:
                gender = "Other";
                break;
        }
        newPlayer.setGender(gender);
        Spinner spinnerClass = findViewById(R.id.spinnerClass);
        Spinner spinnerRace = findViewById(R.id.spinnerRace);
        newPlayer.setJob(spinnerClass.getSelectedItem().toString());
        newPlayer.setRace(spinnerRace.getSelectedItem().toString());
        newPlayer.setBankGold(0);
        newPlayer.setGold(0);
        Intent intent = new Intent(this, TownActivity.class);
        new Thread(() -> {
            long playerId = appDatabase.playerDao().insert(newPlayer);
            createQuests((int) playerId);
            intent.putExtra("playerId", (int) playerId);
            startActivity(intent);
            finish();
        }).start();
    }
    public void IncreaseStat(View view) {
        int StatId = Integer.parseInt(view.getTag().toString());
        if(StatPoints > 0 && STATS[StatId] + 1 <= 10 ) {
            StatPoints--;
            STATS[StatId]++;
        }
        UpdateStatTextViews();
    }
    public void DecreaseStat(View view) {
        int StatId = Integer.parseInt(view.getTag().toString());
        if(STATS[StatId]-1 > 0) {
            STATS[StatId]--;
            StatPoints++;
        }
        UpdateStatTextViews();
    }
    public void UpdateStatTextViews() {
        TextView StatPointView = findViewById(R.id.StatPointView);
        String text =getString(R.string.remaining_points)+StatPoints;
        StatPointView.setText(text);
        TextView STR = findViewById(R.id.STRTextView);
        text = "STR: "+STATS[0];
        STR.setText(text);
        TextView DEX = findViewById(R.id.DEXTextView);
        text = "DEX: "+STATS[1];
        DEX.setText(text);
        TextView INT = findViewById(R.id.INTTextView);
        text = "INT: "+STATS[2];
        INT.setText(text);
        TextView END = findViewById(R.id.ENDTextView);
        text = "END: "+STATS[3];
        END.setText(text);
    }
    public void createQuests(int playerId) {
        Quest quest1 = new Quest();
        quest1.setPlayerId(playerId);
        quest1.setName("Explore the Forest");
        quest1.setDescription("Explore the mysterious forest and uncover its secrets.");
        quest1.setRewardGold(50);
        quest1.setIsCompleted(false);
        quest1.setQuestType(1);
        quest1.setLevelRequirement(1);
        quest1.setQuestRequiredAmount(10);
        appDatabase.questDao().insert(quest1);
        Quest quest2 = new Quest();
        quest2.setPlayerId(playerId);
        quest2.setName("Defeat the Goblin Horde");
        quest2.setDescription("Confront the goblin horde threatening the village.");
        quest2.setRewardGold(75);
        quest2.setIsCompleted(false);
        quest2.setQuestType(1);
        quest2.setLevelRequirement(2);
        quest2.setQuestRequiredAmount(15);
        appDatabase.questDao().insert(quest2);
        Quest quest3 = new Quest();
        quest3.setPlayerId(playerId);
        quest3.setName("Retrieve the Lost Artifact");
        quest3.setDescription("Embark on a quest to retrieve a powerful lost artifact.");
        quest3.setRewardGold(100);
        quest3.setIsCompleted(false);
        quest3.setQuestType(1);
        quest3.setLevelRequirement(3);
        quest3.setQuestRequiredAmount(20);
        appDatabase.questDao().insert(quest3);
    }
}