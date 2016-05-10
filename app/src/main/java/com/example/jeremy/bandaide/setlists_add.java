package com.example.jeremy.bandaide;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
//import android.widget.Toast;

public class setlists_add extends AppCompatActivity {
    DBAdapter songDB;
    public Context mContext;
    public int selectedGig;
    public String gigNameFromCursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlists_add);
        openDB();

        Cursor c = songDB.getAllRows_gigs();

        String[] from = new String[] {"date"};
        int[] to = new int[] {android.R.id.text1};
        SimpleCursorAdapter sca = new SimpleCursorAdapter(this, android.R.layout.simple_spinner_item,c,from,to,0);
        sca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner spinVenue = (Spinner) this.findViewById(R.id.et_setlists_add_gig);
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
    protected void onDestroy()
    {
        super.onDestroy();
        closeDB();
    }
    public void onClick_button_submit(View v){
        EditText et_setlists_add_title = (EditText) findViewById(R.id.et_setlists_add_title);

        EditText et_setlists_add_notes = (EditText) findViewById(R.id.et_setlists_add_notes);

        String setlistsTitleString = et_setlists_add_title.getText().toString();
        int setlistsGigInt = selectedGig;
        String setlistsNotesString = et_setlists_add_notes.getText().toString();

        songDB.insertRow_Setlists(setlistsTitleString, setlistsGigInt, setlistsNotesString);
        Intent intent = new Intent(this, setlists.class);
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
