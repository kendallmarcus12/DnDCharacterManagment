package com.example.DnDCharacterManagment;

import android.os.Parcel;
import android.os.Parcelable;

public class Character implements Parcelable {
    private String name;
    private int[] ability_scores;
    private int level;
    private String race;
    private String class_type;

    public Character(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getAbility_scores() {
        return ability_scores;
    }

    public void setAbility_scores(int[] ability_scores) {
        this.ability_scores = ability_scores;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getClass_type() {
        return class_type;
    }

    public void setClass_type(String class_type) {
        this.class_type = class_type;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeIntArray(ability_scores);
        dest.writeInt(level);
        dest.writeString(race);
        dest.writeString(class_type);
    }

    public static final Creator<Character> CREATOR = new Creator<Character>() {
        public Character createFromParcel(Parcel in) {
            return new Character(in);
        }

        public Character[] newArray(int size) {
            return new Character[size];
        }
    };

    // example constructor that takes a Parcel and gives you an object populated with it's values
    private Character(Parcel in) {
        name = in.readString();
        ability_scores = in.createIntArray();
        level = in.readInt();
        race = in.readString();
        class_type = in.readString();
    }
}
