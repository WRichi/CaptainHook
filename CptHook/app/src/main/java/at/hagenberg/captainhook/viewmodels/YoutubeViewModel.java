package at.hagenberg.captainhook.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import at.hagenberg.captainhook.model.CaptainHookRepository;
import at.hagenberg.captainhook.model.youtube.YoutubeCallback;

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
