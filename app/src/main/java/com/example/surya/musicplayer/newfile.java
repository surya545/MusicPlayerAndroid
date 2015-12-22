package com.example.surya.musicplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by surya on 23/12/15.
 */
public class newfile {
    ArrayList<HashMap<String,String>> songs=new ArrayList<HashMap<String, String>>();
    public ArrayList<HashMap<String, String>> getSongs() {
        String path = System.getenv("SECONDARY_STORAGE");
        File sd = new File(path);
        Update(sd);
        return songs;
    }

    public void Update(File fyl){
        File[] fyls = fyl.listFiles();
        for (File name : fyls){
            if(name.isDirectory()){
                Update(name);
            }
            else {
                String fullPath = name.getAbsolutePath();
                if(fullPath.endsWith(".mp3")||fullPath.endsWith("MP3")){
                    HashMap<String,String> song = new HashMap<String,String>();
                    song.put("Name",name.getName().substring(0,(name.getName().length()-4)));
                    song.put("Path",name.getPath());
                    songs.add(song);
                }
            }
        }
    }
}
