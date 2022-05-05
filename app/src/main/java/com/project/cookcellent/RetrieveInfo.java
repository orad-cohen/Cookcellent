package com.project.cookcellent;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.*;
public class RetrieveInfo extends AsyncTask<String,String,String> {


    String ans = "";


    @Override
    protected String doInBackground(String... strings) {
        try {


            URL url = new URL("http://10.100.102.5:5000/hello?name=orad");
            URLConnection urlcon = url.openConnection();
            InputStream stream = urlcon.getInputStream();
            int i;
            String resp = "";
            while ((i = stream.read()) != -1) {
                resp += (char) i;
            }
            ans=resp;
            return resp;
        }
        catch (Exception e){
            System.out.println(e.toString());
            return "Error";

        }
    }
}
