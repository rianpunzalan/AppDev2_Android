package com.example.recipeasy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.Executor;

public class RecipePageActivity extends AppCompatActivity implements Executor {
    String idMeal;
    Meal temp_meal;
    private static final String ID_MEAL = "idMeal";
    private static final String EXTRA_MEAL = "com.example.recipeasy.Meal";
    private static final String TAG = "RecipePageActivity";

    private String url_API;
    private ImageView imageview_strMealThumb;
    private TextView textview_strMeal;
    private TextView textview_strIngredients;
    private TextView textview_strInstructions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_page);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //temp_meal = (Meal) savedInstanceState.getSerializable(EXTRA_MEAL);
        Log.d(TAG, getIntent().getStringExtra(ID_MEAL));


        //Log.d(TAG, temp_meal.toString());
        if(getIntent().getStringExtra(ID_MEAL) != null) {
            idMeal = getIntent().getStringExtra(ID_MEAL);

            url_API = "https://www.themealdb.com/api/json/v1/1/lookup.php?i="+idMeal;


            imageview_strMealThumb = findViewById(R.id.imageview_strMealThumb);
            textview_strMeal = findViewById(R.id.textview_strMeal);
            textview_strIngredients =findViewById(R.id.textview_strIngredients);
            textview_strInstructions =findViewById(R.id.textview_strInstructions);
            execute(new Runnable() {
                @Override
                public void run() {
                    RecipeGenerator();
                }
            });
        }
    }
    public void RecipeGenerator() {
        URL url;
        try {
            url = new URL(url_API);
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


        Meal temp_meal;
        try {
            InputStream input = new BufferedInputStream(urlConnection.getInputStream());
            JsonReader reader = new JsonReader(new InputStreamReader(input, StandardCharsets.UTF_8));
            reader.beginObject();
            String name = reader.nextName();
            reader.beginArray();
            temp_meal = new Meal();
            while (reader.hasNext()) {
                reader.beginObject();

                ArrayList<String> ingredient_name = new ArrayList<>();
                ArrayList<String> ingredient_measure = new ArrayList<>();

                while (reader.hasNext()) {
                    name = reader.nextName();
                    //Log.d(TAG, name + " " + reader.peek().toString());
                    if (name.equals("idMeal")) {
                        temp_meal.setIdMeal(reader.nextString());
                    } else if (name.equals("strMeal")) {
                        temp_meal.setStrMeal(reader.nextString());
                    } else if (name.equals("strArea") && reader.peek() != JsonToken.NULL) {
                        temp_meal.setStrArea(reader.nextString());
                    } else if (name.equals("strCategory") && reader.peek() != JsonToken.NULL) {
                        temp_meal.setStrCategory(reader.nextString());
                    } else if (name.equals("strInstructions") && reader.peek() != JsonToken.NULL) {
                        temp_meal.setStrInstructions(reader.nextString());
                    } else if (name.equals("strMealThumb") && reader.peek() != JsonToken.NULL) {
                        temp_meal.setStrMealThumb(reader.nextString());
                    } else if (name.equals("strTags") && reader.peek() != JsonToken.NULL) {
                        temp_meal.setStrTags(reader.nextString());
                    } else if (name.equals("strYoutube") && reader.peek() != JsonToken.NULL) {
                        temp_meal.setStrYoutube(reader.nextString());
                    } else if (name.matches("strIngredient(.*)") && (reader.peek() != JsonToken.NULL)) {
                        ingredient_name.add(reader.nextString());
                    } else if (name.matches("strMeasure(.*)") && (reader.peek() != JsonToken.NULL)) {
                        ingredient_measure.add(reader.nextString());
                    } else {
                        reader.skipValue();
                    }
                }

                HashMap<String, String> ingredients = new LinkedHashMap<>();
                ingredient_name.forEach((ingr_name) -> {
                    if (!ingr_name.isEmpty()) {
                        ingredients.put(ingr_name, ingredient_measure.get(ingredient_name.indexOf(ingr_name)));
                    }
                });

                temp_meal.setStrIngredient(ingredients);
                reader.endObject();
            }
            reader.endArray();
            reader.endObject();


            textview_strMeal.setText(temp_meal.getStrMeal());
            textview_strIngredients.setText(temp_meal.getStrIngredient_formatted());
            execute(new Runnable() {
                @Override
                public void run() {
                    imageview_strMealThumb.setImageBitmap(getImageBitmap(temp_meal.getStrThumb()));
                }
            });
            textview_strInstructions.setText(temp_meal.getStrInstructions());


        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            urlConnection.disconnect();
        }

    }

    private Bitmap getImageBitmap(String url) {

        Bitmap bm = null;
        HttpURLConnection conn =null;
        try {
            URL aURL = new URL(url);
            conn = (HttpURLConnection) aURL.openConnection();
            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            bm = BitmapFactory.decodeStream(bis);
            int nh = (int) ( bm.getHeight() * (512.0 / bm.getWidth()) );
            bm = Bitmap.createScaledBitmap(bm, 512, nh, true);
            bis.close();
            is.close();
        } catch (IOException e) {
            Log.e(TAG, "Error getting bitmap", e);
        } finally {

            conn.disconnect();

        }
        return bm;
    }

    @Override
    public void execute(Runnable command) {
        Thread thread =new Thread(command);
        try {
            thread.start();
            thread.join();
        }catch(IllegalThreadStateException ex) {
            Log.d(TAG,ex.toString());// restore interrupt state
        }
        catch ( InterruptedException ex){
            Log.d(TAG,ex.toString());
        }
    }

}