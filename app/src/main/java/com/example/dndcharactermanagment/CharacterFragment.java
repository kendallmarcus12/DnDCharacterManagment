package com.example.dndcharactermanagment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
public class CharacterFragment extends Fragment {
    private static final String TAG = "CharacterFragment";
    private Character mCharacter;
    private TextView tv_main;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mCharacter = getArguments().getParcelable("character");
        View view = inflater.inflate(R.layout.fragment_character, container, false);
        tv_main = view.findViewById(R.id.Character_Text);
        tv_main.setText(mCharacter.getClass_type() + ":" + mCharacter.getRace());
        return view;
    }

    public void setText(String newText){
        tv_main.setText(newText);
    }
}
