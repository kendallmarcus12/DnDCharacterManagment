package com.example.learningapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class ClassSelectionActivity extends AppCompatActivity {
    TextView titleText;

    private static final String TAG = "ClassSelection";

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mProficencies = new ArrayList<>();
    private Intent mIntent;
    private Character mCharacter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_selection);
        titleText = findViewById(R.id.tv_title);
        titleText.setText(titleText.getText() + "Class");
        mIntent = new Intent(this, MainActivity.class);
        Log.d(TAG, "onCreate: started.");

        if(getIntent().hasExtra("character")){
            mCharacter = getIntent().getParcelableExtra("character");
        }

        try {
            fillNames();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fillNames() throws JSONException {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        mNames = db.parseNames("classes");
        mProficencies = db.getSavingThrows();
        for(String var : mProficencies){
            Log.d(TAG, "fillNames: " + var);
        }
        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: Started.");
        RecyclerView recyclerView = findViewById(R.id.rv_classes);
        RecyclerViewAdaptor recyclerViewAdaptor = new RecyclerViewAdaptor(this, mNames, mProficencies, mIntent, mCharacter);
        recyclerView.setAdapter(recyclerViewAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}

