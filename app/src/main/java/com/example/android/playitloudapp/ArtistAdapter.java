package com.example.android.playitloudapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anu on 23/5/17.
 */

public class ArtistAdapter extends ArrayAdapter<String> {
    List<String> mArtists;
    /**
     * Create a new
     */
    public ArtistAdapter(Context context, List<String> artist) {
        super(context, 0, artist);
        mArtists = artist;
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

        // Get the {@link Word} object located at this position in the list
        String currentArtist = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID miwok_text_view.
        TextView songView = (TextView) listItemView.findViewById(R.id.playit_text_view);
        // Get the Miwok translation from the currentWord object and set this text on
        // the Miwok TextView.
        songView.setText(currentArtist);
        // Return the whole list item layout (containing 2 TextViews) so that it can be shown in
        // the ListView.
        return listItemView;
    }
}
