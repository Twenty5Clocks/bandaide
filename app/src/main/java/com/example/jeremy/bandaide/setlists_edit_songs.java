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
//import android.widget.Toast;

import org.w3c.dom.Text;

public class setlists_edit_songs extends AppCompatActivity {
    DBAdapter songDB;
    ListView songsList;
    ListView setlistSongsList;
    int selectedSetlist;
    int selectedSong;
    int selectedSetlistSong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlists_edit_songs);
        selectedSetlist = (int)getIntent().getSerializableExtra("selSetlists");

        openDB();
        songsList = (ListView) findViewById(R.id.lv_song_list);
        setlistSongsList = (ListView) findViewById(R.id.lv_setlist_songs);

        songsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private int mPosition;

            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

                selectedSong = (int) id;
                //Toast toast = Toast.makeText(getApplicationContext(), selectedSong + " song selected", Toast.LENGTH_SHORT);
                //toast.show();

            }
        });
        setlistSongsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private int mPosition;

            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

                selectedSetlistSong = (int) id;
                //Toast toast = Toast.makeText(getApplicationContext(), selectedSetlist + "= setlist, " + selectedSetlistSong + " song selected", Toast.LENGTH_SHORT);
                //toast.show();
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
        int count = songDB.getSongHighest_SetlistID(selectedSetlist) + 1;
        songDB.insertRow_Setlists_Songs(selectedSetlist, selectedSong, count);
        populateListViewFromDB();
    }
    public void onClick_removeSong(View v)
    {
        int count = songDB.getSongCount_SetlistID(selectedSetlist);

        int pos = songDB.setlistSongPos_id(selectedSetlistSong);
        songDB.removeSetlistSong(selectedSetlistSong);
        populateListViewFromDB();
    }
    public void dbTest(View v)
    {
        songDB.dbContents();
    }


    public void populateListViewFromDB()
    {
        Cursor cursor = songDB.getAllRows();

        //allow activity to manage lifetime of the cursor

        startManagingCursor(cursor);

        //setup mapping for cursor to view fields
        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_SONG_TITLE, DBAdapter.KEY_SONG_ARTIST};
        int[] toViewIDs = new int[] {R.id.item_setlist_song_title, R.id.item_setlist_song_artist};

        //create adapter to map columns of the db to elements in the UI
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.itemlayout_setlist_songs,
                cursor,
                fromFieldNames,
                toViewIDs);



        //set adapter for the listview

        ListView myList = (ListView) findViewById(R.id.lv_song_list);
        myList.setAdapter(myCursorAdapter);

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        //This is the setlist songs Listview
        Cursor cursor2 = songDB.getAllRows_setlists_songs(selectedSetlist);
        String[] fromFieldNames2 = new String[]
                {DBAdapter.KEY_SETLISTSONGS_SONG_ID, DBAdapter.KEY_SETLISTSONGS_POSITION};
        int[] toViewIDs2 = new int[] {R.id.item_setlist_song_title, R.id.item_setlist_song_artist};

        //create adapter to map columns of the db to elements in the UI
        SimpleCursorAdapter myCursorAdapter2 = new SimpleCursorAdapter(
                this,
                R.layout.itemlayout_setlist_songs,
                cursor2,
                fromFieldNames2,
                toViewIDs2);

        myCursorAdapter2.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor2, int column) {
                if (column == cursor2.getColumnIndex(DBAdapter.KEY_SETLISTSONGS_SONG_ID)) {
                    TextView tv1 = (TextView) view.findViewById(R.id.item_setlist_song_title);
                    int songID = cursor2.getInt(column);
                    String songName = songDB.getSongName_SongID(songID);
                    tv1.setText(songName);

                    return true;
                }
                if (column == cursor2.getColumnIndex(DBAdapter.KEY_SETLISTSONGS_POSITION)) {

                    int artistID = cursor2.getInt(column-1);

                    TextView tv2 = (TextView) view.findViewById(R.id.item_setlist_song_artist);

                    String songArtist = songDB.getSongArtist_SongID(artistID);
                    tv2.setText(songArtist);
                    return true;
                }
                return false;

            }




        });





        //set adapter for the listview
        ListView myList2 = (ListView) findViewById(R.id.lv_setlist_songs);
        myList2.setAdapter(myCursorAdapter2);


    }

}
