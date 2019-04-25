package com.example.dndcharactermanagment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class Ability_Scores extends AppCompatActivity {
    private ArrayList<String> mAttr = new ArrayList<>(Arrays.asList("Strength","Dexterity","Constitution","Intelligence","Wisdom","Charisma"));
    private Character mCharacter;
    private static final String TAG = "Ability_Scores";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ability__scores);
        Log.d(TAG, "onCreate: started.");

        if(getIntent().hasExtra("character")){
            mCharacter = getIntent().getParcelableExtra("character");
        }

        initRecyclerView();
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: Started.");
        RecyclerView recyclerView = findViewById(R.id.rv_ability_scores);
        AttributeRecyclerAdaptor recyclerViewAdaptor = new AttributeRecyclerAdaptor(this, mAttr, mCharacter);
        recyclerView.setAdapter(recyclerViewAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
