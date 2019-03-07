package at.hagenberg.captainhook.model.youtube;


import com.google.api.services.youtube.model.SearchListResponse;

public interface YoutubeCallback {
    void onSearchResult(SearchListResponse searchListResponse);
}
