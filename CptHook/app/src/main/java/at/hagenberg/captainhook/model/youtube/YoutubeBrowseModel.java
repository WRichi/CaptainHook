package at.hagenberg.captainhook.model.youtube;

public class YoutubeBrowseModel {
    private String id;
    private String title;
    private String channel;
    private String views;
    private String uploaded;
    private String thumbnailUrl;

    public YoutubeBrowseModel(String id, String title, String channel, String uploaded, String thumbnailUrl) {
        this.id = id;
        this.title = title;
        this.channel = channel;
        this.views = views;
        this.uploaded = uploaded;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getUploaded() {
        return uploaded;
    }

    public void setUploaded(String uploaded) {
        this.uploaded = uploaded;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
