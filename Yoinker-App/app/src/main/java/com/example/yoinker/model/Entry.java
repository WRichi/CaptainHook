package com.example.yoinker.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

/**
 * Entry for the spotify search history
 */
@Entity(tableName = "search_entries_table")
public class Entry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;

    private String timestamp;

    public Entry(String title, String timestamp) {
        this.title = title;
        this.timestamp = timestamp;
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

    public String getTimestamp() {
        return timestamp;
    }
}
