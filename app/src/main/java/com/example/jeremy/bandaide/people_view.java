package com.example.jeremy.bandaide;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class people_view extends AppCompatActivity {

    DBAdapter songDB;
    String selPeople;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_view);
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
    public void onClick_mainMenu(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void onClick_edit(View v)
    {
        TextView name = (TextView)findViewById(R.id.tv_people_view_firstName_view);
        String selected = name.getText().toString();
        Intent intent = new Intent(getApplicationContext(), people_edit.class);
        intent.putExtra("selPeople" ,selected);
        startActivity(intent);
    }
    public void onClick_delete(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.app_name);
        builder.setMessage("Delete this person permanently?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                TextView songTitle = (TextView) findViewById(R.id.tv_people_view_firstName_view);
                String name = songTitle.getText().toString();
                songDB.deleteRow_people(name);
                Intent intent = new Intent(getApplicationContext(), people.class);
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
        Cursor cursor = songDB.getRow_person(selPeople);
        startManagingCursor(cursor);

        TextView peopleFirstName = (TextView) findViewById(R.id.tv_people_view_firstName_view); peopleFirstName.setText(cursor.getString(2));
        TextView peopleLastName = (TextView) findViewById(R.id.tv_people_view_lastName_view); peopleLastName.setText(cursor.getString(3));
        TextView peopleTitle = (TextView) findViewById(R.id.tv_people_view_title_view); peopleTitle.setText(cursor.getString(4));
        TextView peoplePhone = (TextView) findViewById(R.id.tv_people_view_phone_view); peoplePhone.setText(cursor.getString(5));
        TextView peopleEmail = (TextView) findViewById(R.id.tv_people_view_email_view); peopleEmail.setText(cursor.getString(6));



    }
}

