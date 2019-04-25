package com.example.dndcharactermanagment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecyclerViewAdaptor extends RecyclerView.Adapter<RecyclerViewAdaptor.ViewHolder>{
    private static final String TAG = "RecyclerViewAdaptor";

    private Class mClass;
    private ArrayList<String> mNames;
    private ArrayList<String> mProficiencies;
    private Context mContext;
    private Intent mNextIntent;
    private Character mCharacter;
    private DatabaseHelper db;



    public RecyclerViewAdaptor(Context mContext, ArrayList<String> mNames, ArrayList<String> mProficiencies, Intent mIntent, Character mCharacter) {
        this.mNames = mNames;
        this.mProficiencies = mProficiencies;
        this.mContext = mContext;
        this.mNextIntent = mIntent;
        this.mClass = this.mContext.getClass();
        this.mCharacter = mCharacter;
        this.db = new DatabaseHelper(mContext);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called.");

        viewHolder.name.setText(mNames.get(i));
        viewHolder.proficiencies.setText(mProficiencies.get(i));
        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mClass == ClassSelectionActivity.class){
                    Toast.makeText(mContext, "Clicked " + mNames.get(i), Toast.LENGTH_SHORT).show();
                    mCharacter.setClass_type(mNames.get(i));
                }
                else if(mClass == RaceSelectionActivity.class){
                    Log.d(TAG, "onClick: " + mClass.toString());
                    Toast.makeText(mContext, "Clicked " + mNames.get(i), Toast.LENGTH_SHORT).show();
                    mCharacter.setRace(mNames.get(i));
                    mCharacter.setAbility_scores(db.getRaceScores(mNames.get(i)));
                    Log.d(TAG, "onClick: LALALALA    " + mCharacter.getAbility_scores().toString());
                }
                mNextIntent.putExtra("character", mCharacter);
                mContext.startActivity(mNextIntent);
            }
        });

        viewHolder.parentLayout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(mContext, Pop.class);
                intent.putExtra("className", mNames.get(i));
                mContext.startActivity(intent);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return mNames.size();
    }

    protected class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView proficiencies;
        RelativeLayout parentLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            proficiencies = itemView.findViewById(R.id.proficiencies);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
