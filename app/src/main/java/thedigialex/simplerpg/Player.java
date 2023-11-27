package thedigialex.simplerpg;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Player {
    @PrimaryKey(autoGenerate = true)
    int playerId;
    String name;
    int level;
    int exp;
    int strength;
    int dexterity;
    int intelligence;
    int endurance;
    String job;
    String race;
    String title;
    String gender;
    int steps;
    int gold;
    int bankGold;
    int inventorySize;
    @Ignore
    AppDatabase appDatabase;

    public void updatePlayer(Player player) {
        new Thread(() -> appDatabase.playerDao().update(player)).start();
    }
    public int getPlayerId() {
        return playerId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }
    public int getExp() {
        return exp;
    }
    public void setExp(int exp) {
        this.exp = exp;
    }
    public int getStrength() {
        return strength;
    }
    public void setStrength(int strength) {
        this.strength = strength;
    }
    public int getDexterity() {
        return dexterity;
    }
    public void setDexterity(int dexterity) {
        this.dexterity = dexterity;
    }
    public int getIntelligence() {
        return intelligence;
    }
    public void setIntelligence(int intelligence) {
        this.intelligence = intelligence;
    }
    public int getEndurance() {
        return endurance;
    }
    public void setEndurance(int endurance) {
        this.endurance = endurance;
    }
    public String getJob() {
        return job;
    }
    public void setJob(String job) {
        this.job = job;
    }
    public String getRace() {
        return race;
    }
    public void setRace(String race) {
        this.race = race;
    }
    public String getTitle() {
        return (title == null) ? "Rookie" : title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public int getSteps() {
        return steps;
    }
    public void setSteps(int steps) {
        this.steps = steps;
    }
    public int getGold() {
        return gold;
    }
    public void setGold(int gold) {
        this.gold = gold;
    }
    public int getBankGold() {
        return bankGold;
    }
    public void setBankGold(int bankGold) {
        this.bankGold = bankGold;
    }
    public int getInventorySize() {
        return inventorySize;
    }
    public void setInventorySize(int inventorySize) {
        this.inventorySize = inventorySize;
    }
}