package at.hagenberg.captainhook.model.spotify;

import at.hagenberg.captainhook.model.spotify.spotify_model.SpotifyData;
import at.hagenberg.captainhook.model.spotify.spotify_model.tracksinplaylist.Tracks;

public interface SpotifyGetTracksFromPlaylistCallback {
    public void onTrackResult(Tracks tracks);
}
