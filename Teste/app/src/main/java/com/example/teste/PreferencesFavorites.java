package com.example.teste;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;

public class PreferencesFavorites {

    private static final String SHARED_PREF_SETTINGS = "prefSettings";

    @SuppressLint("StaticFieldLeak")
    private static PreferencesFavorites preferencesSettings;
    private final Context context;

    private PreferencesFavorites(Context context) {
        this.context = context;
    }

    public static synchronized PreferencesFavorites getInstance(Context context) {
        if (preferencesSettings == null) {
            preferencesSettings = new PreferencesFavorites(context);
        }
        return preferencesSettings;
    }

    public void saveDrink(int id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //editor.putInt("drink_id_" + getPosition(), id);
    }

    private int getString() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_SETTINGS, Context.MODE_PRIVATE);

        return sharedPreferences.getInt("position", 0);
    }

    /*public void saveDrink(int id) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("drink_id_" + getPosition(), id);
        editor.putInt("position", getPosition() + 1);
    }



    private int getPosition() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_SETTINGS, Context.MODE_PRIVATE);

        return sharedPreferences.getInt("position", 0);
    }

    public ArrayList<Integer> getDrinks() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_SETTINGS, Context.MODE_PRIVATE);

        ArrayList<Integer> favorites = new ArrayList<>();

        for(int i = 0; i < getPosition(); i++) {
            favorites.add(sharedPreferences.getInt("drink_id_" + i, -1));
        }

        return favorites;
    }*/

    public void clear() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREF_SETTINGS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

}
