package com.example.captainhook.model.entries;

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

    @Query("DELETE FROM history_table")
    void deleteAllEntries();

    @Query("SELECT * FROM history_table ORDER BY interpret ASC")
    LiveData<List<Entry>> getAllEntries();
}
