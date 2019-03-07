package com.example.captainhook.model.spotify.spotify_model.playlist;

import com.example.captainhook.model.spotify.spotify_model.ExternalUrls;
import com.example.captainhook.model.spotify.spotify_model.Image;
import com.example.captainhook.model.spotify.spotify_model.Owner;
import com.example.captainhook.model.spotify.spotify_model.Tracks;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Item {

        @SerializedName("collaborative")
        @Expose
        private Boolean collaborative;
        @SerializedName("external_urls")
        @Expose
        private ExternalUrls externalUrls;
        @SerializedName("href")
        @Expose
        private String href;
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("images")
        @Expose
        private List<Image> images = null;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("owner")
        @Expose
        private Owner owner;
        @SerializedName("primary_color")
        @Expose
        private Object primaryColor;
        @SerializedName("public")
        @Expose
        private Object _public;
        @SerializedName("snapshot_id")
        @Expose
        private String snapshotId;
        @SerializedName("tracks")
        @Expose
        private Tracks tracks;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("uri")
        @Expose
        private String uri;

        public Boolean getCollaborative() {
            return collaborative;
        }

        public void setCollaborative(Boolean collaborative) {
            this.collaborative = collaborative;
        }

        public ExternalUrls getExternalUrls() {
            return externalUrls;
        }

        public void setExternalUrls(ExternalUrls externalUrls) {
            this.externalUrls = externalUrls;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<Image> getImages() {
            return images;
        }

        public void setImages(List<Image> images) {
            this.images = images;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Owner getOwner() {
            return owner;
        }

        public void setOwner(Owner owner) {
            this.owner = owner;
        }

        public Object getPrimaryColor() {
            return primaryColor;
        }

        public void setPrimaryColor(Object primaryColor) {
            this.primaryColor = primaryColor;
        }

        public Object getPublic() {
            return _public;
        }

        public void setPublic(Object _public) {
            this._public = _public;
        }

        public String getSnapshotId() {
            return snapshotId;
        }

        public void setSnapshotId(String snapshotId) {
            this.snapshotId = snapshotId;
        }

        public Tracks getTracks() {
            return tracks;
        }

        public void setTracks(Tracks tracks) {
            this.tracks = tracks;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

    }

