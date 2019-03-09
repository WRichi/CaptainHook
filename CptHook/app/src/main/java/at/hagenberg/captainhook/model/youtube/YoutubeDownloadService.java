package at.hagenberg.captainhook.model.youtube;

import android.app.Application;
import android.app.DownloadManager;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.os.PersistableBundle;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;

import at.hagenberg.captainhook.model.CaptainHookRepository;
import at.hagenberg.captainhook.model.entries.Entry;
import at.hagenberg.captainhook.model.entries.EntryDao;
import at.hagenberg.captainhook.model.entries.EntryDatabase;
import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class YoutubeDownloadService {

    CaptainHookRepository repository;
    private ArrayList<Entry> ids;
    private Context context;
    private int mJobID = 0;
    private ComponentName mComponentName = new ComponentName("at.hagenberg.captainhook", "at.hagenberg.captainhook.model.youtube.YoutubeDownloadJobService");
    private boolean downloadNow;

    public YoutubeDownloadService(ArrayList<Entry> ids, Context context, Boolean downloadNow){
        this.ids = ids;
        this.context = context;
        this.downloadNow = downloadNow;
        repository = new CaptainHookRepository((Application) context.getApplicationContext());
    }

    public void downloadSongs(){
        if(downloadNow){
            Log.d("Download", "Downloading instantly.");
            for (Entry entry: ids) {
                String yt_id = "https://www.youtube.com/watch?v="+entry.getYt_id();
                getYoutubeDownloadUrl(yt_id, context);
                repository.insertEntry(entry);
            }
        }else {
            Log.d("Download", "Downloading via Job Scheduler.");
            PersistableBundle persistableBundle = new PersistableBundle();
            Gson g = new Gson();
            String jsonIDS = g.toJson(ids);
            persistableBundle.putString("ids", jsonIDS);
            JobInfo DownloadTask = new JobInfo.Builder(mJobID++, mComponentName).setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED).setExtras(persistableBundle).build();
            JobScheduler jobScheduler =
                    (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
            jobScheduler.schedule(DownloadTask);
        }
    }

    private void getYoutubeDownloadUrl(String youtubeLink, final Context context) {
        new YouTubeExtractor(context) {
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
                        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);
                    }
                }
            }
        }.extract(youtubeLink, true,false);
    }

}
