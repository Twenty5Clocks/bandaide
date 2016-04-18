package com.example.jeremy.bandaide;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class people_add extends AppCompatActivity {
    DBAdapter songDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_add);
        openDB();
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        closeDB();
    }
    public void onClick_button_submit(View v){
        EditText et_peopleFirstName = (EditText) findViewById(R.id.et_add_peopleFirstName);
        EditText et_peopleLastName = (EditText) findViewById(R.id.et_add_peopleLastName);
        EditText et_peopleTitle = (EditText) findViewById(R.id.et_add_peopleTitle);
        EditText et_peoplePhone = (EditText) findViewById(R.id.et_add_peoplePhone);
        EditText et_peopleEmail = (EditText) findViewById(R.id.et_add_peopleEmail);

        String peopleFirstNameString = et_peopleFirstName.getText().toString();
        String peopleLastNameString = et_peopleLastName.getText().toString();
        String peopleTitleString = et_peopleTitle.getText().toString();
        String peoplePhoneString = et_peoplePhone.getText().toString();
        String peopleEmailString = et_peopleEmail.getText().toString();

        songDB.insertRow_People(peopleFirstNameString, peopleLastNameString, peopleTitleString, peoplePhoneString, peopleEmailString);
        //String title, String artist, int tempo, String genre, int year, String notes, String length, String timeSignature, String key
        Intent intent = new Intent(this, people.class);
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
