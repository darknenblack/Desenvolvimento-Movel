package com.example.teste;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.lang.reflect.Array;
import java.util.*;

import java.io.IOException;
import java.io.InputStream;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.teste.HandleRequest;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetFeed();
    }

    private void GetFeed() {
        // ArrayList for person names, email Id's and mobile numbers
        ArrayList<String> id = new ArrayList<>();
        ArrayList<String> nome = new ArrayList<>();

        String url = "https://www.thecocktaildb.com/api/json/v1/1/random.php";

        // get the reference of RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        // set a LinearLayoutManager with default vertical orientation
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("Rest Response", response.toString());
                        if (response.toString() != null) {
                            try {

                                JSONObject jsonObj = new JSONObject(response.toString());
                                JSONArray jarray = jsonObj.getJSONArray("drinks");

                                JSONObject jobject = jarray.getJSONObject(0);

                                String jid = jobject.getString("idDrink");
                                String jnome = jobject.getString("strDrink");

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
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());

                    }
                });

        requestQueue.add(objectRequest);

        //  call the constructor of CustomAdapter to send the reference and data to Adapter
        CustomAdapter customAdapter = new CustomAdapter(MainActivity.this, id, nome);
        recyclerView.setAdapter(customAdapter); // set the Adapter to RecyclerView
    }

    public void submitRequest(View view) throws IOException {
        GetRandomDrink();
    }


    private void GetRandomDrink() {

        TextView id = (TextView) findViewById(R.id.id);
        TextView nome = (TextView) findViewById(R.id.nome);

        ArrayList<String> response = new ArrayList<String>();

        // Making a request to url and getting response
        String url = "https://www.thecocktaildb.com/api/json/v1/1/random.php";

        HandleRequest request = new HandleRequest();
        response = request.MakeRequest(url, this);

        id.setText("Request" + response);
        //id.setText(response.get(0));
        //nome.setText(response.get(1));
    }
}

