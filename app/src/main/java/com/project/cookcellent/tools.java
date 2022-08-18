package com.project.cookcellent;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class tools {
    public static String Jasonify(ArrayList<String> ing){
        String result = "{\"Ingredients\":[";
        Iterator<String> it = ing.iterator();
        while(it.hasNext()){
            result+="\""+it.next()+"\"";
            if(it.hasNext()){
                result+=",";
            }
            else{
                result+="]}";
            }
        }

        return result;




    }
    public static void awaitTerm(ExecutorService executor){
        executor.shutdown();
        try{
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
        }
        catch (Exception e){
            System.out.println(e);
        }
        return;
    }
    public static Object saveCache(String path, AppCompatActivity acti, Object data){
        try{

            FileOutputStream cache = new FileOutputStream(acti.getCacheDir()+path);
            ObjectOutputStream object = new ObjectOutputStream(cache);
            object.writeObject(data);
            object.close();
            return data;
        }
        catch (Exception e){
            return null;
        }

    }
    public static Object getCache(String path, AppCompatActivity acti){
        try{

            FileInputStream file = new FileInputStream(acti.getCacheDir()+path);
            ObjectInputStream data = new ObjectInputStream(file);
            Object fromCache =  data.readObject();
            return fromCache;
        }
        catch (Exception e){
            return null;
        }
    }
}
