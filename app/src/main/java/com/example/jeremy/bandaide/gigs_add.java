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
import android.widget.Toast;



public class gigs_add extends AppCompatActivity {
    private static final String TAG = "gigs_add";
    DBAdapter songDB;
    public Context mContext;
    public String venueNameFromCursor;
    public int selectedVenue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gigs_add);
        openDB();

        Cursor c = songDB.getAllRows_venue();

        String[] from = new String[] {"name"};
        int[] to = new int[] {android.R.id.text1};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,c,from,to,0);
        sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinVenue = (Spinner) this.findViewById(R.id.sp_gigs_add_venue);
        spinVenue.setAdapter(sca);

        mContext = this;
        spinVenue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(mContext, "Selected ID=" + id, Toast.LENGTH_LONG).show();
                selectedVenue = (int) id;
                venueNameFromCursor = songDB.getVenueName_VenueID((int)id);
            }

            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }



    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        closeDB();
    }
    public void onClick_button_submit(View v){
        Spinner sp_gigs_add_venue = (Spinner) findViewById(R.id.sp_gigs_add_venue);


        int venueID = selectedVenue;
        //ToDo: Finish the spinner setup in gigs_add
        EditText et_gigs_add_date = (EditText) findViewById(R.id.et_gigs_add_date);
        EditText et_gigs_add_time = (EditText) findViewById(R.id.et_gigs_add_time);
        EditText et_gigs_add_pay = (EditText) findViewById(R.id.et_gigs_add_pay);


        String gigsAddDate = et_gigs_add_date.getText().toString();
        String gigsAddTime = et_gigs_add_time.getText().toString();
        int gigsAddPay = Integer.parseInt(et_gigs_add_pay.getText().toString());
        songDB.insertRow_Gigs(venueID, gigsAddDate, gigsAddTime, gigsAddPay);

        Intent intent = new Intent(this, gigs.class);
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
