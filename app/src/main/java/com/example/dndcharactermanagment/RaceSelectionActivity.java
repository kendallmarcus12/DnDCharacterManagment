package com.example.DnDCharacterManagment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

public class RaceSelectionActivity extends AppCompatActivity {

    private static final String TAG = "ClassSelection";
    private TextView titleText;

    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mAbilities = new ArrayList<>();
    private Intent mIntent;
    private Character mCharacter = new Character();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_selection);
        titleText = findViewById(R.id.tv_title);
        titleText.setText(titleText.getText() + "Race");
        Log.d(TAG, "onCreate: started.");
        mIntent = new Intent(this, ClassSelectionActivity.class);

        try {
            fillNames();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void fillNames() throws JSONException {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());

        mNames = db.parseNames("races");
        mAbilities = db.parseAbilities("races");
        for(String var : mAbilities){
            Log.d(TAG, "fillNames: " + var);
        }
        initRecyclerView();

    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: Started.");
        RecyclerView recyclerView = findViewById(R.id.rv_classes);
        RecyclerViewAdaptor recyclerViewAdaptor = new RecyclerViewAdaptor(this, mNames, mAbilities, mIntent, mCharacter);
        recyclerView.setAdapter(recyclerViewAdaptor);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}
