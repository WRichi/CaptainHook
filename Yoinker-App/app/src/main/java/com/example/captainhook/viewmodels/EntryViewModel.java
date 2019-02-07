package com.example.captainhook.viewmodels;

import android.app.Application;

import com.example.captainhook.model.Entry;
import com.example.captainhook.model.EntryRepository;

import java.util.List;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

public class EntryViewModel extends AndroidViewModel {
    private EntryRepository repository;

    private LiveData<List<Entry>> allEntries;

    public EntryViewModel(Application application) {
        super(application);
        repository = new EntryRepository(application);
        allEntries = repository.getAllEntries();
    }

    public void insert(Entry entry) {
        repository.insert(entry);
    }

    public void delete(Entry entry) {
        repository.delete(entry);
    }

    public void deleteAllEntries() {
        repository.deleteAll();
    }

    public LiveData<List<Entry>> getAllEntries() {
        return allEntries;
    }
}
