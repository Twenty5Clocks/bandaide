package com.example.jeremy.bandaide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class setlists_view extends AppCompatActivity {

    DBAdapter songDB;
    int selSetlists;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlists_view);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            selSetlists = extras.getInt("selSetlists");
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
        int selected = selSetlists;
        Intent intent = new Intent(getApplicationContext(), setlists_edit_basic.class);
        intent.putExtra("selSetlists" ,selected);
        startActivity(intent);
    }
    public void onClick_delete(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Delete this setlist permanently?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                songDB.deleteRow_setlists(selSetlists);
                Intent intent = new Intent(getApplicationContext(), setlists.class);
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
        Cursor cursor = songDB.getRow_setlists(selSetlists);
        startManagingCursor(cursor);


        TextView setlistsTitle = (TextView) findViewById(R.id.tv_setlists_view_title_view); setlistsTitle.setText(cursor.getString(1));
        int venueID = cursor.getInt(2);
        int venueLoc = songDB.getVenueID_GigID(venueID);
        String venueName = songDB.getVenueName_VenueID(venueLoc);
        TextView setlistsGig = (TextView) findViewById(R.id.tv_setlists_view_gig_view); setlistsGig.setText(venueName);
        TextView setlistsNotes = (TextView) findViewById(R.id.tv_setlists_view_notes_view); setlistsNotes.setText(cursor.getString(3));



    }
}
