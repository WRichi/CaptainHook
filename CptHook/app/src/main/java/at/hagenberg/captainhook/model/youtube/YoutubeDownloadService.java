package at.hagenberg.captainhook.model.youtube;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;
import android.util.SparseArray;

import java.util.ArrayList;

import at.hagenberg.captainhook.model.entries.Entry;
import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

public class YoutubeDownloadService {

    private ArrayList<Entry> ids;
    private Context context;

    public YoutubeDownloadService(ArrayList<Entry> ids, Context context){
        this.ids = ids;
        this.context = context;
    }

    public void downloadSongs(){
        for (Entry entry: ids) {
            String yt_id = "https://www.youtube.com/watch?v="+entry.getYt_id();
            getYoutubeDownloadUrl(yt_id, context);
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
                        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);
                        DownloadManager manager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
                        manager.enqueue(request);

                    }
                }
            }
        }.extract(youtubeLink, true,false);
    }
}
