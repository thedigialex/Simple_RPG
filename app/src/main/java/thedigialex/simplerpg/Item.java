package thedigialex.simplerpg;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    public int itemId;
    int itemKey;
    int itemValue;
    int playerId;
    String itemName;
    String itemType;
    boolean isEquipped;
    boolean isEquitable;
    int stack;
    int maxStack;

    public Item(int itemKey, int itemValue, String itemName, String itemType, int playerId, boolean isEquipped, boolean isEquitable, int maxStack) {
        this.itemKey = itemKey;
        this.itemValue = itemValue;
        this.itemName = itemName;
        this.itemType = itemType;
        this.playerId = playerId;
        this.isEquipped = isEquipped;
        this.isEquitable = isEquitable;
        this.maxStack = maxStack;
    }
    public int getItemKey() {
        return itemKey;
    }
    public void setItemKey(int itemKey) {
        this.itemKey = itemKey;
    }
    public int getStack() {
        return stack;
    }
    public void setStack(int stack) {
        this.stack = stack;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public int getItemValue() {
        return itemValue;
    }
    public void setItemValue(int itemValue) {
        this.itemValue = itemValue;
    }
    public String getItemType() {
        return itemType;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    public boolean getIsEquipped() {
        return isEquipped;
    }
    public void setIsEquipped(boolean isEquipped) {
        this.isEquipped = isEquipped;
    }
    public boolean getIsEquitable() {
        return isEquitable;
    }
    public void setIsEquitable(boolean isEquitable) {
        this.isEquitable = isEquitable;
    }
    public int getMaxStack() {
        return maxStack;
    }
    public void setMaxStack(int maxStack) {
        this.maxStack = maxStack;
    }
}