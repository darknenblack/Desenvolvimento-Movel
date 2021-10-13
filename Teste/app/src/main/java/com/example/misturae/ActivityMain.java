package com.example.misturae;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.misturae.adapters.AdapterCategory;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class ActivityMain extends AppCompatActivity {

    private String TAG = ActivityMain.class.getSimpleName();
    RecyclerView rvFirstCategory, rvSecondCategory, rvThirdCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);

        ArrayList<String> idFirstCategory = new ArrayList<>();
        ArrayList<String> imgFirstCategory = new ArrayList<>();
        ArrayList<String> titleFirstCategory = new ArrayList<>();
        ArrayList<String> subtitleFirstCategory = new ArrayList<>();

        ArrayList<String> idSecondCategory = new ArrayList<>();
        ArrayList<String> imgSecondCategory = new ArrayList<>();
        ArrayList<String> titleSecondCategory = new ArrayList<>();
        ArrayList<String> subtitleSecondCategory = new ArrayList<>();

        ArrayList<String> idThirdCategory = new ArrayList<>();
        ArrayList<String> imgThirdCategory = new ArrayList<>();
        ArrayList<String> titleThirdCategory = new ArrayList<>();
        ArrayList<String> subtitleThirdCategory = new ArrayList<>();


        //https://www.thecocktaildb.com/api/json/v2/9973533/popular.php
        String url = "https://www.thecocktaildb.com/api/json/v2/9973533/popular.php";
        String urlNonAlcohol = "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?a=Non_Alcoholic";

        //Initializing Recycler and another View's
        FloatingActionButton randomButton = findViewById(R.id.randomButton);

        rvFirstCategory = findViewById(R.id.recyclerView);
        rvSecondCategory = findViewById(R.id.recyclerView2);
        rvThirdCategory = findViewById(R.id.recyclerView3);

        LinearLayoutManager llManagerFirstCategory = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager llManagerSecondCategory = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager llManagerThirdCategory = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        rvFirstCategory.setLayoutManager(llManagerFirstCategory);
        rvSecondCategory.setLayoutManager(llManagerSecondCategory);
        rvThirdCategory.setLayoutManager(llManagerThirdCategory);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Rest Response", response.toString());
                if (response.toString() != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(response.toString());
                        JSONArray drinksArray = jsonObj.getJSONArray("drinks");

                        for (int i = 0; i < 5; i++) {
                            JSONObject drinksObject = drinksArray.getJSONObject(i);

                            idFirstCategory.add(drinksObject.getString("idDrink"));
                            imgFirstCategory.add(drinksObject.getString("strDrinkThumb"));
                            titleFirstCategory.add(drinksObject.getString("strDrink"));
                            subtitleFirstCategory.add(drinksObject.getString("strGlass") + ", " + drinksObject.getString("strAlcoholic"));
                        }

                        for (int i = 5; i < 10; i++) {
                            JSONObject drinksObject = drinksArray.getJSONObject(i);

                            idSecondCategory.add(drinksObject.getString("idDrink"));
                            imgSecondCategory.add(drinksObject.getString("strDrinkThumb"));
                            titleSecondCategory.add(drinksObject.getString("strDrink"));
                            subtitleSecondCategory.add(drinksObject.getString("strGlass") + ", " + drinksObject.getString("strAlcoholic"));
                        }

                        //Notifying adapters to update the data
                        rvFirstCategory.getAdapter().notifyDataSetChanged();
                        rvSecondCategory.getAdapter().notifyDataSetChanged();

                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response Error", error.toString());

            }
        });

        Random rand = new Random();

        RequestQueue requestQueueNonAlcohol = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest2 = new JsonObjectRequest(
                Request.Method.GET, urlNonAlcohol, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Rest Response", response.toString());
                if (response.toString() != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(response.toString());
                        JSONArray drinksArray = jsonObj.getJSONArray("drinks");

                        for (int i = 0; i < 5; i++) {
                            JSONObject drinksObject = drinksArray.getJSONObject(rand.nextInt((drinksArray.length() - 1) + 1) + 0);

                            idThirdCategory.add(drinksObject.getString("idDrink"));
                            imgThirdCategory.add(drinksObject.getString("strDrinkThumb"));
                            titleThirdCategory.add(drinksObject.getString("strDrink"));
                            subtitleThirdCategory.add("Non alcoholic");
                        }

                        rvThirdCategory.getAdapter().notifyDataSetChanged();

                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                    Toast.makeText(getApplicationContext(),
                            "Couldn't get json from server. Check LogCat for possible errors!",
                            Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response Error", error.toString());
            }
        });

        requestQueue.add(objectRequest);
        requestQueueNonAlcohol.add(objectRequest2);

        //Call the constructor of Adapters to send the reference and data to Adapter
        AdapterCategory adapterFirstCategory = new AdapterCategory(ActivityMain.this, idFirstCategory, imgFirstCategory, titleFirstCategory, subtitleFirstCategory);
        rvFirstCategory.setAdapter(adapterFirstCategory);

        AdapterCategory adapterSecondCategory = new AdapterCategory(ActivityMain.this, idSecondCategory, imgSecondCategory, titleSecondCategory, subtitleSecondCategory);
        rvSecondCategory.setAdapter(adapterSecondCategory);

        AdapterCategory adapterThirdCategory = new AdapterCategory(ActivityMain.this, idThirdCategory, imgThirdCategory, titleThirdCategory, subtitleThirdCategory);
        rvThirdCategory.setAdapter(adapterThirdCategory);

        randomButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RandomDrink.class);
                startActivity(intent);
            }

        });

        @Deprecated
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(0);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        //Do nothing
                        break;
                    case R.id.Favoritos:
                        Intent intent = new Intent(getApplicationContext(), ActivityFavorites.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        break;
                    default:
                        break;

                }
                return true;
            }
        });
    }
}
