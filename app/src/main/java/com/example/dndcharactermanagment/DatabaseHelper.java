package com.example.DnDCharacterManagment;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHelper";
    //The Android's default system path of your application database.
    private static String DB_PATH = "/data/data/com.example.learningapp/databases/";

    private static String DB_NAME = "DnDDb.SQLITE";

    private SQLiteDatabase myDataBase;

    private final Context mContext;
    private static Abilities[] abilities = Abilities.values();

    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.mContext = context;
    }

    /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();
        Log.d(TAG, "createDataBase: " + dbExist);

        if(dbExist){
            //do nothing - database already exist
        }else{

            //By calling this method and empty database will be created into the default system path
            //of your application so we are gonna be able to overwrite that database with our database.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {

                throw new Error("Error copying database");

            }
        }

    }

    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase() {
        File databasePath = mContext.getDatabasePath(DB_NAME);
        return databasePath.exists();
    }

    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private void copyDataBase() throws IOException{

        //Open your local db as the input stream
        InputStream myInput = mContext.getAssets().open(DB_NAME);

        // Path to the just created empty db
        String outFileName = DB_PATH + DB_NAME;

        //Open the empty db as the output stream
        OutputStream myOutput = new FileOutputStream(outFileName);

        //transfer bytes from the inputfile to the outputfile
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

        //Close the streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void openDataBase() throws SQLException {

        //Open the database
        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {

        if(myDataBase != null)
            myDataBase.close();

        super.close();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.

    public ArrayList<String> parseNames(String table) {
        ArrayList<String> labels = new ArrayList<>();
        Log.d("LABELS", "getAllLabels: running labels");
        // Select All Query
        String selectQuery = "SELECT name FROM " + table;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        Log.d("LABELS", "getAllLabels: finished labels");
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public ArrayList<String> getSavingThrows() throws JSONException {
        // Select All Query
        ArrayList<String> result = new ArrayList<>();

        String selectQuery = "SELECT saving_throws FROM classes";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                JSONArray jsonArray = new JSONArray(cursor.getString(0));
                Log.d(TAG, "getSavingThrows: " + jsonArray.length());
                String tmp = new String();
                for(int i = 0; i < jsonArray.length(); i++) {
                    tmp += jsonArray.getJSONObject(i).getString("name") + " +1 ";
                }
                result.add(tmp.trim());
            }while(cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return result;
    }

    public ArrayList<String> parseAbilities(String table) throws JSONException {
        // Select All Query
        ArrayList<String> result = new ArrayList<>();

        String selectQuery = "SELECT ability_bonuses FROM " + table;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                JSONArray jsonArray = new JSONArray(cursor.getString(0));
                Log.d(TAG, "getSavingThrows: " + jsonArray.length());
                String tmp = new String();
                for(int i = 0; i < jsonArray.length(); i++) {
                    if(jsonArray.getInt(i) != 0) {
                        tmp += abilities[i].name() + " " + jsonArray.getInt(i) + " ";
                    }
                }
                result.add(tmp.trim());
            }while(cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return result;
    }

    public String getMultiLineString(){
        return "Filler Content";
    }

    public List<String> getAllLabels(String table) {
        ArrayList<String> labels = new ArrayList<String>();
        Log.d("LABELS", "getAllLabels: running labels");
        // Select All Query
        String selectQuery = "SELECT name FROM " + table;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        Log.d("LABELS", "getAllLabels: finished labels");
        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }
}