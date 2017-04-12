package com.example.surya.musicplayer;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity2 extends Activity {
    public ListView listView;
    private MediaMetadataRetriever mmr = new MediaMetadataRetriever();
    public ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    newfile pla=new newfile();
    //private int[] idd={R.raw.song,R.raw.ayekhuda,R.raw.music,R.raw.paradise,R.raw.phirmohabbat,R.raw.saddigali,R.raw.skyfullofstars,R.raw.waterfall};
    public ArrayList<String>song = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = (ListView)findViewById(R.id.listView);
        String s;
        Uri uri;
        int resID;
        songsList = pla.getPlayList(1);
        for(int i=0;i<songsList.size()-1;i++)
        {
            song.add(songsList.get(i).get("songTitle"));
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,song);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity2.this, "on item click", Toast.LENGTH_SHORT).show();
               // MainActivity m = new MainActivity(position);
                //mp.d=position;
                //Intent intent = new Intent(MainActivity2.this,MainActivity.class);
                //
                //startActivity(intent);
                //finish();
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("pos", position);
                intent.putExtra("id",1);
                startActivity(intent);
                finish();
            }
        });
        //finish();
    }

}
