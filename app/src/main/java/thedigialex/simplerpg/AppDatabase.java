package thedigialex.simplerpg;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Player.class, Item.class, Quest.class, Skill.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract PlayerDao playerDao();
    public abstract QuestDao questDao();
    public abstract ItemDao itemDao();
    public abstract SkillDao skillDao();
}