package at.hagenberg.captainhook.model.youtube;

import android.app.DownloadManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import at.hagenberg.captainhook.model.entries.Entry;
import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class YoutubeDownloadJobService extends JobService {

    ArrayList<Entry> entries;
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d("Download", "Downloading via Job Scheduler now.");
        String json = params.getExtras().getString("ids");
        Gson g = new Gson();
        entries = g.fromJson(json, new TypeToken<List<Entry>>(){}.getType());
        for (Entry entry: entries) {
            String yt_id = "https://www.youtube.com/watch?v="+entry.getYt_id();
            getYoutubeDownloadUrl(yt_id);
        }
        return false;
    }
    private void getYoutubeDownloadUrl(String youtubeLink) {
        new YouTubeExtractor(this) {
            @Override
            public void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta vMeta) {
                if (ytFiles == null) {
                    // Something went wrong we got no urls. Always check this.
                    return;
                }
                // Iterate over itags
                for (int i = 0, itag; i < ytFiles.size(); i++) {
                    itag = ytFiles.keyAt(i);
                    // ytFile represents one file with its url and meta data
                    YtFile ytFile = ytFiles.get(itag);
                    // Just add audio only.
                    if (ytFile.getFormat().getHeight() == -1) {
                        String filename = vMeta.getTitle() + "."+ytFile.getFormat().getExt();
                        filename = filename.replaceAll("[\\\\><\"|*?%:#/]", "");

                        Uri uri = Uri.parse(ytFile.getUrl());

                        DownloadManager.Request request = new DownloadManager.Request(uri);
                        request.setTitle(vMeta.getTitle());
                        request.allowScanningByMediaScanner();
                        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_MUSIC+ File.separator+"CptHook/", filename);
                        DownloadManager manager = (DownloadManager) getApplicationContext().getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);
                    }
                }
            }
        }.extract(youtubeLink, true,false);
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d("Job", "Job cancelled.");
        jobFinished(params, true);
        return false;
    }
}
