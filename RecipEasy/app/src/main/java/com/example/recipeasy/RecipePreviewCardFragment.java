package com.example.recipeasy;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RecipePreviewCardFragment extends Fragment {

    private ImageView imageview_strMealThumb;
    private TextView textview_strMeal;
    private TextView textview_strIngredients;
    private RecipeCardViewHolder mViewModel;
    private static final String TAG = "RecipeCardFragment";

    public static RecipePreviewCardFragment newInstance() {
        return new RecipePreviewCardFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        return inflater.inflate(R.layout.fragment_recipe_card, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){

        //String idMeal = "52772";
        //String url_API = "https://www.themealdb.com/api/json/v1/1/lookup.php?i=";

        String random_url_API = "https://www.themealdb.com/api/json/v1/1/random.php";
        imageview_strMealThumb = getView().findViewById(R.id.imageview_strMealThumb);
        textview_strMeal = getView().findViewById(R.id.textview_strMeal);
        textview_strIngredients =getView().findViewById(R.id.textview_strIngredients);


        //Log.d(TAG,url_API+idMeal);
        Log.d(TAG,random_url_API);
        URL url = null;
        try {
            url = new URL(random_url_API);
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("accept", "application/json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for(int x =0;x<50;x++) {

            try {

                InputStream input = new BufferedInputStream(urlConnection.getInputStream());
                JsonReader reader = new JsonReader(new InputStreamReader(input, StandardCharsets.UTF_8));
                reader.beginObject();
                String name = reader.nextName();
                reader.beginArray();
                Meal temp_meal = new Meal();
                while(reader.hasNext()) {
                    reader.beginObject();

                    ArrayList<String> ingredient_name = new ArrayList<>();
                    ArrayList<String> ingredient_measure = new ArrayList<>();
                    while (reader.hasNext()) {

                        name = reader.nextName();
                        Log.d(TAG, name+" "+reader.peek().toString());
                        if (name.equals("idMeal")) {
                            temp_meal.setIdMeal(reader.nextString());
                        }
                        else if (name.equals("strMeal")) {
                            temp_meal.setStrMeal(reader.nextString());
                        }
                        else if (name.equals("strArea") && reader.peek() != JsonToken.NULL) {
                            temp_meal.setStrArea(reader.nextString());
                        }
                        else if (name.equals("strCategory") && reader.peek() != JsonToken.NULL) {
                            temp_meal.setStrCategory(reader.nextString());
                        }
                        else if (name.equals("strInstructions")&& reader.peek() != JsonToken.NULL) {
                            temp_meal.setStrInstructions(reader.nextString());
                        }
                        else if (name.equals("strMealThumb")&& reader.peek() != JsonToken.NULL) {
                            temp_meal.setStrMealThumb(reader.nextString());
                        }
                        else if (name.equals("strTags")&& reader.peek() != JsonToken.NULL) {
                            temp_meal.setStrTags(reader.nextString());
                        }
                        else if (name.equals("strYoutube")&& reader.peek() != JsonToken.NULL) {
                            temp_meal.setStrYoutube(reader.nextString());
                        }
                        else if (name.matches("strIngredient(.*)") && (reader.peek() != JsonToken.NULL)) {
                            ingredient_name.add(reader.nextString());
                        }
                        else if (name.matches("strMeasure(.*)") && (reader.peek() != JsonToken.NULL)) {
                            ingredient_measure.add(reader.nextString());
                        }
                        else{
                            reader.skipValue();
                        }
                    }
                    HashMap<String,String> ingredients = new LinkedHashMap<>();
                    ingredient_name.forEach((ingr_name)->{
                        //Log.d(TAG,ingr_name);
                        if(!ingr_name.isEmpty()){
                            ingredients.put(ingr_name,ingredient_measure.get(ingredient_name.indexOf(ingr_name)));
                        }
                    });

                    temp_meal.setStrIngredient(ingredients);
                    reader.endObject();
                }
                reader.endArray();
                reader.endObject();


                textview_strMeal.setText(temp_meal.getStrMeal());
                textview_strIngredients.setText(temp_meal.getStrIngredient_formatted());
                imageview_strMealThumb.setImageBitmap(getImageBitmap(temp_meal.getStrThumb()));

            }
            catch (IOException e) {
                throw new RuntimeException(e);
                //textview_my_recipes_fragment.setText("No recipe found");

            } finally {
                urlConnection.disconnect();
            }
        }
    }

    private Bitmap getImageBitmap(String url) {

        Bitmap bm = null;
        try {
            URL aURL = new URL(url);
            URLConnection conn = aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        }
        return bm;
    }

}