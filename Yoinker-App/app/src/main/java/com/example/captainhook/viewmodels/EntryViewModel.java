package com.example.captainhook.viewmodels;

import android.app.Application;

import com.example.captainhook.model.entries.Entry;
import com.example.captainhook.model.CaptainHookRepository;

import java.util.List;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

public class EntryViewModel extends AndroidViewModel {
    private CaptainHookRepository repository;

    private LiveData<List<Entry>> allEntries;

    public EntryViewModel(Application application) {
        super(application);
        repository = new CaptainHookRepository(application);
        allEntries = repository.getAllEntries();
    }

    public void insert(Entry entry) {
        repository.insertEntry(entry);
    }

    public void delete(Entry entry) {
        repository.deleteEntry(entry);
    }

    public void deleteAllEntries() {
        repository.deleteAllEntries();
    }

    public LiveData<List<Entry>> getAllEntries() {
        return allEntries;
    }
}
