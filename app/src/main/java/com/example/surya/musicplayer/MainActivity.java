package com.example.surya.musicplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
//import java.util.logging.Handler;

public class MainActivity extends Activity implements SeekBar.OnSeekBarChangeListener{

    private MediaPlayer mp = new MediaPlayer();
    boolean k=false;
   // private MediaMetadataRetriever mmr;

    private ImageView coverart;
    private SeekBar seekbar;
    //private final int[] idd={R.raw.song,R.raw.ayekhuda,R.raw.music,R.raw.paradise,R.raw.phirmohabbat,R.raw.saddigali,R.raw.skyfullofstars,R.raw.waterfall};
    int d;
    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    sg pla=new sg();

    //private int[] idd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //int resID;
        d=getIntent().getIntExtra("pos", 0);
        int t=getIntent().getIntExtra("id",0);
        songsList = pla.getPlayList(t);
        seekbar = (SeekBar)findViewById(R.id.seekBar);
        //mmr = new MediaMetadataRetriever();
        seekbar.setOnSeekBarChangeListener(this);


            try {
                /*MediaController mc = new MediaController(MainActivity.this,mp);
                mc.setDataSource(songsList.get(d).get("songPath"));
                mc.prepare();
                mc.start();*/
                String path = songsList.get(d).get("songPath");
                mp.setDataSource(path);
                mp.prepare();

                TextView tt = (TextView)findViewById(R.id.name);
                String s = songsList.get(d).get("songTitle");

                tt.setText(s);
                File file = new File(songsList.get(d).get("songPath"));
                Uri uri = Uri.fromFile(file);
                MediaMetadataRetriever mmr = new MediaMetadataRetriever();
                mmr.setDataSource(songsList.get(d).get("songPath"));
                byte[] p=mmr.getEmbeddedPicture();
                ImageView aa = (ImageView)findViewById(R.id.imageView);
                if(p!=null)
                {
                    Bitmap bitmap=BitmapFactory.decodeByteArray(p,0,p.length);
                    aa.setImageBitmap(bitmap);
                }
                seekbar.setMax(mp.getDuration());
                if(t==1)
                {
                    mp.start();
                    mHandler.postDelayed(mRunnable, 0);
                    //playsong(d);
                }


            } catch (Exception e) {

            }

        }




    public void play(View view){
        if(!mp.isPlaying())
        {
            mp.start();
        }

       // seekbar.setProgress(mp.getCurrentPosition());
        mHandler.postDelayed(mRunnable, 0);

    }
    public void pause(View view){
        if(mp.isPlaying())
        {
            mp.pause();
        }
        //seekbar.setProgress( mp.getCurrentPosition());
    }
    public void previous(View view){
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("pos", d - 1);
        intent.putExtra("id", 1);
        startActivity(intent);
    }
    public void next(View view){
        Intent intent = new Intent(MainActivity.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("pos", d + 1);
        intent.putExtra("id", 1);
        startActivity(intent);
    }
    public void stop(View view) throws IOException {


        mp.stop();
        mp.prepare();
        seekbar.setMax(mp.getDuration());
        mp.seekTo(0);
        seekbar.setProgress(0);
    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

          seekbar.setProgress( mp.getCurrentPosition());
        mHandler.postDelayed(mRunnable,0);
    }

    @Override
    protected void onPause() {
        super.onPause();
       // mp.pause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
       mp.stop();
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
       // Toast.makeText(MainActivity.this, " shv3 ", Toast.LENGTH_SHORT).show();
           if(fromUser) {
               mp.seekTo(progress);
               //mp.start();
               //seekBar.setProgress( progress);
           }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        //Toast.makeText(MainActivity.this, " shv1 ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
      //  Toast.makeText(MainActivity.this, " shv2 ", Toast.LENGTH_SHORT).show();
    }
    public void playlist(View view){
       // Toast.makeText(MainActivity.this, "  hcjcj ", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MainActivity.this,MainActivity2.class);
        startActivity(intent);
        //finish();
    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            if (!k) {
                if (mp != null) {
                    int mCurrentPosition = mp.getCurrentPosition();
                    seekbar.setProgress(mCurrentPosition);


                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            if (d < songsList.size() - 1) {
                                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("pos", d + 1);
                                intent.putExtra("id", 1);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("pos",0);
                                intent.putExtra("id",1);
                                startActivity(intent);
                            }
                        }
                    });
                    mHandler.postDelayed(this, 100);
                }
            }

        }
    };
}


