package com.example.captainhook.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.example.captainhook.model.Entry;
import com.example.captainhook.R;

import java.util.ArrayList;
import java.util.List;


public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryHolder> {

    private List<Entry> entries = new ArrayList<>();
    private OnItemClickListener listener;

    // context for GlideApp
    private Context context;

    public EntryAdapter(Context context) {
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


    class EntryHolder extends RecyclerView.ViewHolder {
        private TextView textViewtitle;
        private TextView textViewinterpret;
        private ImageView imageViewthumbnail;

        public EntryHolder(@NonNull View itemView) {
            super(itemView);
            textViewtitle = itemView.findViewById(R.id.text_view_title);
            textViewinterpret = itemView.findViewById(R.id.text_view_interpret);
            imageViewthumbnail = itemView.findViewById(R.id.image_view_thumbnail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(entries.get(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Entry entry);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
