package at.hagenberg.captainhook.views;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.format.DateFormat;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.util.DateTime;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;

import at.hagenberg.captainhook.R;
import at.hagenberg.captainhook.model.entries.Entry;
import at.hagenberg.captainhook.model.youtube.YoutubeBrowseModel;
import at.hagenberg.captainhook.model.youtube.YoutubeCallback;
import at.hagenberg.captainhook.viewmodels.YoutubeViewModel;
import at.hagenberg.captainhook.views.adapter.ClickListener;
import at.hagenberg.captainhook.views.adapter.YoutubeRecyclerViewAdapter;

public class YoutubeBrowseActivity extends AppCompatActivity {

    private final String TAG = getClass().getName();
    YoutubeViewModel youtubeViewModel;
    public Entry currentquery;
    public int queryCount = 0;
    ArrayList<Entry> queryList = null;
    ArrayList<Entry> downloadList = new ArrayList<>();
    boolean downloadNow = true;

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
        if (b.containsKey("query")) {
            currentquery = b.getParcelable("query");
            queryList = new ArrayList<>();
            queryList.add(currentquery);
        } else {
            queryList = b.getParcelableArrayList("queryList");
            currentquery = queryList.get(0);
            Log.d("Query 1: ", currentquery.getTitle() + " " + currentquery.getInterpret());
        }

        searchYoutubeStep();

        Button skipButton = findViewById(R.id.youtube_skip_button);
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                next();
            }
        });

    }

    public void searchYoutubeStep(){
        youtubeViewModel = ViewModelProviders.of(this).get(YoutubeViewModel.class);
        youtubeViewModel.browseYoutube(currentquery.getTitle() + " " + currentquery.getInterpret(), new YoutubeCallback() {
            @Override
            public void onSearchResult(SearchListResponse searchListResponse) {
                TextView progessText = findViewById(R.id.youtube_queue_progress_textview);
                progessText.setText((queryCount+1)+"/"+queryList.size());

                TextView youtubeQueryTextView = findViewById(R.id.youtube_query_textview);
                youtubeQueryTextView.setText("\""+currentquery.getTitle() + " " + currentquery.getInterpret()+"\"");

                Iterator<SearchResult> iteratorSearchResults = searchListResponse.getItems().iterator();
                if (!iteratorSearchResults.hasNext()) {
                    Log.d(TAG, " There aren't any results for your query.");
                    Toast.makeText(getApplicationContext(), "No results for: " + currentquery, Toast.LENGTH_SHORT).show();
                }

                final ArrayList<YoutubeBrowseModel> datamodels = new ArrayList<>();
                while (iteratorSearchResults.hasNext()) {

                    SearchResult singleVideo = iteratorSearchResults.next();
                    ResourceId rId = singleVideo.getId();
                    String title = singleVideo.getSnippet().getTitle();
                    String channel = singleVideo.getSnippet().getChannelTitle();
                    String dateTime = singleVideo.getSnippet().getPublishedAt().toString();
                    String publishedAt = null;
                    try {
                        publishedAt = getDate(dateTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getMedium();
                    datamodels.add(new YoutubeBrowseModel(rId.getVideoId(), title, channel, publishedAt, thumbnail.getUrl()));
                }

                //adapter
                final YoutubeRecyclerViewAdapter recyclerViewAdapter = new YoutubeRecyclerViewAdapter(datamodels, new ClickListener() {
                    @Override
                    public void onPositionClicked(int position) {
                        YoutubeBrowseModel youtubeBrowseModel = datamodels.get(position);
                        currentquery.setYt_id(youtubeBrowseModel.getId());
                        downloadList.add(currentquery);
                        next();
                    }

                    @Override
                    public void onLongClicked(int position) {

                    }
                });

                RecyclerView recyclerView = findViewById(R.id.youtube_recyclerview);
                recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void onError(String message) {
                Toast.makeText(getApplicationContext(), "Error: API Data Usage Limit Exceeded", Toast.LENGTH_SHORT).show();
                YoutubeBrowseActivity.this.finish();
            }
        });
    }

    public void next(){
        queryCount++;
        if(queryCount<queryList.size()){
            currentquery = queryList.get(queryCount);
            searchYoutubeStep();
        }else{
            startDownload();
        }
    }

    public void startDownload(){
        Log.d(TAG, "start download");
        if(!connectedToWifi()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You have no Wifi enabled. Do you want to start downloading or schedule for later?").setCancelable(false).setPositiveButton("Start now", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    downloadNow = true;
                    youtubeViewModel.downloadYoutubeSongs(downloadList, getApplicationContext(), downloadNow);
                    Intent i = new Intent(YoutubeBrowseActivity.this, SpotifySearchActivity.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "Started Download.",
                            Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("Later", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    downloadNow = false;
                    dialog.cancel();
                    youtubeViewModel.downloadYoutubeSongs(downloadList, getApplicationContext(), downloadNow);
                    Intent i = new Intent(YoutubeBrowseActivity.this, SpotifySearchActivity.class);
                    startActivity(i);
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
        }else{
            youtubeViewModel.downloadYoutubeSongs(downloadList, getApplicationContext(), downloadNow);
            Intent i = new Intent(YoutubeBrowseActivity.this, SpotifySearchActivity.class);
            startActivity(i);
            Toast.makeText(this, "Started Download.",
                    Toast.LENGTH_SHORT).show();
        }


    }

    boolean connectedToWifi(){
        WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        if(wifiManager.isWifiEnabled()){
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            if(wifiInfo.getNetworkId() == -1){
                return false;
            }
        }else{
            return false;
        }
        return true;
    }

    public static String getDate(String date) throws ParseException {
        String[] dateSplit = date.split("T");
        SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd");
        Date parsedDate = parser.parse(dateSplit[0]);
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = formatter.format(parsedDate);
        return formattedDate;
    }


}
