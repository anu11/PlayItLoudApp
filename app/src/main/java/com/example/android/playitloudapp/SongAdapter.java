package com.example.android.playitloudapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.data.SongEntity;

import java.util.ArrayList;

/**
 * Created by anu on 15/5/17.
 */

public class SongAdapter extends ArrayAdapter<SongEntity> {
    ArrayList<SongEntity> mSongs;

    /**
     * Create a new song adapter
     */
    public SongAdapter(Context context, ArrayList<SongEntity> songs) {
        super(context, 0, songs);
        mSongs = songs;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }
        LinearLayout songLayout = (LinearLayout) listItemView.findViewById(R.id.song_container);
        songLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongEntity songEntity = mSongs.get(position);
                // Create a new intent to open the {@link playsongactivity}
                Intent songListIntent = new Intent(view.getContext().getApplicationContext(), PlaySongActivity.class);
                songListIntent.putExtra(SongEntity.class.getSimpleName(), songEntity);
                songListIntent.putExtra("mylist", mSongs);
                songListIntent.putExtra("position", position);
                // Start the new activity
                view.getContext().startActivity(songListIntent);
            }
        });

        // Get the {@link song} object located at this position in the list
        SongEntity currentSong = getItem(position);
        // Find the TextView in the list_item.xml layout
        TextView songView = (TextView) listItemView.findViewById(R.id.playit_text_view);
        songView.setText(currentSong.getSongTitle());
        // Return the whole list item layout  so that it can be shown in
        // the ListView.
        return listItemView;
    }
}
