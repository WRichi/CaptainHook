package com.example.captainhook.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.captainhook.model.Entry;
import com.example.captainhook.viewmodels.EntryViewModel;
import com.example.captainhook.R;

import java.util.List;

/**
 *  History Activity, change class name later
 */
public class HistoryActivity extends AppCompatActivity {

    private EntryViewModel entryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        RecyclerView recyclerView = findViewById(R.id.history_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final EntryAdapter adapter = new EntryAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        entryViewModel = ViewModelProviders.of(this).get(EntryViewModel.class);
        entryViewModel.getAllEntries().observe(this, new Observer<List<Entry>>() {
            @Override
            public void onChanged(List<Entry> entries) {
                adapter.setEntries(entries);
            }
        });

        adapter.setOnItemClickListener(new EntryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Entry entry) {
                // Open file or open file manager with path to file
                Toast.makeText(HistoryActivity.this, entry.getPath_to_file(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
