package thedigialex.simplerpg;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface QuestDao {
    @Insert
    void insert(Quest quest);
    @Update
    void update(Quest quest);
    @Delete
    void delete(Quest quest);
    @Query("SELECT * FROM Quest WHERE playerId = :playerId")
    List<Quest> getQuestsByPlayerId(int playerId);

}
