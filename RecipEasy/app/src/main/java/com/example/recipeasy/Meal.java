package com.example.recipeasy;

import android.os.Parcelable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Meal implements Serializable {
    private static final long serialVersionUID = 42L;
    private String idMeal;
    private String strMeal;
    private String strCategory;
    private String strArea;
    private String strInstructions;
    private String strMealThumb;
    private String strTags;
    private String strYoutube;
    private HashMap<String,String> strIngredient;

    public Meal() {
        this.idMeal = "ID";
        this.strMeal = "Name";
        this.strCategory = "Category";
        this.strArea = "Area";
        this.strInstructions = "Instructions";
        this.strMealThumb = "Thumb";
        this.strTags = "Tags";
        this.strYoutube = "Youtube";
        this.strIngredient = new LinkedHashMap<>();
    }
    public Meal(String idMeal, String strMeal, String strCategory, String strArea, String strInstructions, String strMealThumb, String strTags, String strYoutube, HashMap<String, String> strIngredient) {
        this.idMeal = idMeal;
        this.strMeal = strMeal;
        this.strCategory = strCategory;
        this.strArea = strArea;
        this.strInstructions = strInstructions;
        this.strMealThumb = strMealThumb;
        this.strTags = strTags;
        this.strYoutube = strYoutube;
        this.strIngredient = strIngredient;
    }

    public Meal(Meal tempMeal) {
        this.idMeal = tempMeal.idMeal;
        this.strMeal = tempMeal.strMeal;
        this.strCategory = tempMeal.strCategory;
        this.strArea = tempMeal.strArea;
        this.strInstructions = tempMeal.strInstructions;
        this.strMealThumb = tempMeal.strMealThumb;
        this.strTags = tempMeal.strTags;
        this.strYoutube = tempMeal.strYoutube;
        this.strIngredient = tempMeal.strIngredient;
    }

    @Override
    public String toString() {
        return "Meal{" +
                "idMeal='" + idMeal + '\'' +
                ", strMeal='" + strMeal + '\'' +
                ", strCategory='" + strCategory + '\'' +
                ", strArea='" + strArea + '\'' +
                ", strInstructions='" + strInstructions + '\'' +
                ", strThumb='" + strMealThumb + '\'' +
                ", strTags='" + strTags + '\'' +
                ", strYoutube='" + strYoutube + '\'' +
                ", strIngredient=" + strIngredient.toString() +
                '}';
    }

    public String getIdMeal() {
        return idMeal;
    }

    public void setIdMeal(String idMeal) {
        this.idMeal = idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public void setStrMeal(String strMeal) {
        this.strMeal = strMeal;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public void setStrCategory(String strCategory) {
        this.strCategory = strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public void setStrArea(String strArea) {
        this.strArea = strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public void setStrInstructions(String strInstructions) {
        this.strInstructions = strInstructions;
    }

    public String getStrThumb() {
        return strMealThumb;
    }

    public void setStrMealThumb(String strThumb) {
        this.strMealThumb = strThumb;
    }

    public String getStrTags() {
        return strTags;
    }

    public void setStrTags(String strTags) {
        this.strTags = strTags;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public void setStrYoutube(String strYoutube) {
        this.strYoutube = strYoutube;
    }

    public HashMap<String, String> getStrIngredient() {
        return strIngredient;
    }
    public String getStrIngredient_formatted() {
        String ingredient_string ="";
        for(String key:strIngredient.keySet()){
            ingredient_string += strIngredient.get(key) +" "+ key +"\n";
        }
        return ingredient_string;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Meal meal = (Meal) o;
        return Objects.equals(idMeal, meal.idMeal) && Objects.equals(strMeal, meal.strMeal) && Objects.equals(strCategory, meal.strCategory) && Objects.equals(strArea, meal.strArea) && Objects.equals(strInstructions, meal.strInstructions) && Objects.equals(strMealThumb, meal.strMealThumb) && Objects.equals(strTags, meal.strTags) && Objects.equals(strYoutube, meal.strYoutube) && Objects.equals(strIngredient, meal.strIngredient);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idMeal, strMeal, strCategory, strArea, strInstructions, strMealThumb, strTags, strYoutube, strIngredient);
    }

    public void setStrIngredient(HashMap<String, String> strIngredient) {
        this.strIngredient = strIngredient;
    }
}
