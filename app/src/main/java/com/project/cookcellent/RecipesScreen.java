package com.project.cookcellent;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.executor.TaskExecutor;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class RecipesScreen extends AppCompatActivity {

    ArrayList<recipeClass> recipeDetails;
    RecyclerView recipeScreen;
    RecyclerView.LayoutManager  manager;
    recipesAdapter recipeAdap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipelist);
        recipeDetails = new ArrayList<>();
        recipeDetails.addAll((ArrayList<recipeClass>) getIntent().getExtras().get("recipes"));


        recipeScreen= findViewById(R.id.reScreen);


        recipeAdap = new recipesAdapter(recipeDetails, new recipesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(recipeClass item) {
                //Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse(item.link));
                Intent display = new Intent(RecipesScreen.this,DisplayRecipeActivity.class);
                display.putExtra("recipe",item);

                RecipesScreen.this.startActivity(display);
            }
        });
        recipeScreen.setAdapter(recipeAdap);
        manager = new LinearLayoutManager(this);
        recipeScreen.setLayoutManager(manager);


    }

}


