package com.example.captainhook.model;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import android.arch.lifecycle.LiveData;

public class EntryRepository {

    private EntryDao entryDao;
    private LiveData<List<Entry>> allEntries;

    public EntryRepository(Application application) {
        EntryDatabase database = EntryDatabase.getInstance(application);
        entryDao = database.entryDao();
        allEntries = entryDao.getAllEntries();
    }

    /**
     * Api for the outside e.g. ViewModel
     */
    public void insert(Entry entry) {
        new InsertEntryAsyncTask(entryDao).execute(entry);
    }

    public void delete(Entry entry) {
        new DeleteEntryAsyncTask(entryDao).execute(entry);
    }

    public void deleteAll() {
        new DeleteAllEntriesAsyncTask(entryDao).execute();
    }

    public LiveData<List<Entry>> getAllEntries() {
        return allEntries;
    }


    /**
     * Async task for the database functions
     */
    private static class InsertEntryAsyncTask extends AsyncTask<Entry, Void, Void> {

        private EntryDao entryDao;

        private InsertEntryAsyncTask(EntryDao entryDao) {
            this.entryDao = entryDao;
        }

        @Override
        protected Void doInBackground(Entry... entries) {
            entryDao.insert(entries[0]);
            return null;
        }
    }

    private static class DeleteEntryAsyncTask extends AsyncTask<Entry, Void, Void> {

        private EntryDao entryDao;

        private DeleteEntryAsyncTask(EntryDao entryDao) {
            this.entryDao = entryDao;
        }

        @Override
        protected Void doInBackground(Entry... entries) {
            entryDao.delete(entries[0]);
            return null;
        }
    }

    private static class DeleteAllEntriesAsyncTask extends AsyncTask<Void, Void, Void> {

        private EntryDao entryDao;

        private DeleteAllEntriesAsyncTask(EntryDao entryDao) {
            this.entryDao = entryDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            entryDao.deleteAllEntries();
            return null;
        }
    }
}
