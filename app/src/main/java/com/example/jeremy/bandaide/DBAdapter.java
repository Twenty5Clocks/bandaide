

package com.example.jeremy.bandaide;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


public class DBAdapter {

    /////////////////////////////////////////////////////////////////////
    // Constants & Data
    /////////////////////////////////////////////////////////////////////
    // For logging:
    private static final String TAG = "DBAdapter";
    public static final int DATABASE_VERSION = 27 ;


    public static final String KEY_SONG_ID = "_id";
    public static final String KEY_SONG_TITLE = "title";
    public static final String KEY_SONG_ARTIST = "artist";
    public static final String KEY_SONG_TEMPO = "tempo";
    public static final String KEY_SONG_GENRE = "genre";
    public static final String KEY_SONG_YEAR = "year";
    public static final String KEY_SONG_NOTES = "notes";
    public static final String KEY_SONG_LENGTH = "length";
    public static final String KEY_SONG_TIMESIGNATURE = "timeSignature";
    public static final String KEY_SONG_KEY = "key";

    public static final String KEY_PERSON_ID = "_id";
    public static final String KEY_PERSON_VENUE_ID = "venue_id";
    public static final String KEY_PERSON_FIRSTNAME = "firstName";
    public static final String KEY_PERSON_LASTNAME = "lastName";
    public static final String KEY_PERSON_TITLE = "title";
    public static final String KEY_PERSON_PHONE = "phone";
    public static final String KEY_PERSON_EMAIL = "email";

    public static final String KEY_VENUE_ID = "_id";
    public static final String KEY_VENUE_NAME = "name";
    public static final String KEY_VENUE_ADDRESS = "address";
    public static final String KEY_VENUE_STATE = "state";
    public static final String KEY_VENUE_CITY = "city";
    public static final String KEY_VENUE_ZIPCODE = "zipcode";

    public static final String KEY_GIG_ID = "_id";
    public static final String KEY_GIG_VENUE_ID = "venue_id";
    public static final String KEY_GIG_DATE = "date";
    public static final String KEY_GIG_TIME = "time";
    public static final String KEY_GIG_PAY = "pay";

    public static final String KEY_SETLIST_ID = "_id";
    public static final String KEY_SETLIST_TITLE = "title";
    public static final String KEY_SETLIST_GIG_ID = "gig_id";
    public static final String KEY_SETLIST_NOTES = "notes";

    public static final String KEY_SETLISTSONGS_ID = "_id";
    public static final String KEY_SETLISTSONGS_SETLIST_ID = "setlist_id";
    public static final String KEY_SETLISTSONGS_SONG_ID = "song_id";
    public static final String KEY_SETLISTSONGS_POSITION = "position";


    public static final String[] ALL_SONG_KEYS = new String[] {KEY_SONG_ID, KEY_SONG_TITLE, KEY_SONG_ARTIST , KEY_SONG_TEMPO
            , KEY_SONG_GENRE, KEY_SONG_YEAR , KEY_SONG_NOTES, KEY_SONG_LENGTH, KEY_SONG_TIMESIGNATURE , KEY_SONG_KEY};
    public static final String[] ALL_VENUE_KEYS = new String[] {KEY_VENUE_ID, KEY_VENUE_NAME, KEY_VENUE_ADDRESS , KEY_VENUE_STATE
            , KEY_VENUE_CITY, KEY_VENUE_ZIPCODE };
    public static final String[] ALL_GIG_KEYS = new String[] {KEY_GIG_ID, KEY_GIG_VENUE_ID, KEY_GIG_DATE , KEY_GIG_TIME
            , KEY_GIG_PAY};
    public static final String[] ALL_SETLIST_KEYS = new String[] {KEY_SETLIST_ID, KEY_SETLIST_TITLE, KEY_SETLIST_GIG_ID
            , KEY_SETLIST_NOTES};
    public static final String[] ALL_SETLISTSONGS_KEYS = new String[] {KEY_SETLISTSONGS_ID, KEY_SETLISTSONGS_SETLIST_ID
            , KEY_SETLISTSONGS_SONG_ID , KEY_SETLISTSONGS_POSITION};
    public static final String[] ALL_PERSON_KEYS = new String[] {KEY_PERSON_ID, KEY_PERSON_VENUE_ID, KEY_PERSON_FIRSTNAME
            , KEY_PERSON_LASTNAME, KEY_PERSON_TITLE, KEY_PERSON_PHONE, KEY_PERSON_EMAIL };


    public static final String DATABASE_NAME = "MyDb";

    public static final String DATABASE_TABLE_SONG = "song";
    public static final String DATABASE_TABLE_VENUE = "venue";
    public static final String DATABASE_TABLE_PERSON = "person";
    public static final String DATABASE_TABLE_GIG = "gig";
    public static final String DATABASE_TABLE_SETLIST = "setlist";
    public static final String DATABASE_TABLE_SETLISTSONGS = "setlistSongs";



    private static final String DATABASE_CREATE_SQL =
            "create table " + DATABASE_TABLE_SONG
                    + " (" + KEY_SONG_ID + " integer primary key autoincrement, "
                    + KEY_SONG_TITLE + " text not null, "
                    + KEY_SONG_ARTIST + " text not null, "
                    + KEY_SONG_TEMPO + " integer, "
                    + KEY_SONG_GENRE + " text, "
                    + KEY_SONG_YEAR + " integer, "
                    + KEY_SONG_NOTES + " text, "
                    + KEY_SONG_LENGTH + " text, "
                    + KEY_SONG_TIMESIGNATURE + " text, "
                    + KEY_SONG_KEY + " text"
                    + ");\n";
    private static final String DATABASE_CREATE_SQL_VENUE =
                    "create table " + DATABASE_TABLE_VENUE
                    + " (" + KEY_VENUE_ID + " integer primary key autoincrement, "
                    + KEY_VENUE_NAME + " text not null, "
                    + KEY_VENUE_ADDRESS + " text, "
                    + KEY_VENUE_STATE + " text, "
                    + KEY_VENUE_CITY + " text, "
                    + KEY_VENUE_ZIPCODE + " integer"
                    + ");\n";
    private static final String DATABASE_CREATE_SQL_GIG =
                    "create table " + DATABASE_TABLE_GIG
                    + " (" + KEY_GIG_ID + " integer primary key autoincrement, "
                    + KEY_GIG_VENUE_ID + " integer, "
                    + KEY_GIG_DATE + " text, "
                    + KEY_GIG_TIME + " text, "
                    + KEY_GIG_PAY + " integer, "
                    + "FOREIGN KEY("+ KEY_GIG_VENUE_ID +") REFERENCES " + DATABASE_TABLE_VENUE + "(" + KEY_VENUE_ID + ")"
                    + ");\n";
    private static final String DATABASE_CREATE_SQL_SETLIST =
                    "create table " + DATABASE_TABLE_SETLIST
                    + " (" + KEY_SETLIST_ID + " integer primary key autoincrement, "
                    + KEY_SETLIST_TITLE + " text not null, "
                    + KEY_SETLIST_GIG_ID + " integer, "
                    + KEY_SETLIST_NOTES + " text, "
                    + "FOREIGN KEY("+ KEY_SETLIST_GIG_ID +") REFERENCES " + DATABASE_TABLE_GIG + "(" + KEY_GIG_ID + ")"
                    + ");\n";
    private static final String DATABASE_CREATE_SQL_SETLISTSONGS =
                    "create table " + DATABASE_TABLE_SETLISTSONGS
                    + " (" + KEY_SETLISTSONGS_ID + " integer primary key autoincrement, "
                    + KEY_SETLISTSONGS_SETLIST_ID + " integer not null, "
                    + KEY_SETLISTSONGS_SONG_ID + " integer not null, "
                    + KEY_SETLISTSONGS_POSITION + " integer not null, "
                    + "FOREIGN KEY("+ KEY_SETLISTSONGS_SETLIST_ID +") REFERENCES " + DATABASE_TABLE_SETLIST + "(" + KEY_SETLIST_ID + "), "
                    + "FOREIGN KEY("+ KEY_SETLISTSONGS_SONG_ID +") REFERENCES " + DATABASE_TABLE_SONG + "(" + KEY_SONG_ID + ")"
                    + ");\n";
    private static final String DATABASE_CREATE_SQL_PERSON =
                    "create table " + DATABASE_TABLE_PERSON
                    + " (" + KEY_PERSON_ID + " integer primary key autoincrement, "
                    + KEY_PERSON_VENUE_ID + " integer, "
                    + KEY_PERSON_FIRSTNAME + " text not null, "
                    + KEY_PERSON_LASTNAME + " text, "
                    + KEY_PERSON_TITLE + " text, "
                    + KEY_PERSON_PHONE + " text, "
                    + KEY_PERSON_EMAIL + " text, "
                    + "FOREIGN KEY("+ KEY_PERSON_VENUE_ID +") REFERENCES " + DATABASE_TABLE_VENUE + "(" + KEY_VENUE_ID + ")"
                    + ");\n";

    // Context of application who uses us.
    private final Context context;

    private DatabaseHelper myDBHelper;
    private SQLiteDatabase db;


    public DBAdapter(Context ctx) {
        this.context = ctx;
        myDBHelper = new DatabaseHelper( context);
    }

    // Open the database connection.
    public DBAdapter open() {
        db = myDBHelper.getWritableDatabase();
        return this;
    }

    // Close the database connection.
    public void close() {
        myDBHelper.close();
    }

    // Add a new set of values to the database.
    public long insertRow_Song(String title, String artist, int tempo, String genre, int year, String notes, String length, String timeSignature, String key) {

        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SONG_TITLE, title);
        initialValues.put(KEY_SONG_ARTIST, artist);
        initialValues.put(KEY_SONG_TEMPO, tempo);
        initialValues.put(KEY_SONG_GENRE, genre);
        initialValues.put(KEY_SONG_YEAR, year);
        initialValues.put(KEY_SONG_NOTES, notes);
        initialValues.put(KEY_SONG_LENGTH, length);
        initialValues.put(KEY_SONG_TIMESIGNATURE, timeSignature);
        initialValues.put(KEY_SONG_KEY, key);
        Log.v(TAG, "Inserted row in song table: " + title + ", "+ artist + ", "+ tempo + ", "+ genre + ", "+ year + ", "+ notes + ", "+ length + ", "+ timeSignature + ", "+ key);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE_SONG, null, initialValues);
    }
    public long insertRow_Venue(String name, String city, String state, String address, int zip) {

        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_VENUE_NAME, name);
        initialValues.put(KEY_VENUE_CITY, city);
        initialValues.put(KEY_VENUE_STATE, state);
        initialValues.put(KEY_VENUE_ADDRESS, address);
        initialValues.put(KEY_VENUE_ZIPCODE, zip);
        Log.v(TAG, "Inserted row in venue table: " + name + ", " + city + ", " + state + ", " + address + ", " + zip);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE_VENUE, null, initialValues);
    }
    public long insertRow_People(String firstName, String lastName, String title, String phone, String email) {

        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_PERSON_FIRSTNAME, firstName);
        initialValues.put(KEY_PERSON_LASTNAME, lastName);
        initialValues.put(KEY_PERSON_TITLE, title);
        initialValues.put(KEY_PERSON_PHONE, phone);
        initialValues.put(KEY_PERSON_EMAIL, email);
        Log.v(TAG, "Inserted row in people table: " + title + ", " + firstName + ", " + lastName + ", " + title + ", " + phone + ", " + email);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE_PERSON, null, initialValues);
    }
    public long insertRow_Gigs(int venue, String date, String time, int pay) {

        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_GIG_VENUE_ID, venue);
        initialValues.put(KEY_GIG_DATE, date);
        initialValues.put(KEY_GIG_TIME, time);
        initialValues.put(KEY_GIG_PAY, pay);
        Log.v(TAG, "Inserted row in gigs table: " + venue + ", " + date + ", " + time + ", " + pay);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE_GIG, null, initialValues);
    }
    public long insertRow_Setlists(String title, int gigID, String notes) {

        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SETLIST_TITLE, title);
        initialValues.put(KEY_SETLIST_GIG_ID, gigID);
        initialValues.put(KEY_SETLIST_NOTES, notes);
        Log.v(TAG, "Inserted row in setlists table: " + title + ", " + gigID + ", " + notes);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE_SETLIST, null, initialValues);
    }
    public long insertRow_Setlists_Songs(int setlist, int song, int position) {

        // Create row's data:
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_SETLISTSONGS_SETLIST_ID, setlist);
        initialValues.put(KEY_SETLISTSONGS_SONG_ID, song);
        initialValues.put(KEY_SETLISTSONGS_POSITION, position);
        Log.v(TAG, "Inserted row in setlist_songs table: " + setlist + ", " + song + ", " + position);

        // Insert it into the database.
        return db.insert(DATABASE_TABLE_SETLISTSONGS, null, initialValues);
    }
    public int getVenueID_VenueName(String venue){

        Cursor cursor = db.rawQuery("SELECT " + KEY_VENUE_ID + " FROM " + DATABASE_TABLE_VENUE + " WHERE " + KEY_VENUE_NAME + "= " + "\"" +  venue + "\";", null);
        int venue_id;
        if (cursor.moveToFirst()) {
            venue_id = cursor.getInt(0);
        }else {
            venue_id = 0;
        }
        Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor));
        cursor.close();
        Log.v(TAG, "Got venue_ID: " + venue_id + " from " + venue);
        return venue_id;
    }
    public String getVenueName_VenueID(int venueID){
        Cursor cursor = db.rawQuery("SELECT " + KEY_VENUE_NAME + " FROM " + DATABASE_TABLE_VENUE + " WHERE " + KEY_VENUE_ID + "= " + venueID + ";", null);
        Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor));
        String venue_name;
        if (cursor.moveToFirst()) {
            venue_name = cursor.getString(cursor.getColumnIndex("name"));
        }else{
            venue_name = "nothing found";
        }
        Log.v(TAG, "Got venue_name: " + venue_name + " from " + venueID);


        cursor.close();
        return venue_name;
    }
    public String getGigName_GigID(int gigID){
        Cursor cursor = db.rawQuery("SELECT " + KEY_GIG_VENUE_ID + " FROM " + DATABASE_TABLE_GIG + " WHERE " + KEY_GIG_ID + "= " + gigID + ";", null);
        Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor));
        int gig_id;
        if (cursor.moveToFirst()) {
            gig_id = cursor.getInt(cursor.getColumnIndex("venue_id"));
        }else{
            gig_id = 0;
        }
        Log.v(TAG, "Got gig_id: " + gig_id + " from " + gigID);
        Cursor cursor2 = db.rawQuery("SELECT " + KEY_VENUE_NAME + " FROM " + DATABASE_TABLE_VENUE + " WHERE " + KEY_VENUE_ID + "= " + gig_id + ";", null);
        Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor2));
        String venue_name;
        if (cursor2.moveToFirst()) {
            venue_name = cursor2.getString(cursor2.getColumnIndex("name"));
        }else{
            venue_name = "nothing found";
        }
        Log.v(TAG, "Got venue_name: " + venue_name + " from " + gig_id);


        cursor2.close();
        cursor.close();
        return venue_name;
    }
    public String getSongName_SongID(int songID){
        Cursor cursor = db.rawQuery("SELECT " + KEY_SONG_TITLE + " FROM " + DATABASE_TABLE_SONG + " WHERE " + KEY_SONG_ID + "= " + songID + ";", null);
        Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor));
        String song_name;
        if (cursor.moveToFirst()) {
            song_name = cursor.getString(cursor.getColumnIndex("title"));
        }else{
            song_name = "nothing found";
        }
        Log.v(TAG, "Got song_name: " + song_name + " from " + songID);


        cursor.close();
        return song_name;
    }
    public String getSongArtist_SongID(int songID){
        Cursor cursor = db.rawQuery("SELECT " + KEY_SONG_ARTIST + " FROM " + DATABASE_TABLE_SONG + " WHERE " + KEY_SONG_ID + "= " + songID + ";", null);
        Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor));
        String song_artist;
        if (cursor.moveToFirst()) {
            song_artist = cursor.getString(cursor.getColumnIndex("artist"));
        }else{
            song_artist = "nothing found";
        }
        Log.v(TAG, "Got song_artist: " + song_artist + " from " + songID);


        cursor.close();
        return song_artist;
    }
    public int getVenueID_GigID(int gig){

        Cursor cursor = db.rawQuery("SELECT " + KEY_GIG_VENUE_ID + " FROM " + DATABASE_TABLE_GIG + " WHERE " + KEY_GIG_ID + "= "  +  gig + ";", null);
        int venue_id;
        if (cursor.moveToFirst()) {
            venue_id = cursor.getInt(0);
        }else {
            venue_id = 0;
        }
        Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor));
        cursor.close();
        Log.v(TAG, "Got venue_ID: " + venue_id + " from gigID:" + gig);
        return venue_id;
    }
    public int getSongCount_SetlistID(int setlist){

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + DATABASE_TABLE_SETLISTSONGS + " WHERE " + KEY_SETLISTSONGS_SETLIST_ID + "= "  +  setlist + ";", null);
        int song_count;
        if (cursor.moveToFirst()) {
            song_count = cursor.getInt(0);
        }else {
            song_count = 0;
        }
        Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor));
        cursor.close();
        Log.v(TAG, "Got Song_Count: " + song_count + " from " + setlist);
        return song_count;
    }
    public int getSongHighest_SetlistID(int setlist){
        String where = "SELECT MAX(position) FROM " + DATABASE_TABLE_SETLISTSONGS + " WHERE " + KEY_SETLISTSONGS_SETLIST_ID + " = " + setlist + ";";
        Cursor cursor = db.rawQuery(where, null);
        int highest;
        if (cursor.moveToFirst()) {
            highest = cursor.getInt(0);
        }else {
            highest = 0;
        }

        return highest;
    }
    public int getSetlistSongID(int setlist, int song){

        String where = "SELECT " + KEY_SETLISTSONGS_ID + " FROM " + DATABASE_TABLE_SETLISTSONGS + " WHERE " +
                KEY_SETLISTSONGS_SETLIST_ID + " = " + setlist + " AND " +
                KEY_SETLISTSONGS_SONG_ID + " = " + song +";";
        Cursor cursor = db.rawQuery(where, null);
        Log.v(TAG, where);
        Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor));

        int pos;
        if (cursor.moveToFirst()) {
            pos = cursor.getInt(0);
        }else {
            pos = 0;
        }
        Log.v(TAG, "Got setlist_song_id: " + pos + " from " + setlist + ", song_id: " + song);
        return pos;
    }
    public int getSetlistSongPos(int setlist, int position){
        String where = "SELECT " + KEY_SETLISTSONGS_POSITION + " FROM " + DATABASE_TABLE_SETLISTSONGS + " WHERE " +
                KEY_SETLISTSONGS_SETLIST_ID + " = " + setlist + " AND " +
                KEY_SETLISTSONGS_POSITION + " = " + position +";";

        Cursor cursor = db.rawQuery(where, null);
        Log.v(TAG, where);
        Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor));
        int pos;
        if (cursor.moveToFirst()) {
            pos = cursor.getInt(0);
        }else {
            pos = 0;
        }
        Log.v(TAG, "Got setlist_song_pos: " + pos + " from " + setlist);
        return pos;
    }
    public int getSetlistSongSong(int setlist, int position){
        String where = "SELECT " + KEY_SETLISTSONGS_SONG_ID + " FROM " + DATABASE_TABLE_SETLISTSONGS + " WHERE " +
                KEY_SETLISTSONGS_SETLIST_ID + " = " + setlist + " AND " +
                KEY_SETLISTSONGS_POSITION + " = " + position +";";

        Cursor cursor = db.rawQuery(where, null);
        Log.v(TAG, where);
        Log.v(TAG, DatabaseUtils.dumpCursorToString(cursor));
        int pos;
        if (cursor.moveToFirst()) {
            pos = cursor.getInt(0);
        }else {
            pos = 0;
        }
        Log.v(TAG, "Got setlist_song_pos: " + pos + " from " + setlist);
        return pos;
    }
    public int setlistSongPos_id(int id)
    {
        String where = "SELECT " + KEY_SETLISTSONGS_POSITION + " FROM " + DATABASE_TABLE_SETLISTSONGS +
                " WHERE " + KEY_SETLISTSONGS_ID + " = " + id;
        Cursor c = db.rawQuery(where, null);
        int pos;
        if (c.moveToFirst()) {
            pos = c.getInt(0);
        }else {
            pos = 0;
        }
        return pos;
    }
    public void upSongPosition(int setlist, int position){
        int id = getSetlistSongID(setlist, position);
        int song = getSetlistSongSong(setlist, position);
        position--;
        updateRow_setlists_songs(id, setlist, song, position);


    }
    public void downSongPosition(int setlist, int song){
        int id = getSetlistSongID(setlist, song);
        int pos = getSetlistSongPos(setlist, song);
        pos++;
        updateRow_setlists_songs(id,setlist,song,pos);


    }
    public void removeSetlistSong(int setlistSongID){
        //int id = getSetlistSongID(setlist, song);

        String where1 = KEY_SETLIST_ID + "= " +setlistSongID;
        db.delete(DATABASE_TABLE_SETLISTSONGS, where1, null);

        //Log.v(TAG, "Deleted setlist_song: Setlist = " + setlist + ", song = " + song + ", setlistsong_id = " + id);


    }
    public void dbContents()
    {
        Cursor c = db.query(DATABASE_TABLE_SETLISTSONGS, null, null, null, null, null, null);
        Log.v(TAG, DatabaseUtils.dumpCursorToString(c));
    }



    // Delete a row from the database, by rowId (primary key)
    public boolean deleteRow_song(String songName) {
        String where = KEY_SONG_TITLE + "= \""  + songName + "\"";
        return db.delete(DATABASE_TABLE_SONG, where, null) != 0;
    }
    public boolean deleteRow_venue(String venueName) {
        String where = KEY_VENUE_NAME + "= \""  + venueName + "\"";
        return db.delete(DATABASE_TABLE_VENUE, where, null) != 0;
    }
    public boolean deleteRow_people(String name) {
        String where = KEY_PERSON_FIRSTNAME + "= \""  + name + "\"";
        Log.v(TAG, "Deleting person row: " + where);
        return db.delete(DATABASE_TABLE_PERSON, where, null) != 0;
    }
    public boolean deleteRow_gigs(int id) {
        String where = KEY_GIG_ID + "= "  + id;
        Log.v(TAG, "Deleting gigs row: " + where);
        return db.delete(DATABASE_TABLE_GIG, where, null) != 0;
    }
    public boolean deleteRow_setlists(int id) {
        String where = KEY_SETLIST_ID + "="  + id;
        Log.v(TAG, "Deleting setlist row: " + where);
        return db.delete(DATABASE_TABLE_SETLIST, where, null) != 0;
    }

    public void deleteAll() {
        Cursor c = getAllRows();
        long rowId = c.getColumnIndexOrThrow( KEY_SONG_ID);
        if (c.moveToFirst()) {
            do {
                //deleteRow(c.getLong((int) rowId));
            } while (c.moveToNext());
        }
        c.close();
    }

    // Return all data in the database.
    public Cursor getAllRows() {
        String where = null;
        Cursor c =   db.query(true, DATABASE_TABLE_SONG, ALL_SONG_KEYS,
                null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor getAllRows_venue() {
        String where = null;
        Cursor c =   db.query(true, DATABASE_TABLE_VENUE, ALL_VENUE_KEYS,
                null, null, null, null, null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor getAllRows_people() {
        String where = null;
        Cursor c =   db.query(true, DATABASE_TABLE_PERSON, ALL_PERSON_KEYS ,
                null, null, null, null , null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor getAllRows_gigs() {
        String where = null;
        Cursor c =   db.query(true, DATABASE_TABLE_GIG, ALL_GIG_KEYS ,
                null, null, null, null , null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor getAllRows_setlists() {
        String where = null;
        Cursor c =   db.query(true, DATABASE_TABLE_SETLIST, ALL_SETLIST_KEYS ,
                null, null, null, null , null, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }
    public Cursor getAllRows_setlists_songs(int setlist) {
        String whereClause = KEY_SETLISTSONGS_SETLIST_ID + " = " + setlist;
        Cursor c =   db.query(true, DATABASE_TABLE_SETLISTSONGS, ALL_SETLISTSONGS_KEYS ,
                whereClause, null, null, null , KEY_SETLISTSONGS_POSITION, null);
        if (c != null) {
            c.moveToFirst();
        }
        return c;
    }


    // Get a specific row (by rowId)
    public Cursor getRow_song(String songName) {
        String where = KEY_SONG_TITLE + "= \""  + songName + "\"";
        Cursor c =   db.query(DATABASE_TABLE_SONG, ALL_SONG_KEYS ,
                where, null, null, null , null, null);
        if (c != null) {
            c.moveToFirst();
        }
        Log.v(TAG,"getRow_song:");
        Log.v(TAG, DatabaseUtils.dumpCursorToString(c));
        return c;
    }
    public Cursor getRow_venue(String venueName) {
        String where = KEY_VENUE_NAME + "= \""  + venueName + "\"";
        Cursor c =   db.query(DATABASE_TABLE_VENUE, ALL_VENUE_KEYS ,
                where, null, null, null , null, null);
        if (c != null) {
            c.moveToFirst();
        }
        Log.v(TAG,"getRow_venue:");
        Log.v(TAG, DatabaseUtils.dumpCursorToString(c));
        return c;
    }
    public Cursor getRow_person(String personName) {
        String where = KEY_PERSON_FIRSTNAME + "= \""  + personName + "\"";
        Cursor c =   db.query(DATABASE_TABLE_PERSON, ALL_PERSON_KEYS ,
                where, null, null, null , null, null);
        if (c != null) {
            c.moveToFirst();
        }
        Log.v(TAG,"getRow_person:");
        Log.v(TAG, DatabaseUtils.dumpCursorToString(c));
        return c;
    }
    public Cursor getRow_gigs(int gigId) {
        String where = KEY_GIG_ID + "= \""  + gigId + "\"";
        Cursor c =   db.query(DATABASE_TABLE_GIG, ALL_GIG_KEYS ,
                where, null, null, null , null, null);
        if (c != null) {
            c.moveToFirst();
        }
        Log.v(TAG,"getRow_gigs:");
        Log.v(TAG, DatabaseUtils.dumpCursorToString(c));
        return c;
    }
    public Cursor getRow_setlists(int setlistId) {
        String where = KEY_SETLIST_ID + "= \""  + setlistId + "\"";
        Cursor c =   db.query(DATABASE_TABLE_SETLIST, ALL_SETLIST_KEYS ,
                where, null, null, null , null, null);
        if (c != null) {
            c.moveToFirst();
        }
        Log.v(TAG,"getRow_setlists: setlistId = " + setlistId);
        Log.v(TAG, DatabaseUtils.dumpCursorToString(c));
        return c;
    }

    // Change an existing row to be equal to new data.
    public boolean updateRow_song(String title, String artist, int tempo, String genre, int year, String notes, String length, String timeSignature, String key) {
        String where = KEY_SONG_TITLE + "= \""  + title + "\"";


        // Create row's data:
        ContentValues newValues = new ContentValues();
        newValues.put(KEY_SONG_TITLE, title);
        newValues.put(KEY_SONG_ARTIST, artist);
        newValues.put(KEY_SONG_TEMPO, tempo);
        newValues.put(KEY_SONG_GENRE, genre);
        newValues.put(KEY_SONG_YEAR, year);
        newValues.put(KEY_SONG_NOTES, notes);
        newValues.put(KEY_SONG_LENGTH, length);
        newValues.put(KEY_SONG_TIMESIGNATURE, timeSignature);
        newValues.put(KEY_SONG_KEY, key);

        // Insert it into the database.
        return db.update(DATABASE_TABLE_SONG, newValues, where, null) != 0 ;
    }
    public boolean updateRow_venue(String name, String address, String city, String state, int zip) {
        String where = KEY_VENUE_NAME + "= \""  + name + "\"";

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_VENUE_NAME, name);
        newValues.put(KEY_VENUE_ADDRESS, address);
        newValues.put(KEY_VENUE_CITY, city);
        newValues.put(KEY_VENUE_STATE, state);
        newValues.put(KEY_VENUE_ZIPCODE, zip);

        // Insert it into the database.
        return db.update(DATABASE_TABLE_VENUE, newValues, where, null) != 0 ;
    }
    public boolean updateRow_people(String firstName, String lastName, String title, String phone, String email) {
        String where = KEY_PERSON_FIRSTNAME + "= \""  + firstName + "\"";

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_PERSON_FIRSTNAME, firstName);
        newValues.put(KEY_PERSON_LASTNAME, lastName);
        newValues.put(KEY_PERSON_TITLE, title);
        newValues.put(KEY_PERSON_PHONE, phone);
        newValues.put(KEY_PERSON_EMAIL, email);

        // Insert it into the database.
        return db.update(DATABASE_TABLE_PERSON, newValues, where, null) != 0 ;
    }
    //ToDo edit updateRow_gigs to allow for ID
    public boolean updateRow_gigs(int gigID,String venue, String date, String time, int pay) {
        String where = KEY_GIG_ID + "= "  + gigID;
        int venueInt = this.getVenueID_VenueName(venue);

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_GIG_VENUE_ID, venueInt);
        newValues.put(KEY_GIG_DATE, date);
        newValues.put(KEY_GIG_TIME, time);
        newValues.put(KEY_GIG_PAY, pay);

        // Insert it into the database.
        return db.update(DATABASE_TABLE_GIG, newValues, where, null) != 0 ;
    }
    public boolean updateRow_setlists(int setlistsID,String title, int gig, String notes) {
        String where = KEY_SETLIST_ID + "= "  + setlistsID;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_SETLIST_TITLE, title);
        newValues.put(KEY_SETLIST_GIG_ID, gig);
        newValues.put(KEY_SETLIST_NOTES, notes);

        // Insert it into the database.
        return db.update(DATABASE_TABLE_SETLIST, newValues, where, null) != 0 ;
    }
    public boolean updateRow_setlists_songs(int setlistssongID, int setlistID,int songID, int position) {
        String where = KEY_SETLISTSONGS_ID + "= "  + setlistssongID;

        ContentValues newValues = new ContentValues();
        newValues.put(KEY_SETLISTSONGS_SETLIST_ID, setlistID);
        newValues.put(KEY_SETLISTSONGS_SONG_ID, songID);
        newValues.put(KEY_SETLISTSONGS_POSITION, position);

        // Insert it into the database.
        return db.update(DATABASE_TABLE_SETLISTSONGS, newValues, where, null) != 0 ;
    }


    /////////////////////////////////////////////////////////////////////
    // Private Helper Classes:
    /////////////////////////////////////////////////////////////////////


    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase _db) {
            _db.execSQL(DATABASE_CREATE_SQL);
            _db.execSQL(DATABASE_CREATE_SQL_VENUE);
            _db.execSQL(DATABASE_CREATE_SQL_GIG);
            _db.execSQL(DATABASE_CREATE_SQL_PERSON);
            _db.execSQL(DATABASE_CREATE_SQL_SETLIST);
            _db.execSQL(DATABASE_CREATE_SQL_SETLISTSONGS);

            insertTempData(_db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase _db, int oldVersion, int newVersion) {
            Log.w(TAG, "Upgrading application's database from version " + oldVersion
                    + " to " + newVersion + ", which will destroy all old data!");

            // Destroy old database:
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SONG);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_VENUE);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SETLIST);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_SETLISTSONGS);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_PERSON);
            _db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_GIG);

            // Recreate new database:
            onCreate(_db);
        }
        public void insertTempData(SQLiteDatabase db){
            //initial songs
            ContentValues initialValues = new ContentValues();
            initialValues.put(KEY_SONG_TITLE, "Smalls Like Teen Spirit");
            initialValues.put(KEY_SONG_ARTIST, "Nirvana");
            initialValues.put(KEY_SONG_TEMPO, 120);
            initialValues.put(KEY_SONG_GENRE, "Grunge");
            initialValues.put(KEY_SONG_YEAR, 1991);
            initialValues.put(KEY_SONG_NOTES, "Great one!!");
            initialValues.put(KEY_SONG_LENGTH, "5:01");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "F");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Valerie");
            initialValues.put(KEY_SONG_ARTIST, "The Zutons");
            initialValues.put(KEY_SONG_TEMPO, 117);
            initialValues.put(KEY_SONG_GENRE, "Alternative");
            initialValues.put(KEY_SONG_YEAR, 2006);
            initialValues.put(KEY_SONG_NOTES, "Keep it going guys!");
            initialValues.put(KEY_SONG_LENGTH, "3:56");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "Eb");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Chandelier");
            initialValues.put(KEY_SONG_ARTIST, "Sia");
            initialValues.put(KEY_SONG_TEMPO, 95);
            initialValues.put(KEY_SONG_GENRE, "Pop");
            initialValues.put(KEY_SONG_YEAR, 2014);
            initialValues.put(KEY_SONG_NOTES, "Amazing voice!!");
            initialValues.put(KEY_SONG_LENGTH, "3:36");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "Em");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Jeremy");
            initialValues.put(KEY_SONG_ARTIST, "Pearl Jam");
            initialValues.put(KEY_SONG_TEMPO, 112);
            initialValues.put(KEY_SONG_GENRE, "Rock");
            initialValues.put(KEY_SONG_YEAR, 1991);
            initialValues.put(KEY_SONG_NOTES, "That's my name!");
            initialValues.put(KEY_SONG_LENGTH, "5:18");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "E");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Simple Man");
            initialValues.put(KEY_SONG_ARTIST, "Lynyrd Skynyrd");
            initialValues.put(KEY_SONG_TEMPO, 75);
            initialValues.put(KEY_SONG_GENRE, "Classic Rock");
            initialValues.put(KEY_SONG_YEAR, 1973);
            initialValues.put(KEY_SONG_NOTES, "What a classic!!");
            initialValues.put(KEY_SONG_LENGTH, "5:57");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "G");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Unknown Road");
            initialValues.put(KEY_SONG_ARTIST, "Pennywise");
            initialValues.put(KEY_SONG_TEMPO, 150);
            initialValues.put(KEY_SONG_GENRE, "Punk");
            initialValues.put(KEY_SONG_YEAR, 1993);
            initialValues.put(KEY_SONG_NOTES, "Wouldn't it be nice!");
            initialValues.put(KEY_SONG_LENGTH, "2:46");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "A");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Little Red Corvette");
            initialValues.put(KEY_SONG_ARTIST, "Prince");
            initialValues.put(KEY_SONG_TEMPO, 113);
            initialValues.put(KEY_SONG_GENRE, "New Wave");
            initialValues.put(KEY_SONG_YEAR, 1983);
            initialValues.put(KEY_SONG_NOTES, "I see purple!!");
            initialValues.put(KEY_SONG_LENGTH, "5:03");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "Eb");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Uptown Funk");
            initialValues.put(KEY_SONG_ARTIST, "Mark Ronson");
            initialValues.put(KEY_SONG_TEMPO, 122);
            initialValues.put(KEY_SONG_GENRE, "Funk");
            initialValues.put(KEY_SONG_YEAR, 2014);
            initialValues.put(KEY_SONG_NOTES, "Stop... wait a minute.");
            initialValues.put(KEY_SONG_LENGTH, "4:30");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "Bm");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Let's Get It On");
            initialValues.put(KEY_SONG_ARTIST, "Marvin Gaye");
            initialValues.put(KEY_SONG_TEMPO, 88);
            initialValues.put(KEY_SONG_GENRE, "Soul");
            initialValues.put(KEY_SONG_YEAR, 1973);
            initialValues.put(KEY_SONG_NOTES, "What a classic!!");
            initialValues.put(KEY_SONG_LENGTH, "4:44");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "D");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Yesterday");
            initialValues.put(KEY_SONG_ARTIST, "The Beatles");
            initialValues.put(KEY_SONG_TEMPO, 80);
            initialValues.put(KEY_SONG_GENRE, "Pop");
            initialValues.put(KEY_SONG_YEAR, 1965);
            initialValues.put(KEY_SONG_NOTES, "Scrambled Eggs");
            initialValues.put(KEY_SONG_LENGTH, "2:03");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "G");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Stairway to Heaven");
            initialValues.put(KEY_SONG_ARTIST, "Led Zeppelin");
            initialValues.put(KEY_SONG_TEMPO, 120);
            initialValues.put(KEY_SONG_GENRE, "Classic Rock");
            initialValues.put(KEY_SONG_YEAR, 1971);
            initialValues.put(KEY_SONG_NOTES, "Double Neck");
            initialValues.put(KEY_SONG_LENGTH, "8:03");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "F");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Symphony No. 9");
            initialValues.put(KEY_SONG_ARTIST, "Ludwig van Beethoven");
            initialValues.put(KEY_SONG_TEMPO, 75);
            initialValues.put(KEY_SONG_GENRE, "Classical");
            initialValues.put(KEY_SONG_YEAR, 1824);
            initialValues.put(KEY_SONG_NOTES, "Bum bum bum BUM!!!");
            initialValues.put(KEY_SONG_LENGTH, "15:00");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "Dm");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Gold Lion");
            initialValues.put(KEY_SONG_ARTIST, "Yeah Yeah Yeahs");
            initialValues.put(KEY_SONG_TEMPO, 70);
            initialValues.put(KEY_SONG_GENRE, "Indie Rock");
            initialValues.put(KEY_SONG_YEAR, 2006);
            initialValues.put(KEY_SONG_NOTES, "Nice beat");
            initialValues.put(KEY_SONG_LENGTH, "3:09");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "F#");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Where Is My Mind?");
            initialValues.put(KEY_SONG_ARTIST, "Pixies");
            initialValues.put(KEY_SONG_TEMPO, 81);
            initialValues.put(KEY_SONG_GENRE, "Alternative Rock");
            initialValues.put(KEY_SONG_YEAR, 1988);
            initialValues.put(KEY_SONG_NOTES, "Fight Club");
            initialValues.put(KEY_SONG_LENGTH, "3:53");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "G");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Straight Outta Compton");
            initialValues.put(KEY_SONG_ARTIST, "N.W.A");
            initialValues.put(KEY_SONG_TEMPO, 100);
            initialValues.put(KEY_SONG_GENRE, "Rap");
            initialValues.put(KEY_SONG_YEAR, 1988);
            initialValues.put(KEY_SONG_NOTES, "It's a rap!!");
            initialValues.put(KEY_SONG_LENGTH, "3:12");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "E");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "True Companion");
            initialValues.put(KEY_SONG_ARTIST, "Marc Cohn");
            initialValues.put(KEY_SONG_TEMPO, 99);
            initialValues.put(KEY_SONG_GENRE, "Folk");
            initialValues.put(KEY_SONG_YEAR, 1991);
            initialValues.put(KEY_SONG_NOTES, "Love Song!");
            initialValues.put(KEY_SONG_LENGTH, "4:10");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "Bb");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Raining Blood");
            initialValues.put(KEY_SONG_ARTIST, "Slayer");
            initialValues.put(KEY_SONG_TEMPO, 140);
            initialValues.put(KEY_SONG_GENRE, "Thrash Metal");
            initialValues.put(KEY_SONG_YEAR, 1986);
            initialValues.put(KEY_SONG_NOTES, "Chugga Chugga");
            initialValues.put(KEY_SONG_LENGTH, "4:14");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "Eb");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "The Star Spangled Banner");
            initialValues.put(KEY_SONG_ARTIST, "Francis Scott Key");
            initialValues.put(KEY_SONG_TEMPO, 95);
            initialValues.put(KEY_SONG_GENRE, "Anthem");
            initialValues.put(KEY_SONG_YEAR, 1814);
            initialValues.put(KEY_SONG_NOTES, "Merica!");
            initialValues.put(KEY_SONG_LENGTH, "2:30");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "3/4");
            initialValues.put(KEY_SONG_KEY, "F");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Blue Bossa");
            initialValues.put(KEY_SONG_ARTIST, "Kenny Dorham");
            initialValues.put(KEY_SONG_TEMPO, 88);
            initialValues.put(KEY_SONG_GENRE, "Jazz");
            initialValues.put(KEY_SONG_YEAR, 1963);
            initialValues.put(KEY_SONG_NOTES, "Smooth");
            initialValues.put(KEY_SONG_LENGTH, "5:33");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "Cm");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "The Thrill Is Gone");
            initialValues.put(KEY_SONG_ARTIST, "B.B. King");
            initialValues.put(KEY_SONG_TEMPO, 90);
            initialValues.put(KEY_SONG_GENRE, "Blues");
            initialValues.put(KEY_SONG_YEAR, 1969);
            initialValues.put(KEY_SONG_NOTES, "12 Bar");
            initialValues.put(KEY_SONG_LENGTH, "5:24");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "Gm");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Let's Dance");
            initialValues.put(KEY_SONG_ARTIST, "David Bowie");
            initialValues.put(KEY_SONG_TEMPO, 99);
            initialValues.put(KEY_SONG_GENRE, "Pop");
            initialValues.put(KEY_SONG_YEAR, 1982);
            initialValues.put(KEY_SONG_NOTES, "Stevie Ray was here");
            initialValues.put(KEY_SONG_LENGTH, "4:07");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "Abm");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Mamma Mia");
            initialValues.put(KEY_SONG_ARTIST, "ABBA");
            initialValues.put(KEY_SONG_TEMPO, 111);
            initialValues.put(KEY_SONG_GENRE, "Pop");
            initialValues.put(KEY_SONG_YEAR, 1975);
            initialValues.put(KEY_SONG_NOTES, "Here we go again");
            initialValues.put(KEY_SONG_LENGTH, "3:35");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "2/4");
            initialValues.put(KEY_SONG_KEY, "F");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);
            initialValues.put(KEY_SONG_TITLE, "Bye Bye Bye");
            initialValues.put(KEY_SONG_ARTIST, "NSYNC");
            initialValues.put(KEY_SONG_TEMPO, 130);
            initialValues.put(KEY_SONG_GENRE, "Pop");
            initialValues.put(KEY_SONG_YEAR, 2000);
            initialValues.put(KEY_SONG_NOTES, "JT's beginning");
            initialValues.put(KEY_SONG_LENGTH, "3:20");
            initialValues.put(KEY_SONG_TIMESIGNATURE, "4/4");
            initialValues.put(KEY_SONG_KEY, "F");
            db.insert(DATABASE_TABLE_SONG, null, initialValues);

//initial venues
            ContentValues newValuesVenue = new ContentValues();
            newValuesVenue.put(KEY_VENUE_NAME, "Hollywood Bowl");
            newValuesVenue.put(KEY_VENUE_ADDRESS, "2301 Highland Ave");
            newValuesVenue.put(KEY_VENUE_CITY, "Los Angeles");
            newValuesVenue.put(KEY_VENUE_STATE, "CA");
            newValuesVenue.put(KEY_VENUE_ZIPCODE, 90068);
            db.insert(DATABASE_TABLE_VENUE, null, newValuesVenue);
            newValuesVenue.put(KEY_VENUE_NAME, "Staples Center");
            newValuesVenue.put(KEY_VENUE_ADDRESS, "1111 S Figeroa St.");
            newValuesVenue.put(KEY_VENUE_CITY, "Los Angeles");
            newValuesVenue.put(KEY_VENUE_STATE, "CA");
            newValuesVenue.put(KEY_VENUE_ZIPCODE, 90015);
            db.insert(DATABASE_TABLE_VENUE, null, newValuesVenue);
            newValuesVenue.put(KEY_VENUE_NAME, "Red Rocks Amphitheatre");
            newValuesVenue.put(KEY_VENUE_ADDRESS, "18300 W Alameda Pkwy");
            newValuesVenue.put(KEY_VENUE_CITY, "Morrison");
            newValuesVenue.put(KEY_VENUE_STATE, "CO");
            newValuesVenue.put(KEY_VENUE_ZIPCODE, 80465);
            db.insert(DATABASE_TABLE_VENUE, null, newValuesVenue);
            newValuesVenue.put(KEY_VENUE_NAME, "Madison Square Garden");
            newValuesVenue.put(KEY_VENUE_ADDRESS, "4 Pennsylvania Plaza");
            newValuesVenue.put(KEY_VENUE_CITY, "New York");
            newValuesVenue.put(KEY_VENUE_STATE, "NY");
            newValuesVenue.put(KEY_VENUE_ZIPCODE, 10001);
            db.insert(DATABASE_TABLE_VENUE, null, newValuesVenue);
            newValuesVenue.put(KEY_VENUE_NAME, "Coachella");
            newValuesVenue.put(KEY_VENUE_ADDRESS, "81 Avenue 51");
            newValuesVenue.put(KEY_VENUE_CITY, "Indio");
            newValuesVenue.put(KEY_VENUE_STATE, "CA");
            newValuesVenue.put(KEY_VENUE_ZIPCODE, 92201);
            db.insert(DATABASE_TABLE_VENUE, null, newValuesVenue);

            ContentValues initialValuesPeople = new ContentValues();
            initialValuesPeople.put(KEY_PERSON_FIRSTNAME, "Harry");
            initialValuesPeople.put(KEY_PERSON_LASTNAME, "Potter");
            initialValuesPeople.put(KEY_PERSON_TITLE, "Wizard");
            initialValuesPeople.put(KEY_PERSON_PHONE, "020MAGIC934");
            initialValuesPeople.put(KEY_PERSON_EMAIL, "harry.potter@mom.gov");
            db.insert(DATABASE_TABLE_PERSON, null, initialValuesPeople);
            initialValuesPeople.put(KEY_PERSON_FIRSTNAME, "George");
            initialValuesPeople.put(KEY_PERSON_LASTNAME, "Bush");
            initialValuesPeople.put(KEY_PERSON_TITLE, "Ex-President");
            initialValuesPeople.put(KEY_PERSON_PHONE, "(214)200-4300");
            initialValuesPeople.put(KEY_PERSON_EMAIL, "george.bush@whitehouse.gov");
            db.insert(DATABASE_TABLE_PERSON, null, initialValuesPeople);
            initialValuesPeople.put(KEY_PERSON_FIRSTNAME, "Wolfgang");
            initialValuesPeople.put(KEY_PERSON_LASTNAME, "Mozart");
            initialValuesPeople.put(KEY_PERSON_TITLE, "Composer");
            initialValuesPeople.put(KEY_PERSON_PHONE, "1-800-COMPOSE");
            initialValuesPeople.put(KEY_PERSON_EMAIL, "composer4life@aol.com");
            db.insert(DATABASE_TABLE_PERSON, null, initialValuesPeople);
            initialValuesPeople.put(KEY_PERSON_FIRSTNAME, "Bob");
            initialValuesPeople.put(KEY_PERSON_LASTNAME, "Ross");
            initialValuesPeople.put(KEY_PERSON_TITLE, "Happy Painter");
            initialValuesPeople.put(KEY_PERSON_PHONE, "1-800-PAINTER");
            initialValuesPeople.put(KEY_PERSON_EMAIL, "happyTrees@gmail.com");
            db.insert(DATABASE_TABLE_PERSON, null, initialValuesPeople);
            initialValuesPeople.put(KEY_PERSON_FIRSTNAME, "Jolene");
            initialValuesPeople.put(KEY_PERSON_LASTNAME, "Driesler");
            initialValuesPeople.put(KEY_PERSON_TITLE, "Awesome Dog");
            initialValuesPeople.put(KEY_PERSON_PHONE, "1-800-NAPTIME");
            initialValuesPeople.put(KEY_PERSON_EMAIL, "barksAtNothing.Wheresmyball@happydog.com");
            db.insert(DATABASE_TABLE_PERSON, null, initialValuesPeople);

            ContentValues initialValuesGigs = new ContentValues();
            initialValuesGigs.put(KEY_GIG_VENUE_ID, 1);
            initialValuesGigs.put(KEY_GIG_DATE, "03/19/17");
            initialValuesGigs.put(KEY_GIG_TIME, "09:00PM");
            initialValuesGigs.put(KEY_GIG_PAY, 300);
            db.insert(DATABASE_TABLE_GIG, null, initialValuesGigs);
            initialValuesGigs.put(KEY_GIG_VENUE_ID, 2);
            initialValuesGigs.put(KEY_GIG_DATE, "08/27/16");
            initialValuesGigs.put(KEY_GIG_TIME, "09:00PM");
            initialValuesGigs.put(KEY_GIG_PAY, 425);
            db.insert(DATABASE_TABLE_GIG, null, initialValuesGigs);
            initialValuesGigs.put(KEY_GIG_VENUE_ID, 3);
            initialValuesGigs.put(KEY_GIG_DATE, "10/01/16");
            initialValuesGigs.put(KEY_GIG_TIME, "09:00PM");
            initialValuesGigs.put(KEY_GIG_PAY, 600);
            db.insert(DATABASE_TABLE_GIG, null, initialValuesGigs);
            initialValuesGigs.put(KEY_GIG_VENUE_ID, 4);
            initialValuesGigs.put(KEY_GIG_DATE, "12/25/16");
            initialValuesGigs.put(KEY_GIG_TIME, "09:00PM");
            initialValuesGigs.put(KEY_GIG_PAY, 225);
            db.insert(DATABASE_TABLE_GIG, null, initialValuesGigs);
            initialValuesGigs.put(KEY_GIG_VENUE_ID, 5);
            initialValuesGigs.put(KEY_GIG_DATE, "05/06/16");
            initialValuesGigs.put(KEY_GIG_TIME, "09:00PM");
            initialValuesGigs.put(KEY_GIG_PAY, 175);
            db.insert(DATABASE_TABLE_GIG, null, initialValuesGigs);

            ContentValues initialValuesSetlists = new ContentValues();
            initialValuesSetlists.put(KEY_SETLIST_TITLE, "Mallrats first gig");
            initialValuesSetlists.put(KEY_SETLIST_GIG_ID, 1);
            initialValuesSetlists.put(KEY_SETLIST_NOTES, "This is going to be epic!!");
            db.insert(DATABASE_TABLE_SETLIST, null, initialValuesSetlists);
            initialValuesSetlists.put(KEY_SETLIST_TITLE, "Mallrats second gig");
            initialValuesSetlists.put(KEY_SETLIST_GIG_ID, 3);
            initialValuesSetlists.put(KEY_SETLIST_NOTES, "This is going to be more epic!!");
            db.insert(DATABASE_TABLE_SETLIST, null, initialValuesSetlists);

            //setlist song intitialization
            ContentValues initialValuesSetlistSongs = new ContentValues();
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SETLIST_ID, 1);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SONG_ID, 1);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_POSITION, 1);
            db.insert(DATABASE_TABLE_SETLISTSONGS, null, initialValuesSetlistSongs);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SETLIST_ID, 1);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SONG_ID, 2);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_POSITION, 2);
            db.insert(DATABASE_TABLE_SETLISTSONGS, null, initialValuesSetlistSongs);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SETLIST_ID, 1);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SONG_ID, 3);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_POSITION, 3);
            db.insert(DATABASE_TABLE_SETLISTSONGS, null, initialValuesSetlistSongs);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SETLIST_ID, 2);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SONG_ID, 5);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_POSITION, 1);
            db.insert(DATABASE_TABLE_SETLISTSONGS, null, initialValuesSetlistSongs);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SETLIST_ID, 2);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SONG_ID, 6);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_POSITION, 2);
            db.insert(DATABASE_TABLE_SETLISTSONGS, null, initialValuesSetlistSongs);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SETLIST_ID, 2);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SONG_ID, 7);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_POSITION, 3);
            db.insert(DATABASE_TABLE_SETLISTSONGS, null, initialValuesSetlistSongs);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SETLIST_ID, 2);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SONG_ID, 9);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_POSITION, 4);
            db.insert(DATABASE_TABLE_SETLISTSONGS, null, initialValuesSetlistSongs);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SETLIST_ID, 2);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SONG_ID, 10);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_POSITION, 5);
            db.insert(DATABASE_TABLE_SETLISTSONGS, null, initialValuesSetlistSongs);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SETLIST_ID, 2);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_SONG_ID, 11);
            initialValuesSetlistSongs.put(KEY_SETLISTSONGS_POSITION, 6);
            db.insert(DATABASE_TABLE_SETLISTSONGS, null, initialValuesSetlistSongs);

        }

    }
}

