package com.example.jeremy.bandaide;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class venue extends AppCompatActivity {

    DBAdapter songDB;
    ListView venueList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue);

        openDB();
        venueList = (ListView) findViewById(R.id.lv_venues);
        venueList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private int mPosition;

            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                String selected = ((TextView) v.findViewById(R.id.item_venue_name)).getText().toString();
                Intent intent = new Intent(getApplicationContext(), venue_view.class);
                intent.putExtra("selVenue" ,selected);
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
    public void onClick_addVenue(View v)
    {
        Intent intent = new Intent(this, venue_add.class);
        startActivity(intent);
    }
    public void onClick_tempInsert(View v)
    {
        songDB.insertRow_Venue("Glass House","Pomona", "CA", "200 W. Second St.", 91766 );
        populateListViewFromDB();
    }

    public void populateListViewFromDB()
    {
        Cursor cursor = songDB.getAllRows_venue();
        //allow activity to manage lifetime of the cursor
        //DEPRICATED
        startManagingCursor(cursor);

        //setup mapping for cursor to view fields
        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_VENUE_NAME, DBAdapter.KEY_VENUE_CITY, DBAdapter.KEY_VENUE_STATE, DBAdapter.KEY_VENUE_ADDRESS, DBAdapter.KEY_VENUE_ZIPCODE};
        int[] toViewIDs = new int[] {R.id.item_venue_name, R.id.item_venue_city, R.id.item_venue_state, R.id.item_venue_address, R.id.item_venue_zip};

        //create adapter to map columns of the db to elements in the UI
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.itemlayout_venue,
                cursor,
                fromFieldNames,
                toViewIDs);

        //set adapter for the listview
        ListView myList = (ListView) findViewById(R.id.lv_venues);
        myList.setAdapter(myCursorAdapter);

    }
}
