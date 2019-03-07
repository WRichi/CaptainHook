package at.hagenberg.captainhook.model.spotify.spotify_model;

import android.graphics.drawable.Drawable;

public class SpotifySearchModel {

    private String name;
    private String artists;
    private String album;
    private String coverURL;
    private String tracksURL;

    public SpotifySearchModel(String name, String artists, String album, String coverURL) {
        this.name = name;
        this.artists = artists;
        this.album = album;
        this.coverURL = coverURL;
    }

    public SpotifySearchModel(String name, String artists, String album, String coverURL, String tracksURL) {
        this.name = name;
        this.artists = artists;
        this.album = album;
        this.coverURL = coverURL;
        this.tracksURL = tracksURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getArtists() {
        return artists;
    }

    public void setArtists(String artists) {
        this.artists = artists;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public String getTracksURL() {
        return tracksURL;
    }

    public void setTracksURL(String tracksURL) {
        this.tracksURL = tracksURL;
    }
}
