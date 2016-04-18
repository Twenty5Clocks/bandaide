package com.example.jeremy.bandaide;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class song_edit extends AppCompatActivity {
    DBAdapter songDB;
    String selSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_edit);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            selSong = extras.getString("selSong");
        }
        openDB();

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        closeDB();
    }
    public void onClick_mainMenu_songEdit(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClick_button_submit(View v){
        TextView et_edit_songName = (TextView) findViewById(R.id.et_song_edit_name);
        EditText et_edit_artist = (EditText) findViewById(R.id.et_song_edit_artist);
        EditText et_edit_tempo = (EditText) findViewById(R.id.et_song_edit_tempo);
        EditText et_edit_genre = (EditText) findViewById(R.id.et_song_edit_genre);
        EditText et_edit_year = (EditText) findViewById(R.id.et_song_edit_year);
        EditText et_edit_notes = (EditText) findViewById(R.id.et_song_edit_notes);
        EditText et_edit_length = (EditText) findViewById(R.id.et_song_edit_length);
        EditText et_edit_timeSig = (EditText) findViewById(R.id.et_song_edit_timeSig);
        EditText et_edit_key = (EditText) findViewById(R.id.et_song_edit_key);
        String songNameString = et_edit_songName.getText().toString();
        String artistString = et_edit_artist.getText().toString();
        int tempoInt = Integer.parseInt(et_edit_tempo.getText().toString());
        String genreString = et_edit_genre.getText().toString();
        int yearInt = Integer.parseInt(et_edit_year.getText().toString());
        String notesString = et_edit_notes.getText().toString();
        String lengthString = et_edit_length.getText().toString();
        String timeSigString = et_edit_timeSig.getText().toString();
        String keyString = et_edit_key.getText().toString();
        songDB.updateRow_song(songNameString, artistString, tempoInt, genreString, yearInt, notesString, lengthString, timeSigString, keyString);
        //String title, String artist, int tempo, String genre, int year, String notes, String length, String timeSignature, String key
        Intent intent = new Intent(this, song.class);
        startActivity(intent);
    }
    private void openDB()
    {
        songDB = new DBAdapter(this);
        songDB.open();
        populateFieldsFromDB();
    }
    private void closeDB()
    {
        songDB.close();
    }
    public void populateFieldsFromDB(){
        Cursor cursor = songDB.getRow_song(selSong);
        startManagingCursor(cursor);

        TextView songTitle = (TextView) findViewById(R.id.et_song_edit_name); songTitle.setText(cursor.getString(1), TextView.BufferType.EDITABLE);
        EditText songArtist = (EditText) findViewById(R.id.et_song_edit_artist); songArtist.setText(cursor.getString(2), TextView.BufferType.EDITABLE);
        EditText songTempo = (EditText) findViewById(R.id.et_song_edit_tempo); songTempo.setText(cursor.getString(3), TextView.BufferType.EDITABLE);
        EditText songGenre = (EditText) findViewById(R.id.et_song_edit_genre); songGenre.setText(cursor.getString(4), TextView.BufferType.EDITABLE);
        EditText songYear = (EditText) findViewById(R.id.et_song_edit_year); songYear.setText(cursor.getString(5), TextView.BufferType.EDITABLE);
        EditText songNotes = (EditText) findViewById(R.id.et_song_edit_notes); songNotes.setText(cursor.getString(6), TextView.BufferType.EDITABLE);
        EditText songLength = (EditText) findViewById(R.id.et_song_edit_length); songLength.setText(cursor.getString(7), TextView.BufferType.EDITABLE);
        EditText songTimeSig = (EditText) findViewById(R.id.et_song_edit_timeSig); songTimeSig.setText(cursor.getString(8), TextView.BufferType.EDITABLE);
        EditText songKey = (EditText) findViewById(R.id.et_song_edit_key); songKey.setText(cursor.getString(9), TextView.BufferType.EDITABLE);



    }



}