package at.hagenberg.captainhook.model.youtube;


import android.os.AsyncTask;
import android.util.Log;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.io.IOException;
import java.util.Iterator;

public class YoutubeDataSource {

    private final String TAG = getClass().getName();
    private final String API_KEY = "AIzaSyB9xpwEVIDJ3uRnpd6Gd8KBt_FlIVPWRp4";
    private final Long MAX_RESULTS = Long.valueOf(10);

    public void browseYoutube(String query, YoutubeCallback callback) {
        try {
            Log.d(TAG,"Start getYoutubeID");
            YouTube youtube = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("CaptainHook").build();

            // Define the API request for retrieving search results.
            final YouTube.Search.List search = youtube.search().list("id,snippet");

            search.setKey(API_KEY);
            search.setQ(query);
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/medium/url,snippet/channelTitle, snippet/publishedAt)");
            search.setMaxResults(MAX_RESULTS);

            // Call the API and print results.
            YoutubeSearchAsyncTask youtubeSearchAsyncTask = new YoutubeSearchAsyncTask(callback);
            youtubeSearchAsyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, search);

        } catch (GoogleJsonResponseException e) {
            Log.e(TAG,"There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            Log.e(TAG, "There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}
