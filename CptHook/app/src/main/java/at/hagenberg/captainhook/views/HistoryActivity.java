package at.hagenberg.captainhook.views;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import at.hagenberg.captainhook.model.entries.Entry;
import at.hagenberg.captainhook.viewmodels.EntryViewModel;
import at.hagenberg.captainhook.R;
import at.hagenberg.captainhook.views.adapter.EntryAdapter;

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
    }
}
