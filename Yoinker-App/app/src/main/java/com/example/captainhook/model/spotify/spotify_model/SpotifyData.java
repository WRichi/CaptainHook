
package com.example.captainhook.model.spotify.spotify_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SpotifyData {

    @SerializedName("tracks")
    @Expose
    private Tracks tracks;

    public Tracks getTracks() {
        return tracks;
    }

    public void setTracks(Tracks tracks) {
        this.tracks = tracks;
    }

    @SerializedName("playlists")
    @Expose
    private Playlists playlists;

    public Playlists getPlaylists() {
        return playlists;
    }

    public void setPlaylists(Playlists playlists) {
        this.playlists = playlists;
    }


}
