package com.example.surya.musicplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadata;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;
import android.os.Handler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.LogRecord;


public class MainActivity extends Activity {

    public MediaPlayer mp=new MediaPlayer();
    public MediaMetadataRetriever mmr;
    String nam;
    public SeekBar seekBar;
    //public ImageView coverart = (ImageView)findViewById(R.id.imageView);
    private final int[] id={R.raw.a};
    ArrayList <HashMap<String,String>> song_list = new ArrayList<HashMap<String,String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int pos = getIntent().getIntExtra("pos",0);
        int status = getIntent().getIntExtra("status",0);
        newfile m2 = new newfile();
        song_list=m2.getSongs();
        try {
            mp.setDataSource(song_list.get(pos).get("Path"));
            mp.prepare();
            nam = song_list.get(pos).get("Name");
            seekBar = (SeekBar)findViewById(R.id.seekBar);
            seekBar.setMax(mp.getDuration());
            seekBar.setOnSeekBarChangeListener(
                    new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                mp.seekTo(progress);
                            }
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {

                        }

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {

                        }
                    }
            );

        }
        catch (Exception e){

        }

        if(status==1){
            mp.start();
            mHandler.postDelayed(ui,0);
        }
    }

    private Handler mHandler = new Handler();

    private Runnable ui = new Runnable() {
        @Override
        public void run() {
            if(mp!=null) {
                seekBar.setProgress(mp.getCurrentPosition());
                mHandler.postDelayed(this,100);
            }
        }
    };

    public void Play (View view) {
        mp.start();
        mHandler.postDelayed(ui,0);
        Toast.makeText(this, " "+nam , Toast.LENGTH_SHORT).show();
    }
    public void Pause (View view) {
        if (mp.isPlaying()) {
            mp.pause();
        }
    }
    public void Stop (View view) throws IOException {
        mp.stop();
        mp.prepare();
        seekBar.setMax(mp.getDuration());
    }
    public void list (View view) {
        Intent intent = new Intent(this,MainActivity2.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mp.stop();
    }
}
