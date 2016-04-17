package com.example.jeremy.bandaide;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends AppCompatActivity {
    DBAdapter myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();

    }
    public void onClick_btn_song(View v){
        Intent intent = new Intent(this, song.class);
        this.startActivity(intent);
    }
    public void onClick_btn_venue(View v){
        Intent intent = new Intent(this, venue.class);
        this.startActivity(intent);
    }
    public void onClick_btn_people(View v){
        Intent intent = new Intent(this, people.class);
        this.startActivity(intent);
    }
    public void onClick_btn_setlists(View v){
        Intent intent = new Intent(this, setlists.class);
        this.startActivity(intent);
    }
    public void onClick_btn_gigs(View v){
        Intent intent = new Intent(this, gigs.class);
        this.startActivity(intent);
    }







}
