package com.example.jeremy.bandaide;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class venue_edit extends AppCompatActivity {

    DBAdapter songDB;
    String selVenue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_edit);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            selVenue = extras.getString("selVenue");
        }
        openDB();

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        closeDB();
    }
    public void onClick_mainMenu_venueEdit(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClick_button_submit(View v){
        TextView et_edit_venueName = (TextView) findViewById(R.id.et_venue_edit_name);
        EditText et_edit_venueAddress = (EditText) findViewById(R.id.et_venue_edit_address);
        EditText et_edit_venueCity = (EditText) findViewById(R.id.et_venue_edit_city);
        EditText et_edit_venueState = (EditText) findViewById(R.id.et_venue_edit_state);
        EditText et_edit_venueZip = (EditText) findViewById(R.id.et_venue_edit_zip);
        String venueEditName = et_edit_venueName.getText().toString();
        String venueEditAddress = et_edit_venueAddress.getText().toString();
        String venueEditCity = et_edit_venueCity.getText().toString();
        String venueEditState = et_edit_venueState.getText().toString();
        int venueEditZip = Integer.parseInt(et_edit_venueZip.getText().toString());

        songDB.updateRow_venue(venueEditName, venueEditAddress, venueEditCity, venueEditState, venueEditZip);
        //String title, String artist, int tempo, String genre, int year, String notes, String length, String timeSignature, String key
        Intent intent = new Intent(this, venue.class);
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
        Cursor cursor = songDB.getRow_venue(selVenue);
        startManagingCursor(cursor);

        TextView venueName = (TextView) findViewById(R.id.et_venue_edit_name); venueName.setText(cursor.getString(1), TextView.BufferType.EDITABLE);
        EditText venueAddress = (EditText) findViewById(R.id.et_venue_edit_address); venueAddress.setText(cursor.getString(2), TextView.BufferType.EDITABLE);
        EditText venueCity = (EditText) findViewById(R.id.et_venue_edit_city); venueCity.setText(cursor.getString(3), TextView.BufferType.EDITABLE);
        EditText venueState = (EditText) findViewById(R.id.et_venue_edit_state); venueState.setText(cursor.getString(4), TextView.BufferType.EDITABLE);
        EditText venueZip = (EditText) findViewById(R.id.et_venue_edit_zip); venueZip.setText(cursor.getString(5), TextView.BufferType.EDITABLE);



    }
}
