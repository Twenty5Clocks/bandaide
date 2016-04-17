package com.example.jeremy.bandaide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class song_view extends AppCompatActivity {
    DBAdapter songDB;
    String selSong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_view);
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
    public void onClick_mainMenu(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClick_edit(View v)
    {
        TextView name = (TextView)findViewById(R.id.tv_view_peopleFirstName);
        String selected = name.getText().toString();
        Intent intent = new Intent(getApplicationContext(), song_edit.class);
        intent.putExtra("selSong" ,selected);
        startActivity(intent);
    }
    public void onClick_delete(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Delete this song permanently?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                TextView songTitle = (TextView) findViewById(R.id.tv_view_peopleFirstName);
                String name = songTitle.getText().toString();
                songDB.deleteRow_song(name);
                Intent intent = new Intent(getApplicationContext(), song.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

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

        TextView songTitle = (TextView) findViewById(R.id.tv_view_peopleFirstName); songTitle.setText(cursor.getString(1));
        TextView songArtist = (TextView) findViewById(R.id.tv_view_peopleLastName); songArtist.setText(cursor.getString(2));
        TextView songTempo = (TextView) findViewById(R.id.tv_view_peopleTitle); songTempo.setText(cursor.getString(3));
        TextView songGenre = (TextView) findViewById(R.id.tv_view_peoplePhone); songGenre.setText(cursor.getString(4));
        TextView songYear = (TextView) findViewById(R.id.tv_view_peopleEmail); songYear.setText(cursor.getString(5));
        TextView songNotes = (TextView) findViewById(R.id.tv_view_notes); songNotes.setText(cursor.getString(6));
        TextView songLength = (TextView) findViewById(R.id.tv_view_length); songLength.setText(cursor.getString(7));
        TextView songTimeSig = (TextView) findViewById(R.id.tv_view_timeSig); songTimeSig.setText(cursor.getString(8));
        TextView songKey = (TextView) findViewById(R.id.tv_view_key); songKey.setText(cursor.getString(9));



    }



}

