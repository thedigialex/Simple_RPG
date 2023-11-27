package thedigialex.simplerpg;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Delete;
import java.util.List;

@Dao
public interface PlayerDao {
    @Insert
    long insert(Player player);
    @Update
    void update(Player player);
    @Delete
    void delete(Player player);
    @Query("SELECT * FROM Player")
    List<Player> getAllPlayers();
    @Query("SELECT * FROM Player WHERE playerId = :id")
    Player getPlayerById(int id);
}
