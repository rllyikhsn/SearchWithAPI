package com.example.searchcermati;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mancj.materialsearchbar.MaterialSearchBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private MaterialSearchBar searchBar;
    private adapterSearch adapterSearch;
    private ArrayList<modelUsers> user = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        adapterSearch = new adapterSearch(inflater,getApplicationContext(),user);
        adapterSearch.setSuggestions(user);

        searchBar = findViewById(R.id.searchBar);
        searchBar.setHint("Search Github Users");
        searchBar.setSpeechMode(false);
        searchBar.setCustomSuggestionAdapter(adapterSearch);
        searchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("LOG_TAG", getClass().getSimpleName() + " text changed " + searchBar.getText());
                adapterSearch.getFilter().filter(searchBar.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {
                adapterSearch.getFilter().filter(searchBar.getText());
            }
        });

        getUsersApi();
    }

    private void getUsersApi() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://api.github.com/users?since=135", new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String username = jsonObject.getString("login");
                        String avatar = jsonObject.getString("avatar_url");
                        String url = jsonObject.getString("html_url");
                        user.add(new modelUsers(username,avatar,url));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley", error.toString());
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);
    }

}
