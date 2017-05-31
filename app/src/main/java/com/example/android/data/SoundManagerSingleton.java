package com.example.android.data;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
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

    private ArrayList<SongEntity> songEntityList = null;
    private HashMap<String, String> albumArtData;
    private int counter = 0;
    SongEntity currentSongEntity = null;
    HashMap<String, ArrayList<SongEntity>> albumHashMap = new HashMap<>();
    HashMap<String, ArrayList<SongEntity>> artistHashMap = new HashMap<>();
    private static SoundManagerSingleton instance;

    private SoundManagerSingleton() {
    };

    public static SoundManagerSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new SoundManagerSingleton();
            instance.populateSongData(context);
            instance.getAlbumArtData(context);
        }
        return instance;
    }


    private void populateSongData(Context context) {
        songEntityList = new ArrayList<>();
        String selection = MediaStore.Audio.Media.IS_MUSIC + " != 0";
        String[] projection = {
                MediaStore.Audio.Media.TITLE,
                MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA,
                MediaStore.Audio.Media.DISPLAY_NAME,
                MediaStore.Audio.Media.DURATION,
                MediaStore.Audio.Media.ALBUM,
                MediaStore.Audio.Media.ALBUM_ID

        };
        final String sortOrder = MediaStore.Audio.AudioColumns.TITLE + " COLLATE LOCALIZED ASC";

        Cursor cursor = null;
        try {
            Uri uri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            cursor = context.getContentResolver().query(uri, projection, selection, null, sortOrder);
            if (cursor != null) {
                cursor.moveToFirst();

                while (!cursor.isAfterLast()) {
                    String title = cursor.getString(0);
                    String artist = cursor.getString(1);
                    String path = cursor.getString(2);
                    String displayName = cursor.getString(3);
                    String songDuration = cursor.getString(4);
                    String album = cursor.getString(5);
                    String albumId = cursor.getString(6);

                    cursor.moveToNext();
                    if (path != null && path.endsWith(".mp3")) {
                        SongEntity newSong = new SongEntity(title, artist, path, displayName, songDuration, album, albumId);
                        songEntityList.add(newSong);
                    }
                }

            }

            // print to see list of mp3 files
            for (SongEntity file : songEntityList) {
                Log.i("TAG", file.getSongPath());
                Log.i("TAG", file.getSongAlbum());
                Log.i("TAG", file.getSongArtist());
                Log.i("TAG", file.getSongTitle());
            }

        } catch (Exception e) {
            Log.e("TAG", e.toString());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public ArrayList<SongEntity> getSongEntityList(SongType type, String metaData) {
        switch (type) {
            case ALL:
                return songEntityList;
            case ALBUM:
                if (metaData != null) {
                    return albumHashMap.get(metaData);
                }
                return songEntityList;
            case ARTIST:
                if (metaData != null) {
                    return artistHashMap.get(metaData);
                }
        }
        return songEntityList;
    }


    public HashMap<String, String> getAlbumArtData(Context context) {
        albumArtData = new HashMap();
        String[] projection = {
                MediaStore.Audio.Albums._ID,
                MediaStore.Audio.Albums.ALBUM_ART
        };

        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI, projection, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                String albumId = cursor.getString(0);
                String albumImage = cursor.getString(1);

                albumArtData.put(albumId, albumImage);

                cursor.moveToNext();
            }
        }
        return albumArtData;
    }

    public String getImageforAlbum(SongEntity entity) {
        String albumID = entity.getAlbumArtId();
        return albumArtData.get(albumID);
    }

    public int getCurrentSongIndex() {
        return counter;
    }

    public void incrementSongCounter() {
        counter++;
        if (counter >= songEntityList.size()) {
            counter = 0;
        }
        currentSongEntity = songEntityList.get(counter);
    }

    public void decrementSongCounter() {
        counter--;
        if (counter < 0) {
            counter = songEntityList.size() - 1;
        }
        currentSongEntity = songEntityList.get(counter);
    }

    public SongEntity getCurrentSongEntity() {
        return currentSongEntity;
    }

    public void setCurrentSongEntity(SongEntity currentSongEntity) {
        this.currentSongEntity = currentSongEntity;
        int index = 0;
        for (SongEntity songEntity : songEntityList) {
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
