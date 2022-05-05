package com.project.cookcellent;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.HttpURLConnection;
import java.net.URLConnection;

public class RetrieveJson extends AsyncTask<String,String,String> {


    String ans = "";


    @Override
    protected String doInBackground(String... strings) {
        try {

            URL url = new URL("https://trackapi.nutritionix.com/v2/natural/nutrients");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("x-app-id","759de310");
            con.setRequestProperty("x-app-key","78f2efe0b3b925b64b1b6544a7be09c1");
            con.setDoOutput(true);
            String jsonInputString = "{\"query\" : \"¼ cup Louisiana-style hot sauce\"}";
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

            return response.toString();
        } catch (Exception ex) {

                return "Failure";
        }
    }}



