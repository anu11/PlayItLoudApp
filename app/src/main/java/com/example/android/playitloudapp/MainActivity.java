package com.example.android.playitloudapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.android.data.SoundManagerSingleton;

public class MainActivity extends AppCompatActivity  {

    View.OnClickListener textViewOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Create a new intent to open the {@link songActiity}
            Intent songListIntent = new Intent(MainActivity.this, ListSongActivity.class);
            songListIntent.putExtra("type", SoundManagerSingleton.SongType.ALL);
            // Start the new activity
            startActivity(songListIntent);
        }
    };
    View.OnClickListener artistTextViewOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Create a new intent to open the {@link artistActivity}
            Intent songListIntent = new Intent(MainActivity.this, ListArtistActivity.class);
            // Start the new activity
            startActivity(songListIntent);
        }
    };
    View.OnClickListener albumtextViewOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            // Create a new intent to open the {@link albumActivity}
            Intent songListIntent = new Intent(MainActivity.this, ListAlbumActivity.class);

            // Start the new activity
            startActivity(songListIntent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set the content of the activity to use the activity_main.xml layout file
        setContentView(R.layout.activity_main);


        //find the view that shows a list based on songs
        TextView textView = (TextView) findViewById(R.id.create_song_list);
        textView.setOnClickListener(textViewOnclickListener);

        //find the view that shows a list based on artist
        TextView artistTextView = (TextView) findViewById(R.id.create_artist_list);
        artistTextView.setOnClickListener(artistTextViewOnclickListener);

        //find the view that shows a list based on album
        TextView albumTextView = (TextView) findViewById(R.id.create_album_list);
        albumTextView.setOnClickListener(albumtextViewOnclickListener);
    }
}
