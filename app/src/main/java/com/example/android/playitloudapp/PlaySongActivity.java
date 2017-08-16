package com.example.android.playitloudapp;

import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.data.SongEntity;
import com.example.android.data.SoundManagerSingleton;

import java.io.IOException;
import java.util.ArrayList;

import static com.example.android.playitloudapp.R.id.seekBar;

/**
 * Created by anu on 16/5/17.
 */

public class PlaySongActivity extends AppCompatActivity {

    private static final String TAG = PlaySongActivity.class.getSimpleName();

    boolean isPlayingSong = false;
    private SeekBar mSeekBar;
    private Handler mHandler = new Handler();
    ImageView imagePlayArrow = null;
    ActionBar actionBar;

    ArrayList<SongEntity> mSongs;

    /**
     * Handles playback of all the sound files
     */
    private MediaPlayer mMediaPlayer;

    SoundManagerSingleton soundManagerSingleton;
    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            playNext();
        }

    };

    /**
    * play the next song in the queue
     */
    void playNext() {
        soundManagerSingleton.incrementSongCounter();
        SongEntity entity = soundManagerSingleton.getCurrentSongEntity();
        playSong(entity);
        actionBar.setTitle(entity.getSongTitle().toLowerCase());
        updateSongImage();
    }

    /**
     * play the previous song in the queue
    */
    void playPrevious() {
        soundManagerSingleton.decrementSongCounter();
        SongEntity entity = soundManagerSingleton.getCurrentSongEntity();
        playSong(entity);
        actionBar.setTitle(entity.getSongTitle().toLowerCase());
        updateSongImage();
    }

    /*
    * updates the image of the album/song/artist if present*/
    void updateSongImage() {
        ImageView image = (ImageView) findViewById(R.id.music_icon_play);
        image.setImageBitmap( BitmapFactory.decodeResource(getResources(), R.drawable.music_icon));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_song);

        mSongs = (ArrayList<SongEntity>) getIntent().getSerializableExtra("mylist");
        soundManagerSingleton = SoundManagerSingleton.getInstance(MyApplication.sMyApplicationContext);
        mSeekBar = (SeekBar) findViewById(seekBar);
        Bundle bundle = getIntent().getExtras();

        SongEntity songEntity = null;
        if (bundle != null) {
            songEntity = (SongEntity) bundle.getSerializable(SongEntity.class.getSimpleName());
            Log.d(TAG, "path = " + songEntity.getSongPath());
        }
        soundManagerSingleton.setCurrentSongEntity(songEntity);

        actionBar = getSupportActionBar();
        actionBar.setTitle(soundManagerSingleton.getCurrentSongEntity().getSongTitle().toLowerCase());

        imagePlayArrow = (ImageView) findViewById(R.id.image_play);

        // Set a click listener to play the audio when the list item is clicked on
        imagePlayArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start/pause the audio file
                if (isPlayingSong) {
                    mMediaPlayer.pause();

                    imagePlayArrow.setImageResource(R.drawable.ic_pause_circle_outline_white);

                } else {
                    mMediaPlayer.start();
                    imagePlayArrow.setImageResource(R.drawable.ic_play_arrow_white);
                }
                isPlayingSong = !isPlayingSong;

            }
        });

        ImageView playNextView = (ImageView) findViewById(R.id.play_next);
        playNextView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                playNext();
            }
        });

        ImageView playPreviousView = (ImageView) findViewById(R.id.play_previous);
        playPreviousView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                playPrevious();
            }
        });

        //Start time of the song on the seekbar
        TextView startTime = (TextView) findViewById(R.id.duration_start);
        startTime.setText(R.string.time_start);
        updateSongImage();

        //update Seekbar on UI thread
        PlaySongActivity.this.runOnUiThread(new Runnable() {

            @Override
            public void run() {
                if (mMediaPlayer != null) {
                    int mCurrentPosition = mMediaPlayer.getCurrentPosition();
                    mSeekBar.setProgress(mCurrentPosition);
                }
                mHandler.postDelayed(this, 1000);
            }
        });

        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progresValue, boolean fromUser) {
                progress = progresValue;
                Log.d(TAG, progresValue + "");
                if (mMediaPlayer != null && fromUser) {
                    mMediaPlayer.seekTo(progress);
                    isPlayingSong = true;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Log.d(TAG, "onStartTrackingTouch");
                mMediaPlayer.pause();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //textView.setText("Covered: " + progress + "/" + seekBar.getMax());
                Log.d(TAG, "onStopTrackingTouch");
                mMediaPlayer.seekTo(progress);
                mMediaPlayer.start();
            }
        });

    }

    void setDurationUI() {
        //end time of the song on the seekbar
        TextView endTime = (TextView) findViewById(R.id.duration_stop);
        String endTimeInt = getMinutesFromMillis(mMediaPlayer.getDuration());
        endTime.setText(endTimeInt);
        mSeekBar.setMax(mMediaPlayer.getDuration());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Uri uri = getSongUri(soundManagerSingleton.getCurrentSongEntity());
        mMediaPlayer = MediaPlayer.create(PlaySongActivity.this, uri);
        mMediaPlayer.start();
        setDurationUI();
        if (isPlayingSong) {
            mMediaPlayer.pause();
            imagePlayArrow.setImageResource(R.drawable.ic_pause_circle_outline_white);
        } else {
            mMediaPlayer.start();
            imagePlayArrow.setImageResource(R.drawable.ic_play_arrow_white);
        }
        isPlayingSong = !isPlayingSong;

        //Setup a listener on the media player, so that we can stop and release the
        //media player once the sound has finished playing.
        mMediaPlayer.setOnCompletionListener(mCompletionListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // When the activity is stopped, release the media player resources because we won't
        // be playing any more sounds.
        releaseMediaPlayer();
    }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

        }
    }

    //get the song uri
    private Uri getSongUri(SongEntity entity) {
        String path = entity.getSongPath();
        return Uri.parse("android.resource://"+getPackageName()+"/raw/" + path);
    }

    //plays the current song
    private void playSong(SongEntity songEntity) {
        mMediaPlayer.reset();
        Uri uri = getSongUri(songEntity);
        try {
            mMediaPlayer.setDataSource(MyApplication.sMyApplicationContext, uri);
            mMediaPlayer.prepare();
            setDurationUI();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            releaseMediaPlayer();
            return;
        } catch (IOException e) {
            e.printStackTrace();
            releaseMediaPlayer();
            return;
        }

        mMediaPlayer.start();

    }


    public static String getMinutesFromMillis(long milliseconds) {

        long minutes = (milliseconds / 1000) / 60;
        long seconds = (milliseconds / 1000) % 60;
        String time = "" + minutes + ":" + seconds;
        return time;
    }
}

