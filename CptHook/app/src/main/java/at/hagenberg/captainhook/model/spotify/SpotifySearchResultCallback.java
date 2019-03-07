package at.hagenberg.captainhook.model.spotify;

import at.hagenberg.captainhook.model.spotify.spotify_model.SpotifyData;

public interface SpotifySearchResultCallback {

    public void onSearchResult(SpotifyData spotifyData);
}
