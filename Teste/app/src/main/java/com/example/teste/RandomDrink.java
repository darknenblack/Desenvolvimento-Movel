package com.example.teste;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

import java.util.HashMap;
import java.util.Map;

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

        TextView ing1 = (TextView) findViewById(R.id.ing1);
        TextView ing2 = (TextView) findViewById(R.id.ing2);

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

                                if (jobject.getString("strIngredient1") != null) {
                                    ing1.setVisibility(View.VISIBLE);
                                    ing1.setText(jobject.getString("strIngredient1") + ": " + jobject.getString("strMeasure1"));
                                }
                                if (jobject.getString("strIngredient2") != null) {
                                    ing2.setVisibility(View.VISIBLE);
                                    ing2.setText(jobject.getString("strIngredient2") + ": " + jobject.getString("strMeasure2"));
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

        requestQueue.add(objectRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.drink_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.action_favorite:
                Toast.makeText(getApplicationContext(), "Setting favorite", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action_share:
                Toast.makeText(getApplicationContext(), "Search share", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
