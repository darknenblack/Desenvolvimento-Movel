package com.example.misturae;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.misturae.adapters.AdapterBD;
import com.example.misturae.adapters.AdapterBookmark;
import com.example.misturae.data.User;
import com.example.misturae.data.UserDatabase;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityFavorites extends AppCompatActivity {

    private String TAG = ActivityMain.class.getSimpleName();
    ArrayList<Integer> favoritos = new ArrayList<Integer>();
    private AdapterBD userListAdapter;
    List<User> userList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_favoritos);
        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);


        ArrayList<String> id = new ArrayList<>();
        ArrayList<String> nome = new ArrayList<>();
        ArrayList<String> img = new ArrayList<>();
        ArrayList<String> subtitle = new ArrayList<>();
        Integer i;

        //Recycler
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        AdapterBookmark adapterbookmark = new AdapterBookmark(ActivityFavorites.this, id, nome, img, subtitle);
        recyclerView.setAdapter(adapterbookmark);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        RecyclerView recyclerView2 = findViewById(R.id.recyclerView5);
        userListAdapter = new AdapterBD(this);
        recyclerView2.setAdapter(userListAdapter);


        loadUserList();

        for(i = 0; i < TamanhoBanco(); i++){
            Log.e("IDS NO BANCO", userList.get(i).ID.toString());
            favoritos.add(Integer.parseInt(userList.get(i).ID));
        }


        //FOR AQUI, percorrendo o vetor com os IDS salvos
        for(i = 0; i < favoritos.size(); i++) {
            String url = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=" + favoritos.get(i);

            JsonObjectRequest objectRequest = new JsonObjectRequest(
                    Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.e("Rest Response", response.toString());
                    if (response.toString() != null) {
                        try {
                            JSONObject jsonObj = new JSONObject(response.toString());
                            JSONArray drinksArray = jsonObj.getJSONArray("drinks");

                            JSONObject drinksObject = drinksArray.getJSONObject(0);

                            id.add(drinksObject.getString("idDrink"));
                            nome.add(drinksObject.getString("strDrink"));
                            img.add(drinksObject.getString("strDrinkThumb"));
                            subtitle.add(drinksObject.getString("strGlass") + ", " + drinksObject.getString("strAlcoholic"));
                            //Picasso.get().load(jobject.getString("strDrinkThumb")).into(imagemthumb);

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

            requestQueue.add(objectRequest);
        }


        FloatingActionButton randomButton = findViewById(R.id.randomButton);

        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(), RandomDrink.class);
                startActivity(intent3);
            }

        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);

        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);


        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        Intent intentHome = new Intent(getApplicationContext(), ActivityMain.class);
                        startActivity(intentHome);
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                        break;
                    case R.id.Favoritos:
                        //Do nothing
                        break;
                }
                return true;
            }
        });
    }


    private void loadUserList() {
        UserDatabase db = UserDatabase.getDbInstance(this.getApplicationContext());
        userList = db.userDao().getIDS();
        userListAdapter.setUserList(userList);
    }

    private Integer TamanhoBanco() {
        UserDatabase db = UserDatabase.getDbInstance(this.getApplicationContext());
        Integer usertamanho = db.userDao().getTamanho();
        return usertamanho;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 100) {
            loadUserList();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void saveNewUser(String IDs) {
        UserDatabase db  = UserDatabase.getDbInstance(this.getApplicationContext());
        User user = new User();
        user.ID = IDs;
        db.userDao().insertUsers(user);
    }

    private void removeUser(String IDs) {
        UserDatabase db  = UserDatabase.getDbInstance(this.getApplicationContext());
        User user = new User();
        user.ID = IDs;
        db.userDao().delete(user);
    }
}