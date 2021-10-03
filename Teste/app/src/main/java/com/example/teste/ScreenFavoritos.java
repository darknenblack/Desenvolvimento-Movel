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
import androidx.appcompat.widget.Toolbar;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ScreenFavoritos extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_favoritos);
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbarbookmark);
        setSupportActionBar(myToolbar);

        ArrayList<String> id = new ArrayList<>();
        ArrayList<String> nome = new ArrayList<>();
        ArrayList<String> img = new ArrayList<>();

        String url = "https://www.thecocktaildb.com/api/json/v2/9973533/randomselection.php";

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

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


                        //AQUI coloquei 2 (so pra diferenciar inicialmente), drinksArray.length()
                        for (int i = 0; i < 2; i++) {
                            JSONObject drinksObject = drinksArray.getJSONObject(i);

                            id.add(drinksObject.getString("idDrink"));
                            nome.add(drinksObject.getString("strDrink"));
                            img.add(drinksObject.getString("strDrinkThumb") + "/preview");
                            //Picasso.get().load(jobject.getString("strDrinkThumb")).into(imagemthumb);

                        }

                        recyclerView.getAdapter().notifyDataSetChanged();

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


        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        AdapterBookmark adapterbookmark = new AdapterBookmark(ScreenFavoritos.this, id, nome,img);
        recyclerView.setAdapter(adapterbookmark);

        requestQueue.add(objectRequest);

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
                        Toast.makeText(ScreenFavoritos.this, "Home", Toast.LENGTH_SHORT).show();
                        Intent intentHome = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intentHome);
                        break;
                    case R.id.Favoritos:
                        Toast.makeText(ScreenFavoritos.this, "Favoritos", Toast.LENGTH_SHORT).show();
                        Intent Favoritosbottom = new Intent(getApplicationContext(), ScreenFavoritos.class);
                        startActivity(Favoritosbottom);
                        break;
                }
                return true;
            }
        });


    }

}