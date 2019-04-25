package com.example.dndcharactermanagment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

public class finalize_character extends AppCompatActivity {
    private static final String TAG = "finalize_character";
    private final FragmentManager fm = getSupportFragmentManager();
    private final Fragment charFrag = new CharacterFragment();
    private final Fragment spellFrag = new SpellsFragment();
    private final Fragment attrFrag = new AttributesFragment();
    Bundle bundle = new Bundle();
    private Fragment active = charFrag;



    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch(menuItem.getItemId()){
                        case R.id.nav_char:
                            fm.beginTransaction().hide(active).show(charFrag).commit();
                            active = charFrag;
                            return true;

                        case R.id.nav_spells:
                            fm.beginTransaction().hide(active).show(spellFrag).commit();
                            active = spellFrag;
                            return true;

                        case R.id.nav_attri:
                            fm.beginTransaction().hide(active).show(attrFrag).commit();
                            active = attrFrag;
                            return true;
                    }
                    return false;
                }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finalize_character);
        bundle.putParcelable("character", getIntent().getParcelableExtra("character"));
        charFrag.setArguments(bundle);
        spellFrag.setArguments(bundle);
        attrFrag.setArguments(bundle);
        fm.beginTransaction().add(R.id.fragment_container, attrFrag, "3").hide(attrFrag).commit();
        fm.beginTransaction().add(R.id.fragment_container, spellFrag, "2").hide(spellFrag).commit();
        fm.beginTransaction().add(R.id.fragment_container, charFrag, "1").commit();


        BottomNavigationView navigation = findViewById(R.id.bottom_navigation);
        navigation.setOnNavigationItemSelectedListener(navListener);

//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                new CharacterFragment()).commit();
    }

}
