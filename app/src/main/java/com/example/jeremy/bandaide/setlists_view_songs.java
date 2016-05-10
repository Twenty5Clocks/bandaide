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

public class setlists_view_songs extends AppCompatActivity {
    DBAdapter songDB;
    ListView songList;
    int selectedSetlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlists_view_songs);
        selectedSetlist = (int)getIntent().getSerializableExtra("selSetlists");

        openDB();
        songList = (ListView) findViewById(R.id.lv_setlists_view_songs);
        songList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private int mPosition;

            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                String selected = ((TextView) v.findViewById(R.id.item_setlist_song_title)).getText().toString();
                Intent intent = new Intent(getApplicationContext(), song_view.class);
                intent.putExtra("selSong", selected);
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


    public void populateListViewFromDB()
    {
        Cursor cursor = songDB.getAllRows_setlists_songs(selectedSetlist);
        //allow activity to manage lifetime of the cursor
        //DEPRICATED
        startManagingCursor(cursor);

        //setup mapping for cursor to view fields
        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_SETLISTSONGS_SONG_ID, DBAdapter.KEY_SETLISTSONGS_POSITION};
        int[] toViewIDs = new int[] {R.id.item_setlist_song_title, R.id.item_setlist_song_artist};

        //create adapter to map columns of the db to elements in the UI
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.itemlayout_setlist_songs,
                cursor,
                fromFieldNames,
                toViewIDs);
        myCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int column) {
                if (column == cursor.getColumnIndex(DBAdapter.KEY_SETLISTSONGS_SONG_ID)) {
                    TextView tv1 = (TextView) view.findViewById(R.id.item_setlist_song_title);
                    int songID = cursor.getInt(column);
                    String songName = songDB.getSongName_SongID(songID);
                    tv1.setText(songName);
                    //ToDo make this listview populate both the title and artist (code below does not work...)

                    //TextView tv2 = (TextView) view.findViewById(R.id.item_setlist_song_artist);
                    //String songArtist = songDB.getSongArtist_SongID(songID);
                    //tv2.setText(songArtist);
                    return true;
                }
                if (column == cursor.getColumnIndex(DBAdapter.KEY_SETLISTSONGS_POSITION)) {
                    //ToDo make this listview populate both the title and artist (code below does not work...)
                    int artistID = cursor.getInt(column-1);

                    TextView tv2 = (TextView) view.findViewById(R.id.item_setlist_song_artist);

                    String songArtist = songDB.getSongArtist_SongID(artistID);
                    tv2.setText(songArtist);
                    return true;
                }
                return false;
            }



        });

        //set adapter for the listview
        ListView myList = (ListView) findViewById(R.id.lv_setlists_view_songs);
        myList.setAdapter(myCursorAdapter);

    }

}