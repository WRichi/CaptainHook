package com.example.captainhook.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import com.example.captainhook.model.CaptainHookRepository;
import com.example.captainhook.model.youtube.YoutubeCallback;

public class YoutubeViewModel extends AndroidViewModel{

    CaptainHookRepository repository;

    public YoutubeViewModel(@NonNull Application application) {
        super(application);
        repository = new CaptainHookRepository(application);
    }

    public void browseYoutube(String query, YoutubeCallback callback){
        repository.browseYoutube(query, callback);
    }
}
