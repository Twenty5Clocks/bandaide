package com.example.jeremy.bandaide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class venue_view extends AppCompatActivity {

    DBAdapter songDB;
    String selVenue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_venue_view);
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
    public void onClick_mainMenu(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClick_edit(View v)
    {
        TextView name = (TextView)findViewById(R.id.tv_venue_view_name_view);
        String selected = name.getText().toString();
        Intent intent = new Intent(getApplicationContext(), venue_edit.class);
        intent.putExtra("selVenue" ,selected);
        startActivity(intent);
    }
    public void onClick_delete(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Delete this venue permanently?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                TextView songTitle = (TextView) findViewById(R.id.tv_venue_view_name_view);
                String name = songTitle.getText().toString();
                songDB.deleteRow_venue(name);
                Intent intent = new Intent(getApplicationContext(), venue.class);
                startActivity(intent);

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

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

        TextView venueName = (TextView) findViewById(R.id.tv_venue_view_name_view); venueName.setText(cursor.getString(1));
        TextView venueAddress = (TextView) findViewById(R.id.tv_venue_view_address_view); venueAddress.setText(cursor.getString(2));
        TextView venueCity = (TextView) findViewById(R.id.tv_venue_view_city_view); venueCity.setText(cursor.getString(3));
        TextView venueState = (TextView) findViewById(R.id.tv_venue_view_state_view); venueState.setText(cursor.getString(4));
        TextView venueZip = (TextView) findViewById(R.id.tv_venue_view_zip_view); venueZip.setText(cursor.getString(5));



    }
}
