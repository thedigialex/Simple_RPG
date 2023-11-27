package thedigialex.simplerpg;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Skill {
    @PrimaryKey(autoGenerate = true)
    public int skillId;
    int skillKey;
    int playerId;
    String skillName;
    String skillType;
    boolean isEquipped;

    public Skill( int skillKey, String skillName, String skillType, int playerId, boolean isEquipped) {
        this.skillKey = skillKey;
        this.skillName = skillName;
        this.skillType = skillType;
        this.playerId = playerId;
        this.isEquipped = isEquipped;
    }
    public int getSkillKey() {
        return skillKey;
    }
    public void setSkillKey(int skillKey) {
        this.skillKey = skillKey;
    }
    public String getSkillName() {
        return skillName;
    }
    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
    public String getSkillType() {
        return skillType;
    }
    public void setSkillType(String skillType) {
        this.skillType = skillType;
    }
    public boolean getIsEquipped() {
        return isEquipped;
    }
    public void setIsEquipped(boolean isEquipped) {
        this.isEquipped = isEquipped;
    }
}
