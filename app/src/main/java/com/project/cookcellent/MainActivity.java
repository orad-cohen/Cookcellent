package com.project.cookcellent;

import android.nfc.Tag;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    // declare attribute for textview
    private TextView pagenameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pagenameTextView = findViewById(R.id.pagename);

        try{
            String res = new RetrieveJson().execute("d").get();

            //String recipename=
            System.out.println(res.toString());
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
        // creating a client







        // building a request
        Request request = new Request.Builder().url("https://10.100.102.5:443/").build();

        // making call asynchronously

}}