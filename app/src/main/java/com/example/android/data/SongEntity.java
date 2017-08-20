package com.example.android.data;

import java.io.Serializable;

/**
 * Created by anu on 11/5/17.
 */

public class SongEntity implements Serializable {
    private String songTitle;
    private String songArtist;
    private String songPath;
    private String songAlbum;

    public SongEntity(String title, String artist, String data, String album) {
        songTitle = title;
        songArtist = artist;
        songPath = data;
        songAlbum = album;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getSongArtist() {
        return songArtist;
    }

    public String getSongPath() {
        return songPath;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

}