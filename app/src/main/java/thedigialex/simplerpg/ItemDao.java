package thedigialex.simplerpg;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insert(Item item);
    @Update
    void update(Item item);
    @Delete
    void delete(Item item);
    @Query("SELECT * FROM item WHERE playerId = :playerId")
    List<Item> getItemsByPlayerId(int playerId);
}
