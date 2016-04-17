package com.example.jeremy.bandaide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class song extends AppCompatActivity {
    DBAdapter songDB;
    ListView songList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        openDB();
        songList = (ListView) findViewById(R.id.lv_songs);
        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private int mPosition;

            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                String selected = ((TextView) v.findViewById(R.id.item_title)).getText().toString();
                Intent intent = new Intent(getApplicationContext(), song_view.class);
                intent.putExtra("selSong" ,selected);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        closeDB();
    }
    private void openDB()
    {
        songDB = new DBAdapter(this);
        songDB.open();
        populateListViewFromDB();
    }
    private void closeDB()
    {
        songDB.close();
    }

    public void onClick_mainMenu(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClick_addSong(View v)
    {
        Intent intent = new Intent(this, song_add.class);
        startActivity(intent);
    }
    public void onClick_tempInsert(View v)
    {
        songDB.insertRow_Song("Paranoid Android","Radiohead", 125, "Rock", 1995, "Great Song!!","3:50","4/4","Eb" );
        populateListViewFromDB();
    }

    public void populateListViewFromDB()
    {
        Cursor cursor = songDB.getAllRows();
        //allow activity to manage lifetime of the cursor
        //DEPRICATED
        startManagingCursor(cursor);

        //setup mapping for cursor to view fields
        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_SONG_TITLE, DBAdapter.KEY_SONG_ARTIST, DBAdapter.KEY_SONG_KEY, DBAdapter.KEY_SONG_GENRE, DBAdapter.KEY_SONG_YEAR};
        int[] toViewIDs = new int[] {R.id.item_title, R.id.item_artist, R.id.item_key, R.id.item_genre, R.id.item_year};

        //create adapter to map columns of the db to elements in the UI
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.itemlayout,
                cursor,
                fromFieldNames,
                toViewIDs);

        //set adapter for the listview
        ListView myList = (ListView) findViewById(R.id.lv_songs);
        myList.setAdapter(myCursorAdapter);

    }

}
