package com.project.cookcellent;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DisplayHistory extends AppCompatActivity {


    RecyclerView historyScreen;
    RecyclerView.LayoutManager  manager;
    IngredientHistoryAdapter historyAdap;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchistory);
        ArrayList<ArrayList<String>> selected = (ArrayList<ArrayList<String>>)tools.getCache("searchHistory",this);
        historyScreen = findViewById(R.id.SearchList);
        historyAdap = new IngredientHistoryAdapter(selected, new IngredientHistoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ArrayList<String> item) {
                item.remove(item.size()-1);
                tools.saveCache("search",DisplayHistory.this,item);
                Intent inent = new Intent(DisplayHistory.this,SearchActivity.class);
                DisplayHistory.this.startActivity(inent);
            }
        });
        historyScreen.setAdapter(historyAdap);
        manager = new LinearLayoutManager(this);
        historyScreen.setLayoutManager(manager);




    }

}
