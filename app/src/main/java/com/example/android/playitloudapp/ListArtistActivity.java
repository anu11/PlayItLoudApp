package com.example.android.playitloudapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.data.SongEntity;
import com.example.android.data.SoundManagerSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by anu on 12/5/17.
 */

public class ListArtistActivity extends AppCompatActivity {
    public static final String TAG = ListArtistActivity.class.getSimpleName();
    List<String> listOfArtists = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);
        HashMap<String, ArrayList<SongEntity>> hmap = SoundManagerSingleton.getInstance(this.getApplicationContext()).populateSongDataBasedOnArtist(this.getApplicationContext());
        ListView listView = (ListView) findViewById(R.id.list);
        listOfArtists = new ArrayList<>();
        for (String key : hmap.keySet()) {
            Log.d(TAG, "Key : " + key);
            if (key != null) {
                listOfArtists.add(key);
            } else {
                Log.d(TAG, "Key Null found");
            }
        }
        ArtistAdapter adapter = new ArtistAdapter(this, listOfArtists);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String artistKey = listOfArtists.get(position);

                // Create a new intent to open the {@link SongActivity}
                Intent songListIntent = new Intent(ListArtistActivity.this, ListSongActivity.class);
                songListIntent.putExtra("metaData", artistKey);
                songListIntent.putExtra("type", SoundManagerSingleton.SongType.ARTIST);
                // Start the new activity
                startActivity(songListIntent);
            }
        });
    }
}
