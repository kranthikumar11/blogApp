package com.example.minetrest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.minetrest.adapter.PostAdapter;
import com.example.minetrest.posts.Post;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    //https://www.googleapis.com/blogger/v3/blogs/8885356545111944225/posts?key=AIzaSyB3z5IKc7Xacs-ohabkIepQJBAOhgcF_t0
    private final String URL = "https://www.googleapis.com/blogger/v3/blogs/8885356545111944225/posts?key=AIzaSyB3z5IKc7Xacs-ohabkIepQJBAOhgcF_t0";
    private RecyclerView recyclerView;
    private PostAdapter postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    GsonBuilder gsonBuilder=new GsonBuilder();
                    Gson gson=gsonBuilder.create();
                    JSONArray jsonArray = response.getJSONArray("items");
                    Post[] posts=gson.fromJson(String.valueOf(jsonArray), Post[].class);

                    postAdapter = new PostAdapter(MainActivity.this, posts);
                    recyclerView.setAdapter(postAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Please turn on Mobile Data", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue rq= Volley.newRequestQueue(this);
        rq.add(jsonObjectRequest);

    }
}