package com.example.android.playitloudapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.android.data.SongEntity;
import com.example.android.data.SoundManagerSingleton;

import java.util.ArrayList;


public class ListSongActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.song_list);
        Bundle bundle = getIntent().getExtras();
        String metaData = bundle.getString("metaData");
        SoundManagerSingleton.SongType songType = (SoundManagerSingleton.SongType) bundle.getSerializable("type");

        ArrayList<SongEntity> arrayOfSongs = SoundManagerSingleton.getInstance(
                this.getApplicationContext()).getSongEntityList(songType, metaData);
        SoundManagerSingleton.getInstance(this.getApplicationContext()).setCurrentPlayList(arrayOfSongs);
// Create the adapter to convert the array to views
        SongAdapter adapter = new SongAdapter(this, arrayOfSongs);
// Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);
    }
}