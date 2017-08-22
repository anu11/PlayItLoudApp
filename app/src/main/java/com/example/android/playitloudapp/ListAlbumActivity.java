package com.example.android.playitloudapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.data.SongEntity;
import com.example.android.data.SoundManagerSingleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by anu on 12/5/17.
 */
public class ListAlbumActivity extends AppCompatActivity {
    public static final String TAG = ListAlbumActivity.class.getSimpleName();
    List<String> listOfAlbums = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);
        getSupportActionBar().setTitle("ALBUMS");

        ListView listView = (ListView) findViewById(R.id.list);
        // Inflate header view
        ViewGroup headerView = (ViewGroup)getLayoutInflater().inflate(R.layout.playlist_header_detail, listView, false);
        TextView textHeader = (TextView) headerView.findViewById(R.id.text_listview_header);
        textHeader.setText(getString(R.string.choosesong_detail));
        // Add header view to the ListView
        listView.addHeaderView(headerView);

        HashMap<String, ArrayList<SongEntity>> hmap = SoundManagerSingleton.getInstance(this.getApplicationContext()).populateSongDataBasedOnAlbum(this.getApplicationContext());
        listView = (ListView) findViewById(R.id.list);
        listOfAlbums = new ArrayList<>();
        for (String key : hmap.keySet()) {
            Log.d(TAG, "Key : " + key);
            if (key != null) {
                listOfAlbums.add(key);
            } else {
                Log.d(TAG, "Key Null found");
            }
        }
        AlbumAdapter adapter = new AlbumAdapter(this, listOfAlbums);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int listIndex = position - 1;
                String albumKey = listOfAlbums.get(position);
                // Create a new intent to open the {@link SongActivity}
                Intent songListIntent = new Intent(ListAlbumActivity.this, ListSongActivity.class);
                songListIntent.putExtra("metaData", albumKey);
                songListIntent.putExtra("type", SoundManagerSingleton.SongType.ALBUM);
                // Start the new activity
                startActivity(songListIntent);
            }
        });
        ImageView homeButton = (ImageView) findViewById(R.id.home_button);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Create a new intent to open the BluesActivity
                Intent mainIntent = new Intent(ListAlbumActivity.this, MainActivity.class);

                // Start the new activity
                startActivity(mainIntent);
            }
        });
    }
}
