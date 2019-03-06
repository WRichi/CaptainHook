package com.example.captainhook.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.captainhook.model.CaptainHookRepository;
import com.example.captainhook.model.spotify.SpotifySearchResultCallback;
import com.example.captainhook.model.spotify.SpotifySearchType;
import com.example.captainhook.model.spotify.spotify_model.SpotifyData;

public class SpotifyViewModel extends AndroidViewModel{

    private CaptainHookRepository repository;

    public SpotifyViewModel(@NonNull Application application) {
        super(application);
        repository = new CaptainHookRepository(application);
    }

    public void searchSpotify(String query, SpotifySearchType searchType, SpotifySearchResultCallback callback){
        repository.searchSpotify(query, searchType, callback);
    }
}
