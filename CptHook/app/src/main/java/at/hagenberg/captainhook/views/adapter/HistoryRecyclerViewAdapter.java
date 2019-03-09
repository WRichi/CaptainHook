package at.hagenberg.captainhook.views.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import at.hagenberg.captainhook.model.entries.Entry;
import at.hagenberg.captainhook.R;
import at.hagenberg.captainhook.views.GlideApp;

import java.util.ArrayList;
import java.util.List;


public class HistoryRecyclerViewAdapter extends RecyclerView.Adapter<HistoryRecyclerViewAdapter.EntryHolder> {

    private List<Entry> entries = new ArrayList<>();

    // context for GlideApp
    private Context context;

    private int selectedPos = RecyclerView.NO_POSITION;

    public HistoryRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public EntryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.history_item, viewGroup, false);
        return new EntryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryHolder entryHolder, int i) {
        Entry currentEntry = entries.get(i);
        entryHolder.textViewtitle.setText(currentEntry.getTitle());
        entryHolder.textViewinterpret.setText(currentEntry.getInterpret());
        entryHolder.textViewAlbum.setText(currentEntry.getAlbum());
        // Load Image with glide
        GlideApp.with(context)
                .load(currentEntry.getThumbnail_link())
                .override(60, 60)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_error)
                .into(entryHolder.imageViewthumbnail);
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public void setEntries(List<Entry> entries) {
        this.entries = entries;
        notifyDataSetChanged();
    }

    class EntryHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView textViewtitle;
        private TextView textViewinterpret;
        private ImageView imageViewthumbnail;
        private TextView textViewAlbum;

        public EntryHolder(@NonNull View itemView) {
            super(itemView);
            textViewtitle = itemView.findViewById(R.id.history_title_textview);
            textViewinterpret = itemView.findViewById(R.id.history_interpret_textview);
            textViewAlbum = itemView.findViewById(R.id.history_album_textview);
            imageViewthumbnail = itemView.findViewById(R.id.history_thumbnail_imageview);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            if (position == RecyclerView.NO_POSITION) {
                return;
            }
        }


    }
}
