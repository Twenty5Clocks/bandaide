package com.example.jeremy.bandaide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class song_add extends AppCompatActivity {
    DBAdapter songDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_add);
        openDB();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        closeDB();
    }
    public void onClick_button_submit(View v){
        EditText et_songName = (EditText) findViewById(R.id.et_gigs_add_venue);
        EditText et_artist = (EditText) findViewById(R.id.et_gigs_add_date);
        EditText et_tempo = (EditText) findViewById(R.id.et_gigs_add_time);
        EditText et_genre = (EditText) findViewById(R.id.et_genre);
        EditText et_year = (EditText) findViewById(R.id.et_year);
        EditText et_notes = (EditText) findViewById(R.id.et_notes);
        EditText et_length = (EditText) findViewById(R.id.et_length);
        EditText et_timeSig = (EditText) findViewById(R.id.et_timeSig);
        EditText et_key = (EditText) findViewById(R.id.et_key);
        String songNameString = et_songName.getText().toString();
        String artistString = et_artist.getText().toString();
        int tempoInt = Integer.parseInt(et_tempo.getText().toString());
        String genreString = et_genre.getText().toString();
        int yearInt = Integer.parseInt(et_year.getText().toString());
        String notesString = et_notes.getText().toString();
        String lengthString = et_length.getText().toString();
        String timeSigString = et_timeSig.getText().toString();
        String keyString = et_key.getText().toString();
        songDB.insertRow_Song(songNameString, artistString, tempoInt, genreString, yearInt, notesString, lengthString, timeSigString, keyString);
        //String title, String artist, int tempo, String genre, int year, String notes, String length, String timeSignature, String key
        Intent intent = new Intent(this, song.class);
        startActivity(intent);
    }
    public void onClick_button_main_menu(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void openDB()
    {
        songDB = new DBAdapter(this);
        songDB.open();
    }
    private void closeDB(){
        songDB.close();
    }
}
