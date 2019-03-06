package com.example.captainhook.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import com.example.captainhook.R;
import com.example.captainhook.model.spotify.spotify_model.SpotifySearchModel;
import com.squareup.picasso.Picasso;

public class SpotifyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        final public static int TYPE_TRACK = 0;
        final public static int TYPE_PLAYLIST = 1;

        private final String TAG = getClass().getName();

        class ViewHolderTrack extends RecyclerView.ViewHolder implements View.OnClickListener{
            private TextView nameTextView;
            private TextView artistsTextView;
            private TextView albumTextView;
            private ImageView coverImageView;
            private ImageView downloadImageView;
            private WeakReference<ClickListener> listenerRef;

            public ViewHolderTrack(View v, ClickListener listener) {
                super(v);

                listenerRef = new WeakReference<>(listener);
                this.nameTextView = (TextView) v.findViewById(R.id.spotify_name_textview);
                this.artistsTextView = v.findViewById(R.id.spotify_artists_textview);
                this.albumTextView = v.findViewById(R.id.spotify_album_textview);
                this.coverImageView = v.findViewById(R.id.spotify_cover_imageview);
                v.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                listenerRef.get().onPositionClicked(getAdapterPosition());
            }
        }

        private ArrayList<SpotifySearchModel> mDataset;
        private final ClickListener clickListener;

        public SpotifyRecyclerViewAdapter(ArrayList<SpotifySearchModel> myDataset, Context context, ClickListener clickListener) {
            mDataset = myDataset;
            this.clickListener = clickListener;;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType){
                case TYPE_TRACK:
                    View vTrack = LayoutInflater.from(parent.getContext()).inflate(R.layout.spotify_search_item, parent, false);
                    return new ViewHolderTrack(vTrack, clickListener);
//                case TYPE_PLUS:
//                    View vPlus = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_add, parent, false);
//                    return new ViewHolderPlus(vPlus, clickListener);
            }
            return null;
        }

        @Override
        public int getItemViewType(int position) {
//            if(position == mDataset.size()-1){
//                return TYPE_PLUS;
//            }
            return TYPE_TRACK;
        }


        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
            switch (holder.getItemViewType()){
                case 0: ViewHolderTrack vh = (ViewHolderTrack) holder;
                    vh.nameTextView.setText(mDataset.get(vh.getAdapterPosition()).getName());
                    vh.artistsTextView.setText(mDataset.get(vh.getAdapterPosition()).getArtists());
                    vh.albumTextView.setText(mDataset.get(vh.getAdapterPosition()).getAlbum());
                    Picasso.get().load(mDataset.get(vh.getAdapterPosition()).getCoverURL()).into(vh.coverImageView);
                    break;
            }
        }


        @Override
        public int getItemCount() {
            return mDataset.size();
        }


        public ArrayList<SpotifySearchModel> getmDataset() {
            return mDataset;
        }

        public void setmDataset(ArrayList<SpotifySearchModel> mDataset) {
            this.mDataset = mDataset;
        }
    }


