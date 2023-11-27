package thedigialex.simplerpg;

import androidx.room.Entity;
import java.util.ArrayList;
import java.util.List;

@Entity
public class InventoryControls {
    List<Item> items;
    List<Skill> skills;
    AppDatabase appDatabase;
    int maxInventorySize;
    public InventoryControls(List<Item> items, List<Skill> skills, AppDatabase appDatabase, int maxInventorySize) {
        this.items = items;
        this.skills = skills;
        this.appDatabase = appDatabase;
        this.maxInventorySize = maxInventorySize;
    }
    public void addItem(Item item) {
        for (Item existingItem : items) {
            if (existingItem.getItemKey()  == item.getItemKey()) {
                if (existingItem.getStack() < existingItem.getMaxStack()) {
                    existingItem.setStack(existingItem.getStack() + 1);
                    updateItem(existingItem);
                }
                break;
            }
        }
        if (maxInventorySize > items.size()) {
            new Thread(() -> appDatabase.itemDao().insert(item)).start();
            items.add(item);
        }
    }
    public void updateItem(Item item) {
        new Thread(() -> appDatabase.itemDao().update(item)).start();
    }
    public void removeItem(Item item) {
        new Thread(() -> appDatabase.itemDao().delete(item)).start();
        items.remove(item);
    }
    public void addSkill(Skill skill) {
        boolean add = true;
        for (Skill existingSkill : skills) {
            if (existingSkill.getSkillKey() == skill.getSkillKey()) {
                add = false;
                break;
            }
        }
        if(add) {
            new Thread(() -> appDatabase.skillDao().insert(skill)).start();
            skills.add(skill);
        }
    }
    public void updateSkill(Skill skill) {
        new Thread(() -> appDatabase.skillDao().update(skill)).start();
    }
    public List<Item> getEquippedItems() {
        List<Item> equippedItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getIsEquipped()) {
                equippedItems.add(item);
            }
        }
        return equippedItems;
    }
}