package com.project.cookcellent;


import static android.view.View.VISIBLE;

import android.content.Intent;
import android.os.Bundle;
import java.util.Calendar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class SearchActivity  extends AppCompatActivity implements SearchView.OnQueryTextListener{
    SearchView bar;
    RecyclerView autoComplete;
    RecyclerView.LayoutManager  man;
    RecyclerView ingredients;
    RecyclerView.LayoutManager  ingredientsManger;
    IngredientAdapter ingAdapter;
    AutocompleteAdaptar autoAdapter;
    ArrayList<String> listdata;
    ProgressBar load;
    ExecutorService threads;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_recipe);
        bar = findViewById(R.id.search_window);
        bar.setOnQueryTextListener(this);

        autoComplete = findViewById(R.id.display);
        Button search = findViewById(R.id.searchB);

        threads = Executors.newFixedThreadPool(4);
        threads.execute(()->listdata = networkTools.getIngredients());
        AtomicReference<ArrayList<String>> selectedList = new AtomicReference<>(new ArrayList<String>());
        threads.execute(()-> selectedList.set((ArrayList<String>) tools.getCache("search", this)));


        tools.awaitTerm(threads);
        if(selectedList.get()==null){
            selectedList.set(new ArrayList<>());
        }
        autoAdapter = new AutocompleteAdaptar(listdata, item -> {
            bar.setQuery("",false);
            ingAdapter.add(item);
        });
        autoComplete.setAdapter(autoAdapter);
        man = new LinearLayoutManager(this);
        autoComplete.setLayoutManager(man);


        ingredients = findViewById(R.id.rec);

        ingAdapter = new IngredientAdapter(selectedList.get(), item -> ingAdapter.remove(item));

        ingredients.setAdapter(ingAdapter);
        ingredientsManger = new LinearLayoutManager(this);
        ingredients.setLayoutManager(ingredientsManger);
        findViewById(R.id.searchHistory).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SearchActivity.this,DisplayHistory.class);
                SearchActivity.this.startActivity(intent);

            }
        });

        search.setOnClickListener(v -> {


            ArrayList < String > selections = (ArrayList<String>) ingAdapter.getSelection();
            tools.saveCache("search",this,selections);
            AtomicReference<ArrayList<recipeClass>> recipes = new AtomicReference<>(new ArrayList<>());
            ExecutorService re = Executors.newFixedThreadPool(2);
            re.execute(()-> recipes.set(networkTools.getRecipes(selections)));

            tools.awaitTerm(re);
            ArrayList<ArrayList<String>> previousSearches =(ArrayList<ArrayList<String>> ) tools.getCache("searchHistory",this);
            if(previousSearches==null){
                previousSearches = new ArrayList<>();
            }
            previousSearches.add(selectedList.get());
            previousSearches.get(previousSearches.size()-1).add(Calendar.getInstance().getTime().toString());
            tools.saveCache("searchHistory",this,previousSearches);
            Intent recipeScreen = new Intent(SearchActivity.this,RecipesScreen.class);
            recipeScreen.putExtra("recipes",recipes.get());

            SearchActivity.this.startActivity(recipeScreen);
        });


    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        bar.setQuery("",false);
        ingAdapter.add(query);


        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Toast.makeText(this, "whats", Toast.LENGTH_SHORT).show();
        autoAdapter.filter(newText);
        return false;
    }




}
