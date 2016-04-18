package com.example.jeremy.bandaide;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class people_edit extends AppCompatActivity {

    DBAdapter songDB;
    String selPeople;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_edit);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            selPeople = extras.getString("selPeople");
        }
        openDB();

    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
        closeDB();
    }
    public void onClick_mainMenu_peopleEdit(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void onClick_button_submit(View v){
        TextView et_edit_peopleFirstName = (TextView) findViewById(R.id.et_edit_peopleFirstName);
        EditText et_edit_peopleLastName = (EditText) findViewById(R.id.et_edit_peopleLastName);
        EditText et_edit_peopleTitle = (EditText) findViewById(R.id.et_edit_peopleTitle);
        EditText et_edit_peoplePhone = (EditText) findViewById(R.id.et_edit_peoplePhone);
        EditText et_edit_peopleEmail = (EditText) findViewById(R.id.et_edit_peopleEmail);
        String peopleEditFirstName = et_edit_peopleFirstName.getText().toString();
        String peopleEditLastName = et_edit_peopleLastName.getText().toString();
        String peopleEditTitle = et_edit_peopleTitle.getText().toString();
        String peopleEditPhone = et_edit_peoplePhone.getText().toString();
        String peopleEditEmail = et_edit_peopleEmail.getText().toString();

        songDB.updateRow_people(peopleEditFirstName, peopleEditLastName, peopleEditTitle, peopleEditPhone, peopleEditEmail);
        //String title, String artist, int tempo, String genre, int year, String notes, String length, String timeSignature, String key
        Intent intent = new Intent(this, people.class);
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
        Cursor cursor = songDB.getRow_person(selPeople);
        startManagingCursor(cursor);

        TextView peopleFirstName = (TextView) findViewById(R.id.et_edit_peopleFirstName); peopleFirstName.setText(cursor.getString(2), TextView.BufferType.EDITABLE);
        EditText peopleLastName = (EditText) findViewById(R.id.et_edit_peopleLastName); peopleLastName.setText(cursor.getString(3), TextView.BufferType.EDITABLE);
        EditText peopleTitle = (EditText) findViewById(R.id.et_edit_peopleTitle); peopleTitle.setText(cursor.getString(4), TextView.BufferType.EDITABLE);
        EditText peoplePhone = (EditText) findViewById(R.id.et_edit_peoplePhone); peoplePhone.setText(cursor.getString(5), TextView.BufferType.EDITABLE);
        EditText peopleEmail = (EditText) findViewById(R.id.et_edit_peopleEmail); peopleEmail.setText(cursor.getString(6), TextView.BufferType.EDITABLE);



    }
}