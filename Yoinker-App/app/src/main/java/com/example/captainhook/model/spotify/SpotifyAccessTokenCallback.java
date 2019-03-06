package com.example.captainhook.model.spotify;

import com.example.captainhook.model.spotify.spotify_model.AccessToken;

public interface SpotifyAccessTokenCallback {
    public void onAccessTokenRecieved(AccessToken access_token);
}
