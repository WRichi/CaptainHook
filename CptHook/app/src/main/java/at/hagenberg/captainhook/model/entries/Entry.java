package at.hagenberg.captainhook.model.entries;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Entry for the spotify search history
 */
@Entity(tableName = "history_table")
public class Entry implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String interpret;

    private String album;

    private String thumbnail_link;

    private String yt_id;

    public Entry(String title, String interpret, String album, String thumbnail_link, String yt_id) {
        this.title = title;
        this.interpret = interpret;
        this.thumbnail_link = thumbnail_link;
        this.album = album;
        this.yt_id = yt_id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setYt_id(String yt_id) {
        this.yt_id = yt_id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getInterpret() {
        return interpret;
    }

    public String getThumbnail_link() {
        return thumbnail_link;
    }

    public String getAlbum() {
        return album;
    }

    public String getYt_id() {
        return yt_id;
    }

    protected Entry(Parcel in) {
        title = in.readString();
        interpret = in.readString();
        album = in.readString();
        thumbnail_link = in.readString();
        yt_id = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(interpret);
        dest.writeString(album);
        dest.writeString(thumbnail_link);
        dest.writeString(yt_id);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Entry> CREATOR = new Parcelable.Creator<Entry>() {
        @Override
        public Entry createFromParcel(Parcel in) {
            return new Entry(in);
        }

        @Override
        public Entry[] newArray(int size) {
            return new Entry[size];
        }
    };
}