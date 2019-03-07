package at.hagenberg.captainhook.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import at.hagenberg.captainhook.R;
import at.hagenberg.captainhook.model.youtube.YoutubeBrowseModel;
import com.squareup.picasso.Picasso;

public class YoutubeRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final String TAG = getClass().getName();

    class ViewHolderVideo extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView titleTextView;
        private TextView channelTextView;
        private TextView dateTextView;
        private ImageView thumbnailImageView;
        private WeakReference<ClickListener> listenerRef;

        public ViewHolderVideo(View v, ClickListener listener) {
            super(v);

            listenerRef = new WeakReference<>(listener);
            this.titleTextView = (TextView) v.findViewById(R.id.youtube_title_textview);
            this.channelTextView = v.findViewById(R.id.youtube_channel_textview);
            this.dateTextView = v.findViewById(R.id.youtube_upload_date_textview);
            this.thumbnailImageView = v.findViewById(R.id.youtube_thumbnail_imageview);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listenerRef.get().onPositionClicked(getAdapterPosition());
        }
    }

    private ArrayList<YoutubeBrowseModel> mDataset;
    private final ClickListener clickListener;

    public YoutubeRecyclerViewAdapter(ArrayList<YoutubeBrowseModel> myDataset, ClickListener clickListener) {
        mDataset = myDataset;
        this.clickListener = clickListener;
        ;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View vTrack = LayoutInflater.from(parent.getContext()).inflate(R.layout.youtube_browse_item, parent, false);
        return new ViewHolderVideo(vTrack, clickListener);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ViewHolderVideo vh = (ViewHolderVideo) holder;
        vh.titleTextView.setText(Html.fromHtml(mDataset.get(vh.getAdapterPosition()).getTitle() , Html.FROM_HTML_MODE_LEGACY));
        vh.channelTextView.setText(mDataset.get(vh.getAdapterPosition()).getChannel());
        vh.dateTextView.setText(mDataset.get(vh.getAdapterPosition()).getUploaded());
        Picasso.get().load(mDataset.get(vh.getAdapterPosition()).getThumbnailUrl()).into(vh.thumbnailImageView);
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    public ArrayList<YoutubeBrowseModel> getmDataset() {
        return mDataset;
    }

    public void setmDataset(ArrayList<YoutubeBrowseModel> mDataset) {
        this.mDataset = mDataset;
    }
}


