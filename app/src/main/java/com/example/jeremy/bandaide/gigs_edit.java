package com.example.jeremy.bandaide;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class gigs_edit extends AppCompatActivity {
    private static final String TAG = "gigs_edit";
    DBAdapter songDB;
    int selGigs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gigs_edit);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            selGigs = extras.getInt("selGigs");
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
        TextView et_edit_venue = (TextView) findViewById(R.id.et_gigs_edit_venue);
        EditText et_edit_date = (EditText) findViewById(R.id.et_gigs_edit_date);
        EditText et_edit_time = (EditText) findViewById(R.id.et_gigs_edit_time);
        EditText et_edit_pay = (EditText) findViewById(R.id.et_gigs_edit_pay);
        String venueNameString = et_edit_venue.getText().toString();

        String dateString = et_edit_date.getText().toString();
        String timeString = et_edit_time.getText().toString();
        int payInt = Integer.parseInt(et_edit_pay.getText().toString());
        //Log.v(TAG, "gig update submitted:SelGigs:" + selGigs + "  name:" + venueNameString + " date:" + dateString + " time:" + timeString + " pay:" + payInt);
        //ToDo edit updateRow_gigs to allow for ID
        songDB.updateRow_gigs(selGigs, venueNameString, dateString, timeString, payInt);
        Intent intent = new Intent(this, gigs.class);
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
        Cursor cursor = songDB.getRow_gigs(selGigs);
        startManagingCursor(cursor);

        TextView gigVenue = (TextView) findViewById(R.id.et_gigs_edit_venue);
        String venueName = songDB.getVenueName_VenueID(cursor.getInt(1));
        gigVenue.setText(venueName);
        EditText gigsDate = (EditText) findViewById(R.id.et_gigs_edit_date); gigsDate.setText(cursor.getString(2), TextView.BufferType.EDITABLE);
        EditText gigsTime = (EditText) findViewById(R.id.et_gigs_edit_time); gigsTime.setText(cursor.getString(3), TextView.BufferType.EDITABLE);
        EditText gigsPay = (EditText) findViewById(R.id.et_gigs_edit_pay); gigsPay.setText(cursor.getString(4), TextView.BufferType.EDITABLE);




    }



}