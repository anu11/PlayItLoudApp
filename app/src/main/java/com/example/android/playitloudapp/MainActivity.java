package com.example.android.playitloudapp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.data.SoundManagerSingleton;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private static final int PERMISSION_REQUEST_CODE = 1;
    View.OnClickListener textViewOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity.this.permissionRequirementCheck();

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
            MainActivity.this.permissionRequirementCheck();

            // Create a new intent to open the {@link artistActivity}
            Intent songListIntent = new Intent(MainActivity.this, ListArtistActivity.class);

            // Start the new activity
            startActivity(songListIntent);
        }
    };
    View.OnClickListener albumtextViewOnclickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MainActivity.this.permissionRequirementCheck();

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


/*
  *runtime request for permission to access files and folders
  * */

    void permissionRequirementCheck() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                // Code for above or equal 23 API Oriented Device
                // Your Permission granted already .Do next code'
                Log.d("TAG", "checkPermission");

            } else {
                requestPermission(); // Code for permission
                Log.d("TAG", "requestPermission");
            }
        } else {

            // Code for Below 23 API Oriented Device

            Log.d("TAG", "Code for Below 23 API Oriented Device");

        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}
