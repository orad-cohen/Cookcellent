package com.project.cookcellent;


import android.os.Bundle;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class SearchActivity  extends AppCompatActivity implements SearchView.OnQueryTextListener{
    SearchView bar;
    RecyclerView list;
    RecyclerView.LayoutManager  man;
    CustomAdapter adapt;
    ArrayList<String> animals= new ArrayList<String>();;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_recipe);
        Toast.makeText(this, "waosstt", Toast.LENGTH_LONG).show();
        bar = findViewById(R.id.search_window);
        bar.setOnQueryTextListener(this);
        bar.setIconifiedByDefault(false);

        String[] animalNameList = new String[]{"Lion", "Tiger", "Dog",
                "Cat", "Tortoise", "Rat", "Elephant", "Fox",
                "Cow","Donkey","Monkey"};
        toList(animalNameList);
        // Locate the ListView in listview_main.xml
        ArrayList<String> listdata = new ArrayList<String>();
        try{
            String res = new RetrieveIngredients().execute("stop").get();
            JSONObject jsnobject = new JSONObject(res);
            JSONArray jsonArray = jsnobject.getJSONArray("Ingredients");
            for (int i=0;i<jsonArray.length();i++){

                //Adding each element of JSON array into ArrayList
                listdata.add((String) jsonArray.get(i));
            }
        }
        catch(Exception e){

        }


        list = findViewById(R.id.rec);
        adapt = new CustomAdapter(listdata);
        list.setAdapter(adapt);
        man = new LinearLayoutManager(this);
        list.setLayoutManager(man);

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        bar.setQuery("",false);
        System.out.println(query);

        Toast.makeText(this, query, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        adapt.filter(newText);
        return false;
    }
    public void toList(String[] array) {
        if (array==null) {
            return;
        } else {
            int size = array.length;

            for(int i = 0; i < size; i++) {
                animals.add(array[i]);
            }

        }
    }
}
