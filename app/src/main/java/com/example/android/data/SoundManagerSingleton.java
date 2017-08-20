package com.example.android.data;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.android.playitloudapp.ListAlbumActivity.TAG;

/**
 * Created by anu on 11/5/17.
 */

public class SoundManagerSingleton {

    public enum SongType {
        ALL,
        ALBUM,
        ARTIST
    }

    private static SoundManagerSingleton instance;
    SongEntity currentSongEntity = null;
    HashMap<String, ArrayList<SongEntity>> albumHashMap = new HashMap<>();
    HashMap<String, ArrayList<SongEntity>> artistHashMap = new HashMap<>();
    private ArrayList<SongEntity> songEntityList = null;
    private int counter = 0;
    private ArrayList<SongEntity> currentPlayList = null;

    //Only one instance of this class can be created
    private SoundManagerSingleton() {
    }

    public static SoundManagerSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new SoundManagerSingleton();
            instance.populateSongData();
        }
        return instance;
    }

    public void setCurrentPlayList(ArrayList<SongEntity> currentPlayList) {
        this.currentPlayList = currentPlayList;
    }

    //List of all the songs
    protected void populateSongData() {
        songEntityList = new ArrayList<>();
        songEntityList.add(new SongEntity("Side to side", "Ariana Grande", "ariana_grande_side_to_side", "Dangerous women"));
        songEntityList.add(new SongEntity("One last time", "Ariana Grande", "ariana_grande_one_last_time", "Dangerous women"));
        songEntityList.add(new SongEntity("The greatest", "Sia", "sia_the_greatest", "This is Acting"));
        songEntityList.add(new SongEntity("Shake it off", "Taylor Swift", "taylor_swift_shake_it_off", "T.S 1989"));
        songEntityList.add(new SongEntity("State of grace", "Taylor Swift", "taylor_swift_state_of_grace", "RED"));
        songEntityList.add(new SongEntity("All of me", "John Legend", "john_legend_all_of_me", "Love in the future"));
        songEntityList.add(new SongEntity("Ordinary people", "John Legend", "john_legend_ordinary_people", "get lifted"));
        songEntityList.add(new SongEntity("Kill em wth kindness", "Selena Gomez", "selena_gomez_kill_em_with_kindness", "Revival"));
        songEntityList.add(new SongEntity("The heart wants what it wants", "Selena Gomez", "selena_gomez_the_heart_wants_what_it_wants", "For you"));
    }

    public ArrayList<SongEntity> getSongEntityList(SongType type, String metaData) {
        switch (type) {
            case ALL:
                return songEntityList;

            case ALBUM:
                if (metaData != null) {
                    return albumHashMap.get(metaData);
                }
                break;
            case ARTIST:
                if (metaData != null) {
                    return artistHashMap.get(metaData);
                }
                break;
        }
        return songEntityList;
    }

    public void incrementSongCounter() {
        counter++;
        if (counter >= currentPlayList.size()) {
            counter = 0;
        }
        currentSongEntity = currentPlayList.get(counter);
    }

    public void decrementSongCounter() {
        counter--;
        if (counter < 0) {
            counter = currentPlayList.size() - 1;
        }
        currentSongEntity = currentPlayList.get(counter);
    }

    public SongEntity getCurrentSongEntity() {
        return currentSongEntity;
    }

    public void setCurrentSongEntity(SongEntity currentSongEntity) {
        this.currentSongEntity = currentSongEntity;
        int index = 0;
        for (SongEntity songEntity : currentPlayList) {
            if (songEntity.getSongPath().equalsIgnoreCase(currentSongEntity.getSongPath())) {
                break;
            }
            index++;
        }
        counter = index;
    }

    public HashMap<String, ArrayList<SongEntity>> populateSongDataBasedOnAlbum(Context context) {
        albumHashMap = new HashMap<>();
        for (SongEntity songEntity : songEntityList) {
            String songAlbum = songEntity.getSongAlbum();
            ArrayList<SongEntity> entityList = albumHashMap.get(songAlbum);
            if (entityList == null) {
                entityList = new ArrayList<>();
            }

            if (songAlbum != null) {
                entityList.add(songEntity);
                albumHashMap.put(songAlbum, entityList);
            } else {
                Log.d(TAG, songEntity.getSongPath());
            }

        }
        return albumHashMap;

    }

    public HashMap<String, ArrayList<SongEntity>> populateSongDataBasedOnArtist(Context context) {
        artistHashMap = new HashMap<>();
        for (SongEntity songEntity : songEntityList) {
            String songArtist = songEntity.getSongArtist();
            ArrayList<SongEntity> entityList = artistHashMap.get(songArtist);
            if (entityList == null) {
                entityList = new ArrayList<>();
            }

            if (songArtist != null) {
                entityList.add(songEntity);
                artistHashMap.put(songArtist, entityList);
            } else {
                Log.d(TAG, songEntity.getSongPath());
            }

        }
        return artistHashMap;
    }
}