package com.example.dndcharactermanagment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Array;
import java.util.ArrayList;

public class AttributeRecyclerAdaptor extends RecyclerView.Adapter<AttributeRecyclerAdaptor.ViewHolder> {
    private static final String TAG = "AttributeRecyclerAdapto";
    private ArrayList<String> mNames;
    private ArrayList<Integer> mAbility_Scores;
    private Context mContext;
    private Character mCharacter;

    public AttributeRecyclerAdaptor(Context mContext, ArrayList<String> mNames, Character mCharacter) {
        super();
        this.mNames = mNames;
        this.mContext = mContext;
        this.mCharacter = mCharacter;
        this.mAbility_Scores = mCharacter.getAbility_scores();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_ability_score, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AttributeRecyclerAdaptor.ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        viewHolder.name.setText(mNames.get(i));
        viewHolder.ability_count.setText(String.format("%d", mAbility_Scores.get(i) + 10));
        viewHolder.plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curr = viewHolder.ability_count.getText().toString();
                int currAsInt = Integer.parseInt(curr);
                viewHolder.ability_count.setText(String.format("%d", currAsInt++));
            }
        });
        viewHolder.minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String curr = viewHolder.ability_count.getText().toString();
                int currAsInt = Integer.parseInt(curr);
                viewHolder.ability_count.setText(String.format("%d", currAsInt--));
                Log.d(TAG, "onClick: new num:" + currAsInt);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView ability_count;
        RelativeLayout parentLayout;
        Button plusButton;
        Button minusButton;

        protected ViewHolder(@NonNull View itemView) {
            super(itemView);
            plusButton = itemView.findViewById(R.id.btn_plus);
            minusButton = itemView.findViewById(R.id.btn_minus);
            name = itemView.findViewById(R.id.tv_name);
            ability_count = itemView.findViewById(R.id.tv_ability_count);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}

