package com.example.captainhook.views;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.captainhook.R;
import com.example.captainhook.model.youtube.YoutubeBrowseModel;
import com.example.captainhook.model.youtube.YoutubeCallback;
import com.example.captainhook.viewmodels.SpotifyViewModel;
import com.example.captainhook.viewmodels.YoutubeViewModel;
import com.example.captainhook.views.adapter.ClickListener;
import com.example.captainhook.views.adapter.YoutubeRecyclerViewAdapter;
import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;

public class YoutubeBrowseActivity extends AppCompatActivity {


    private final String TAG = getClass().getName();
    YoutubeViewModel youtubeViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_browse);

        Spannable text = new SpannableString(getSupportActionBar().getTitle());
        text.setSpan(new ForegroundColorSpan(getColor(R.color.youtube_white)), 0, text.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        getSupportActionBar().setTitle(text);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getColor(R.color.youtube_red)));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(true);

        Bundle b = getIntent().getExtras();
        final String query = b.getString("query");

        TextView youtubeQueryTextView = findViewById(R.id.youtube_query_textview);
        youtubeQueryTextView.setText("\""+query+"\"");

        youtubeViewModel = ViewModelProviders.of(this).get(YoutubeViewModel.class);
        youtubeViewModel.browseYoutube(query, new YoutubeCallback() {
            @Override
            public void onSearchResult(SearchListResponse searchListResponse) {
                TextView progessText = findViewById(R.id.youtube_queue_progress_textview);
                progessText.setText("1/1");

                Iterator<SearchResult> iteratorSearchResults = searchListResponse.getItems().iterator();
                if (!iteratorSearchResults.hasNext()) {
                    Log.d(TAG, " There aren't any results for your query.");
                    Toast.makeText(getApplicationContext(), "No results for: " + query, Toast.LENGTH_SHORT).show();
                }

                ArrayList<YoutubeBrowseModel> datamodels = new ArrayList<>();
                while (iteratorSearchResults.hasNext()) {

                    SearchResult singleVideo = iteratorSearchResults.next();
                    ResourceId rId = singleVideo.getId();
                    String title = singleVideo.getSnippet().getTitle();
                    String channel = singleVideo.getSnippet().getChannelTitle();
                    DateTime dateTime = singleVideo.getSnippet().getPublishedAt();
                    String uploaded = getDate(dateTime.getValue());
                    Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getMedium();
                    datamodels.add(new YoutubeBrowseModel(rId.getVideoId(), title, channel, uploaded, thumbnail.getUrl()));
                }

                //adapter
                YoutubeRecyclerViewAdapter recyclerViewAdapter = new YoutubeRecyclerViewAdapter(datamodels, new ClickListener() {
                    @Override
                    public void onPositionClicked(int position) {
                        Toast.makeText(getApplicationContext(), "Result at pos: "+ position + "clicked", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLongClicked(int position) {

                    }
                });

                RecyclerView recyclerView = findViewById(R.id.youtube_recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(recyclerViewAdapter);
            }
        });

        TextView progessText = findViewById(R.id.youtube_queue_progress_textview);
        progessText.setText("1/1");

    }

    private String getDate(long time) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time * 1000);
        String date = DateFormat.format("dd.MM.yyyy", cal).toString();
        return date;
    }
}
