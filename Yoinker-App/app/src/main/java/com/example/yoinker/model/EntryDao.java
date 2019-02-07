package com.example.yoinker.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import android.arch.lifecycle.LiveData;

@Dao
public interface EntryDao {

    @Insert
    void insert(Entry entry);

    @Delete
    void delete(Entry entry);

    @Query("DELETE FROM search_entries_table")
    void deleteAllEntries();

    @Query("SELECT * FROM search_entries_table ORDER BY timestamp ASC")
    LiveData<List<Entry>> getAllEntries();
}
