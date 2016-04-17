package com.example.jeremy.bandaide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class gigs_view extends AppCompatActivity {
    private static final String TAG = "gigs_view";
    DBAdapter songDB;
    int selGigs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gigs_view);
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
    public void onClick_mainMenu(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClick_edit(View v)
    {
        int selected = selGigs;
        Intent intent = new Intent(getApplicationContext(), gigs_edit.class);
        intent.putExtra("selGigs" ,selected);
        startActivity(intent);
    }
    public void onClick_delete(View v)
    {
        //ToDo: the delete function for gig deletion is not working evey time.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Delete this gig permanently?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();

                songDB.deleteRow_gigs(selGigs);
                Intent intent = new Intent(getApplicationContext(), gigs.class);
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
        Cursor cursor = songDB.getRow_gigs(selGigs);
        //Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor));
        startManagingCursor(cursor);

        TextView gigVenue = (TextView) findViewById(R.id.tv_gigs_view_venue_view);
        String venueName = songDB.getVenueName_VenueID(cursor.getInt(1));
        gigVenue.setText(venueName);
        TextView gigDate = (TextView) findViewById(R.id.tv_gigs_view_date_view); gigDate.setText(cursor.getString(2));
        TextView gigTime = (TextView) findViewById(R.id.tv_gigs_view_time_view); gigTime.setText(cursor.getString(3));
        TextView gigPay = (TextView) findViewById(R.id.tv_gigs_view_pay_view); gigPay.setText(cursor.getString(4));



    }



}