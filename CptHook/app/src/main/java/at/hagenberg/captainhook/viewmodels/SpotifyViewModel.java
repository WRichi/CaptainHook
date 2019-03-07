package at.hagenberg.captainhook.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import at.hagenberg.captainhook.model.CaptainHookRepository;
import at.hagenberg.captainhook.model.spotify.SpotifyGetTracksFromPlaylistCallback;
import at.hagenberg.captainhook.model.spotify.SpotifySearchResultCallback;
import at.hagenberg.captainhook.model.spotify.SpotifySearchType;
import at.hagenberg.captainhook.model.spotify.spotify_model.SpotifyData;

public class SpotifyViewModel extends AndroidViewModel{

    private CaptainHookRepository repository;

    public SpotifyViewModel(@NonNull Application application) {
        super(application);
        repository = new CaptainHookRepository(application);
    }

    public void searchSpotify(String query, SpotifySearchType searchType, SpotifySearchResultCallback callback){
        repository.searchSpotify(query, searchType, callback);
    }

    public void getTracksForUrl(String url, SpotifyGetTracksFromPlaylistCallback resultCallback){
        repository.getTracks(url, resultCallback);
    }
}
