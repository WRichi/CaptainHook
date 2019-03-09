package at.hagenberg.captainhook.model.entries;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Entry.class}, version = 3)
public abstract class EntryDatabase extends RoomDatabase {

    private static EntryDatabase instance;

    public abstract EntryDao entryDao();

    public static synchronized EntryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    EntryDatabase.class, "entry_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
