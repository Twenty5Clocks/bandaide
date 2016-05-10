package com.example.jeremy.bandaide;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class setlists_edit_basic extends AppCompatActivity {
    private static final String TAG = "setlist_edit_basic";
    DBAdapter songDB;
    int selSetlists;
    public Context mContext;
    public int selectedGig;
    public String gigNameFromCursor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlists_edit_basic);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            selSetlists = extras.getInt("selSetlists");
        }
        openDB();

        Cursor c = songDB.getAllRows_gigs();

        String[] from = new String[] {"date"};
        int[] to = new int[] {android.R.id.text1};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,c,from,to,0);
        sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinVenue = (Spinner) this.findViewById(R.id.et_setlists_edit_gig);
        spinVenue.setAdapter(sca);

        mContext = this;
        spinVenue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
                selectedGig = (int) id;
                gigNameFromCursor = songDB.getGigName_GigID((int) id);
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

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
        TextView et_edit_title = (TextView) findViewById(R.id.et_setlists_edit_title);
        //EditText et_edit_gig = (EditText) findViewById(R.id.et_setlists_edit_gig);
        EditText et_edit_notes = (EditText) findViewById(R.id.et_setlists_edit_notes);
        String titleNameString = et_edit_title.getText().toString();

        int gigString = selectedGig;//Integer.parseInt(et_edit_gig.getText().toString());
        String notesString = et_edit_notes.getText().toString();
        Log.v(TAG, "setlist update submitted:SelSetlists:" + selSetlists + "  title:" + titleNameString + " gig:" + gigString + " notes:" + notesString);
        //ToDo edit updateRow_gigs to allow for ID
        songDB.updateRow_setlists(selSetlists, titleNameString, gigString, notesString);
        //String title, String artist, int tempo, String genre, int year, String notes, String length, String timeSignature, String key
        Intent intent = new Intent(this, setlists.class);
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
        Cursor cursor = songDB.getRow_setlists(selSetlists);
        startManagingCursor(cursor);

        //TextView gigVenue = (TextView) findViewById(R.id.et_gigs_edit_venue);
        //String venueName = songDB.getVenueName_VenueID(cursor.getInt(1));
        //gigVenue.setText(venueName);
        TextView setlistsTitle = (TextView) findViewById(R.id.et_setlists_edit_title); setlistsTitle.setText(cursor.getString(1));
        //EditText setlistsGig = (EditText) findViewById(R.id.et_setlists_edit_gig); setlistsGig.setText(cursor.getString(2), TextView.BufferType.EDITABLE);
        EditText setlistsNotes = (EditText) findViewById(R.id.et_setlists_edit_notes); setlistsNotes.setText(cursor.getString(3), TextView.BufferType.EDITABLE);




    }



}