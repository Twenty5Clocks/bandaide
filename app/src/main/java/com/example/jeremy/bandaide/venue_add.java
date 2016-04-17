package com.example.jeremy.bandaide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class venue_add extends AppCompatActivity {
    DBAdapter songDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_add);
        openDB();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        closeDB();
    }
    public void onClick_button_submit(View v){
        EditText et_venueName = (EditText) findViewById(R.id.et_edit_peopleFirstName);
        EditText et_venueAddress = (EditText) findViewById(R.id.et_add_peopleLastName);
        EditText et_venueCity = (EditText) findViewById(R.id.et_add_peopleTitle);
        EditText et_venueState = (EditText) findViewById(R.id.et_add_peoplePhone);
        EditText et_venueZip = (EditText) findViewById(R.id.et_add_peopleEmail);

        String venueNameString = et_venueName.getText().toString();
        String venueAddressString = et_venueAddress.getText().toString();
        String venueCityString = et_venueCity.getText().toString();
        String venueStateString = et_venueState.getText().toString();
        int venueZipInt = Integer.parseInt(et_venueZip.getText().toString());

        songDB.insertRow_Venue(venueNameString, venueAddressString, venueCityString, venueStateString, venueZipInt);
        //String title, String artist, int tempo, String genre, int year, String notes, String length, String timeSignature, String key
        Intent intent = new Intent(this, venue.class);
        startActivity(intent);
    }
    public void onClick_button_main_menu(View v){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    private void openDB()
    {
        songDB = new DBAdapter(this);
        songDB.open();
    }
    private void closeDB(){
        songDB.close();
    }
}