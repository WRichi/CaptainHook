package at.hagenberg.captainhook.model.youtube;

import android.os.AsyncTask;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;

import java.io.IOException;

public class YoutubeSearchAsyncTask extends AsyncTask<YouTube.Search.List, Void, SearchListResponse > {

    YoutubeCallback mYoutubeCallback;
    Exception e = null;

    public YoutubeSearchAsyncTask(YoutubeCallback mYoutubeCallback) {
        this.mYoutubeCallback = mYoutubeCallback;
    }

    @Override
    protected SearchListResponse doInBackground(YouTube.Search.List... lists) {
        try {
            return lists[0].execute();
        } catch (IOException e) {
            e.printStackTrace();
            this.e = e;
            return null;
        }
    }

    @Override
    protected void onPostExecute(SearchListResponse searchListResponse) {
        super.onPostExecute(searchListResponse);
        if(searchListResponse!= null){
            mYoutubeCallback.onSearchResult(searchListResponse);
        }else{
            mYoutubeCallback.onError(e.getMessage());
        }
    }
}
