package com.example.jeremy.bandaide;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

public class people extends AppCompatActivity {

    DBAdapter songDB;
    ListView peopleList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people);

        openDB();
        peopleList = (ListView) findViewById(R.id.lv_people);
        peopleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            private int mPosition;

            public void onItemClick(AdapterView<?> arg0, View v, int position, long id) {
                String selected = ((TextView) v.findViewById(R.id.item_people_firstName)).getText().toString();
                Intent intent = new Intent(getApplicationContext(), people_view.class);
                intent.putExtra("selPeople" ,selected);
                startActivity(intent);
            }
        });


    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        closeDB();
    }
    private void openDB()
    {
        songDB = new DBAdapter(this);
        songDB.open();
        populateListViewFromDB();
    }
    private void closeDB()
    {
        songDB.close();
    }

    public void onClick_mainMenu(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClick_addPeople(View v)
    {
        Intent intent = new Intent(this, people_add.class);
        startActivity(intent);
    }
    public void onClick_tempInsert_people(View v)
    {
        songDB.insertRow_People("Valerie","Driesler", "My Love", "(909)904-4452", "vcdriesler@gmail.com" );
        populateListViewFromDB();
    }

    public void populateListViewFromDB()
    {
        Cursor cursor = songDB.getAllRows_people();
        //allow activity to manage lifetime of the cursor
        startManagingCursor(cursor);

        //setup mapping for cursor to view fields
        String[] fromFieldNames = new String[]
                {DBAdapter.KEY_PERSON_FIRSTNAME, DBAdapter.KEY_PERSON_LASTNAME, DBAdapter.KEY_PERSON_TITLE, DBAdapter.KEY_PERSON_PHONE, DBAdapter.KEY_PERSON_EMAIL};
        int[] toViewIDs = new int[] {R.id.item_people_firstName, R.id.item_people_lastName, R.id.item_people_title, R.id.item_people_phone, R.id.item_people_email};

        //create adapter to map columns of the db to elements in the UI
        SimpleCursorAdapter myCursorAdapter = new SimpleCursorAdapter(
                this,
                R.layout.itemlayout_people,
                cursor,
                fromFieldNames,
                toViewIDs);

        //set adapter for the listview
        ListView myList = (ListView) findViewById(R.id.lv_people);
        myList.setAdapter(myCursorAdapter);

    }
}

