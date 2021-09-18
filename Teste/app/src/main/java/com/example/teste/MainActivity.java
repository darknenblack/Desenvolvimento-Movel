package com.example.teste;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> id = new ArrayList<>();
        ArrayList<String> nome = new ArrayList<>();

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

                        for (int i = 0; i < drinksArray.length(); i++) {
                            JSONObject drinksObject = drinksArray.getJSONObject(i);

                            id.add(drinksObject.getString("idDrink"));
                            nome.add(drinksObject.getString("strDrink"));
                        }

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
        },new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Rest Response Error", error.toString());

            }
                });

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, id, nome);
        recyclerView.setAdapter(customAdapter);


        requestQueue.add(objectRequest);
    }

    public void GetRandomDrink(View view) throws IOException {
        Intent intent = new Intent(this, RandomDrink.class);
        startActivity(intent);
    }
}
