package com.project.cookcellent;

import android.graphics.Paint;
import android.os.Bundle;


import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class DisplayRecipeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_activity);
        recipeClass toDisplay = (recipeClass) getIntent().getExtras().get("recipe");
        ImageView image = findViewById(R.id.displayFood);
        TextView recipeName = findViewById(R.id.displayName);
        ScrollView Ingredients = findViewById(R.id.displayIngredients);
        ScrollView Directions = findViewById(R.id.displayDirection);
        recipeName.setText(toDisplay.name);
        recipeName.setTextColor(getResources().getColor(R.color.blue,null));

        LinearLayout IngredientDisplay = new LinearLayout(getApplicationContext());
        IngredientDisplay.setOrientation(LinearLayout.VERTICAL);
        ExecutorService threads = Executors.newFixedThreadPool(2);
        threads.execute(()->image.setImageDrawable(networkTools.getImage(toDisplay.image.get("url"))));
        tools.awaitTerm(threads);
        TextView header = new TextView(this);

        header.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        header.setText(R.string.ingredients);
        header.setTextSize(18);
        header.setTextColor(getResources().getColor(R.color.blue,null));
        IngredientDisplay.addView(header);
        for(String ingredient : toDisplay.ingredients){
            TextView temp = new TextView(this);
            temp.setText(ingredient);
            temp.setPadding(0,10,0,10);
            temp.setTextColor(getResources().getColor(R.color.blue,null));
            IngredientDisplay.addView(temp);
        }
        Ingredients.addView(IngredientDisplay);
        LinearLayout DirectionDisplay = new LinearLayout(getApplicationContext());
        DirectionDisplay.setOrientation(LinearLayout.VERTICAL);
        TextView headers = new TextView(this);
        headers.setText(R.string.directions);
        DirectionDisplay.addView(headers);
        headers.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        headers.setTextColor(getResources().getColor(R.color.blue,null));
        headers.setTextSize(18);

        headers.setPaintFlags(header.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
        for(String direction : toDisplay.directions){
            TextView temp = new TextView(this);
            temp.setText(direction);
            temp.setPadding(0,10,0,10);
            temp.setTextColor(getResources().getColor(R.color.blue,null));
            DirectionDisplay.addView(temp);
        }
        Directions.addView(DirectionDisplay);

    }


}