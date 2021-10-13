package com.example.teste;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();
    RecyclerView recyclerView, recyclerView2, recyclerView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);

        ArrayList<String> id = new ArrayList<>();
        ArrayList<String> img = new ArrayList<>();
        ArrayList<String> title = new ArrayList<>();
        ArrayList<String> subtitle = new ArrayList<>();

        ArrayList<String> id2 = new ArrayList<>();
        ArrayList<String> img2 = new ArrayList<>();
        ArrayList<String> title2 = new ArrayList<>();
        ArrayList<String> subtitle2 = new ArrayList<>();

        ArrayList<String> id3 = new ArrayList<>();
        ArrayList<String> img3 = new ArrayList<>();
        ArrayList<String> title3 = new ArrayList<>();
        ArrayList<String> subtitle3 = new ArrayList<>();


        //https://www.thecocktaildb.com/api/json/v2/9973533/popular.php
        String url = "https://www.thecocktaildb.com/api/json/v2/9973533/popular.php";

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2);
        recyclerView3 = findViewById(R.id.recyclerView3);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView2.setLayoutManager(linearLayoutManager2);
        recyclerView3.setLayoutManager(linearLayoutManager3);

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

                            id.add(drinksObject.getString("idDrink"));
                            img.add(drinksObject.getString("strDrinkThumb"));
                            title.add(drinksObject.getString("strDrink"));
                            subtitle.add(drinksObject.getString("strGlass") + ", " + drinksObject.getString("strAlcoholic"));
                        }

                        for (int i = 5; i < 10; i++) {
                            JSONObject drinksObject = drinksArray.getJSONObject(i);

                            id2.add(drinksObject.getString("idDrink"));
                            img2.add(drinksObject.getString("strDrinkThumb"));
                            title2.add(drinksObject.getString("strDrink"));
                            subtitle2.add(drinksObject.getString("strGlass") + ", " + drinksObject.getString("strAlcoholic"));
                        }

                        recyclerView.getAdapter().notifyDataSetChanged();
                        recyclerView2.getAdapter().notifyDataSetChanged();

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


        String url2 = "https://www.thecocktaildb.com/api/json/v2/9973533/filter.php?a=Non_Alcoholic";
        Random rand = new Random();

        RequestQueue requestQueue2 = Volley.newRequestQueue(this);
        JsonObjectRequest objectRequest2 = new JsonObjectRequest(
                Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("Rest Response", response.toString());
                if (response.toString() != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(response.toString());
                        JSONArray drinksArray = jsonObj.getJSONArray("drinks");

                        for (int i = 0; i < 5; i++) {
                            JSONObject drinksObject = drinksArray.getJSONObject(rand.nextInt((drinksArray.length() - 1) + 1) + 0);
                            //drinksArray.length()

                            id3.add(drinksObject.getString("idDrink"));
                            img3.add(drinksObject.getString("strDrinkThumb"));
                            title3.add(drinksObject.getString("strDrink"));
                            subtitle3.add("Non alcoholic");
                        }

                        recyclerView3.getAdapter().notifyDataSetChanged();

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
        requestQueue2.add(objectRequest2);

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, id, img, title, subtitle);
        recyclerView.setAdapter(customAdapter);

        CustomAdapter customAdapter2 = new CustomAdapter(MainActivity.this, id2, img2, title2, subtitle2);
        recyclerView2.setAdapter(customAdapter2);

        CustomAdapter customAdapter3 = new CustomAdapter(MainActivity.this, id3, img3, title3, subtitle3);
        recyclerView3.setAdapter(customAdapter3);



        FloatingActionButton randomButton = findViewById(R.id.randomButton);

        randomButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), RandomDrink.class);
                startActivity(intent3);
            }

        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.Favoritos:
                        Intent Favoritosbottom = new Intent(getApplicationContext(), ScreenFavoritos.class);
                        startActivity(Favoritosbottom);
                        break;
                    default:
                        break;

                }
                return true;
            }
        });


    }
}
