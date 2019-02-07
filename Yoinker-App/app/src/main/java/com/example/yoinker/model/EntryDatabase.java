package com.example.yoinker.model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import java.util.Date;

@Database(entities = {Entry.class}, version = 1)
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
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
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
            Date date = new Date();
            String timestamp = date.toString();
            entryDao.insert(new Entry("Title 1", timestamp));
            entryDao.insert(new Entry("Title 2", timestamp));
            entryDao.insert(new Entry("Title 3", timestamp));
            return null;
        }
    }
}