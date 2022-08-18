package com.project.cookcellent;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.io.Serializable;
import java.util.Map;

public class recipeClass implements Serializable {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("link")
    @Expose
    public String link;
    @SerializedName("image")
    @Expose
    public Map<String,String> image;
    @SerializedName("rating")
    @Expose
    public Map<String,String> rating;
    @SerializedName("ingredients")
    @Expose
    public List<String> ingredients = null;
    @SerializedName("directions")
    @Expose
    public List<String> directions = null;

    public recipeClass withId(String id) {
        this.id = id;
        return this;
    }

    public recipeClass withName(String name) {
        this.name = name;
        return this;
    }

    public recipeClass withLink(String link) {
        this.link = link;
        return this;
    }



    public recipeClass withIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
        return this;
    }

    public recipeClass withDirections(List<String> directions) {
        this.directions = directions;
        return this;
    }

}
