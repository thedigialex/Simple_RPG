package thedigialex.simplerpg;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Quest {
    @PrimaryKey(autoGenerate = true)
    long questId;
    String name;
    String description;
    int levelRequirement;
    int questType;
    int questRequiredAmount;
    int rewardGold;
    int playerId;
    boolean isCompleted;

    public int getLevelRequirement() {
        return levelRequirement;
    }
    public void setLevelRequirement(int levelRequirement) {
        this.levelRequirement = levelRequirement;
    }
    public long getQuestId() {
        return questId;
    }
    public void setQuestId(long questId) {
        this.questId = questId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public int getQuestType() {
        return questType;
    }
    public void setQuestType(int questType) {
        this.questType = questType;
    }
    public int getQuestRequiredAmount() {
        return questRequiredAmount;
    }
    public void setQuestRequiredAmount(int questRequiredAmount) {
        this.questRequiredAmount = questRequiredAmount;
    }
    public int getRewardGold() {
        return rewardGold;
    }
    public void setRewardGold(int rewardGold) {
        this.rewardGold = rewardGold;
    }
    public int getPlayerId() {
        return playerId;
    }
    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }
    public boolean getIsCompleted() {
        return isCompleted;
    }
    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }
}
