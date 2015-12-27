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
import android.widget.TextView;
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
    int pos;
    ArrayList <HashMap<String,String>> song_list = new ArrayList<HashMap<String,String>>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pos = getIntent().getIntExtra("pos",0);
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
            mmr = new MediaMetadataRetriever();
            ImageView imageView = (ImageView)findViewById(R.id.imageView);
            mmr.setDataSource(song_list.get(pos).get("Path"));
            byte[] art = mmr.getEmbeddedPicture();
            if(art!=null){
                Bitmap bitmap = BitmapFactory.decodeByteArray(art,0,art.length);
                imageView.setImageBitmap(bitmap);
            }
            if (status==1){
                mp.start();
                mHandler.postDelayed(ui,0);
            }

        }
        catch (Exception e){

        }

        if(status==1){
            mp.start();
            mHandler.postDelayed(ui, 0);
            TextView t = (TextView) findViewById(R.id.songnames);
            t.setText(nam + "");
        }
    }

    private Handler mHandler = new Handler();

    private Runnable ui = new Runnable() {
        @Override
        public void run() {
            if(mp!=null) {
                seekBar.setProgress(mp.getCurrentPosition());
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if(pos < song_list.size()-1) {
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.putExtra("pos",pos+1);
                            intent.putExtra("status", 1);
                            startActivity(intent);
                        }
                    }
                });
                mHandler.postDelayed(this,100);
            }
        }
    };

    public void Play (View view) {
        mp.start();
        mHandler.postDelayed(ui, 0);
        TextView t = (TextView) findViewById(R.id.songnames);
        t.setText(nam + "");
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

    public void next (View view) {
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("pos",pos+1);
        intent.putExtra("status", 1);
        startActivity(intent);
    }
    public void prev (View view) {
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("pos",pos-1);
        intent.putExtra("status", 1);
        startActivity(intent);
    }
}
