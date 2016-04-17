package com.example.jeremy.bandaide;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.database.Cursor;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class gigs extends AppCompatActivity {
    DBAdapter songDB;
    ListView gigsList;
    private static final String TAG = "gigs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gigs);

        openDB();
        gigsList = (ListView) findViewById(R.id.lv_gigs);
        gigsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private int mPosition;

            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                //TextView tv = (TextView) v.findViewById(R.id.item_gig_venue);
                //String tvValue = tv.getText().toString();
                //int selected = Integer.valueOf(tvValue);
                int selected = (int)id;
                Intent intent = new Intent(getApplicationContext(), gigs_view.class);
                intent.putExtra("selGigs" ,selected);
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
    public void onClick_addGigs(View v)
    {
        Intent intent = new Intent(this, gigs_add.class);
        startActivity(intent);
    }
    public void onClick_tempInsert(View v)
    {
        songDB.insertRow_Gigs(99, "03/19/16", "09:00 PM", 400);
        populateListViewFromDB();
    }

    public void populateListViewFromDB()
    {
        Cursor cursor = songDB.getAllRows_gigs();
        //allow activity to manage lifetime of the cursor
        //DEPRICATED
        startManagingCursor(cursor);

        //setup mapping for cursor to view fields
        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_GIG_VENUE_ID, DBAdapter.KEY_GIG_DATE, DBAdapter.KEY_GIG_TIME, DBAdapter.KEY_GIG_PAY};
        int[] toViewIDs = new int[] {R.id.item_gig_venue, R.id.item_gig_date, R.id.item_gig_time, R.id.item_gig_pay};

        //create adapter to map columns of the db to elements in the UI
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.itemlayout_gigs,
                cursor,
                fromFieldNames,
                toViewIDs);
        //Log.v(TAG,"cursor adapter created" );
        myCursorAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int column) {
                if( column == cursor.getColumnIndex(DBAdapter.KEY_GIG_VENUE_ID) ){ // let's suppose that the column 0 is the date
                    TextView tv = (TextView) view.findViewById(R.id.item_gig_venue);
                    int venueID = cursor.getInt(column);
                    // here you use SimpleDateFormat to bla blah blah
                    String venueName = songDB.getVenueName_VenueID(venueID);
                    //String logString = "Changed something: " + venueName + "  "+ String.valueOf(venueID);
                    //Log.v(TAG,logString );
                    tv.setText(venueName);
                    return true;
                }
                return false;
            }
        });

        //set adapter for the listview
        ListView myList = (ListView) findViewById(R.id.lv_gigs);
        myList.setAdapter(myCursorAdapter);

    }

}