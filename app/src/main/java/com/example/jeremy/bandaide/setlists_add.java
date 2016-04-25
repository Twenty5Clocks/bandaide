package com.example.jeremy.bandaide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class setlists_add extends AppCompatActivity {
    DBAdapter songDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setlists_add);
        openDB();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        closeDB();
    }
    public void onClick_button_submit(View v){
        EditText et_setlists_add_title = (EditText) findViewById(R.id.et_setlists_add_title);
        EditText et_setlists_add_gig = (EditText) findViewById(R.id.et_setlists_add_gig);
        EditText et_setlists_add_notes = (EditText) findViewById(R.id.et_setlists_add_notes);

        String setlistsTitleString = et_setlists_add_title.getText().toString();
        int setlistsGigInt = Integer.parseInt(et_setlists_add_gig.getText().toString());
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
