package com.example.android.playitloudapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anu on 23/5/17.
 */

public class AlbumAdapter extends ArrayAdapter<String> {


    protected List<String> mAlbums;

    /**
     * Create a new album adapter
     */
    public AlbumAdapter(Context context, List<String> albums) {
        super(context, 0, albums);
        mAlbums = albums;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the { String}  located at this position in the list
        String currentAlbum = getItem(position);

        // Find the TextView in the list_item.xml layout
        TextView songView = (TextView) listItemView.findViewById(R.id.playit_text_view);

        songView.setText(currentAlbum);


        // Return the whole list item layout  so that it can be shown in
        // the ListView.
        return listItemView;
    }

}
