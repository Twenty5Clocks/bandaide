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

public class setlists extends AppCompatActivity {
    DBAdapter songDB;
    ListView setlistsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlists);

        openDB();
        setlistsList = (ListView) findViewById(R.id.lv_setlists);
        setlistsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private int mPosition;

            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {

                int selected = (int)id;
                Intent intent = new Intent(getApplicationContext(), setlists_view.class);
                intent.putExtra("selSetlists" ,selected);
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
        Intent intent = new Intent(this, setlists_add.class);
        startActivity(intent);
    }
    public void onClick_tempInsert(View v)
    {
        songDB.insertRow_Setlists("First Gig", 1, "First gig for new band");
        populateListViewFromDB();
    }

    public void populateListViewFromDB()
    {
        Cursor cursor = songDB.getAllRows_setlists();
        //allow activity to manage lifetime of the cursor

        startManagingCursor(cursor);

        //setup mapping for cursor to view fields
        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_SETLIST_TITLE, DBAdapter.KEY_SETLIST_GIG_ID, DBAdapter.KEY_SETLIST_NOTES};
        int[] toViewIDs = new int[] {R.id.item_setlists_name, R.id.item_setlists_venue, R.id.item_setlists_notes};

        //create adapter to map columns of the db to elements in the UI
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.itemlayout_setlists,
                cursor,
                fromFieldNames,
                toViewIDs);

        myCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int column) {
                if( column == cursor.getColumnIndex(DBAdapter.KEY_SETLIST_GIG_ID) ){
                    TextView tv = (TextView) view.findViewById(R.id.item_setlists_venue);
                    int venueID = cursor.getInt(2);
                    int venueLoc = songDB.getVenueID_GigID(venueID);
                    String venueName = songDB.getVenueName_VenueID(venueLoc);
                    tv.setText(venueName);
                    return true;
                }
                return false;
            }
        });

        //set adapter for the listview
        ListView myList = (ListView) findViewById(R.id.lv_setlists);
        myList.setAdapter(myCursorAdapter);

    }

}
