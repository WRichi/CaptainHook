package com.example.captainhook.model.spotify;

import com.example.captainhook.model.spotify.spotify_model.SpotifyData;
import com.example.captainhook.model.spotify.spotify_model.tracksinplaylist.Tracks;

public interface SpotifyGetTracksFromPlaylistCallback {
    public void onTrackResult(Tracks tracks);
}
