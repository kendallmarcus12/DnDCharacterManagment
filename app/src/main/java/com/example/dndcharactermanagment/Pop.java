package com.example.DnDCharacterManagment;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;

public class Pop extends Activity {
    private static final String TAG = "Pop";

    TextView name;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup);
        name = findViewById(R.id.tv_title);

        getIncomingIntent();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        Log.d(TAG, "onCreate: " + height + ":" + width);

        getWindow().setLayout((int)(width * .75), (int)(height * .75));
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("className")){
            String className = getIntent().getStringExtra("className");
            Log.d(TAG, "getIncomingIntent: " + className);
            name.setText(name.getText().toString() + className);
        }
    }
}
