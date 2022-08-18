package com.project.cookcellent;

import android.content.Intent;
import android.graphics.drawable.Drawable;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class networkTools {
public static String con_str = "http://10.100.102.7:5000/";

    public static ArrayList<String> getIngredients(){
        try {
            ArrayList<String> ingredients = new ArrayList<>();
            URL url = new URL(con_str+"rawIngredients");
            URLConnection urlcon = url.openConnection();
            InputStream stream = urlcon.getInputStream();
            int i;
            String resp = "";
            while ((i = stream.read()) != -1) {
                resp += (char) i;
            }
            JSONObject jsnobject = new JSONObject(resp);
            JSONArray jsonArray = jsnobject.getJSONArray("Ingredients");
            for (int j=0;j<jsonArray.length();j++){

                //Adding each element of JSON array into ArrayList
                ingredients.add((String) jsonArray.get(j));
            }

            return ingredients;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return null;

        }
    }

    public static ArrayList<recipeClass> getRecipes(ArrayList<String> phrase){
        try {

            URL url = new URL(con_str+"search");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");

            con.setDoOutput(true);

            String jsonInputString = tools.Jasonify(phrase);
            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes("utf-8");
                os.write(input, 0, input.length);
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            Gson g = new Gson();
            recipeClass[] recipes = g.fromJson(response.toString(),recipeClass[].class);
            ArrayList<recipeClass> finite = new ArrayList<>();
            for(recipeClass recipe : recipes){
                finite.add(recipe);
            }
            return finite;
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    public static Drawable getImage(String url){
        final Drawable[] image = new Drawable[1];
        ExecutorService executor = Executors.newFixedThreadPool(2);
        executor.execute(new Runnable() {
            @Override
            public void run() {
                image[0] =networkTools.LoadImageFromWebOperations(url);
            }
        });
        tools.awaitTerm(executor);
        return image[0];

    }

}

