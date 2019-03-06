package com.example.captainhook.model.entries;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Entry for the spotify search history
 */
@Entity(tableName = "history_table")
public class Entry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String interpret;

    private String thumbnail_link;

    private String path_to_file;

    public Entry(String title, String interpret, String thumbnail_link, String path_to_file) {
        this.title = title;
        this.interpret = interpret;
        this.thumbnail_link = thumbnail_link;
        this.path_to_file = path_to_file;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getPath_to_file() {
        return path_to_file;
    }
}
