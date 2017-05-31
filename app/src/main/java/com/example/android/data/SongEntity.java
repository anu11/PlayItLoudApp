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
    private String songdisplayName;
    private String songDuration;
    private String albumArtId;



    public SongEntity(String title, String artist, String data, String displayName, String songDurationTime, String album, String imageId) {
        songTitle = title;
        songArtist = artist;
        songPath = data;
        songdisplayName = displayName;
        songDuration = songDurationTime;
        songAlbum = album;
        albumArtId = imageId;
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

    public String getSongdisplayName() {
        return songdisplayName;
    }

    public String getDuration() {
        return songDuration;
    }

    public String getSongAlbum() {
        return songAlbum;
    }

    public String getAlbumArtId() {
        return albumArtId;
    }

}
