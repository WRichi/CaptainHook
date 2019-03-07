package at.hagenberg.captainhook.model.spotify;

import at.hagenberg.captainhook.model.spotify.spotify_model.AccessToken;

public interface SpotifyAccessTokenCallback {
    public void onAccessTokenRecieved(AccessToken access_token);
}
