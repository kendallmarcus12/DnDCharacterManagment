package com.example.DnDCharacterManagment;

import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    DatabaseHelper myDb;
    EditText editName, editSurname, editMarks;
    Button btnAddItem, btnDelete;
    Spinner classSpinner, raceSpinner, languageSpinner;
    TextView multiLine;
    FloatingActionButton addCharButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDb = new DatabaseHelper(this);

        editName = findViewById(R.id.editText_name);
        editSurname = findViewById(R.id.editText_surname);
        editMarks = findViewById(R.id.editText_marks);
        btnAddItem = findViewById(R.id.button_add);
        btnDelete = findViewById(R.id.button_delete);
        classSpinner = findViewById(R.id.classes);
        raceSpinner = findViewById(R.id.races);
        languageSpinner = findViewById(R.id.languages);
        multiLine = findViewById(R.id.dbChecker);

        ArrayList<Spinner> spinnerArrayList = new ArrayList<Spinner>(){
            {
                add(classSpinner);
                add(raceSpinner);
                add(languageSpinner);
            }
        };

        try {
            myDb.createDataBase();
        } catch (IOException ioe) {
            throw new Error("Unable to create database");
        }

        try {
            myDb.openDataBase();
        }catch(SQLException sqle){
            throw sqle;
        }

        // Loading spinner data from database
        for(Spinner spinner : spinnerArrayList) {
            loadSpinnerData(spinner.getTag().toString(), spinner);
            spinner.setOnItemSelectedListener(this);
        }

        multiLine.setText(myDb.getMultiLineString());

        myDb.close();
        addData();
        deleteData();

        addCharacter();
    }

    public void addData(){
        btnAddItem.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //boolean result = myDb.insertData(editName.getText().toString(), editSurname.getText().toString(), editMarks.getText().toString());
                        //if(result){
                        Toast.makeText(MainActivity.this, "Data Inserted", Toast.LENGTH_LONG).show();
                            //myDb.getTableAsString(myDb.getWritableDatabase(), "student_Table");
                        //}
                        //else
                          //  Toast.makeText(MainActivity.this, "Data NOT Inserted", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    public void deleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //myDb.deleteData("student_Table");
                        Toast.makeText(MainActivity.this, "Table has been cleared of all Data", Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loadSpinnerData(String table, Spinner spinner) {
        // database handler
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        //TODO: Make a for each loop with all spinners to populate at beginning
        //for(Spinner spin: spinArr)

        // Spinner Drop down elements
        List<String> labels = db.getAllLabels(table);

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, labels);

        // Drop down layout style - list view with radio button
        dataAdapter
                .setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        // On selecting a spinner item
        String label = parent.getItemAtPosition(position).toString();

        // Showing selected spinner item
        Toast.makeText(parent.getContext(), "You selected: " + label,
                Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub

    }

    private void addCharacter(){
        addCharButton = findViewById(R.id.addCharacter);
        addCharButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RaceSelectionActivity.class));
            }
        });
    }

}
