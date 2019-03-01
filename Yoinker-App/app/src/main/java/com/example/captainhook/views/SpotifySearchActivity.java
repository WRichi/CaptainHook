package com.example.captainhook.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.captainhook.R;
import com.example.captainhook.model.Entry;

public class SpotifySearchActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spotify_search);

        final EditText edit_text = findViewById(R.id.edit_text_search);

        // Start the search on spotify
        ImageButton search_button = findViewById(R.id.button_search);
        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String search_text = edit_text.getText().toString();
                Toast.makeText(getApplicationContext(), "Search Spotify for: " + search_text, Toast.LENGTH_SHORT).show();
            }
        });

        // Select Track or Playlist to search
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> spinner_adapter = ArrayAdapter.createFromResource(this, R.array.spotify_search_category, android.R.layout.simple_spinner_item);
        spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinner_adapter);
        spinner.setOnItemSelectedListener(this);

        // Default item selected: Track
        int selectionPosition= spinner_adapter.getPosition("Track");
        spinner.setSelection(selectionPosition);


        // Recycler View for the search results of spotify
        RecyclerView recyclerView = findViewById(R.id.spotify_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        final EntryAdapter adapter = new EntryAdapter(getApplicationContext());
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new EntryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Entry entry) {
                // Open file or open file manager with path to file
                Toast.makeText(SpotifySearchActivity.this, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
