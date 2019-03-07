
package com.example.captainhook.model.spotify.spotify_model.tracksinplaylist;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VideoThumbnail {

    @SerializedName("url")
    @Expose
    private Object url;

    public Object getUrl() {
        return url;
    }

    public void setUrl(Object url) {
        this.url = url;
    }

}
