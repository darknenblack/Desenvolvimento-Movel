package com.example.misturae;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
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
import com.example.misturae.data.User;
import com.example.misturae.data.UserDatabase;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class ActivityDrink extends AppCompatActivity {
    private Toolbar toolbar;
    String ID_Buff;

    TextView nomeDrink;
    TextView Glasstype;
    ImageView imagemthumb;

    ListView lista;
    ArrayList<String> ingr;

    TextView inst;

    RequestQueue requestQueue;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_drink);

        toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);


        nomeDrink = findViewById(R.id.nome2);
        Glasstype = findViewById(R.id.glasstype2);
        imagemthumb = findViewById(R.id.imagethumbactivity2);

        lista = (ListView) findViewById(R.id.lista2);
        ingr = new ArrayList<>();

        inst = (TextView) findViewById(R.id.inst2);

        ImageButton favButton = findViewById(R.id.isFavDrink);

        //
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        requestQueue = Volley.newRequestQueue(this);

        getData();
        setData(ID_Buff);


        favButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    view.setBackgroundResource(R.drawable.ic_baseline_favorite_24);
                    saveNewUser(ID_Buff);
                    Toast.makeText(getApplicationContext(), "Success =)", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    view.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24);
                    removeUser(ID_Buff);
                    Toast.makeText(getApplicationContext(), "Deleted =(", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void getData(){
        if(getIntent().hasExtra("desc2bookmark")){

            ID_Buff = getIntent().getStringExtra("desc2bookmark");

        }else{
            Toast.makeText(getApplicationContext(),
                    "DADOS NAO ENCONTRADOS",
                    Toast.LENGTH_LONG).show();
        }
    }

    private void setData(String ID){

        //Receber argumento aqui
        String url = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + ID;

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
                                        if (jobject.getString("strIngredient"+i) != "null" ){
                                            ingr.add(jobject.getString("strIngredient" + i) + ": " + jobject.getString("strMeasure" + i));
                                            Log.e("ingredientes:", jobject.getString("strIngredient" + i) + ": " + jobject.getString("strMeasure" + i));
                                            lista.notifyAll();
                                        }
                                    }
                                }

                                ArrayAdapter<String> adapter = new ArrayAdapter<String>(ActivityDrink.this, R.layout.list_item, ingr);
                                lista.setAdapter(adapter);


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


    private void saveNewUser(String IDs) {
        UserDatabase db  = UserDatabase.getDbInstance(getApplicationContext());
        User user = new User();
        user.ID = IDs;
        db.userDao().insertUsers(user);
    }

    private void removeUser(String IDs) {
        UserDatabase db  = UserDatabase.getDbInstance(getApplicationContext());
        User user = new User();
        user.ID = IDs;
        db.userDao().delete(user);
    }

}


