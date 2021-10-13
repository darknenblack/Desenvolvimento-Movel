package com.example.misturae;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RandomDrink extends AppCompatActivity {
    private Toolbar toolbar;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_drink);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        //TextView idDrink = (TextView) findViewById(R.id.id);
        TextView nomeDrink = (TextView) findViewById(R.id.nome);

        TextView Glasstype = (TextView) findViewById(R.id.glasstype);
        ImageView imagemthumb = (ImageView) findViewById(R.id.imagethumbactivity);

        //TextView instrucao = (TextView) findViewById(R.id.inst);


        ListView lista = (ListView) findViewById(R.id.lista);
        ArrayList<String> ingr = new ArrayList<>();

        TextView inst = (TextView) findViewById(R.id.inst);

        String url = "https://www.thecocktaildb.com/api/json/v1/1/random.php";

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

                                toolbar.setTitle(jobject.getString("strCategory"));

                                Picasso.get().load(jobject.getString("strDrinkThumb")).into(imagemthumb);

                                nomeDrink.setText(jobject.getString("strDrink"));
                                Glasstype.setText(jobject.getString("strGlass") + ", " + jobject.getString("strAlcoholic"));
                                inst.setText(jobject.getString("strInstructions"));

                                for(int i=1; i<=15; i++){
                                    synchronized(lista) {
                                        if (jobject.getString("strIngredient"+i) != null) {
                                                ingr.add(jobject.getString("strMeasure" + i) + ": " + jobject.getString("strIngredient" + i));
                                                Log.e("ingredientes:", jobject.getString("strMeasure" + i) + ": " + jobject.getString("strIngredient" + i));
                                                lista.notifyAll();
                                        }
                                    }
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
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Rest Response", error.toString());

                    }
                });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(RandomDrink.this, android.R.layout.simple_list_item_1, ingr);
        lista.setAdapter(adapter);

        requestQueue.add(objectRequest);
    }


}
