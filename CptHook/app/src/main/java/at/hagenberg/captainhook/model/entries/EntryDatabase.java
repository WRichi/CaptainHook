package at.hagenberg.captainhook.model.entries;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

@Database(entities = {Entry.class}, version = 2)
public abstract class EntryDatabase extends RoomDatabase {

    private static EntryDatabase instance;

    public abstract EntryDao entryDao();

    public static synchronized EntryDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    EntryDatabase.class, "entry_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    /**
     * oncreate database just to get some data into the database
     * maybe delete later
     */
    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    /**
     * Async task just to get some Data into the Database for test purposes
     * delete later
     */
    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private EntryDao entryDao;

        private PopulateDbAsyncTask(EntryDatabase db) {
            entryDao = db.entryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            entryDao.insert(new Entry("Jan Ulrich", "Genz", "Ich bin unschuldig", "https://asdhkfladsgasdfasdf.com/jdsalkgkajsg.png", "C/Users/Me/Music/video.mp3", "1k4c7MM2pEk"));
            entryDao.insert(new Entry("Bohemian Rapsody", "Queen", "Meddler", "https://asdhkfladsgasdfasdf.com/jdsalkgkajsg.png", "C/Users/Me/Music/video.mp3", "fJ9rUzIMcZQ"));
            entryDao.insert(new Entry("Bleed it out", "Linkin Park","Hybrid Theory", "https://asdhkfladsgasdfasdf.com/jdsalkgkajsg.png", "C/Users/Me/Music/video.mp3", "OnuuYcqhzCE"));
            return null;
        }
    }
}
