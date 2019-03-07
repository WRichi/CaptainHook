package com.example.captainhook.views;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.captainhook.R;
import com.example.captainhook.model.spotify.SpotifyGetTracksFromPlaylistCallback;
import com.example.captainhook.model.spotify.SpotifySearchResultCallback;
import com.example.captainhook.model.spotify.SpotifySearchType;
import com.example.captainhook.model.spotify.spotify_model.Artist_;
import com.example.captainhook.model.spotify.spotify_model.Image;
import com.example.captainhook.model.spotify.spotify_model.Playlists;
import com.example.captainhook.model.spotify.spotify_model.SpotifyData;
import com.example.captainhook.model.spotify.spotify_model.track.Item;
import com.example.captainhook.model.spotify.spotify_model.tracksinplaylist.Tracks;
import com.example.captainhook.viewmodels.SpotifyViewModel;
import com.example.captainhook.views.adapter.ClickListener;
import com.example.captainhook.views.adapter.SpotifyRecyclerViewAdapter;
import com.example.captainhook.model.spotify.spotify_model.SpotifySearchModel;

import java.util.ArrayList;
import java.util.List;

public class SpotifySearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    SpotifyViewModel spotifyViewModel;
    private final String TAG = getClass().getName();
    private SpotifySearchType currentResult = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_search);


        Spannable text = new SpannableString(getSupportActionBar().getTitle());
        text.setSpan(new ForegroundColorSpan(getColor(R.color.spotify_black)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(text);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.spotify_green)));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        spotifyViewModel = ViewModelProviders.of(this).get(SpotifyViewModel.class);

        final EditText edit_text = findViewById(R.id.spotify_search_edittext);
        edit_text.setImeOptions(EditorInfo.IME_ACTION_DONE);

        // Select Track or Playlist to search
        final Spinner spinner = findViewById(R.id.spotify_search_spinner);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.spotify_search_category, R.layout.spotify_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);

        // Default item selected: Track
        int selectionPosition = spinner_adapter.getPosition("Track");
        spinner.setSelection(selectionPosition);

        // Start the search on spotify
        Button searchButton = findViewById(R.id.spotify_search_button);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_text = edit_text.getText().toString();
                hideKeyboard(SpotifySearchActivity.this);
                //Toast.makeText(getApplicationContext(), "Search Spotify for: " + search_text, Toast.LENGTH_SHORT).show();

                String selectedItem = (String) spinner.getSelectedItem();
                SpotifySearchType searchType = SpotifySearchType.valueOf(selectedItem);
                spotifyViewModel.searchSpotify(search_text, searchType, new SpotifySearchResultCallback() {
                    @Override
                    public void onSearchResult(final SpotifyData spotifyData) {
                        Log.d(TAG, "Spotify Search Result Recieved");
                        final ArrayList<SpotifySearchModel> dataSet = new ArrayList<>();


                        if (spotifyData.getPlaylists() == null) {
                            currentResult = SpotifySearchType.TRACK;
                            for (Item item : spotifyData.getTracks().getItems()) {
                                String artist = "";
                                List<Artist_> artists = item.getArtists();
                                for (int x = 0; x < artists.size(); x++) {
                                    Artist_ artist_ = artists.get(x);
                                    if (x != 0) {
                                        artist += ", ";
                                    }
                                    artist += artist_.getName();
                                }
                                String album = item.getAlbum().getName();
                                List<Image> images = item.getAlbum().getImages();
                                String coverUrl = images.get(1).getUrl(); //Image 0 = 640x640, Image 1 = 300x300, Image 2 = 64x64
                                dataSet.add(new SpotifySearchModel(item.getName(), artist, album, coverUrl));
                            }

                        } else {
                            currentResult = SpotifySearchType.PLAYLIST;
                            Playlists playlists = spotifyData.getPlaylists();
                            List<com.example.captainhook.model.spotify.spotify_model.playlist.Item> items = playlists.getItems();
                            for (int x = 0; x < items.size(); x++) {
                                com.example.captainhook.model.spotify.spotify_model.playlist.Item item = items.get(x);
                                String owner = item.getOwner().getDisplayName();
                                String thumbnailURL = "";
                                List<Image> imageList = item.getImages();
                                if (imageList != null) {
                                    for (int z = 0; z < imageList.size() && z <= 1; z++) {
                                        Image image = imageList.get(z);
                                        if (image != null) {
                                            thumbnailURL = image.getUrl();
                                        }
                                    }
                                }
                                String trackCount = item.getTracks().getTotal() + "";
                                String tracksURL = item.getTracks().getHref();
                                tracksURL = tracksURL.substring(tracksURL.indexOf("playlists"));
                                dataSet.add(new SpotifySearchModel(item.getName(), owner, trackCount, thumbnailURL, tracksURL));
                            }
                        }

                        final SpotifyRecyclerViewAdapter recyclerViewAdapter = new SpotifyRecyclerViewAdapter(dataSet, getApplicationContext(), new ClickListener() {
                            @Override
                            public void onPositionClicked(int position) {
                                Log.d(TAG, "SpotifyRecyclerView Clicked at Pos=" + position);
                                SpotifySearchModel spotifySearchModel = dataSet.get(position);
                                if (currentResult != null) {
                                    if (currentResult == SpotifySearchType.TRACK) {
                                        Intent i = new Intent(SpotifySearchActivity.this, YoutubeBrowseActivity.class);

                                        String query = spotifySearchModel.getName() + " " + spotifySearchModel.getArtists();
                                        i.putExtra("query", query);
                                        i.putExtra("total", 1);
                                        startActivity(i);
                                    } else if (currentResult == SpotifySearchType.PLAYLIST) {
                                        final ArrayList<String> trackQueriesList = new ArrayList<>();
                                        spotifyViewModel.getTracksForUrl(spotifySearchModel.getTracksURL(), new SpotifyGetTracksFromPlaylistCallback() {

                                            @Override
                                            public void onTrackResult(Tracks tracks) {
                                                Intent intent = new Intent(SpotifySearchActivity.this, YoutubeBrowseActivity.class);
                                                List<com.example.captainhook.model.spotify.spotify_model.tracksinplaylist.Item> trackList = tracks.getItems();
                                                for (int i = 0; i < trackList.size(); i++) {
                                                    com.example.captainhook.model.spotify.spotify_model.tracksinplaylist.Item item = trackList.get(i);
                                                    String artist = "";
                                                    List<Artist_> artists = item.getTrack().getArtists();
                                                    for (int x = 0; x < artists.size(); x++) {
                                                        Artist_ artist_ = artists.get(x);
                                                        if (x != 0) {
                                                            artist += ", ";
                                                        }
                                                        artist += artist_.getName();
                                                    }
                                                    trackQueriesList.add(item.getTrack().getName() + " " + artist);
                                                }
                                                intent.putExtra("queryList", trackQueriesList);
                                                startActivity(intent);
                                            }
                                        });

                                    }
                                }

                            }

                            @Override
                            public void onLongClicked(int position) {

                            }
                        });

                        RecyclerView recyclerView = findViewById(R.id.spotify_recyclerview);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }
                });


            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void connected() {
        // Then we will write some more code here.
        //mSpotifyAppRemote.getConnectApi().
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Aaand we will finish off here.
        //SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }

    public static void hideKeyboard(SpotifySearchActivity context) {
        // Check if no view has focus:
        View view = context.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.spotify_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(this, HistoryActivity.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }
}
