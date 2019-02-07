package com.example.yoinker.views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yoinker.R;
import com.example.yoinker.model.Entry;

import java.util.ArrayList;
import java.util.List;


public class EntryAdapter extends RecyclerView.Adapter<EntryAdapter.EntryHolder> {

    private List<Entry> entries = new ArrayList<>();

    @NonNull
    @Override
    public EntryHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.entry_item, viewGroup, false);
        return new EntryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryHolder entryHolder, int i) {
        Entry currentEntry = entries.get(i);
        entryHolder.textViewtitle.setText(currentEntry.getTitle());
        entryHolder.textViewtimestamp.setText(currentEntry.getTimestamp());
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
        private TextView textViewtimestamp;

        public EntryHolder(@NonNull View itemView) {
            super(itemView);
            textViewtitle = itemView.findViewById(R.id.text_view_title);
            textViewtimestamp = itemView.findViewById(R.id.text_view_timestamp);
        }
    }
}
