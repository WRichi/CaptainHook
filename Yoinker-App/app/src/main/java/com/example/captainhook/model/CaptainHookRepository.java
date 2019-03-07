package com.example.captainhook.model;

import android.app.Application;
import android.os.AsyncTask;

import java.util.List;

import android.arch.lifecycle.LiveData;

import com.example.captainhook.model.entries.Entry;
import com.example.captainhook.model.entries.EntryDao;
import com.example.captainhook.model.entries.EntryDatabase;
import com.example.captainhook.model.spotify.SpotifyAccessTokenCallback;
import com.example.captainhook.model.spotify.SpotifyDataSource;
import com.example.captainhook.model.spotify.SpotifyGetTracksFromPlaylistCallback;
import com.example.captainhook.model.spotify.SpotifySearchResultCallback;
import com.example.captainhook.model.spotify.SpotifySearchType;
import com.example.captainhook.model.spotify.spotify_model.AccessToken;
import com.example.captainhook.model.youtube.YoutubeCallback;
import com.example.captainhook.model.youtube.YoutubeDataSource;

public class CaptainHookRepository {

    private EntryDao entryDao;
    private SpotifyDataSource spotifyDataSource;
    private YoutubeDataSource youtubeDataSource;
    private LiveData<List<Entry>> allEntries;
    private AccessToken accessToken = null;

    public CaptainHookRepository(Application application) {
        EntryDatabase database = EntryDatabase.getInstance(application);
        entryDao = database.entryDao();
        spotifyDataSource = new SpotifyDataSource();
        youtubeDataSource = new YoutubeDataSource();
        allEntries = entryDao.getAllEntries();
    }

    /**
     * Api for the outside e.g. ViewModel
     */
    public void insertEntry(Entry entry) {
        new InsertEntryAsyncTask(entryDao).execute(entry);
    }

    public void deleteEntry(Entry entry) {
        new DeleteEntryAsyncTask(entryDao).execute(entry);
    }

    public void deleteAllEntries() {
        new DeleteAllEntriesAsyncTask(entryDao).execute();
    }

    public LiveData<List<Entry>> getAllEntries() {
        return allEntries;
    }

    public void searchSpotify(final String query, final SpotifySearchType searchType, final SpotifySearchResultCallback resultCallback){
        if(accessToken != null && accessToken.getExpiresIn() != 0){
            spotifyDataSource.searchSpotify(query, searchType, accessToken, resultCallback);
        }else{
            spotifyDataSource.getAccessToken(new SpotifyAccessTokenCallback() {
                @Override
                public void onAccessTokenRecieved(AccessToken accessToken) {
                    CaptainHookRepository.this.accessToken = accessToken;
                    spotifyDataSource.searchSpotify(query, searchType, accessToken, resultCallback);
                }
            });
        }
    }

    public void getTracks(final String url, final SpotifyGetTracksFromPlaylistCallback resultCallback){
        if(accessToken != null && accessToken.getExpiresIn() != 0){
            spotifyDataSource.getTracksForUrl(url, accessToken, resultCallback);
        }else{
            spotifyDataSource.getAccessToken(new SpotifyAccessTokenCallback() {
                @Override
                public void onAccessTokenRecieved(AccessToken accessToken) {
                    CaptainHookRepository.this.accessToken = accessToken;
                    spotifyDataSource.getTracksForUrl(url, accessToken, resultCallback);
                }
            });
        }
    }

    public void browseYoutube(String query, YoutubeCallback youtubeCallback){
        youtubeDataSource.browseYoutube(query, youtubeCallback);
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
