package thedigialex.simplerpg;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.room.Room;

import java.util.Arrays;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class PlayerControls {
    Player player;
    InventoryControls inventoryControls;
    HeaderControls headerControls;
    FooterControls footerControls;
    QuestControls questControls;
    AppDatabase appDatabase;
    final Executor executor = Executors.newSingleThreadExecutor();
    Context context;

    public PlayerControls(int playerId, View headerView, View footerView, Activity activity, Context context, String currentLocation) {
        this.context = context;
        headerControls = new HeaderControls(headerView);
        footerControls = new FooterControls(footerView, 0, activity, currentLocation);
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "simpleRPGDatabase").build();
        executor.execute(() -> {
            player = appDatabase.playerDao().getPlayerById(playerId);
            player.appDatabase = appDatabase;
            activity.runOnUiThread(() -> executor.execute(() -> {
                inventoryControls = new InventoryControls(appDatabase.itemDao().getItemsByPlayerId(playerId), appDatabase.skillDao().getSkillsByPlayerId(playerId), appDatabase, player.getInventorySize());
                questControls = new QuestControls(context, player);
                questControls.quests = appDatabase.questDao().getQuestsByPlayerId(player.getPlayerId());
                activity.runOnUiThread(() -> executor.execute(() -> activity.runOnUiThread(() -> setUp(currentLocation))));
            }));
        });
    }
    public void UpdateViews(View headerView, View footerView, String currentLocation, Activity activity) {
        footerControls.footerView = footerView;
        footerControls.activity = activity;
        headerControls.headerView = headerView;
        setUp(currentLocation);
    }
    public void setUp(String currentLocation) {
        headerControls.initViews();
        footerControls.initViews(currentLocation);
        updateHeader();
    }
    public void createQuestControls( LayoutInflater inflater, LinearLayout PopUpLinearLayout, ConstraintLayout PopUpMenu) {
        questControls.inflater = inflater;
        questControls.PopUpLinearLayout = PopUpLinearLayout;
        questControls.PopUpMenu = PopUpMenu;
    }
    public void updateHeader() {
        String nameAndTitle = player.getTitle() + " " + player.getName();
        String raceAndClass = player.getRace() + " " + player.getJob();
        headerControls.setUpViews(nameAndTitle, player.getGold(), 0, player.getLevel(), player.getExp(), raceAndClass);
    }
    public int[] getPlayerStats() {
        int[] stats = new int[9];
        Arrays.fill(stats, player.getLevel());
        //Atk, Def, MagAtk, MagDef, Speed, Hit, HP, MA, ST
        stats[0] *= ((player.getStrength() * 2) + (player.getEndurance() / 2));
        stats[1] *= ((player.getStrength() / 2) + (player.getEndurance() * 2));
        stats[2] *= ((player.getIntelligence() / 2) + (player.getIntelligence() * 2));
        stats[3] *= (player.getEndurance() + (player.getDexterity() / 2));
        stats[4] *= (player.getDexterity() * 2 + player.getStrength());
        stats[5] *= (player.getDexterity() + player.getIntelligence());
        stats[6] *= (20 + 5 * getJobGrowthRate("Health") );
        stats[7] += (4 + getJobGrowthRate("Mana") + player.getLevel() / 5);
        stats[8] += (4 + getJobGrowthRate("Stamina") + player.getLevel() / 5);
        //Item[] items = inventory.getEquippedItems();
        //for (Item item : items) {
        //    stats = item.getStatBoots(stats);
        //}
        return stats;
    }
    public int getJobGrowthRate(String type) {
        int growthRate = 1;
        String job = player.getJob();
        String race = player.getRace();
        switch (type) {
            case "Health":
                if(job.equals("Warrior")) {
                    growthRate += 2;
                }
                if(race.equals("Human")) {
                    growthRate++;
                }
                break;
            case "Mana":
                if(job.equals("Mage")) {
                    growthRate += 2;
                }
                if(race.equals("Elf")) {
                    growthRate++;
                }
                break;
            case "Stamina":
                if(job.equals("Rogue")) {
                    growthRate += 2;
                }
                if(race.equals("Dwarf")) {
                    growthRate++;
                }
                break;
        }
        return  growthRate;
    }
    public void DisplayMessage(String text) {
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}