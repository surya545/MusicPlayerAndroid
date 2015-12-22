package com.example.surya.musicplayer;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by surya on 22/12/15.
 */
public class MainActivity2 extends Activity {


    ArrayList <HashMap<String,String>> songs = new ArrayList<HashMap<String, String>>();
    ArrayList<String> listname = new ArrayList<String>();
    ListView listview;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //ArrayList <HashMap<String,String>> so = new ArrayList<HashMap<String, String>>();
        newfile nf = new newfile();
        songs = nf.getSongs();
        listview = (ListView) findViewById(R.id.listView);

        for (int i = 0; i<songs.size()-1;i++){
            if(songs.get(i).get("Name")!=null)
            {
                listname.add(songs.get(i).get("Name"));
            }
            else
            {
                listname.add("Track "+(i+1));
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listname);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity2.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("pos", position);
                intent.putExtra("status", 1);
                startActivity(intent);
                finish();
            }
        });
    }


}
